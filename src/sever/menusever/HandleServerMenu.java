package sever.menusever;

import client.accountplayer.PlayAccount;
import client.accountplayer.PlayAccountManagement;
import food.FoodManagement;
import food.Foot;
import input.Input;
import readandwritefile.ReadAndWriteAccountFile;
import sever.accountsever.AccountManagement;
import sever.accountsever.MasterAccount;
import validate.ValiDate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class HandleServerMenu {
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
                    System.out.println("Không có lựa chọn này vui lòng nhập lại!");
            }
        } while (choose != 0);
    }

    public void registrationProcessing() {
        String userName = "";
        String passWord = "";
        do {
            System.out.println("---------- Đăng kí ----------");
            System.out.println("Nhập tên tài khoản (linh1704@gmail.com)");
            userName = Input.inputText(userName);
            if (!ValiDate.getValiDateUsrSv(userName)) {
                System.err.println("Sai định dạng nhập lại !");
            }
            if (checkAccountExists(userName)) {
                System.err.println("Tài khoản đã tồn tại !");
            }
        } while (!ValiDate.getValiDateUsrSv(userName) || checkAccountExists(userName));

        do {
            System.out.println("Nhập mật khẩu (ít nhất 1 chữ 1 số)");
            passWord = Input.inputText(passWord);
            if (!ValiDate.getValiDatePswSv(passWord)) {
                System.err.println("Sai định dạng nhập lại !");
            }
        } while (!ValiDate.getValiDatePswSv(passWord));
        System.out.println("Đăng kí thành công !");
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
            System.out.println("---------- Đăng nhập ----------");
            System.out.println("Nhập tên tài khoản");
            userName = Input.inputText(passWord);
            System.out.println("Nhập mật khẩu");
            passWord = Input.inputText(passWord);
            for (MasterAccount account : accountList) {
                if (account.getUserName().equals(userName) && account.getPassWord().equals(passWord)) {
                    System.out.println("Đăng nhập thành công !");
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
                    System.out.println("Không có lựa chọn trong menu!");
            }

        } while (choose != 0);
    }

    public void showAccount() {
        List<MasterAccount> accountList = ReadAndWriteAccountFile.readFromFileAccount();
        String userName = "";
        System.out.println("Nhập tên tài khoản ");
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
            System.out.println("Không tìm thấy tài khoản này!");
        }
    }

    public void handlingPasswordChange() {
        try {
            Socket socket = new Socket("localhost", 1704);
            PrintStream ps = new PrintStream(socket.getOutputStream());
            String userName = "";
            System.out.println("Nhập tên tài khoản cần đổi lên sever : ");
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
            System.out.println("Cập nhật mật khẩu thành công");
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
                    break;
                case 3:
                    handleFoodManagement();
                    break;
                case 0:
                    handleAccountMenu();
                    break;
                default:
                    System.out.println("Không có lựa chọn này vui lòng nhập lại!");
            }
        } while (choose != 0);
    }

    public void registrationAccPlay() {
        String userName = "";
        String passWord = "";
        int moneyAccount = 0;
        do {
            System.out.println("---------- Đăng kí tài khoản chơi ----------");
            System.out.println("Nhập tên tài khoản (linh1704)");
            userName = Input.inputText(userName);
            if (!ValiDate.getValiDateUsrPlay(userName)) {
                System.err.println("Sai định dạng nhập lại !");
            }
            if (checkAccountPlayExists(userName)) {
                System.err.println("Tài khoản đã tồn tại !");
            }
        } while (!ValiDate.getValiDateUsrPlay(userName) || checkAccountPlayExists(userName));
        do {
            System.out.println("Nhập mật khẩu (ít nhất 1 số)");
            passWord = Input.inputText(passWord);
            if (!ValiDate.getValiDatePswPlay(passWord)) {
                System.err.println("Sai định dạng nhập lại !");
            }
        } while (!ValiDate.getValiDatePswPlay(passWord));
        do {
            System.out.println("Nạp tiền vào tài khoản");
            moneyAccount = Input.inputNumber(moneyAccount);
            if(moneyAccount <= 20000) System.err.println("Vui lòng nhập số tiền tối thiểu 20.000");
        } while (moneyAccount <= 20000);
        System.out.println("Cấp tài khoản thành công !");
        PlayAccount playAccount = new PlayAccount(userName, passWord , moneyAccount);
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
                    System.out.println("Không có lựa chọn này vui lòng nhập lại!");
            }
        } while (choose != 0);
    }

    public void addFood() {
        List<Foot> readFromFileFood = ReadAndWriteAccountFile.readFromFileFood();
        String product = "";
        int amount = 0;
        Foot foot = null;
        System.out.println("Nhập tên đồ ăn");
        product = Input.inputText(product);
        System.out.println("Nhập số lượng ");
        amount = Input.inputNumber(amount);
        boolean checkFoot = false;
        for (Foot f : readFromFileFood) {
            if (f.getProduct().equals(product)) System.out.println("Đã có đồ ăn này !");
            checkFoot = true;
        }
        if (checkFoot) {
            foot = new Foot(product, amount);
            System.out.println("Đã thêm danh sách thành công !");
            foodManagement.addFoot(foot);
        }
    }

    public void showFood() {
        List<Foot> readFromFileFood = ReadAndWriteAccountFile.readFromFileFood();
        for (Foot foot : readFromFileFood) {
            System.out.println("Tên món ăn : " + foot.getProduct() + " - Số lượng : " + foot.getAmount());
        }
    }

    public void editAmountFood() {
        List<Foot> readFromFileFood = ReadAndWriteAccountFile.readFromFileFood();
        int amountNew = 1;
        String product = "";
        System.out.println("Nhập tên sản phẩm cần thêm");
        product = Input.inputText(product);
        boolean checkProduct = true;
        int index = 1;
        for (Foot foot : readFromFileFood) {
            if (foot.getProduct().equals(product)) {
                System.out.println("Nhập số lượng mới cho đồ ăn : ");
                amountNew = Input.inputNumber(amountNew);
                foot.setAmount(amountNew);
                for (int i = 0; i < readFromFileFood.size(); i++) {
                    if (readFromFileFood.get(i).getProduct().equals(product)) {
                        index = i;
                    }
                }
                readFromFileFood.set(index, foot);
                System.out.println("Đã thay đổi số lượng của " + foot.getProduct());
                checkProduct = false;
            }
        }
        if (checkProduct) {
            System.out.println("Không tìm thấy đồ ăn này!");
        }
        ReadAndWriteAccountFile.writeToFileFootNoAppend(readFromFileFood);
    }

    public static void oderFood() {
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

}

