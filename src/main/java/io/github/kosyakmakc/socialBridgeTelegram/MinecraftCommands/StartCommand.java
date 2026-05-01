package io.github.kosyakmakc.socialBridgeTelegram.MinecraftCommands;

import java.util.HashMap;
import java.util.List;

import io.github.kosyakmakc.socialBridge.Commands.MinecraftCommands.MinecraftCommandBase;
import io.github.kosyakmakc.socialBridge.Commands.MinecraftCommands.MinecraftCommandExecutionContext;
import io.github.kosyakmakc.socialBridgeTelegram.TelegramPlatform;
import io.github.kosyakmakc.socialBridgeTelegram.Utils.TelegramMessageKey;
import io.github.kosyakmakc.socialBridgeTelegram.Utils.TelegramPermissions;

public class StartCommand extends MinecraftCommandBase {

    public StartCommand() {
        super("start", TelegramMessageKey.BOT_START_COMMAND_DESCRIPTION, TelegramPermissions.CAN_START_BOT);
    }

    @Override
    public void execute(MinecraftCommandExecutionContext ctx, List<Object> args) {
        var sender = ctx.getSender();
        var platform = getBridge().getSocialPlatform(TelegramPlatform.class);

        platform.startBot()
            .thenAccept(isSuccess -> {
                var placeholders = new HashMap<String, String>();
                if (isSuccess) {
                    sender.sendMessage(TelegramMessageKey.BOT_START_COMMAND_SUCCESS, placeholders, null);
                }
                else {
                    sender.sendMessage(TelegramMessageKey.BOT_START_COMMAND_FAILED, placeholders, null);
                }
            });
    }
}
