package com.tavisca.workshops.MultiClientChatApplication.Server;

import com.tavisca.workshops.MultiClientChatApplication.ClientsConnectedList;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class AcceptClients implements Runnable {
    Server server;
    Scanner scanner;
    Socket clientSocket;

    AcceptClients(Server server) {
        scanner = new Scanner(System.in);
        this.server = server;
    }

    @Override
    public void run() {
        try {
            clientSocket = server.acceptClients();
            System.out.println(clientSocket.getRemoteSocketAddress());
            String clientDetails = server.receiveData(clientSocket);
            if (clientDetails.startsWith("Client Details : ")) {
                String clientName = clientDetails.substring(17).trim();
                System.out.println(clientName + " added");
                ClientsConnectedList.socketsClientNameMap.put(clientSocket, clientName);
                ClientsConnectedList.clientNameSocketMap.put(clientName,clientSocket);
            } else {
                server.sendData(clientSocket, "Please Provide Client Information.\nClosing Connection...");
                clientSocket.close();
            }
        } catch (IOException e) {
            System.out.println("Client can't be connected !!!");
        }


        Thread newThread = new Thread(new AcceptClients(this.server));
        newThread.start();

        Thread receiveDataFromClients = new Thread(new ReceiveDataFromClients(server, clientSocket));
        receiveDataFromClients.start();

        /*while (true) {
//            System.out.print("You : ");
            String message = scanner.nextLine();
            try {
                for (Socket clientSockets : ClientsConnectedList.clientSocketsMap.keySet()) {
                    if (true || !clientSocket.equals(clientSockets)) {
                        String clientName = ClientsConnectedList.clientSocketsMap.get(clientSockets);
                        server.sendData(clientSockets, clientName + " : " + message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }
}
