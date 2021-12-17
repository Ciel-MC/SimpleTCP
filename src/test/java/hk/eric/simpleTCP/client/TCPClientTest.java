package hk.eric.simpleTCP.client;

import hk.eric.simpleTCP.MessagePacket;
import hk.eric.simpleTCP.byteBuffer.IAbstractedByteBuffer;
import hk.eric.simpleTCP.byteBuffer.IAbstractedByteBufferImpl;
import hk.eric.simpleTCP.networkHandler.ITCPInterface;
import hk.eric.simpleTCP.networkHandler.ITCPInterfaceImpl;
import hk.eric.simpleTCP.networkHandler.ITCPPacketHandler;
import hk.eric.simpleTCP.packetHandler.IClientPacketHandler;
import hk.eric.simpleTCP.packets.Packet;
import hk.eric.simpleTCP.packets.PacketID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TCPClientTest {

    private ITCPInterface tcpInterface;
    private ITCPClient tcpClient;

    @BeforeEach
    void setUp() {
        tcpClient = new TCPClient<>(new ClientPacketHandler());
        tcpInterface = new ITCPInterfaceImpl(new TCPPacketHandler());
    }

    @Test
    void testClient() throws IOException {
        new Thread(() -> {
            try {
                tcpInterface.listen(6666);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        tcpClient.registerPackets(new MessagePacket(""));
        tcpClient.connect("localhost", 6666);

        tcpClient.send(new MessagePacket("Hello World!"));

        IAbstractedByteBuffer buffer = new IAbstractedByteBufferImpl();

        buffer.writePacketID(new PacketID("MS"));
        buffer.writeString("Hi!");

        tcpInterface.sendAsServer(buffer.getAllData());
    }

    public static class TCPPacketHandler implements ITCPPacketHandler {
        @Override
        public void handle(byte[] data) {
            IAbstractedByteBuffer buffer = new IAbstractedByteBufferImpl(data);
            buffer.readPacketID();
            System.out.println(buffer.readString());
        }
    }

    public static class ClientPacketHandler implements IClientPacketHandler {
        @Override
        public void handle(Packet packet) {
            System.out.println("Client: " + packet);
        }
    }
}