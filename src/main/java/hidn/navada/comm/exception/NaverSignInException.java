package hidn.navada.comm.exception;

public class NaverSignInException extends RuntimeException{
    public NaverSignInException(String msg, Throwable t) {
        super(msg, t);
    }

    public NaverSignInException(String msg) {
        super(msg);
    }

    public NaverSignInException() {
        super();
    }
}
