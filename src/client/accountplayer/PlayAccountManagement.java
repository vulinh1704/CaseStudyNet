package client.accountplayer;
import readandwritefile.ReadAndWriteAccountFile;

import java.util.ArrayList;
import java.util.List;

public class PlayAccountManagement {
    private final List<PlayAccount> accountList = new ArrayList<>();

    public List<PlayAccount> getAccountList() {
        return accountList;
    }

    public void registerAnAccount(PlayAccount account) {
        accountList.add(account);
        ReadAndWriteAccountFile.writeToFileAccountPlay(accountList);
    }

    public int accountSearch(String userName) {
        for (int i = 0; i < accountList.size(); i++) {
            if (accountList.get(i).getUserName().equals(userName)) {
                return i;
            }
        }
        return -1;
    }
    public void showAccounts() {
        for (PlayAccount account : accountList) {
            System.out.println(account);
        }
    }
}
