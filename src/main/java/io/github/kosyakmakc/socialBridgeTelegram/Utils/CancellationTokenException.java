package io.github.kosyakmakc.socialBridgeTelegram.Utils;

public class CancellationTokenException extends RuntimeException {

    public CancellationTokenException() {
    }

    public CancellationTokenException(String message) {
        super(message);
    }

    public CancellationTokenException(Throwable cause) {
        super(cause);
    }

    public CancellationTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public CancellationTokenException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
