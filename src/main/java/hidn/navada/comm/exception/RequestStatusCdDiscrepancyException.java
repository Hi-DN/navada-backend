package hidn.navada.comm.exception;

public class RequestStatusCdDiscrepancyException extends RuntimeException{
    public RequestStatusCdDiscrepancyException(String msg, Throwable t) {
        super(msg, t);
    }

    public RequestStatusCdDiscrepancyException(String msg) {
        super(msg);
    }

    public RequestStatusCdDiscrepancyException() {
        super();
    }
}
