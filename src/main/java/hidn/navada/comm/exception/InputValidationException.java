package hidn.navada.comm.exception;

public class InputValidationException extends RuntimeException{
    public InputValidationException(String msg, Throwable t) {
        super(msg, t);
    }

    public InputValidationException(String msg) {
        super(msg);
    }

    public InputValidationException() {
        super();
    }
}
