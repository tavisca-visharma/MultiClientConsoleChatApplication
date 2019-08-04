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
//                System.out.println(clientSocket.getRemoteSocketAddress() + " : " + message);
                System.out.println(message);
                for (Socket clientSockets : ClientsConnectedList.clientSocketsMap.keySet()) {
                    if (!clientSocket.equals(clientSockets)) {
                        server.sendData(clientSockets, message);
                    }
                }
            }
        } catch (IOException e) {
            ClientsConnectedList.clientSocketsMap.remove(clientSocket);
            System.out.println("Client Exited !!!");
        }

//        Thread newReceiveThread = new Thread(new ReceiveDataFromClients(this.server,this.clientSocket));
    }
}
