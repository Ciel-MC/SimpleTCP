package hk.eric.simpleTCP.packets;

import hk.eric.simpleTCP.byteBuffer.IAbstractedByteBuffer;
import hk.eric.simpleTCP.packetHandler.IPacketHandler;

public abstract class Packet<T extends IPacketHandler> {

    public Packet() {
    }

    public Packet(IAbstractedByteBuffer buffer) {

    }

    public void write(IAbstractedByteBuffer byteBuffer) {
        byteBuffer.writePacketID(getPacketID());
    }

    public abstract void handle(T handler);

    public abstract PacketID getPacketID();

    public abstract Packet<T> createThis(IAbstractedByteBuffer byteBuffer);

    public int getSize() {
        return 4;
    }
}
