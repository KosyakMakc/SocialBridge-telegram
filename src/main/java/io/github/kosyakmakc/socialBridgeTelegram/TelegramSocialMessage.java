package io.github.kosyakmakc.socialBridgeTelegram;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

import org.telegram.telegrambots.meta.api.objects.message.Message;

import io.github.kosyakmakc.socialBridge.SocialPlatforms.ISocialAttachment;
import io.github.kosyakmakc.socialBridge.SocialPlatforms.ISocialMessage;
import io.github.kosyakmakc.socialBridge.SocialPlatforms.Identifier;
import io.github.kosyakmakc.socialBridge.SocialPlatforms.IdentifierType;
import io.github.kosyakmakc.socialBridge.SocialPlatforms.SocialUser;
import io.github.kosyakmakc.socialBridgeTelegram.DatabaseTables.TelegramUserTable;

public class TelegramSocialMessage implements ISocialMessage {
    private final TelegramPlatform socialPlatform;
    private final Message telegramMessage;
    private final LinkedList<ISocialAttachment> attachments = new LinkedList<>();
    private SocialUser socialUser;
    private boolean socialUserLoaded = false;

    public TelegramSocialMessage(TelegramPlatform socialPlatform, Message telegramMessage) {
        this.socialPlatform = socialPlatform;
        this.telegramMessage = telegramMessage;

        var replyMessage = telegramMessage.getReplyToMessage();
        if (replyMessage != null) {
            attachments.add(new TelegramSocialAttachmentReply(socialPlatform, replyMessage));
        }
    }

    @Override
    public Collection<ISocialAttachment> getAttachments() {
        return attachments;
    }

    @Override
    public SocialUser getAuthor() {
        if (!socialUserLoaded) {
            loadSocialUser().join();
        }
        return socialUser;
    }

    @Override
    public Identifier getChannelId() {
        var chatId = telegramMessage.getChatId();
        var topicId = telegramMessage.getMessageThreadId();
        return new Identifier(IdentifierType.String, chatId + '/' + topicId);
    }

    @Override
    public Identifier getId() {
        return new Identifier(IdentifierType.Integer, telegramMessage.getMessageId());
    }
    
    public int getTelegramMessageId() {
        return telegramMessage.getMessageId();
    }

    @Override
    public String getStringMessage() {
        return telegramMessage.getText();
    }

    private CompletableFuture<Void> loadSocialUser() {
        var tgUser = telegramMessage.getFrom();
        var longId = tgUser.getId();
        var identifier = new Identifier(IdentifierType.Long, longId);
        return socialPlatform.tryGetUser(identifier, null)
            .thenApply(socialUser -> {
                if (socialUser == null) {
                    var dbUser = new TelegramUserTable(longId, tgUser.getUserName(), tgUser.getFirstName(), tgUser.getLastName(), tgUser.getLanguageCode());
                    socialUser = new TelegramUser(socialPlatform, dbUser);

                    // non-blocking save user in background
                    socialPlatform.getBridge().doTransaction(transaction -> {
                        var databaseContext = transaction.getDatabaseContext();

                        var table = databaseContext.getDaoTable(TelegramUserTable.class);
                        try {
                            table.createIfNotExists(dbUser);
                            return CompletableFuture.completedFuture(true);
                        } catch (SQLException e) {
                            e.printStackTrace();
                            return CompletableFuture.completedFuture(false);
                        }
                    });
                }
                return socialUser;
            })
            .thenApply(socialUser -> {
                if (socialUser instanceof TelegramUser telegramUser) {
                    // telegramUser.setLastMessage(tgMessage);

                    var isChanged = telegramUser.tryActualize(tgUser);
                    if (isChanged) {
                        socialPlatform.getLogger().info("telegram user info updated (id " + longId + " - " + telegramUser.getName() + ")");
                    }
                }

                return socialUser;
            })
            .thenAccept(socialUser -> {
                this.socialUser = socialUser;
                this.socialUserLoaded = true;
            });
    }

    @Override
    public CompletableFuture<Boolean> sendReply(String messageTemplate, HashMap<String, String> placeholders) {
        return socialPlatform.sendReply(this, messageTemplate, placeholders);
    }
}
