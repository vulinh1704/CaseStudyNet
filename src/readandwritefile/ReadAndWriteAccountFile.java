package readandwritefile;

import client.accountplayer.PlayAccount;
import food.FoodManagement;
import food.Foot;
import input.Input;
import sever.accountsever.MasterAccount;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadAndWriteAccountFile {
    public static final String PATH_ACCOUNT = "account.csv";
    public static final String PATH_ACCOUNT_PLAY = "account_play.csv";
    public static final String PATH_FOOT = "foot.csv";

    public static void writeToFileAccount(List<MasterAccount> list) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            String str = "";
            fw = new FileWriter(PATH_ACCOUNT, true);
            bw = new BufferedWriter(fw);
            for (MasterAccount account : list) {
                str += account + "\n";
            }
            bw.write(str);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToFileAccountNoAppend(List<MasterAccount> list) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            String str = "";
            fw = new FileWriter(PATH_ACCOUNT);
            bw = new BufferedWriter(fw);
            for (MasterAccount account : list) {
                str += account + "\n";
            }
            bw.write(str);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<MasterAccount> readFromFileAccount() {
        List<MasterAccount> accountList = new ArrayList<>();
        FileReader fr = null;
        BufferedReader br = null;
        String inf = "";
        try {
            fr = new FileReader(PATH_ACCOUNT);
            br = new BufferedReader(fr);
            while ((inf = br.readLine()) != null) {
                String[] arr = inf.split(",");
                accountList.add(new MasterAccount(arr[0], arr[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accountList;
    }

    public static void writeToFileAccountPlay(List<PlayAccount> list) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            String str = "";
            fw = new FileWriter(PATH_ACCOUNT_PLAY, true);
            bw = new BufferedWriter(fw);
            for (PlayAccount account : list) {
                str += account + "\n";
            }
            bw.write(str);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<PlayAccount> readFromFileAccountPlay() {
        List<PlayAccount> accountList = new ArrayList<>();
        FileReader fr = null;
        BufferedReader br = null;
        String inf = "";
        try {
            fr = new FileReader(PATH_ACCOUNT_PLAY);
            br = new BufferedReader(fr);
            while ((inf = br.readLine()) != null) {
                String[] arr = inf.split(",");
                accountList.add(new PlayAccount(arr[0], arr[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accountList;
    }

    public static void writeToFileFoot(List<Foot> list) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            String str = "";
            fw = new FileWriter(PATH_FOOT, true);
            bw = new BufferedWriter(fw);
            for (Foot foot : list) {
                str += foot + "\n";
            }
            bw.write(str);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToFileFootNoAppend(List<Foot> list) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            String str = "";
            fw = new FileWriter(PATH_FOOT);
            bw = new BufferedWriter(fw);
            for (Foot foot : list) {
                str += foot + "\n";
            }
            bw.write(str);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Foot> readFromFileFood() {
        List<Foot> footList = new ArrayList<>();
        FileReader fr = null;
        BufferedReader br = null;
        String inf = "";
        try {
            fr = new FileReader(PATH_FOOT);
            br = new BufferedReader(fr);
            while ((inf = br.readLine()) != null) {
                String[] arr = inf.split(",");
                footList.add(new Foot(arr[0],Integer.parseInt( arr[1])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return footList;
    }
}
