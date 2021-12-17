package hk.eric.simpleTCP.networkHandler;

import java.io.IOException;
import java.net.Socket;

public interface ITCPInterface {

    void connect(Socket socket);

    void disconnect();

    void listen(int port) throws IOException;

    void stopListening();

    default byte[] receive() throws IOException {
        return receive(true);
    }

    byte[] receive(boolean server) throws IOException;

    void send(byte[] data);

    void sendAsServer(byte[] data);

    default void handlerTCPPacket(byte[] data) {
        getPacketHandler().handle(data);
    }

    ITCPPacketHandler getPacketHandler();

}
