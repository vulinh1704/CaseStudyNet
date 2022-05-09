package sever.menusever;

import client.accountplayer.PlayAccount;
import client.accountplayer.PlayAccountManagement;
import food.FoodManagement;
import food.Foot;
import input.Input;
import readandwritefile.ReadAndWriteAccountFile;
import sever.Main;
import sever.accountsever.AccountManagement;
import sever.accountsever.MasterAccount;
import validate.ValiDate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

public class HandleServerMenu extends Thread {
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    private final AccountManagement accountManagement = new AccountManagement();
    private final PlayAccountManagement playAccountManagement = new PlayAccountManagement();
    private final FoodManagement foodManagement = new FoodManagement();

    public void handleAccountMenu() {
        int choose = -1;
        Menu.showStartMenu();
        do {
            choose = Input.inputNumber(choose);
            switch (choose) {
                case 1:
                    registrationProcessing();
                    Menu.showStartMenu();
                    break;
                case 2:
                    handleLogin();
                    handleMainMenu();
                    break;
                case 3:
                    handleForgotPrw();
                    Menu.showStartMenu();
                    break;
                default:
                    System.out.println(ANSI_YELLOW + "Không có lựa chọn này vui lòng nhập lại!" + ANSI_RESET);
            }
        } while (choose != 0);
    }

    public void registrationProcessing() {
        String userName = "";
        String passWord = "";
        do {
            System.out.println("---------- ĐĂNG KÍ ----------");
            System.out.println("NHẬP TÊN TÀI KHOẢN (linh1704@gmail.com)");
            userName = Input.inputText(userName);
            if (!ValiDate.getValiDateUsrSv(userName)) {
                System.err.println("Sai định dạng nhập lại !");
            }
            if (checkAccountExists(userName)) {
                System.err.println("Tài khoản đã tồn tại !");
            }
        } while (!ValiDate.getValiDateUsrSv(userName) || checkAccountExists(userName));

        do {
            System.out.println("NHẬP MẬT KHẨU (ít nhất 1 chữ 1 số)");
            passWord = Input.inputText(passWord);
            if (!ValiDate.getValiDatePswSv(passWord)) {
                System.err.println("Sai định dạng nhập lại !");
            }
        } while (!ValiDate.getValiDatePswSv(passWord));
        System.out.println(ANSI_GREEN + "Đăng kí thành công !" + ANSI_RESET);
        MasterAccount masterAccount = new MasterAccount(userName, passWord);
        accountManagement.registerAnAccount(masterAccount);
    }

