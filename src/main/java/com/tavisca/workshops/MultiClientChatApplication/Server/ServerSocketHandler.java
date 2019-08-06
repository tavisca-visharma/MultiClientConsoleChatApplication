package com.tavisca.workshops.MultiClientChatApplication.Server;

import com.tavisca.workshops.MultiClientChatApplication.ClientsConnectedList;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ServerSocketHandler {
    public static void main(String[] args) {
        Server server = null;
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the Server Name : ");
            String serverName = scanner.nextLine();
            server = new Server(8000, serverName);
            System.out.println("Server Started ...");
            System.out.println("=========================");

        } catch (IOException e) {
            System.out.println("Server startup failed !!!");
        }

        Thread acceptClientsThread = new Thread(new AcceptClients(server));
        acceptClientsThread.start();


        Scanner scanner = new Scanner(System.in);
        while (true) {
//            System.out.print("You : ");
            String message = scanner.nextLine();
            try {
                for (Socket clientSockets : ClientsConnectedList.socketsClientNameMap.keySet()) {
//                    String clientName = ClientsConnectedList.clientSocketsMap.get(clientSockets);
                    server.sendData(clientSockets, server.getServerName() + " : " + message);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
