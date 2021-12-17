package hk.eric.simpleTCP.byteBuffer;

import hk.eric.simpleTCP.packets.PacketID;

import java.util.UUID;

public interface IAbstractedByteBuffer {

    void writeString(String s);

    void writeInt(int i);

    void writeFloat(float f);

    void writeDouble(double d);

    void writeByte(byte b);

    void writeShort(short s);

    void writeLong(long l);

    void writeChar(char c);

    void writeBoolean(boolean b);

    void writePacketID(PacketID packetID);

    void writeUUID(UUID uuid);

    String readString();

    int readInt();

    float readFloat();

    double readDouble();

    byte readByte();

    short readShort();

    long readLong();

    char readChar();

    boolean readBoolean();

    UUID readUUID();

    PacketID readPacketID();

    String[] readStringArray(int size);

    int[] readIntArray(int size);

    float[] readFloatArray(int size);

    double[] readDoubleArray(int size);

    byte[] readByteArray(int size);

    short[] readShortArray(int size);

    long[] readLongArray(int size);

    char[] readCharArray(int size);

    void writeStringArray(String[] s);

    void writeIntArray(int[] i);

    void writeFloatArray(float[] f);

    void writeDoubleArray(double[] d);

    void writeByteArray(byte[] b);

    void writeShortArray(short[] s);

    void writeLongArray(long[] l);

    void writeCharArray(char[] c);

    byte[] readAllBytes();

    boolean hasRemaining();

    byte[] getAllData();

    int getCapacity();

    int getPosition();

}
