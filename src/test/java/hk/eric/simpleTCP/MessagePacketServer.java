package hk.eric.simpleTCP;

import hk.eric.simpleTCP.byteBuffer.IAbstractedByteBuffer;
import hk.eric.simpleTCP.packetHandler.TestServerPacketHandler;
import hk.eric.simpleTCP.packets.Packet;
import hk.eric.simpleTCP.packets.PacketID;

public class MessagePacketServer extends Packet<TestServerPacketHandler> {

    private final PacketID packetID = new PacketID("MS");

    private final String message;

    public MessagePacketServer(String message) {
        this.message = message;
    }

    public MessagePacketServer(IAbstractedByteBuffer buffer) {
        this.message = buffer.readString();
    }

    @Override
    public void handle(TestServerPacketHandler handler) {
        handler.handleMessage(this);
    }

    @Override
    public PacketID getPacketID() {
        return packetID;
    }

    @Override
    public void write(IAbstractedByteBuffer byteBuffer) {
        super.write(byteBuffer);
        byteBuffer.writeString(message);
    }

    @Override
    public Packet<TestServerPacketHandler> createThis(IAbstractedByteBuffer byteBuffer) {
        return new MessagePacketServer(byteBuffer);
    }

    @Override
    public int getSize() {
        return super.getSize() + message.length();
    }

    public String getMessage() {
        return message;
    }
}