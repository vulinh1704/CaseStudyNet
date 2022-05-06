package client.accountplayer;

public class PlayAccount {
    private String userName;
    private String passWord;
    public int moneyAccount;

    public PlayAccount() {
    }

    public PlayAccount(String userName, String passWord , int moneyAccount) {
        this.userName = userName;
        this.passWord = passWord;
        this.moneyAccount = moneyAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getMoneyAccount() {
        return moneyAccount;
    }

    public void setMoneyAccount(int moneyAccount) {
        this.moneyAccount = moneyAccount;
    }

    @Override
    public String toString() {
        return userName + "," + passWord + "," + moneyAccount;
    }
}
