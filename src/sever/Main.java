package sever;

import sever.menusever.HandleServerMenu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Main extends Thread {
    static ServerSocket sever;

    static {
        try {
            sever = new ServerSocket(2006);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Socket socket;

    static {
        try {
            socket = sever.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String reply = br.readLine();
            System.out.println(reply);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HandleServerMenu handleServerMenu = new HandleServerMenu();
        Main main = new Main();
        main.start();
        handleServerMenu.handleAccountMenu();
    }
}
