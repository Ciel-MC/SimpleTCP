package hk.eric.simpleTCP.packetHandler;

import hk.eric.simpleTCP.packets.Packet;

public interface IPacketHandler {
    void handle(Packet packet);
}
