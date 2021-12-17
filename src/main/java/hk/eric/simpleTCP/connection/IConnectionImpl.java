package hk.eric.simpleTCP.connection;

import hk.eric.simpleTCP.byteBuffer.IAbstractedByteBuffer;
import hk.eric.simpleTCP.byteBuffer.IAbstractedByteBufferImpl;
import hk.eric.simpleTCP.exceptions.UnknownPacketIDException;
import hk.eric.simpleTCP.networkInterface.INetworkInterface;
import hk.eric.simpleTCP.networkInterface.INetworkInterfaceImpl;
import hk.eric.simpleTCP.packetHandler.IPacketHandler;
import hk.eric.simpleTCP.packets.Packet;
import hk.eric.simpleTCP.packets.PacketID;

import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class IConnectionImpl implements IConnection {

    private final IAbstractedByteBuffer buffer = new IAbstractedByteBufferImpl(2048);
    private final INetworkInterface networkInterface = new INetworkInterfaceImpl(this::handlePacket);
    private final Set<Packet> packetRegistry = new HashSet<>();
    private final IPacketHandler packetHandler;

    private final Function<IAbstractedByteBuffer,Packet> packetFactory = (buffer) -> {
        PacketID packetID = buffer.readPacketID();
        Optional<Packet> packet = packetRegistry.stream().filter(p -> p.getPacketID().equals(packetID)).findFirst();
        if (packet.isPresent()) {
            return packet.get().createThis(buffer);
        } else {
            try {
                throw new UnknownPacketIDException(packetID);
            } catch (UnknownPacketIDException e) {
                e.printStackTrace();
            }
            return null;
        }
    };

    public IConnectionImpl(IPacketHandler packetHandler) {
        this.packetHandler = packetHandler;
    }

    @Override
    public void add(Packet packet) {
        packet.write(buffer);
    }

    @Override
    public void send(boolean server) {
        if (server) {
            sendDataAsServer(buffer);
        } else {
            sendData(buffer);
        }
    }

    @Override
    public Packet sendWithReturn(boolean server) {
        if(server) {
            return sendDataAsServerWithReturn(buffer);
        } else {
            return sendDataWithReturn(buffer);
        }
    }

    @Override
    public void registerPacket(Packet packet) {
        packetRegistry.add(packet);
    }

    private void sendData(IAbstractedByteBuffer data) {
        networkInterface.send(data);
    }

    private Packet sendDataWithReturn(IAbstractedByteBuffer data) {
        return packetFactory.apply(networkInterface.sendWithReturn(data));
    }

    private void sendDataAsServer(IAbstractedByteBuffer data) {
        networkInterface.sendAsServer(data);
    }

    private Packet sendDataAsServerWithReturn(IAbstractedByteBuffer data) {
        return packetFactory.apply(networkInterface.sendAsServerWithReturn(data));
    }

    @Override
    public void connect(Socket socket) throws IOException {
        networkInterface.connect(socket);
    }

    @Override
    public void listen(int port) throws IOException {
        networkInterface.listen(port);
    }

    @Override
    public void handlePacket(IAbstractedByteBuffer byteBuffer) {
        while (byteBuffer.hasRemaining()) {
            packetFactory.apply(byteBuffer).handle(packetHandler);
        }
    }

    @Override
    public void close() {
        networkInterface.disconnect();
    }
}
