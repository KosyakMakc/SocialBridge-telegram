package io.github.kosyakmakc.socialBridgeTelegram;
import io.github.kosyakmakc.socialBridge.ITransaction;
import io.github.kosyakmakc.socialBridge.DatabasePlatform.LocalizationService;
import io.github.kosyakmakc.socialBridge.SocialPlatforms.Identifier;
import io.github.kosyakmakc.socialBridge.SocialPlatforms.IdentifierType;
import io.github.kosyakmakc.socialBridge.SocialPlatforms.SocialUser;
import io.github.kosyakmakc.socialBridge.Utils.MessageKey;
import io.github.kosyakmakc.socialBridgeTelegram.DatabaseTables.TelegramUserTable;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import org.telegram.telegrambots.meta.api.objects.User;

public class TelegramUser extends SocialUser implements Comparable<TelegramUser> {
    private final TelegramUserTable userRecord;
    private final Identifier id;

    public TelegramUser(TelegramPlatform socialPlatform, TelegramUserTable userRecord) {
        super(socialPlatform);
        this.userRecord = userRecord;
        this.id = new Identifier(IdentifierType.Long, userRecord.getId());
    }

    @Override
    public String getName() {
        var lastname = userRecord.getLastName();
        if (lastname == null) {
            return userRecord.getFirstName();
        }
        else {
            return userRecord.getFirstName() + ' ' + userRecord.getLastName();
        }
    }

    @Override
    public CompletableFuture<Boolean> sendMessage(String message, HashMap<String, String> placeholders) {
        return ((TelegramPlatform) getPlatform()).sendMessage(this, message, placeholders);
    }

    @Override
    public CompletableFuture<Boolean> sendMessage(MessageKey message, String locale, HashMap<String, String> placeholders, ITransaction transaction) {
        return getPlatform()
            .getBridge()
            .getLocalizationService()
            .getMessage(locale, message, transaction)
            .thenCompose(messageTemplate -> sendMessage(messageTemplate, placeholders));
    }

    @Override
    public String getLocale() {
        var userLocale = userRecord.getLocalization();
        return userLocale == null ? LocalizationService.defaultLocale : userLocale;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public int compareTo(TelegramUser anotherUser) {
        var delta = (long) this.getId().value() - (long) anotherUser.getId().value();
        return delta > 0
            ? 1
            : delta < 0
                ? -1
                : 0;
    }

    public TelegramUserTable getUserRecord() {
        return userRecord;
    }

    boolean isNullOrBlank (String str) {
        return str == null || str.isBlank();
    }

    public boolean tryActualize(User tgUser) {
        var changed = false;

        if (!isNullOrBlank(tgUser.getUserName())
         && !tgUser.getUserName().equals(userRecord.getUsername())) {
            userRecord.setUsername(tgUser.getUserName());
            changed = true;
        }
        if (!isNullOrBlank(tgUser.getFirstName())
         && !tgUser.getFirstName().equals(userRecord.getFirstName())) {
            userRecord.setFirstName(tgUser.getFirstName());
            changed = true;
        }
        if (!isNullOrBlank(tgUser.getLastName())
         && !tgUser.getLastName().equals(userRecord.getLastName())) {
            userRecord.setLastName(tgUser.getLastName());
            changed = true;
        }
        if (!isNullOrBlank(tgUser.getLanguageCode())
         && !tgUser.getLanguageCode().equals(userRecord.getLocalization())) {
            userRecord.setLocalization(tgUser.getLanguageCode());
            changed = true;
        }

        if (changed) {
            userRecord.setUpdatedAt(Date.from(Instant.now()));
            
            // non-blocking save user in background
            ((TelegramPlatform) getPlatform()).getBridge().doTransaction(transaction -> {
                var databaseContext = transaction.getDatabaseContext();

                var table = databaseContext.getDaoTable(TelegramUserTable.class);
                try {
                    table.update(userRecord);
                    return CompletableFuture.completedFuture(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                    return CompletableFuture.completedFuture(false);
                }
            });
        }

        return changed;
    }
}
