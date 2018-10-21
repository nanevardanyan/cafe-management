package am.nv.cafe.exception;

public class InvalidObjectException extends Exception {

    public InvalidObjectException() {
    }

    public InvalidObjectException(String message) {
        super(message);
    }

    public InvalidObjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidObjectException(Throwable cause) {
        super(cause);
    }

    public InvalidObjectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
