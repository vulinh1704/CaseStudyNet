package client;

import client.menu.HandlePlayerMenu;
import sever.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MainPlayer extends Thread {
    static  HandlePlayerMenu handlePlayerMenu = new HandlePlayerMenu();
    @Override
    public void run() {
        Socket socket = handlePlayerMenu.getSocket();
        BufferedReader br = null;
        while (true) {
            try {
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String reply = null;
            try {
                assert br != null;
                reply = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(reply);
        }
    }

    public static void main(String[] args) {
        handlePlayerMenu.start();
        MainPlayer mainPlayer =  new MainPlayer();
        mainPlayer.start();
    }
}
