package com.tavisca.workshops.MultiClientChatApplication.Server;

import com.tavisca.workshops.MultiClientChatApplication.ClientsConnectedList;

import java.io.IOException;
import java.net.Socket;

public class ReceiveDataFromClients implements Runnable {
    Server server;
    Socket clientSocket;

    ReceiveDataFromClients(Server server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = server.receiveData(clientSocket);
                System.out.println(clientSocket.getRemoteSocketAddress() + " : " + message);
                for (Socket clientSockets : ClientsConnectedList.clientSocketsMap.keySet()) {
                    if (!clientSocket.equals(clientSockets)) {
                        String clientName = ClientsConnectedList.clientSocketsMap.get(clientSockets);
                        server.sendData(clientSockets, clientName + " : " + message);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to receive data from client !!!");
        }

//        Thread newReceiveThread = new Thread(new ReceiveDataFromClients(this.server,this.clientSocket));
    }
}
