package hk.eric.simpleTCP.server;

import hk.eric.simpleTCP.MessagePacket;
import hk.eric.simpleTCP.byteBuffer.IAbstractedByteBuffer;
import hk.eric.simpleTCP.byteBuffer.IAbstractedByteBufferImpl;
import hk.eric.simpleTCP.networkHandler.ITCPInterface;
import hk.eric.simpleTCP.networkHandler.ITCPInterfaceImpl;
import hk.eric.simpleTCP.networkHandler.ITCPPacketHandler;
import hk.eric.simpleTCP.packetHandler.TestServerPacketHandler;
import hk.eric.simpleTCP.packets.PacketID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

class TCPServerTest {

    private ITCPInterface tcpInterface;
    private ITCPServer tcpServer;

    @BeforeEach
    void setUp() {
        tcpServer = new TCPServer<>(new TestServerPacketHandler());
        tcpInterface = new ITCPInterfaceImpl(new TCPPacketHandler());
    }

    @Test
    public void testServer() throws IOException {
        tcpServer.listen(6969);
        tcpServer.registerPackets(new MessagePacket(""));

        tcpInterface.connect(new Socket("localhost", 6969));

        IAbstractedByteBuffer buffer = new IAbstractedByteBufferImpl(10);

        buffer.writePacketID(new PacketID("MS"));
        buffer.writeString("Hi");

        tcpInterface.send(buffer.getAllData());
    }

    private static class TCPPacketHandler implements ITCPPacketHandler {
        @Override
        public void handle(byte[] data) {

        }
    }

}