package client.menu;

import client.accountplayer.PlayAccount;
import food.Foot;
import input.Input;
import readandwritefile.ReadAndWriteAccountFile;
import sever.menusever.HandleServerMenu;
import sever.menusever.Menu;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

public class HandlePlayerMenu extends Thread {
    Socket socket;

    {
        try {
            socket = new Socket("localhost", 2006);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        handlePlayerLogin();
        handlePlayerMenu();
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
                    try {
                        handleMenuOder();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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

    public void handleMenuOder() throws IOException {
        List<Foot> readFromFileFood = ReadAndWriteAccountFile.readFromFileFood();
        int amountNew = 1;
        String product = "";
        System.out.println("Nhập tên đồ ăn cần oder");
        int amountRest = 0;
        product = Input.inputText(product);
        boolean checkProduct = true;
        boolean checkAmount = true;
        int index = 1;
        for (Foot foot : readFromFileFood) {
            if (foot.getProduct().equalsIgnoreCase(product.trim())) {
                System.out.println("Nhập số lượng đồ ăn : ");
                amountNew = Input.inputNumber(amountNew);
                amountRest = foot.getAmount() - amountNew;
                if (amountRest >= 0) {
                    foot.setAmount(amountRest);
                    for (int i = 0; i < readFromFileFood.size(); i++) {
                        if (readFromFileFood.get(i).getProduct().equals(product)) {
                            index = i;
                        }
                    }
                    readFromFileFood.set(index, foot);
                    System.out.println("Đã oder " + amountNew + " " + foot.getProduct());
                    PrintStream ps = new PrintStream(socket.getOutputStream());
                    ps.println("Máy chủ : Khách đã oder " + amountNew + " " + foot.getProduct());
                    checkAmount = false;

                }
                checkProduct = false;
            }
        }
        if (checkProduct) {
            System.out.println("Không tìm thấy đồ ăn này!");
        }
        if (checkAmount) {
            System.out.println("Không đủ số lượng thiếu : " + (-amountRest));
        }
        ReadAndWriteAccountFile.writeToFileFootNoAppend(readFromFileFood);
    }

    @Override
    public void run() {
        connect();
    }
}
