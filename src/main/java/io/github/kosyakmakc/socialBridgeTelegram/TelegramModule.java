package io.github.kosyakmakc.socialBridgeTelegram;

import java.util.UUID;

import io.github.kosyakmakc.socialBridge.MinecraftPlatform.IModuleLoader;
import io.github.kosyakmakc.socialBridge.Modules.SocialModule;
import io.github.kosyakmakc.socialBridge.Utils.Version;
import io.github.kosyakmakc.socialBridgeTelegram.MinecraftCommands.SetToken;
import io.github.kosyakmakc.socialBridgeTelegram.MinecraftCommands.Status;
import io.github.kosyakmakc.socialBridgeTelegram.Translations.English;
import io.github.kosyakmakc.socialBridgeTelegram.Translations.Russian;

public class TelegramModule extends SocialModule {
    public static UUID MODULE_ID = UUID.fromString("f7e27e90-3e6c-4331-990f-1977b8a5481a");
    private static final Version compabilityVersion = new Version("0.9.1");
    private static final String ModuleName = "telegram";

    public TelegramModule(IModuleLoader loader) {
        super(loader, compabilityVersion, MODULE_ID, ModuleName);

        addMinecraftCommand(new SetToken());
        addMinecraftCommand(new Status());

        addTranslationSource(new English());
        addTranslationSource(new Russian());
    }
}
