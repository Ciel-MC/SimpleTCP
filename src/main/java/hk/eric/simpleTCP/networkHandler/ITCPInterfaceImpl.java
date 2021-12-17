package hk.eric.simpleTCP.networkHandler;

import hk.eric.simpleTCP.exceptions.NotListeningException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ITCPInterfaceImpl implements ITCPInterface{

    private Socket socket;
    private Socket serverSocket;
    private final ITCPPacketHandler packetHandler;
    private boolean stopListening;

    public ITCPInterfaceImpl(ITCPPacketHandler packetHandler) {
        this.packetHandler = packetHandler;
    }

    public void connect(Socket socket) {
        this.socket = socket;
        new Thread(()-> startListening(socket)).start();
    }

    @Override
    public void disconnect() {
        try {
            if(socket != null) {
                socket.close();
                socket = null;
            }
            if(serverSocket != null){
                serverSocket.close();
                serverSocket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void listen(int port) throws IOException {
        serverSocket = new ServerSocket(port).accept();
        new Thread(()-> startListening(serverSocket)).start();
    }

    private void startListening(Socket socket) {
        try {
            byte[] data;
            stopListening = false;
            while (!stopListening){
                while((data = receive(socket)) != null) {
                    handlerTCPPacket(data);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopListening() {
        try {
            getServerSocket().close();
        } catch (IOException | NotListeningException e) {
            e.printStackTrace();
        }
        stopListening = true;
    }

    @Override
    public byte[] receive(boolean isServer) throws IOException {
        Socket socket = isServer ? this.serverSocket : this.socket;
        return receive(socket);
    }

    private synchronized byte[] receive(Socket socket) throws IOException {
        DataInputStream dIn = new DataInputStream(socket.getInputStream());
        int length;
        if ((length = dIn.readInt()) != -1) {
            byte[] data = new byte[length];
            dIn.readNBytes(data, 0, length);
            return data;
        }else {
            return null;
        }
    }

    public void send(byte[] data) {
        try {
            sendPacket(new DataOutputStream(socket.getOutputStream()),data);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendAsServer(byte[] data) {
        try {
            sendPacket(new DataOutputStream(getServerSocket().getOutputStream()),data);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendPacket(DataOutputStream dOut, byte[] data) throws IOException {
        dOut.writeInt(data.length);
        dOut.write(data);
        dOut.flush();
    }

    private Socket getServerSocket() throws NotListeningException {
        if(serverSocket == null) {
            throw new NotListeningException();
        }
        return serverSocket;
    }

    @Override
    public ITCPPacketHandler getPacketHandler() {
        return packetHandler;
    }
}
