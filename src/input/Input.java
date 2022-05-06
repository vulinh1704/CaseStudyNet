package input;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {
    public static int inputNumber(int num) {
        Scanner inN = new Scanner(System.in);
            try {
                num = inN.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("Nhập sai định dạng !");
                inN.nextLine();
                num = -1;
            }
        return num;
    }
    public static String inputText(String text){
        Scanner inT = new Scanner(System.in);
        text = inT.nextLine();
        return text;
    }
}
