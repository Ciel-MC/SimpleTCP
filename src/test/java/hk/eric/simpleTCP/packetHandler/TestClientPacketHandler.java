package hk.eric.simpleTCP.packetHandler;

import hk.eric.simpleTCP.MessagePacket;
import hk.eric.simpleTCP.packets.Packet;

public class TestClientPacketHandler implements IClientPacketHandler {
    @Override
    public void handle(Packet packet) {
        System.out.println("TestClientPacketHandler(Unknown Packet): " + packet.toString());
    }

    public void handleMessage(MessagePacket packet) {
        System.out.println("TestClientPacketHandler: " + packet.getMessage());
    }
}
