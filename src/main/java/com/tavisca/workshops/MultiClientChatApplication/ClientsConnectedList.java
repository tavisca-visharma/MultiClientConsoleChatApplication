package com.tavisca.workshops.MultiClientChatApplication;

import java.net.Socket;
import java.util.HashMap;

public class ClientsConnectedList {
    public static HashMap<Socket,String> socketsClientNameMap = new HashMap<Socket, String>();

    public static HashMap<String,Socket> clientNameSocketMap = new HashMap<String, Socket>();
}
