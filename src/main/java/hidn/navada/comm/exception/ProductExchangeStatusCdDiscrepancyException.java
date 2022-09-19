package hidn.navada.comm.exception;

public class ProductExchangeStatusCdDiscrepancyException extends RuntimeException{
    public ProductExchangeStatusCdDiscrepancyException(String msg, Throwable t) {
        super(msg, t);
    }

    public ProductExchangeStatusCdDiscrepancyException(String msg) {
        super(msg);
    }

    public ProductExchangeStatusCdDiscrepancyException() {
        super();
    }
}
