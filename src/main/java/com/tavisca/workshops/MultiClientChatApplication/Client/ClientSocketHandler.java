package com.tavisca.workshops.MultiClientChatApplication.Client;

import com.tavisca.workshops.MultiClientChatApplication.ClientsConnectedList;

import java.io.IOException;
import java.util.Scanner;

public class ClientSocketHandler {

    static Client client = null;

    public static void main(String[] args) {

        try {

            client = new Client("127.0.0.1", 8000);
            System.out.println("Client Connected To Server...");
            System.out.println("----------------------------------\n");

            Thread receiveThread = new Thread(new ReceiveDataFromServer(client));
            receiveThread.start();

            Scanner scanner = new Scanner(System.in);

            while (true) {
                try {
//                    System.out.print("You : ");
                    String dataToSend = scanner.nextLine();
                    client.sendData(client.getClientName() + " : "+ dataToSend);
                } catch (IOException e) {
                    System.out.println("Client is unable to send data !!!");
                }
            }

        } catch (IOException e) {
            System.out.println("Client is unable to connect !!!");
        }
    }

}

