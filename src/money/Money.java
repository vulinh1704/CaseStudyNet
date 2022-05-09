package money;

import client.accountplayer.PlayAccount;
import client.menu.HandlePlayerMenu;
import readandwritefile.ReadAndWriteAccountFile;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Money {
    public void deductFromAccount(int index) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                List<PlayAccount> playAccounts = ReadAndWriteAccountFile.readFromFileAccountPlay();
                int money = 0;
                for (int i = 0; i < playAccounts.size(); i++) {
                    if (i == index) {
                        money = playAccounts.get(index).getMoneyAccount();
                        break;
                    }
                }
                if (money > 0) {
                    money = money - 5000;
                    for (int i = 0; i < playAccounts.size(); i++) {
                        if (i == index) {
                            playAccounts.get(i).setMoneyAccount(money);
                            playAccounts.set(i , playAccounts.get(i));
                            ReadAndWriteAccountFile.writeToFileAccountPlayNoAppend(playAccounts);
                        }
                    }
                } else {
                    System.out.println("Đã hết tiền !");
                    HandlePlayerMenu handlePlayerMenu = new HandlePlayerMenu();
                    handlePlayerMenu.handlePlayerLogin();
                }
            }
        };
        Calendar data = Calendar.getInstance();
        timer.schedule(task, data.getTime(), 20000);
    }

    public static void main(String[] args) {
        Money money = new Money();
        money.deductFromAccount(1);
    }

}

