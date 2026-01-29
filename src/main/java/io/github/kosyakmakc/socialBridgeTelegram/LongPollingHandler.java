package io.github.kosyakmakc.socialBridgeTelegram;
import io.github.kosyakmakc.socialBridge.Commands.Arguments.ArgumentFormatException;
import io.github.kosyakmakc.socialBridge.Modules.ISocialModule;
import java.util.HashMap;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

public class LongPollingHandler implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramPlatform socialPlatform;

    private String botUsername;

    public LongPollingHandler(TelegramPlatform telegramPlatform) {
        this.socialPlatform = telegramPlatform;
    }

    @Override
    public void consume(Update update) {
        var tgMessage = update.getMessage();
        if (tgMessage == null) {
            return;
        }

        
        var message = update.getMessage().getText();
        
        if (message == null || message.isBlank()) {
            return;
        }

        // Commands handling
        if (tryCommandHandle(update)) {
            return;
        }
        
        // TODO Messages handling in future
        // var mcPlayer = socialUser.getMinecraftUser();

        // if (mcPlayer != null) {
        //     chatEvent.getPlaceholders().addPlain(new Pair<>("authBridge-minecraftName", mcPlayer.getName()));
        // }
    }

    private boolean tryCommandHandle(Update chatEvent) {
        var textMessage = chatEvent.getMessage().getText();
        if (textMessage.charAt(0) != '/') {
            return false;
        }

        var socialMessage = new TelegramSocialMessage(socialPlatform, chatEvent.getMessage());
        var commandCtx = new TelegramSocialCommandExecutionContext(socialMessage, botUsername);

        try {
            for (var module : socialPlatform.getBridge().getModules()) {

                if (!(module instanceof ISocialModule socialModule)) {
                    continue;
                }

                if (!commandCtx.getModuleName().equals(module.getName())) {
                    continue;
                }

                for (var socialCommand : socialModule.getSocialCommands()) {
                    if (commandCtx.getCommandLiteral().equals(socialCommand.getLiteral())) {
                        socialCommand.handle(commandCtx);
                        return true;
                    }
                }
            }
        } catch (ArgumentFormatException e) {
            e.logTo(socialPlatform.getBridge().getLogger());
            socialPlatform.sendMessage(commandCtx.getSender(), e.getMessage(), new HashMap<String, String>());
            return true;
        }
        return false;
    }

    public void setBotUsername(String userName) {
        botUsername = userName;
    }
}
