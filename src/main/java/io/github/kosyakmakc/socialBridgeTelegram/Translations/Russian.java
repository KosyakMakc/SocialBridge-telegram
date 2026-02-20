package io.github.kosyakmakc.socialBridgeTelegram.Translations;

import java.util.List;

import io.github.kosyakmakc.socialBridge.DatabasePlatform.DefaultTranslations.ITranslationSource;
import io.github.kosyakmakc.socialBridge.DatabasePlatform.DefaultTranslations.LocalizationRecord;
import io.github.kosyakmakc.socialBridgeTelegram.Utils.TelegramMessageKey;

public class Russian implements ITranslationSource {
    @Override
    public String getLanguage() {
        return "ru";
    }

    @Override
    public List<LocalizationRecord> getRecords() {
        return List.of(
                new LocalizationRecord(TelegramMessageKey.SET_TOKEN_DESCRIPTION.key(), "Установить новый токен боту телеграмма, Если бот подключен, то он будет перезагружен с новым токеном."),
                new LocalizationRecord(TelegramMessageKey.SET_TOKEN_SUCCESS.key(), "<green>Новый токен сохранен и применен.</green>"),
                new LocalizationRecord(TelegramMessageKey.SET_TOKEN_FAILED_CONFIG.key(), "<red>Не удалось сохранить токен в сервисе конфигураций.</red>"),
                new LocalizationRecord(TelegramMessageKey.SET_TOKEN_FAILED_STOP_BOT.key(), "<red>Не удалось остановить бота телеграмма, новый токен сохранен, но не был применен.</red>"),
                new LocalizationRecord(TelegramMessageKey.SET_TOKEN_FAILED_START_BOT.key(), "<red>Не удалось запустить бота телеграмма, новый токен сохранен, но не был применен.</red>"),

                new LocalizationRecord(TelegramMessageKey.BOT_STATUS_DESCRIPTION.key(), "Узнать статус подключения бота телеграмма."),
                new LocalizationRecord(TelegramMessageKey.BOT_STATUS_CONNECTING.key(), "<yellow>Бот телеграмма подключается...</yellow>"),
                new LocalizationRecord(TelegramMessageKey.BOT_STATUS_CONNECTED.key(), "<green>Бот телеграмма успешно подключен.</green>"),
                new LocalizationRecord(TelegramMessageKey.BOT_STATUS_STOPPING.key(), "<yellow>Бот телеграмма останавливается...</yellow>"),
                new LocalizationRecord(TelegramMessageKey.BOT_STATUS_STOPPED.key(), "<red>Бот телеграмма остановлен.</red>")
        );
    }
}
