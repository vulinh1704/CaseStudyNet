package sever;

import sever.menusever.HandleServerMenu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    static ServerSocket sever = null;
    static Socket socket = null;
    public static void main(String[] args) {
        HandleServerMenu handleServerMenu = new HandleServerMenu();
        try {
            sever = new ServerSocket(2006);
            socket = sever.accept();
            handleServerMenu.handleAccountMenu();
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String request = br.readLine();
            System.out.println(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
