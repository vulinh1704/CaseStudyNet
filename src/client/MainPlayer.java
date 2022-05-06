package client;

import sever.menusever.HandleServerMenu;

public class MainPlayer {
    public static void main(String[] args) {
        HandlePlayerMenu handlePlayerMenu = new HandlePlayerMenu();
        handlePlayerMenu.connect();
    }
}
