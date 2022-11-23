package net.vogeez.authorization.service.exception;

/**
 * @author : Niklas Tat
 * @since : 0.5
 */
public class UserAlreadyVerified extends RuntimeException {

    public UserAlreadyVerified() {
        super();
    }

    public UserAlreadyVerified(String message) {
        super(message);
    }

    public UserAlreadyVerified(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyVerified(Throwable cause) {
        super(cause);
    }

    public UserAlreadyVerified(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
