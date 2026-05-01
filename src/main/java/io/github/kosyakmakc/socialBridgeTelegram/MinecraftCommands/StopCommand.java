package io.github.kosyakmakc.socialBridgeTelegram.MinecraftCommands;

import java.util.HashMap;
import java.util.List;

import io.github.kosyakmakc.socialBridge.Commands.MinecraftCommands.MinecraftCommandBase;
import io.github.kosyakmakc.socialBridge.Commands.MinecraftCommands.MinecraftCommandExecutionContext;
import io.github.kosyakmakc.socialBridgeTelegram.TelegramPlatform;
import io.github.kosyakmakc.socialBridgeTelegram.Utils.TelegramMessageKey;
import io.github.kosyakmakc.socialBridgeTelegram.Utils.TelegramPermissions;

public class StopCommand extends MinecraftCommandBase {

    public StopCommand() {
        super("stop", TelegramMessageKey.BOT_STOP_COMMAND_DESCRIPTION, TelegramPermissions.CAN_STOP_BOT);
    }

    @Override
    public void execute(MinecraftCommandExecutionContext ctx, List<Object> args) {
        var sender = ctx.getSender();
        var platform = getBridge().getSocialPlatform(TelegramPlatform.class);

        platform.stopBot()
            .thenAccept(isSuccess -> {
                var placeholders = new HashMap<String, String>();
                if (isSuccess) {
                    sender.sendMessage(TelegramMessageKey.BOT_STOP_COMMAND_SUCCESS, placeholders, null);
                }
                else {
                    sender.sendMessage(TelegramMessageKey.BOT_STOP_COMMAND_FAILED, placeholders, null);
                }
            });
    }
}
