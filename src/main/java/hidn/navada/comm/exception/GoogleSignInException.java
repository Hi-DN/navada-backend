package hidn.navada.comm.exception;

public class GoogleSignInException extends RuntimeException{
    public GoogleSignInException(String msg, Throwable t) {
        super(msg, t);
    }

    public GoogleSignInException(String msg) {
        super(msg);
    }

    public GoogleSignInException() {
        super();
    }
}
