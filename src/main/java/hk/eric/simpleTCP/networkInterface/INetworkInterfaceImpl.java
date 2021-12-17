package hk.eric.simpleTCP.networkInterface;

import hk.eric.simpleTCP.byteBuffer.IAbstractedByteBuffer;
import hk.eric.simpleTCP.byteBuffer.IAbstractedByteBufferImpl;
import hk.eric.simpleTCP.networkHandler.ITCPInterface;
import hk.eric.simpleTCP.networkHandler.ITCPInterfaceImpl;

import java.io.IOException;
import java.net.Socket;
import java.util.function.Consumer;

public class INetworkInterfaceImpl implements INetworkInterface {

    private final ITCPInterface tcpInterface;
    private final Consumer<IAbstractedByteBuffer> packetHandler;

    public INetworkInterfaceImpl(Consumer<IAbstractedByteBuffer> packetHandler) {
        this.tcpInterface = new ITCPInterfaceImpl(this::handlePacket);
        this.packetHandler = packetHandler;
    }

    @Override
    public void connect(Socket socket) {
        tcpInterface.connect(socket);
    }

    @Override
    public void disconnect() {
        tcpInterface.disconnect();
    }

    @Override
    public void listen(int port) throws IOException {
        tcpInterface.listen(port);
    }

    @Override
    public void stopListening() {
        tcpInterface.stopListening();
    }

    @Override
    public void send(IAbstractedByteBuffer data) {
        tcpInterface.send(data.readAllBytes());
    }

    @Override
    public IAbstractedByteBuffer sendWithReturn(IAbstractedByteBuffer data) {
        try {
            send(data);
            return new IAbstractedByteBufferImpl(tcpInterface.receive(false));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new IAbstractedByteBufferImpl(0);
    }

    @Override
    public void sendAsServer(IAbstractedByteBuffer data) {
        tcpInterface.sendAsServer(data.readAllBytes());
    }

    @Override
    public IAbstractedByteBuffer sendAsServerWithReturn(IAbstractedByteBuffer data) {
        try {
            sendAsServer(data);
            return new IAbstractedByteBufferImpl(tcpInterface.receive());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new IAbstractedByteBufferImpl(0);
    }

    @Override
    public void handlePacket(byte[] data) {
        packetHandler.accept(new IAbstractedByteBufferImpl(data));
    }
}
