package hidn.navada.comm.exception;

public class BusinessRuleException extends RuntimeException{
    public BusinessRuleException(String msg, Throwable t) {
        super(msg, t);
    }

    public BusinessRuleException(String msg) {
        super(msg);
    }

    public BusinessRuleException() {
        super();
    }
}
