package sever.menusever;


import readandwritefile.ReadAndWriteAccountFile;
import sever.accountsever.MasterAccount;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


public class SeverCheckAccount {
    public void connect() {
        System.out.println("Đang đợi yêu cầu ....");
        ServerSocket server = null;
        try {
            server = new ServerSocket(1704);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                Socket socket = server.accept();
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String userName = "";
                userName = br.readLine();
                System.out.println("TÊN TÀI KHOẢN CẦN CHECK : " + userName);
                PrintStream ps = new PrintStream(socket.getOutputStream());
                if (checkUserNameFromClint(userName)) {
                    ps.println("Máy chủ: Hãy nhập mật khẩu mới (ít nhất 1 chữ 1 số)!");
                } else {
                    ps.println("Máy chủ: Không tìm thấy tài khoản này!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkUserNameFromClint(String userName) {
        List<MasterAccount> accountList = ReadAndWriteAccountFile.readFromFileAccount();
        for (MasterAccount account : accountList) {
            if (account.getUserName().equals(userName)) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        SeverCheckAccount severCheckAccount = new SeverCheckAccount();
        severCheckAccount.connect();
    }
}
