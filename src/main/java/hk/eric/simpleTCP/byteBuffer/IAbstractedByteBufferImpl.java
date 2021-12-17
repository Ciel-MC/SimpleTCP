package hk.eric.simpleTCP.byteBuffer;

import hk.eric.simpleTCP.packets.PacketID;

import java.nio.ByteBuffer;
import java.util.UUID;

public class IAbstractedByteBufferImpl implements IAbstractedByteBuffer {

    private int readPos = 0;
    private int writePos = 0;
    private final byte[] bytes;
    private final ByteBuffer buffer;

    public IAbstractedByteBufferImpl() {
        this.bytes = new byte[4096];
        buffer = ByteBuffer.wrap(bytes);
    }

    public IAbstractedByteBufferImpl(byte[] initData) {
        this.bytes = initData;
        buffer = ByteBuffer.wrap(bytes);
    }

    public IAbstractedByteBufferImpl(int size) {
        this.bytes = new byte[size];
        buffer = ByteBuffer.wrap(bytes);
    }

    @Override
    public void writeString(String s) {
        buffer.putInt(s.length());
        writePos += 4;
        buffer.put(s.getBytes());
        writePos += s.length();
    }

    @Override
    public void writeInt(int i) {
        buffer.putInt(i);
        writePos += 4;
    }

    @Override
    public void writeFloat(float f) {
        buffer.putFloat(f);
        writePos += 4;
    }

    @Override
    public void writeDouble(double d) {
        buffer.putDouble(d);
        writePos += 8;
    }

    @Override
    public void writeByte(byte b) {
        buffer.put(b);
        writePos++;
    }

    @Override
    public void writeShort(short s) {
        buffer.putShort(s);
        writePos += 2;
    }

    @Override
    public void writeLong(long l) {
        buffer.putLong(l);
        writePos += 8;
    }

    @Override
    public void writeChar(char c) {
        buffer.putChar(c);
        writePos += 2;
    }

    @Override
    public void writeBoolean(boolean b) {
        writeByte((byte) (b ? 1 : 0));
    }

    @Override
    public void writePacketID(PacketID packetID) {
        writeCharArray(packetID.getIDChars());
    }

    @Override
    public void writeUUID(UUID uuid) {
        writeLong(uuid.getMostSignificantBits());
        writeLong(uuid.getLeastSignificantBits());
    }

    @Override
    public String readString() {
        int length = buffer.getInt(readPos);
        readPos += 4;
        byte[] bytes = new byte[length];
        buffer.get(readPos,bytes);
        readPos += length;
        return new String(bytes);
    }

    @Override
    public int readInt() {
        int result = buffer.getInt(readPos);
        readPos += 4;
        return result;
    }

    @Override
    public float readFloat() {
        float result = buffer.getFloat(readPos);
        readPos += 4;
        return result;
    }

    @Override
    public double readDouble() {
        double result = buffer.getDouble(readPos);
        readPos += 8;
        return result;
    }

    @Override
    public byte readByte() {
        return buffer.get(readPos++);
    }

    @Override
    public short readShort() {
        short result = buffer.getShort(readPos);
        readPos += 2;
        return result;
    }

    @Override
    public long readLong() {
        long result = buffer.getLong(readPos);
        readPos += 8;
        return result;
    }

    @Override
    public char readChar() {
        char result = buffer.getChar(readPos);
        readPos += 2;
        return result;
    }

    @Override
    public boolean readBoolean() {
        return readByte() == 1;
    }

    @Override
    public UUID readUUID() {
        return new UUID(readLong(),readLong());
    }

    @Override
    public PacketID readPacketID() {
        return new PacketID(readCharArray(2));
    }

    @Override
    public String[] readStringArray(int size) {
        String[] result = new String[size];
        for (int i = 0; i < size; i++) {
            result[i] = readString();
        }
        return result;
    }

    @Override
    public int[] readIntArray(int size) {
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = readInt();
        }
        return result;
    }

    @Override
    public float[] readFloatArray(int size) {
        float[] result = new float[size];
        for (int i = 0; i < size; i++) {
            result[i] = readFloat();
        }
        return result;
    }

    @Override
    public double[] readDoubleArray(int size) {
        double[] result = new double[size];
        for (int i = 0; i < size; i++) {
            result[i] = readDouble();
        }
        return result;
    }

    @Override
    public byte[] readByteArray(int size) {
        byte[] bytes = new byte[size];
        buffer.get(readPos,bytes);
        readPos += size;
        return bytes;
    }

    @Override
    public short[] readShortArray(int size) {
        short[] result = new short[size];
        for (int i = 0; i < size; i++) {
            result[i] = readShort();
        }
        return result;
    }

    @Override
    public long[] readLongArray(int size) {
        long[] result = new long[size];
        for (int i = 0; i < size; i++) {
            result[i] = readLong();
        }
        return result;
    }

    @Override
    public char[] readCharArray(int size) {
        char[] result = new char[size];
        for (int i = 0; i < size; i++) {
            result[i] = readChar();
        }
        return result;
    }

    @Override
    public void writeStringArray(String[] s) {
        for (String str : s) {
            writeString(str);
        }
    }

    @Override
    public void writeIntArray(int[] i) {
        for (int integer : i) {
            writeInt(integer);
        }
    }

    @Override
    public void writeFloatArray(float[] f) {
        for (float fl : f) {
            writeFloat(fl);
        }
    }

    @Override
    public void writeDoubleArray(double[] d) {
        for (double doub : d) {
            writeDouble(doub);
        }
    }

    @Override
    public void writeByteArray(byte[] b) {
        buffer.put(b);
        writePos += b.length;
    }

    @Override
    public void writeShortArray(short[] s) {
        for (short sh : s) {
            writeShort(sh);
        }
    }

    @Override
    public void writeLongArray(long[] l) {
        for (long lon : l) {
            writeLong(lon);
        }
    }

    @Override
    public void writeCharArray(char[] c) {
        for (char ch : c) {
            writeChar(ch);
        }
    }

    @Override
    public byte[] readAllBytes() {
        byte[] result = new byte[writePos];
        buffer.get(readPos, result);
        readPos = 0;
        writePos = 0;
        buffer.clear();
        return result;
    }

    @Override
    public boolean hasRemaining() {
        return readPos < bytes.length;
    }

    @Override
    public byte[] getAllData() {
        return bytes;
    }

    @Override
    public int getCapacity() {
        return bytes.length;
    }

    @Override
    public int getPosition() {
        return writePos;
    }

    private void print(String s) {
        System.out.println(s);
    }
}
