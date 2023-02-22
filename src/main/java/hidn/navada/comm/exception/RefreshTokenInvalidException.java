package hidn.navada.comm.exception;

public class RefreshTokenInvalidException extends RuntimeException{
    public RefreshTokenInvalidException(String msg, Throwable t) {
        super(msg, t);
    }

    public RefreshTokenInvalidException(String msg) {
        super(msg);
    }

    public RefreshTokenInvalidException() {
        super();
    }
}
