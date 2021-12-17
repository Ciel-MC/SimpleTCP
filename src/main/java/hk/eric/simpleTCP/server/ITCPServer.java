package hk.eric.simpleTCP.server;

import hk.eric.simpleTCP.packets.Packet;

public interface ITCPServer {

    void listen(int port);

    void stop();

    void add(Packet packet);

    void send(Packet packet);

    void send();

    Packet sendWithReturn(Packet packet);

    Packet sendWithReturn();

    void registerPackets(Packet... packets);

}
