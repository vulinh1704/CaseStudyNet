package sever;

import input.Input;
import sever.menusever.HandleServerMenu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main extends Thread {
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RESET = "\u001B[0m";
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
            while (true) {
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String reply = br.readLine();
                System.out.println(reply);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleChat() {
        PrintStream ps = null;
        try {
            ps = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String inbox = "";
        System.out.println("NHẬP TIN NHẮN : ");
        inbox = Input.inputText(inbox);
        assert ps != null;
        ps.println( ANSI_PURPLE + "Chủ Quán : " + inbox + ANSI_RESET);
    }

    public static void main(String[] args) {
        HandleServerMenu handleServerMenu = new HandleServerMenu();
        Main main = new Main();
        main.start();
        handleServerMenu.start();
    }
}
