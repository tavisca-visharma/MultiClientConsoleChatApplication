package com.tavisca.workshops.MultiClientChatApplication.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {

    private String serverName = null;
    private ServerSocket serverSocket;
    private Socket socket;
    private Scanner scanner;
    private int port;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;

    Server(int port,String serverName) throws IOException {
        this.serverName = serverName;
        this.port = port;
        serverSocket = new ServerSocket(this.port);
    }

    public String getServerName(){
        return serverName;
    }

    public Socket acceptClients() throws IOException {
        socket = serverSocket.accept();
        return socket;
        //Message to all that a new client is connected
//        clientSocketsList.add(socket);
    }

    public Socket getClientSocket() {
        return this.socket;
    }

    public void sendData(Socket clientSocket, String messageDataToSend) throws IOException {
        this.socket = clientSocket;
        dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
        dataOutputStream.writeUTF(messageDataToSend);
        dataOutputStream.flush();
    }

    public String receiveData(Socket clientSocket) throws IOException {
        this.socket = clientSocket;
        dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
        String messageDataRecieved = dataInputStream.readUTF();
        return messageDataRecieved;
    }

    public void close() throws IOException {
        dataInputStream.close();
        dataOutputStream.close();
        socket.close();
        serverSocket.close();
    }

}
