package hidn.navada.comm.exception;

public class ExchangeStatusCdDiscrepancyException extends RuntimeException{
    public ExchangeStatusCdDiscrepancyException(String msg, Throwable t) {
        super(msg, t);
    }

    public ExchangeStatusCdDiscrepancyException(String msg) {
        super(msg);
    }

    public ExchangeStatusCdDiscrepancyException() {
        super();
    }
}
