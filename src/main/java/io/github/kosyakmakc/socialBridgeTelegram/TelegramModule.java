package io.github.kosyakmakc.socialBridgeTelegram;

import java.util.UUID;

import io.github.kosyakmakc.socialBridge.MinecraftPlatform.IModuleLoader;
import io.github.kosyakmakc.socialBridge.Modules.SocialModule;
import io.github.kosyakmakc.socialBridge.Utils.Version;
import io.github.kosyakmakc.socialBridgeTelegram.MinecraftCommands.GetBotLinkCommand;
import io.github.kosyakmakc.socialBridgeTelegram.MinecraftCommands.SetProxyCommand;
import io.github.kosyakmakc.socialBridgeTelegram.MinecraftCommands.SetTokenCommand;
import io.github.kosyakmakc.socialBridgeTelegram.MinecraftCommands.StatusCommand;
import io.github.kosyakmakc.socialBridgeTelegram.SocialCommands.HeartbeatCommand;
import io.github.kosyakmakc.socialBridgeTelegram.Translations.English;
import io.github.kosyakmakc.socialBridgeTelegram.Translations.Russian;

public class TelegramModule extends SocialModule {
    public static UUID MODULE_ID = UUID.fromString("f7e27e90-3e6c-4331-990f-1977b8a5481a");
    private static final Version compabilityVersion = new Version("0.10.1");
    private static final String ModuleName = "telegram";

    public TelegramModule(IModuleLoader loader, Version version) {
        super(loader, compabilityVersion, version, MODULE_ID, ModuleName);

        addMinecraftCommand(new SetTokenCommand());
        addMinecraftCommand(new SetProxyCommand());
        addMinecraftCommand(new StatusCommand());
        addMinecraftCommand(new GetBotLinkCommand());

        addSocialCommand(new HeartbeatCommand());

        addTranslationSource(new English());
        addTranslationSource(new Russian());
    }
}
