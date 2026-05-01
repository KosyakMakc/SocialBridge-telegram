package io.github.kosyakmakc.socialBridgeTelegram.Translations;

import io.github.kosyakmakc.socialBridgeTelegram.Utils.TelegramMessageKey;
import io.github.kosyakmakc.socialBridge.DatabasePlatform.DefaultTranslations.ITranslationSource;
import io.github.kosyakmakc.socialBridge.DatabasePlatform.DefaultTranslations.LocalizationRecord;
import io.github.kosyakmakc.socialBridge.DatabasePlatform.LocalizationService;

import java.util.List;

public class English implements ITranslationSource {
    @Override
    public String getLanguage() {
        return LocalizationService.defaultLocale;
    }

    @Override
    public List<LocalizationRecord> getRecords() {
        return List.of(
                new LocalizationRecord(TelegramMessageKey.SET_TOKEN_DESCRIPTION.key(), "Setup new token of Telegram bot, existed bot also will be reloaded with new token"),
                new LocalizationRecord(TelegramMessageKey.SET_TOKEN_SUCCESS.key(), "<green>New token saved and applied.</green>"),
                new LocalizationRecord(TelegramMessageKey.SET_TOKEN_FAILED_CONFIG.key(), "<red>Failed to save token to configuration service.</red>"),

                new LocalizationRecord(TelegramMessageKey.SET_TOKEN_FAILED_STOP_BOT.key(), "<red>Failed to stop telegram bot, please try again later.</red>"),
                new LocalizationRecord(TelegramMessageKey.SET_TOKEN_FAILED_START_BOT.key(), "<red>Failed to start telegram bot, please try again later.</red>"),

                new LocalizationRecord(TelegramMessageKey.SET_PROXY_DESCRIPTION.key(), "Setup new proxy config to Telegram bot, existed bot also will be reloaded with new proxy config"),
                new LocalizationRecord(TelegramMessageKey.SET_PROXY_SUCCESS.key(), "<green>New proxy config saved and applied.</green>"),
                new LocalizationRecord(TelegramMessageKey.SET_PROXY_FAILED_CONFIG.key(), "<red>Failed to save proxy config to configuration service.</red>"),

                new LocalizationRecord(TelegramMessageKey.BOT_STATUS_DESCRIPTION.key(), "View status of Telegram bot connection"),
                new LocalizationRecord(TelegramMessageKey.BOT_STATUS_CONNECTING.key(), "<yellow>Telegram bot are connecting...</yellow>"),
                new LocalizationRecord(TelegramMessageKey.BOT_STATUS_CONNECTED.key(), "<green>Telegram bot successfully connected.</green>"),
                new LocalizationRecord(TelegramMessageKey.BOT_STATUS_STOPPING.key(), "<yellow>Telegram bot are stopping...</yellow>"),
                new LocalizationRecord(TelegramMessageKey.BOT_STATUS_STOPPED.key(), "<red>Telegram bot stopped.</red>"),

                new LocalizationRecord(TelegramMessageKey.BOT_START_COMMAND_DESCRIPTION.key(), "Start telegram bot."),
                new LocalizationRecord(TelegramMessageKey.BOT_START_COMMAND_SUCCESS.key(), "<green>Telegram bot started and connected.</green>"),
                new LocalizationRecord(TelegramMessageKey.BOT_START_COMMAND_FAILED.key(), "<red>Failed to start telegram bot.</red>"),

                new LocalizationRecord(TelegramMessageKey.BOT_STOP_COMMAND_DESCRIPTION.key(), "Stop telegram bot."),
                new LocalizationRecord(TelegramMessageKey.BOT_STOP_COMMAND_SUCCESS.key(), "<yellow>Telegram bot stopped and disconnected.</red>"),
                new LocalizationRecord(TelegramMessageKey.BOT_STOP_COMMAND_FAILED.key(), "<red>Failed to stop telegram bot, but signal to stop was sent, check status later.</red>")
            );
    }
}
