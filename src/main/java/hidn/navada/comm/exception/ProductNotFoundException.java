package hidn.navada.comm.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public ProductNotFoundException(String msg) {
        super(msg);
    }

    public ProductNotFoundException() {
        super();
    }
}
