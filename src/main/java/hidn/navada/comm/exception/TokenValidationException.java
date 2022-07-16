package hidn.navada.comm.exception;

public class TokenValidationException extends RuntimeException{
    public TokenValidationException(String msg, Throwable t) {
        super(msg, t);
    }

    public TokenValidationException(String msg) {
        super(msg);
    }

    public TokenValidationException() {
        super();
    }
}
