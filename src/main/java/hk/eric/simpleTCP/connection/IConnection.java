package hk.eric.simpleTCP.connection;

import hk.eric.simpleTCP.byteBuffer.IAbstractedByteBuffer;
import hk.eric.simpleTCP.packets.Packet;

import java.io.IOException;
import java.net.Socket;

public interface IConnection {

    default void send(Packet packet, boolean server) {
        add(packet);
        send(server);
    }

    default Packet sendWithReturn(Packet packet, boolean server) {
        add(packet);
        return sendWithReturn(server);
    }

    void add(Packet packet);

    void send(boolean server);

    Packet sendWithReturn(boolean server);

    void registerPacket(Packet packet);

    void connect(Socket socket) throws IOException;

    void listen(int port) throws IOException;

    void handlePacket(IAbstractedByteBuffer packet);

    void close();
}