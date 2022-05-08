package client.menu;

import client.accountplayer.PlayAccount;
import food.Foot;
import input.Input;
import readandwritefile.ReadAndWriteAccountFile;;
import sever.menusever.Menu;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

public class HandlePlayerMenu extends Thread {
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";
    Socket socket;

    {
        try {
            socket = new Socket("localhost", 2006);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
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
            System.out.println("---------- ĐĂNG NHẬP MÁY ----------");
            System.out.println("NHẬP TÊN TÀI KHOẢN");
            userName = Input.inputText(passWord);
            System.out.println("NHẬP TÊN MẬT KHẨU");
            passWord = Input.inputText(passWord);
            for (PlayAccount account : accountList) {
                if (account.getUserName().equals(userName) && account.getPassWord().equals(passWord)) {
                    System.out.println(ANSI_GREEN + "Đăng nhập thành công !" + ANSI_RESET);
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
                case 4:
                    handleChat();
                    break;
                case 0:
                    handlePlayerLogin();
                    break;
                default:
                    System.out.println(ANSI_YELLOW + "Không có lựa chọn này vui lòng nhập lại!" + ANSI_RESET);
            }
        } while (choose != 0);
    }

    public void handleMenuOder() throws IOException {
        List<Foot> readFromFileFood = ReadAndWriteAccountFile.readFromFileFood();
        int amountNew = 1;
        String product = "";
        System.out.println("NHẬP ĐỒ ĂN CẦN ODER : ");
        int amountRest = 0;
        product = Input.inputText(product);
        boolean checkProduct = true;
        boolean checkAmount = true;
        int index = 1;
        for (Foot foot : readFromFileFood) {
            if (foot.getProduct().equalsIgnoreCase(product.trim())) {
                System.out.println("NHẬP SỐ LƯỢNG ĐỒ ĂN :  ");
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
                    System.out.println(ANSI_CYAN + "Đã oder " + amountNew + " " + foot.getProduct());
                    PrintStream ps = new PrintStream(socket.getOutputStream());
                    ps.println(ANSI_CYAN + "Khách : Khách đã oder " + amountNew + " " + foot.getProduct());
                    checkAmount = false;

                }
                checkProduct = false;
            }
        }
        if (checkProduct) {
            System.out.println(ANSI_YELLOW + "Không tìm thấy đồ ăn này!" + ANSI_RESET);
        }
        if (checkAmount) {
            System.err.println("Không đủ số lượng thiếu : " + (-amountRest));
        }
        ReadAndWriteAccountFile.writeToFileFootNoAppend(readFromFileFood);
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
        ps.println(ANSI_PURPLE + "Khách : " + inbox + ANSI_RESET);
    }

    @Override
    public void run() {
        connect();
    }
}
