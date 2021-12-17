package hk.eric.simpleTCP.client;

import hk.eric.simpleTCP.connection.IConnection;
import hk.eric.simpleTCP.connection.IConnectionImpl;
import hk.eric.simpleTCP.packetHandler.IClientPacketHandler;
import hk.eric.simpleTCP.packets.Packet;

import java.io.IOException;
import java.net.Socket;

public class TCPClient<T extends IClientPacketHandler> implements ITCPClient {

    private final IConnection connection;

    public TCPClient(T packetHandler) {
        this.connection = new IConnectionImpl(packetHandler);
    }

    @Override
    public void connect(String ip, int port) throws IOException {
        connection.connect(new Socket(ip, port));
    }

    @Override
    public void add(Packet packet) {
        connection.add(packet);
    }

    @Override
    public void send(Packet packet) {
        connection.send(packet,false);
    }

    @Override
    public Packet sendWithReturn(Packet packet) {
        return connection.sendWithReturn(packet,false);
    }

    @Override
    public void send() {
        connection.send(false);
    }

    @Override
    public Packet sendWithReturn() {
        return connection.sendWithReturn(false);
    }

    @Override
    public void stop() {
        connection.close();
    }

    @Override
    public void registerPackets(Packet... packets) {
        for (Packet packet : packets) {
            connection.registerPacket(packet);
        }
    }
}
