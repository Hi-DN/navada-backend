package hidn.navada.comm.exception;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public CategoryNotFoundException(String msg) {
        super(msg);
    }

    public CategoryNotFoundException() {
        super();
    }
}
