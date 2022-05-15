package hidn.navada.comm.exception;

public class ImageNotFoundException extends RuntimeException{
    public ImageNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public ImageNotFoundException(String msg) {
        super(msg);
    }

    public ImageNotFoundException() {
        super();
    }
}