    public boolean checkAccountExists(String userName) {
        List<MasterAccount> accountList = ReadAndWriteAccountFile.readFromFileAccount();
        for (MasterAccount account : accountList) {
            if (account.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public void handleLogin() {
        List<MasterAccount> accountList = ReadAndWriteAccountFile.readFromFileAccount();
        String userName = "";
        String passWord = "";
        boolean checkLogin = true;
        do {
            System.out.println("---------- ĐĂNG NHẬP ----------");
            System.out.println("NHẬP TÊN TÀI KHOẢN");
            userName = Input.inputText(passWord);
            System.out.println("NHẬP MẬT KHẨU");
            passWord = Input.inputText(passWord);
            for (MasterAccount account : accountList) {
                if (account.getUserName().equals(userName) && account.getPassWord().equals(passWord)) {
                    System.out.println(ANSI_GREEN + "Đăng nhập thành công !" + ANSI_RESET);
                    checkLogin = false;
                    break;
                }
            }
            if (checkLogin) {
                System.err.println("Sai tài khoản hoặc mật khẩu !");
                handleAccountMenu();
            }
        } while (checkLogin);
    }

    public void handleForgotPrw() {
        int choose = -1;
        do {
            Menu.showForgotPwdMenu();
            choose = Input.inputNumber(choose);
            switch (choose) {
                case 1:
                    showAccount();
                    break;
                case 2:
                    handlingPasswordChange();
                    break;
                case 0:
                    break;
                default:
                    System.out.println(ANSI_YELLOW + "Không có lựa chọn trong menu!" + ANSI_RESET);
            }

        } while (choose != 0);
    }

    public void showAccount() {
        List<MasterAccount> accountList = ReadAndWriteAccountFile.readFromFileAccount();
        String userName = "";
        System.out.println("NHẬP TÊN TÀI KHOẢN : ");
        userName = Input.inputText(userName);
        boolean checkAccount = true;
        for (MasterAccount account : accountList) {
            if (userName.equals(account.getUserName())) {
                System.out.println("[ Tên Tài Khoản : " + account.getUserName()
                        + " ; Mật Khẩu : " + account.getPassWord() + " ]");
                checkAccount = false;
            }
        }
        if (checkAccount) {
            System.out.println(ANSI_YELLOW + "Không tìm thấy tài khoản này!" + ANSI_RESET);
        }
    }

    public void handlingPasswordChange() {
        try {
            Socket socket = new Socket("localhost", 1704);
            PrintStream ps = new PrintStream(socket.getOutputStream());
            String userName = "";
            System.out.println("NHẬP TÊN TÀI KHOẢN CẦN ĐỔI LÊN SEVER : ");
            userName = Input.inputText(userName);
            ps.println(userName);

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String reply = br.readLine();
            System.out.println(reply);
            changePwd(reply, userName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changePwd(String reply, String userName) {
        String newPwd = "";
        List<MasterAccount> accountList = ReadAndWriteAccountFile.readFromFileAccount();
        if ("Máy chủ : Hãy nhập mật khẩu mới (ít nhất 1 chữ 1 số)!".equals(reply)) {
            do {
                newPwd = Input.inputText(newPwd);
                if (!ValiDate.getValiDatePswSv(newPwd)) {
                    System.err.println("Sai định dạng nhập lại !");
                }
            } while (!ValiDate.getValiDatePswSv(newPwd));
            for (MasterAccount account : accountList) {
                if (account.getUserName().equals(userName)) account.setPassWord(newPwd);
            }
            System.out.println(ANSI_GREEN + "Cập nhật mật khẩu thành công" + ANSI_RESET);
            ReadAndWriteAccountFile.writeToFileAccountNoAppend(accountList);
        }
    }

    public void handleMainMenu() {
        int choose = -1;
        do {
            Menu.showMainMenu();
            choose = Input.inputNumber(choose);
            switch (choose) {
                case 1:
                    registrationAccPlay();
                    break;
                case 2:
                    topUpAccount();
                    break;
                case 3:
                    handleFoodManagement();
                    break;
                case 4:
                    disPlayAccounts();
                    break;
                case 5:
                    Main main = new Main();
                    main.handleChat();
                    break;
                case 0:
                    handleAccountMenu();
                    break;
                default:
                    System.out.println(ANSI_YELLOW + "Không có lựa chọn này vui lòng nhập lại!" + ANSI_RESET);
            }
        } while (choose != 0);
    }

    public void registrationAccPlay() {
        String userName = "";
        String passWord = "";
        int moneyAccount = 0;
        do {
            System.out.println("---------- ĐĂNG KÍ TÀI KHOẢN NGƯỜI CHƠI ----------");
            System.out.println("NHẬP TÊN TÀI KHOẢN (linh1704)");
            userName = Input.inputText(userName);
            if (!ValiDate.getValiDateUsrPlay(userName)) {
                System.err.println("Sai định dạng nhập lại !");
            }
            if (checkAccountPlayExists(userName)) {
                System.err.println("Tài khoản đã tồn tại !");
            }
        } while (!ValiDate.getValiDateUsrPlay(userName) || checkAccountPlayExists(userName));
        do {
            System.out.println("NHẬP MẬT KHẨU (1)");
            passWord = Input.inputText(passWord);
            if (!ValiDate.getValiDatePswPlay(passWord)) {
                System.err.println("Sai định dạng nhập lại !");
            }
        } while (!ValiDate.getValiDatePswPlay(passWord));
        do {
            System.out.println("NẠP TIỀN VÀO TÀI KHOẢN");
            moneyAccount = Input.inputNumber(moneyAccount);
            if (moneyAccount <= 20000) System.err.println("Vui lòng nhập số tiền tối thiểu 20.000");
        } while (moneyAccount <= 20000);
        System.out.println(ANSI_GREEN + "Cấp tài khoản thành công !" + ANSI_RESET);
        PlayAccount playAccount = new PlayAccount(userName, passWord, moneyAccount);
        playAccountManagement.registerAnAccount(playAccount);
    }

    public boolean checkAccountPlayExists(String userName) {
        List<PlayAccount> accountList = ReadAndWriteAccountFile.readFromFileAccountPlay();
        for (PlayAccount account : accountList) {
            if (account.getUserName().equals(userName)) {

                return true;
            }
        }
        return false;
    }

    public void handleFoodManagement() {
        int choose = -1;
        do {
            Menu.showMenuManagement();
            choose = Input.inputNumber(choose);
            switch (choose) {
                case 1:
                    showFood();
                    break;
                case 2:
                    addFood();
                    break;
                case 3:
                    editAmountFood();
                    break;
                case 4:
                    oderFood();
                    break;
                case 0:
                    handleMainMenu();
                    break;
                default:
                    System.out.println(ANSI_YELLOW + "Không có lựa chọn này vui lòng nhập lại!" + ANSI_RESET);
            }
        } while (choose != 0);
    }

    public void addFood() {
        List<Foot> readFromFileFood = ReadAndWriteAccountFile.readFromFileFood();
        String product = "";
        int amount = 0;
        Foot foot = null;
        System.out.println("NHẬP TÊN ĐỒ ĂN CẦN THÊM : ");
        product = Input.inputText(product);
        System.out.println("NHẬP SỐ LƯỢNG :  ");
        amount = Input.inputNumber(amount);
        boolean checkFoot = false;
        for (Foot f : readFromFileFood) {
            if (f.getProduct().equals(product)) System.out.println(ANSI_YELLOW + "Đã có đồ ăn này !" + ANSI_RESET);
            checkFoot = true;
        }
        if (checkFoot) {
            foot = new Foot(product, amount);
            System.out.println(ANSI_GREEN + "Đã thêm danh sách thành công !" + ANSI_RESET);
            foodManagement.addFoot(foot);
        }
    }

    public void showFood() {
        List<Foot> readFromFileFood = ReadAndWriteAccountFile.readFromFileFood();
        for (Foot foot : readFromFileFood) {
            System.out.println("TÊN MÓN ĂN :  " + foot.getProduct() + " - SỐ LƯỢNG : " + foot.getAmount());
        }
        System.out.println("\n");
    }

    public void editAmountFood() {
        List<Foot> readFromFileFood = ReadAndWriteAccountFile.readFromFileFood();
        int amountNew = 1;
        String product = "";
        System.out.println("NHẬP TÊN ĐỒ ĂN CẦN THÊM ");
        product = Input.inputText(product);
        boolean checkProduct = true;
        int index = 1;
        for (Foot foot : readFromFileFood) {
            if (foot.getProduct().equals(product)) {
                System.out.println("NHẬP SỐ LƯỢNG MỚI CHO ĐỒ ĂN ");
                amountNew = Input.inputNumber(amountNew);
                foot.setAmount(amountNew);
                for (int i = 0; i < readFromFileFood.size(); i++) {
                    if (readFromFileFood.get(i).getProduct().equals(product)) {
                        index = i;
                    }
                }
                readFromFileFood.set(index, foot);
                System.out.println(ANSI_GREEN + "Đã thay đổi số lượng của " + foot.getProduct() + ANSI_RESET);
                checkProduct = false;
            }
        }
        if (checkProduct) {
            System.out.println(ANSI_YELLOW + "Không tìm thấy đồ ăn này!" + ANSI_RESET);
        }
        ReadAndWriteAccountFile.writeToFileFootNoAppend(readFromFileFood);
    }

    public static void oderFood() {
        List<Foot> readFromFileFood = ReadAndWriteAccountFile.readFromFileFood();
        int amountNew = 1;
        String product = "";
        System.out.println("NHẬP TÊN ĐỒ ĂN CẦN ODER : ");
        int amountRest = 0;
        product = Input.inputText(product);
        boolean checkProduct = true;
        boolean checkAmount = true;
        int index = 1;
        for (Foot foot : readFromFileFood) {
            if (foot.getProduct().equalsIgnoreCase(product.trim())) {
                System.out.println("NHẬP SỐ LƯỢNG ĐỒ ĂN : ");
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
                    System.out.println(ANSI_CYAN + "Đã oder " + amountNew + " " + foot.getProduct() + " !" + ANSI_RESET);
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

    public void disPlayAccounts() {
        List<PlayAccount> playAccounts = ReadAndWriteAccountFile.readFromFileAccountPlay();
        String playAcc = "";
        System.out.println("NHẬP TÊN TÀI KHOẢN KHÁCH");
        playAcc = Input.inputText(playAcc);
        boolean checkPlayAcc = true;
        for (PlayAccount p : playAccounts) {
            if (playAcc.equals(p.getUserName())) {
                System.out.println("TÀI KHOẢN : " + p.getUserName() + " , MẬT KHẨU : " + p.getPassWord() + " , SỐ TIỀN : " + p.moneyAccount);
                checkPlayAcc = false;
            }
        }
        if (checkPlayAcc) System.out.println(ANSI_YELLOW + " Không tìm thấy tài khoản này" + ANSI_RESET);
    }

    public void topUpAccount() {
        List<PlayAccount> playAccounts = ReadAndWriteAccountFile.readFromFileAccountPlay();
        String playAcc = "";
        int money = 0;
        System.out.println("NHẬP TÊN TÀI KHOẢN KHÁCH");
        playAcc = Input.inputText(playAcc);
        int moneyLeftOver = 0;
        boolean checkPlayAcc = true;
        boolean checkUp = false;
        for (int i = 0; i < playAccounts.size(); i++) {
            if (playAcc.equals(playAccounts.get(i).getUserName())) {
                System.out.println("NHẬP SỐ TIỀN CẦN NẠP : ");
                money = Input.inputNumber(money);
                moneyLeftOver = playAccounts.get(i).moneyAccount;
                playAccounts.get(i).setMoneyAccount(money + moneyLeftOver);
                playAccounts.set(i, playAccounts.get(i));
                checkPlayAcc = false;
                checkUp = true;
            }
        }
        if (checkUp) System.out.println(ANSI_GREEN + "Nạp tiền thành công !" + ANSI_RESET);
        if (checkPlayAcc) System.out.println(ANSI_YELLOW + " Không tìm thấy tài khoản này !" + ANSI_RESET);
        ReadAndWriteAccountFile.writeToFileAccountPlayNoAppend(playAccounts);
    }

    @Override
    public void run() {
        handleAccountMenu();
    }
}

