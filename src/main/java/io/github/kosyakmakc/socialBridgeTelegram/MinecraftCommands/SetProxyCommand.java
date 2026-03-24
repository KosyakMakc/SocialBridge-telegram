package io.github.kosyakmakc.socialBridgeTelegram.MinecraftCommands;

import java.util.HashMap;
import java.util.List;

import io.github.kosyakmakc.socialBridge.Commands.Arguments.CommandArgument;
import io.github.kosyakmakc.socialBridge.Commands.MinecraftCommands.MinecraftCommandBase;
import io.github.kosyakmakc.socialBridge.Commands.MinecraftCommands.MinecraftCommandExecutionContext;
import io.github.kosyakmakc.socialBridgeTelegram.TelegramPlatform;
import io.github.kosyakmakc.socialBridgeTelegram.Utils.ProxyDefinition;
import io.github.kosyakmakc.socialBridgeTelegram.Utils.TelegramMessageKey;
import io.github.kosyakmakc.socialBridgeTelegram.Utils.TelegramPermissions;
import io.github.kosyakmakc.socialBridgeTelegram.Utils.TranslationException;

public class SetProxyCommand extends MinecraftCommandBase {

    public SetProxyCommand() {
        super("setupProxy",
        TelegramMessageKey.SET_PROXY_DESCRIPTION,
        TelegramPermissions.CAN_SET_PROXY,
        List.of(CommandArgument.ofGreedyString("Proxy definition")));
    }

    @Override
    public void execute(MinecraftCommandExecutionContext ctx, List<Object> parameters) {
        var sender = ctx.getSender();

        var rawProxyDefinition = (String) parameters.get(0);
        var placeholders = new HashMap<String, String>();
        try {
            var proxy = new ProxyDefinition(rawProxyDefinition);
            
            var setupTask = this.getBridge().getSocialPlatform(TelegramPlatform.class).setupProxy(proxy, null);
            
            setupTask.thenCompose(isSuccess -> sender.sendMessage(TelegramMessageKey.SET_PROXY_SUCCESS, sender.getLocale(), placeholders, null));
            
            setupTask
            .exceptionally(err -> {
                if (err instanceof TranslationException translationException) {
                    sender.sendMessage(translationException.getMessageKey(), sender.getLocale(), placeholders, null);
                } else {
                    sender.sendMessage(err.getMessage(), placeholders);
                }
                
                return true; // not used, just for close signature of lambda
            });
        }
        catch (NumberFormatException e) {
            sender.sendMessage("please provide valid proxy definition (type://username:password@hostname:port)", placeholders);
        }
    }

}
