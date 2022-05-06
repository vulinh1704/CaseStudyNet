package sever.accountsever;

import readandwritefile.ReadAndWriteAccountFile;

import java.util.ArrayList;
import java.util.List;

public class AccountManagement {
    private final List<MasterAccount> accountList = new ArrayList<>();

    public List<MasterAccount> getAccountList() {
        return accountList;
    }

    public void registerAnAccount(MasterAccount account) {
        accountList.add(account);
        ReadAndWriteAccountFile.writeToFileAccount(accountList);
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
        for (MasterAccount account : accountList) {
            System.out.println(account);
        }
    }
}
