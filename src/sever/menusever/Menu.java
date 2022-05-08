package sever.menusever;

public class Menu {
    public static void showStartMenu() {
        System.out.println("---------- MENU MÁY CHỦ ----------\n" +
                "1.ĐĂNG KÍ\n" +
                "2.ĐĂNG NHẬP\n" +
                "3.QUÊN MẬT KHẨU\n" +
                "Mời nhập lựa chọn của bạn !");
    }

    public static void showForgotPwdMenu() {
        System.out.println("------ QUÊN MẬT KHẨU ------\n" +
                "1.HIỂN THỊ TÀI KHOẢN\n" +
                "2.ĐỔI MẬT KHẨU\n" +
                "0.THOÁT");
    }

    public static void showMainMenu() {
        System.out.println("------ MENU CHÍNH ------\n" +
                "1.ĐĂNG KÍ TÀI KHOẢN CHO KHÁCH\n" +
                "2.NẠP TÀI KHOẢN CHO KHÁCH\n" +
                "3.QUẢN LÝ ĐỒ ĂN\n" +
                "4.TÍNH TIỀN\n" +
                "5.XEM TÀI KHOẢN KHÁCH\n" +
                "6.CHAT\n" +
                "0.ĐĂNG XUẤT");
    }

    public static void showMenuManagement() {
        System.out.println("------ QUẢN LÝ ĐỒ ĂN ------\n" +
                "1.XEM MENU ĐỒ ĂN\n" +
                "2.THÊM DANH SÁCH ĐỒ ĂN\n" +
                "3.SỬA SỐ LƯỢNG ĐỒ ĂN\n" +
                "4.ODER ĐỒ ĂN\n" +
                "0.THOÁT");
    }

    // ------------------------------------------ Máy ----------------------------------------------------
    public static void showMenuPlayer(){
        System.out.println("---------- MENU MÁY CHƠI ----------\n" +
                "1.XEM SỐ TIỀN TRONG TÀI KHOẢN\n" +
                "2.ODER ĐỒ ĂN\n" +
                "3.NẠP TÀI KHOẢN\n" +
                "4.CHAT\n" +
                "0.ĐĂNG XUẤT");
    }
}
