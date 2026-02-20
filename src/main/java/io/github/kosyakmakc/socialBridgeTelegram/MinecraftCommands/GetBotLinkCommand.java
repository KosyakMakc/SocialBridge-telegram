package io.github.kosyakmakc.socialBridgeTelegram.MinecraftCommands;

import java.util.HashMap;
import java.util.List;

import io.github.kosyakmakc.socialBridge.Commands.MinecraftCommands.MinecraftCommandBase;
import io.github.kosyakmakc.socialBridge.Commands.MinecraftCommands.MinecraftCommandExecutionContext;
import io.github.kosyakmakc.socialBridge.Utils.MessageKey;
import io.github.kosyakmakc.socialBridgeTelegram.BotState;
import io.github.kosyakmakc.socialBridgeTelegram.TelegramPlatform;
import io.github.kosyakmakc.socialBridgeTelegram.Utils.TelegramMessageKey;
import io.github.kosyakmakc.socialBridgeTelegram.Utils.TelegramPermissions;

public class GetBotLinkCommand extends MinecraftCommandBase {

    public GetBotLinkCommand() {
        super("getBotLink", MessageKey.EMPTY, TelegramPermissions.CAN_GET_BOT_LINK);
    }

    @Override
    public void execute(MinecraftCommandExecutionContext ctx, List<Object> args) {
        var bridge = getBridge();
        var platform = bridge.getSocialPlatform(TelegramPlatform.class);
        if (platform.getBotState() == BotState.Started) {
            var botName = platform.getBotName();

            var url = "https://t.me/" + botName;
            var text = "@" + botName;

            var template = "<#7878ff><underlined><hover:show_text:'<green>" + url + "'><click:open_url:'" + url + "'>" + text;
            ctx.getSender().sendMessage(template, new HashMap<>());
        }
        else {
            var locale = ctx.getSender().getLocale();
            ctx.getSender().sendMessage(TelegramMessageKey.BOT_STATUS_STOPPED, locale, new HashMap<>(), null);
        }
    }

}
