package com.tavisca.workshops.MultiClientChatApplication;

import java.net.Socket;
import java.util.HashMap;

public class ClientsConnectedList {
    public static HashMap<Socket,String> clientSocketsMap = new HashMap<Socket, String>();
}
