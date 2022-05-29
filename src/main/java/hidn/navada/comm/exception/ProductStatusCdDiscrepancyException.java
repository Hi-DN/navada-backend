package hidn.navada.comm.exception;

public class ProductStatusCdDiscrepancyException extends RuntimeException{
    public ProductStatusCdDiscrepancyException(String msg, Throwable t) {
        super(msg, t);
    }

    public ProductStatusCdDiscrepancyException(String msg) {
        super(msg);
    }

    public ProductStatusCdDiscrepancyException() {
        super();
    }
}
