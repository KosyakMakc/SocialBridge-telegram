package io.github.kosyakmakc.socialBridgeTelegram.Utils;

import io.github.kosyakmakc.socialBridge.Utils.MessageKey;
import io.github.kosyakmakc.socialBridgeTelegram.TelegramModule;

public class TelegramMessageKey {
    public static final MessageKey SET_TOKEN_DESCRIPTION = new MessageKey(TelegramModule.MODULE_ID, "set_token_description");
    public static final MessageKey SET_TOKEN_SUCCESS = new MessageKey(TelegramModule.MODULE_ID, "set_token_success");
    public static final MessageKey SET_TOKEN_FAILED_CONFIG = new MessageKey(TelegramModule.MODULE_ID, "set_token_failed_config");
    public static final MessageKey SET_TOKEN_FAILED_STOP_BOT = new MessageKey(TelegramModule.MODULE_ID, "set_token_failed_stop_bot");
    public static final MessageKey SET_TOKEN_FAILED_START_BOT = new MessageKey(TelegramModule.MODULE_ID, "set_token_failed_start_bot");

    public static final MessageKey BOT_STATUS_DESCRIPTION = new MessageKey(TelegramModule.MODULE_ID, "bot_status_description");
    public static final MessageKey BOT_STATUS_CONNECTING = new MessageKey(TelegramModule.MODULE_ID, "bot_status_connecting");
    public static final MessageKey BOT_STATUS_CONNECTED = new MessageKey(TelegramModule.MODULE_ID, "bot_status_connected");
    public static final MessageKey BOT_STATUS_STOPPING = new MessageKey(TelegramModule.MODULE_ID, "bot_status_stopping");
    public static final MessageKey BOT_STATUS_STOPPED = new MessageKey(TelegramModule.MODULE_ID, "bot_status_stopped");

    // public static final MessageKey BOT_STARTED = new MessageKey(TelegramModule.MODULE_ID, "bot_started");
    // public static final MessageKey BOT_STOPPED = new MessageKey(TelegramModule.MODULE_ID, "bot_stopped");
}
