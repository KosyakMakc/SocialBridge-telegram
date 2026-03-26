package io.github.kosyakmakc.socialBridgeTelegram.Utils;
/**
 * TO DO move to SocialBridge
 */
public class CancellationToken {
    private boolean isCancelled = false;
    private final CancellationToken parent;

    public CancellationToken() {
        this.parent = null;
    }

    public CancellationToken(CancellationToken parent) {
        this.parent = parent;
    }

    public boolean isCancelled() {
        return isCancelled || (parent != null && parent.isCancelled);
    }

    public void cancel() {
        isCancelled = true;
    }

    public void throwIfCancelled() {
        if (isCancelled()) {
            throw new CancellationTokenException();
        }
    }
}