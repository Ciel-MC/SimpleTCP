package hk.eric.simpleTCP.server;

import hk.eric.simpleTCP.MessagePacket;
import hk.eric.simpleTCP.MessagePacketServer;
import hk.eric.simpleTCP.client.ITCPClient;
import hk.eric.simpleTCP.client.TCPClient;
import hk.eric.simpleTCP.packetHandler.TestClientPacketHandler;
import hk.eric.simpleTCP.packetHandler.TestServerPacketHandler;
import hk.eric.simpleTCP.packets.Packet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class GeneralTest {

    private static ITCPServer tcpServer;
    private static ITCPClient tcpClient;

    @BeforeEach
    void setUp() {
        tcpServer = new TCPServer<>(new TestServerPacketHandler());
        tcpClient = new TCPClient<>(new TestClientPacketHandler());
    }

    @Test
    public void testServer() throws IOException {
        tcpServer.listen(6969);
        tcpServer.registerPackets(new MessagePacketServer(""));

        tcpClient.registerPackets(new MessagePacket(""));
        tcpClient.connect("localhost", 6969);

        tcpClient.send(new MessagePacket("Hello"));
        tcpClient.send(new MessagePacket("Hi"));
//        tcpClient.sendWithReturn(new MessagePacket("Hewwo"));

//        tcpServer.send(new MessagePacketServer("Hello Client"));
    }

    public static void sendToClient(Packet packet) {
        tcpServer.send(packet);
        tcpServer.send(packet);
        tcpServer.send(packet);
    }

}
