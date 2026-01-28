package io.github.kosyakmakc.socialBridgeTelegram.MinecraftCommands;

import java.util.HashMap;
import java.util.List;

import io.github.kosyakmakc.socialBridge.Commands.MinecraftCommands.MinecraftCommandBase;
import io.github.kosyakmakc.socialBridge.Commands.MinecraftCommands.MinecraftCommandExecutionContext;
import io.github.kosyakmakc.socialBridge.Utils.MessageKey;
import io.github.kosyakmakc.socialBridgeTelegram.TelegramModule;
import io.github.kosyakmakc.socialBridgeTelegram.TelegramPlatform;
import io.github.kosyakmakc.socialBridgeTelegram.Utils.TelegramMessageKey;
import io.github.kosyakmakc.socialBridgeTelegram.Utils.TelegramPermissions;

public class Status extends MinecraftCommandBase {

    public Status() {
        super("status", TelegramMessageKey.BOT_STATUS_DESCRIPTION, TelegramPermissions.CAN_STATUS);
    }

    @Override
    public void execute(MinecraftCommandExecutionContext ctx, List<Object> args) {
        var sender = ctx.getSender();
        var platform = getBridge().getSocialPlatform(TelegramPlatform.class);
        MessageKey messageKey;
        switch (platform.getBotState()) {
            case Started:
                messageKey = TelegramMessageKey.BOT_STATUS_CONNECTED;
                break;
            case Starting:
                messageKey = TelegramMessageKey.BOT_STATUS_CONNECTING;
                break;
            case Stopped:
                messageKey = TelegramMessageKey.BOT_STATUS_STOPPED;
                break;
            case Stopping:
                messageKey = TelegramMessageKey.BOT_STATUS_STOPPING;
                break;
            default:
                throw new RuntimeException("Unexpected telegram bot state");

        }
        getBridge()
            .getLocalizationService()
            .getMessage(getBridge().getModule(TelegramModule.class), sender.getLocale(), messageKey, null)
            .thenAccept(msgTemplate -> sender.sendMessage(msgTemplate, new HashMap<String, String>()));
    }

}
