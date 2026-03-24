# SocialBridge-telegram
## It is social platform connector to [telegram](https://telegram.org/) for [SocialBridge](https://github.com/KosyakMakc/social-bridge) minecraft plugin

### this connector provide commands for setup bot and check his health

### Commands for minecraft:

| Command literal      | Permission node                  | Description                                                                          |
|----------------------|----------------------------------|--------------------------------------------------------------------------------------|
| /telegram setupToken | SocialBridge.telegram.setToken   | Save new token to SocialBridge configuration and reconnect bot with new token |
| /telegram status     | SocialBridge.telegram.status     | Provide information about current connection bot to telegram |
| /telegram getBotLink | SocialBridge.telegram.getBotLink | Get direct link to this bot if token exist and bot connected |
| /telegram setupProxy | SocialBridge.telegram.setProxy   | Save new proxy config to SocialBridge configuration and reconnect bot with new proxy (type://username:password@host:port, where type is http or socks) |

### Commands for social platforms:

| Command literal      | Description                                                                   |
|----------------------|-------------------------------------------------------------------------------|
| /telegram_heartbeat  | Check activity bot in Telegram |

## API for developers

### You can connect API of this module for your purposes
```
repositories {
    maven {
        name = "gitea"
        url = "https://git.kosyakmakc.ru/api/packages/kosyakmakc/maven"
    }
}
dependencies {
    compileOnly "io.github.kosyakmakc:SocialBridge-telegram:0.10.+"
}
```

### via `ISocialBridge.getSocialPlatform(TelegramModule.class)` you can access built-in module and use API (Recommended)
### via `ISocialBridge.getSocialPlatform(TelegramPlatform.class)` you can access this connector and use telegram-specific API
