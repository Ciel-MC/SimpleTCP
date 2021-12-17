package hk.eric.simpleTCP.networkHandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

class ITCPInterfaceImplTest {

    ITCPInterface tcpServer;

    ITCPInterface tcpClient;

    @BeforeEach
    void setUp() {
        tcpClient = new ITCPInterfaceImpl(new ClientPacketHandler());
        tcpServer = new ITCPInterfaceImpl(new ServerPacketHandler());
    }

    @Test
    public void testConnection() {
        try {
            Thread serverThread = new Thread(() -> {
                try {
                    tcpServer.listen(6666);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            serverThread.start();
            tcpClient.connect(new Socket("localhost", 6666));

            tcpClient.send(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});

            tcpServer.sendAsServer(new byte[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static class ServerPacketHandler implements ITCPPacketHandler {
        @Override
        public void handle(byte[] data) {
            System.out.println("Server " + Arrays.toString(data));
        }
    }

    private static class ClientPacketHandler implements ITCPPacketHandler {
        @Override
        public void handle(byte[] data) {
            System.out.println("Client " + Arrays.toString(data));
        }
    }
}