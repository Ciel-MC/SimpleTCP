package hk.eric.simpleTCP.exceptions;

import hk.eric.simpleTCP.packets.PacketID;

public class UnknownPacketIDException extends Exception {

    private final PacketID packetID;

    public UnknownPacketIDException(PacketID packetID) {
        super("Unrecognised packet ID: " + packetID.getID());
        this.packetID = packetID;
    }

    int[] getAsInt() {
        return new int[] {
            (int) packetID.getIDChars()[0],
            (int) packetID.getIDChars()[1],
        };
    }

    @Override
    public void printStackTrace() {
        System.out.println("PacketID: " + packetID.getID() + " as int: " + getAsInt()[0] + " " + getAsInt()[1]);
        super.printStackTrace();
    }
}
