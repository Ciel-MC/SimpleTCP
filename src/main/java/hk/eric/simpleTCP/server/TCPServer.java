package hk.eric.simpleTCP.server;

import hk.eric.simpleTCP.connection.IConnection;
import hk.eric.simpleTCP.connection.IConnectionImpl;
import hk.eric.simpleTCP.packetHandler.IServerPacketHandler;
import hk.eric.simpleTCP.packets.Packet;

import java.io.IOException;

public class TCPServer<T extends IServerPacketHandler> implements ITCPServer {

    private final IConnection connection;

    public TCPServer(T packetHandler) {
        this.connection = new IConnectionImpl(packetHandler);
    }

    @Override
    public void listen(int port) {
        new Thread(() -> {
            try {
                connection.listen(port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void stop() {
        connection.close();
    }

    @Override
    public void add(Packet packet) {
        connection.add(packet);
    }

    @Override
    public void send(Packet packet) {
        connection.send(packet,true);
    }

    @Override
    public void send() {
        connection.send(true);
    }

    @Override
    public Packet sendWithReturn(Packet packet) {
        return connection.sendWithReturn(packet,true);
    }

    @Override
    public Packet sendWithReturn() {
        return connection.sendWithReturn(true);
    }

    @Override
    public void registerPackets(Packet... packets) {
        for (Packet packet : packets) {
            connection.registerPacket(packet);
        }
    }
}
