package hidn.navada.comm.exception;

public class KakaoSignInException extends RuntimeException{
    public KakaoSignInException(String msg, Throwable t) {
        super(msg, t);
    }

    public KakaoSignInException(String msg) {
        super(msg);
    }

    public KakaoSignInException() {
        super();
    }
}
