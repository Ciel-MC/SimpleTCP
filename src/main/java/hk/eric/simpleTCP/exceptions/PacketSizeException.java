package hk.eric.simpleTCP.exceptions;

public class PacketSizeException extends Exception {

    @Override
    public String getMessage() {
        return "Packet size is too large";
    }
}
