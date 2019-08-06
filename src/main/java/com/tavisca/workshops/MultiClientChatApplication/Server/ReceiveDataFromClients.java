package com.tavisca.workshops.MultiClientChatApplication.Server;

import com.tavisca.workshops.MultiClientChatApplication.ClientsConnectedList;

import java.io.IOException;
import java.net.Socket;

public class ReceiveDataFromClients implements Runnable {
    Server server;
    Socket clientSocket;
    private String clientName;

    ReceiveDataFromClients(Server server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = server.receiveData(clientSocket);
//                System.out.println(clientSocket.getRemoteSocketAddress() + " : " + message);
                String[] splittedMessage;
                clientName = ClientsConnectedList.socketsClientNameMap.get(clientSocket);
                System.out.println(clientName + " : " + message);
                if (message.contains("#") && message.contains("@")) {
                    splittedMessage = message.split("#");
                    Socket specificClientSocket = ClientsConnectedList.clientNameSocketMap.get(splittedMessage[0].trim().substring(1));
                    server.sendData(specificClientSocket, clientName + " : " + splittedMessage[1].trim());
                } else {
                    for (Socket clientSockets : ClientsConnectedList.socketsClientNameMap.keySet()) {
                        if (!clientSocket.equals(clientSockets)) {
                            server.sendData(clientSockets, clientName + " : " + message);
                        }
                    }
                }
            }
        } catch (IOException e) {
            ClientsConnectedList.socketsClientNameMap.remove(clientSocket);
            ClientsConnectedList.clientNameSocketMap.remove(clientName);
            System.out.println("Client Exited !!!");
        }

//        Thread newReceiveThread = new Thread(new ReceiveDataFromClients(this.server,this.clientSocket));
    }
}
