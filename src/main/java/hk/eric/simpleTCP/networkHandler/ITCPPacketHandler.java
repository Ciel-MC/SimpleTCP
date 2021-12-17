package hk.eric.simpleTCP.networkHandler;

public interface ITCPPacketHandler {
    void handle(byte[] data);
}
