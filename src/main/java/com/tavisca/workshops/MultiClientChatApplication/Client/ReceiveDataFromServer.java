package com.tavisca.workshops.MultiClientChatApplication.Client;

import java.io.IOException;

class ReceiveDataFromServer implements Runnable {
    Client client;

    ReceiveDataFromServer(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String receivedData = client.receiveData();
                System.out.println(receivedData);
            }
        } catch(IOException e){
                e.printStackTrace();
        }

//        Thread t1 = new Thread(new ReceiveDataFromServer(client));
//        t1.start();
    }
}

