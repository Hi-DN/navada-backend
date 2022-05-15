package hidn.navada.comm.exception;

public class ExchangeNotFoundException extends RuntimeException{
    public ExchangeNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public ExchangeNotFoundException(String msg) {
        super(msg);
    }

    public ExchangeNotFoundException() {
        super();
    }
}
