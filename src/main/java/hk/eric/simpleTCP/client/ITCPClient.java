package hk.eric.simpleTCP.client;

import hk.eric.simpleTCP.packets.Packet;

import java.io.IOException;

public interface ITCPClient {

    void connect(String ip, int port) throws IOException;

    void add(Packet packet);

    void send(Packet packet);

    Packet sendWithReturn(Packet packet);

    void send();

    Packet sendWithReturn();

    void stop() throws IOException;

    void registerPackets(Packet... packets);

}
