package io.github.kosyakmakc.socialBridgeTelegram;

import org.telegram.telegrambots.meta.api.objects.message.Message;

import io.github.kosyakmakc.socialBridge.SocialPlatforms.ISocialAttachmentReply;
import io.github.kosyakmakc.socialBridge.SocialPlatforms.ISocialMessage;

public class TelegramSocialAttachmentReply implements ISocialAttachmentReply {
    private final Message replyMessage;
    private final TelegramPlatform socialPlatform;

    public TelegramSocialAttachmentReply(TelegramPlatform socialPlatform, Message replyMessage) {
        this.socialPlatform = socialPlatform;
        this.replyMessage = replyMessage;
    }

    @Override
    public ISocialMessage getReply() {
        return new TelegramSocialMessage(socialPlatform, replyMessage);
    }

}
