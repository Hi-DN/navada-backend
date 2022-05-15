package hidn.navada.comm.exception;

public class ExchangeRequestNotFoundException extends RuntimeException{
    public ExchangeRequestNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public ExchangeRequestNotFoundException(String msg) {
        super(msg);
    }

    public ExchangeRequestNotFoundException() {
        super();
    }
}
