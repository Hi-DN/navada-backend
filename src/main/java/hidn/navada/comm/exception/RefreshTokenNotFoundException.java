package hidn.navada.comm.exception;

public class RefreshTokenNotFoundException extends RuntimeException{
    public RefreshTokenNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public RefreshTokenNotFoundException(String msg) {
        super(msg);
    }

    public RefreshTokenNotFoundException() {
        super();
    }
}
