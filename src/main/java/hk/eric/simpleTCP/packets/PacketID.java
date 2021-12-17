package hk.eric.simpleTCP.packets;

public class PacketID {

    private final String id;

    public PacketID(String id) {
        if(id.length() != 2) {
            throw new IllegalArgumentException("ID must be 2 characters long");
        }
        this.id = id;
    }

    public PacketID(char[] id) {
        this(new String(id));
    }

    public PacketID(char id1, char id2) {
        this(Character.toString(id1) + Character.toString(id2));
    }

    public String getID() {
        return id;
    }

    public char[] getIDChars() {
        return id.toCharArray();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PacketID && ((PacketID) obj).id.equals(id);
    }
}
