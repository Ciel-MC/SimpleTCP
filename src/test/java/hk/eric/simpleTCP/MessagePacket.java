package hk.eric.simpleTCP;

import hk.eric.simpleTCP.byteBuffer.IAbstractedByteBuffer;
import hk.eric.simpleTCP.packetHandler.TestClientPacketHandler;
import hk.eric.simpleTCP.packets.Packet;
import hk.eric.simpleTCP.packets.PacketID;

public class MessagePacket extends Packet<TestClientPacketHandler> {

    private final PacketID packetID = new PacketID("MS");

    private final String message;

    public MessagePacket(String message) {
        this.message = message;
    }

    public MessagePacket(IAbstractedByteBuffer buffer) {
        this.message = buffer.readString();
    }

    @Override
    public void handle(TestClientPacketHandler handler) {
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
    public Packet<TestClientPacketHandler> createThis(IAbstractedByteBuffer byteBuffer) {
        return new MessagePacket(byteBuffer);
    }

    @Override
    public int getSize() {
        return super.getSize() + message.length();
    }

    public String getMessage() {
        return message;
    }
}