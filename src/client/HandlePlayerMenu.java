package client;

import client.accountplayer.PlayAccount;
import input.Input;
import readandwritefile.ReadAndWriteAccountFile;
import sever.menusever.HandleServerMenu;
import sever.menusever.Menu;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class HandlePlayerMenu {
    public void connect() {
        try {
            Socket socket = new Socket("localhost", 2006);
            handlePlayerLogin();
            handlePlayerMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handlePlayerLogin() {
        List<PlayAccount> accountList = ReadAndWriteAccountFile.readFromFileAccountPlay();
        String userName = "";
        String passWord = "";
        boolean checkLogin = true;
        do {
            System.out.println("---------- Đăng nhập máy ----------");
            System.out.println("Nhập tên tài khoản");
            userName = Input.inputText(passWord);
            System.out.println("Nhập mật khẩu");
            passWord = Input.inputText(passWord);
            for (PlayAccount account : accountList) {
                if (account.getUserName().equals(userName) && account.getPassWord().equals(passWord)) {
                    System.out.println("Đăng nhập thành công !");
                    checkLogin = false;
                    break;
                }
            }
            if (checkLogin) {
                System.err.println("Sai tài khoản hoặc mật khẩu !");
            }
        } while (checkLogin);
    }

    public void handlePlayerMenu() {
        int choose = -1;
        do {
            Menu.showMenuPlayer();
            choose = Input.inputNumber(choose);
            switch (choose) {
                case 1:
                    break;
                case 2:
                    HandleServerMenu.oderFood();
                    break;
                case 3:

                    break;
                case 0:
                    handlePlayerLogin();
                    break;
                default:
                    System.out.println("Không có lựa chọn này vui lòng nhập lại!");
            }
        } while (choose != 0);
    }
}
