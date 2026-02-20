package io.github.kosyakmakc.socialBridgeTelegram;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

import org.telegram.telegrambots.meta.api.objects.message.Message;

import io.github.kosyakmakc.socialBridge.ITransaction;
import io.github.kosyakmakc.socialBridge.SocialPlatforms.ISocialAttachment;
import io.github.kosyakmakc.socialBridge.SocialPlatforms.ISocialMessage;
import io.github.kosyakmakc.socialBridge.SocialPlatforms.ISocialPlatform;
import io.github.kosyakmakc.socialBridge.SocialPlatforms.Identifier;
import io.github.kosyakmakc.socialBridge.SocialPlatforms.IdentifierType;
import io.github.kosyakmakc.socialBridge.SocialPlatforms.SocialUser;
import io.github.kosyakmakc.socialBridge.Utils.MessageKey;
import io.github.kosyakmakc.socialBridgeTelegram.DatabaseTables.TelegramUserTable;

public class TelegramSocialMessage implements ISocialMessage {
    private final TelegramPlatform socialPlatform;
    private final Message telegramMessage;
    private final LinkedList<ISocialAttachment> attachments = new LinkedList<>();
    private CompletableFuture<SocialUser> socialUserLoading;

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
    public CompletableFuture<SocialUser> getAuthor() {
        if (socialUserLoading == null) {
            socialUserLoading = loadSocialUser();
        }
        return socialUserLoading;
    }

    @Override
    public Identifier getChannelId() {
        var chatId = telegramMessage.getChatId();
        // var topicId = telegramMessage.getMessageThreadId();
        return new Identifier(IdentifierType.Long, chatId); //  + '/' + topicId
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

    private CompletableFuture<SocialUser> loadSocialUser() {
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
                    var isChanged = telegramUser.tryActualize(tgUser);
                    if (isChanged) {
                        socialPlatform.getLogger().info("telegram user info updated (id " + longId + " - " + telegramUser.getName() + ")");
                    }
                }

                return socialUser;
            });
    }

    @Override
    public CompletableFuture<Boolean> sendReply(String messageTemplate, HashMap<String, String> placeholders) {
        return socialPlatform.sendReply(this, messageTemplate, placeholders);
    }

    @Override
    public CompletableFuture<Boolean> sendReply(MessageKey message, String locale, HashMap<String, String> placeholders, ITransaction transaction) {
        return socialPlatform
            .getBridge()
            .getLocalizationService()
            .getMessage(locale, message, transaction)
            .thenCompose(messageTemplate -> sendReply(messageTemplate, placeholders));
    }

    @Override
    public ISocialPlatform getSocialPlatform() {
        return socialPlatform;
    }
}
