package hk.eric.simpleTCP.networkInterface;

import hk.eric.simpleTCP.byteBuffer.IAbstractedByteBuffer;

import java.io.IOException;
import java.net.Socket;

public interface INetworkInterface {

    void connect(Socket socket);

    void disconnect();

    void listen(int port) throws IOException;

    void stopListening();

    void send(IAbstractedByteBuffer data);

    IAbstractedByteBuffer sendWithReturn(IAbstractedByteBuffer data);

    void sendAsServer(IAbstractedByteBuffer data);

    IAbstractedByteBuffer sendAsServerWithReturn(IAbstractedByteBuffer data);

    void handlePacket(byte[] data);

}
