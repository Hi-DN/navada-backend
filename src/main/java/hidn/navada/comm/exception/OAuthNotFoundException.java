package hidn.navada.comm.exception;

public class OAuthNotFoundException extends RuntimeException{
    public OAuthNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public OAuthNotFoundException(String msg) {
        super(msg);
    }

    public OAuthNotFoundException() {
        super();
    }
}
