package hidn.navada.comm.exception;

public class KaKaoSignInException extends RuntimeException{
    public KaKaoSignInException(String msg, Throwable t) {
        super(msg, t);
    }

    public KaKaoSignInException(String msg) {
        super(msg);
    }

    public KaKaoSignInException() {
        super();
    }
}
