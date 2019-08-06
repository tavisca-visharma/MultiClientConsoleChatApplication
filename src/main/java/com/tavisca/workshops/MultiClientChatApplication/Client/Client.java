package com.tavisca.workshops.MultiClientChatApplication.Client;

import com.tavisca.workshops.MultiClientChatApplication.ClientsConnectedList;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private String clientName;
    private Socket socket;
    private String ipAddress;
    private int port;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    Client(String ipAddress, int port) throws IOException {
        this.ipAddress = ipAddress;
        this.port = port;
        socket = new Socket(this.ipAddress, this.port);
        sendClientDetails();
    }

    public String getClientName() {
        return clientName;
    }

    private void sendClientDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Your Name : ");
        this.clientName = scanner.nextLine();
        try {
            sendData("Client Details : " + clientName);
        } catch (IOException e) {
            System.out.println("Error in sending Client Information !!!");
            System.out.println("Trying to resend...");
            sendClientDetails();
        }
    }

    Client(Socket socket){
        this.socket = socket;
    }

    public void sendData(String messageToSend) throws IOException {
        dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dataOutputStream.writeUTF(messageToSend);
        dataOutputStream.flush();
    }

    public String receiveData() throws IOException {
        dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        String messageDataReceived = dataInputStream.readUTF();
        return messageDataReceived;
    }

    public void close() throws IOException {
        ClientsConnectedList.socketsClientNameMap.remove(socket);
        dataInputStream.close();
        dataOutputStream.close();
        socket.close();
    }

}
