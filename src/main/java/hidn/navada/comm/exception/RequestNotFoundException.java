package hidn.navada.comm.exception;

public class RequestNotFoundException extends RuntimeException{
    public RequestNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public RequestNotFoundException(String msg) {
        super(msg);
    }

    public RequestNotFoundException() {
        super();
    }
}
