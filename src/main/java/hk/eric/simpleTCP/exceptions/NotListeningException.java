package hk.eric.simpleTCP.exceptions;

public class NotListeningException extends Exception {
    @Override
    public String getMessage() {
        return "Server is not listening.";
    }
}
