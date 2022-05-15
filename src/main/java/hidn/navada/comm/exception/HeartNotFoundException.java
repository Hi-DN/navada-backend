package hidn.navada.comm.exception;

public class HeartNotFoundException extends RuntimeException{
    public HeartNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public HeartNotFoundException(String msg) {
        super(msg);
    }

    public HeartNotFoundException() {
        super();
    }
}
