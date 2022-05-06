package sever;

import sever.menusever.HandleServerMenu;

public class Main {
    public static void main(String[] args) {
        HandleServerMenu handleServerMenu = new HandleServerMenu();
        handleServerMenu.sever();
    }
}
