package hk.eric.simpleTCP.packetHandler;

import hk.eric.simpleTCP.MessagePacket;
import hk.eric.simpleTCP.MessagePacketServer;
import hk.eric.simpleTCP.packets.Packet;
import hk.eric.simpleTCP.server.GeneralTest;

public class TestServerPacketHandler implements IServerPacketHandler {
    @Override
    public void handle(Packet packet) {
        System.out.println("TestServerPacketHandler(Unknown Packet): " + packet.toString());
    }

    public void handleMessage(MessagePacketServer packet) {
        System.out.println("TestServerPacketHandler: " + packet.getMessage());
        if(packet.getMessage().equals("Hewwo")) {
            GeneralTest.sendToClient(new MessagePacket("Hwllw"));
        }
    }
}
