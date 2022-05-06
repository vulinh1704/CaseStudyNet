package sever.menusever;

public class Menu {
    public static void showStartMenu() {
        System.out.println("---------- Menu Máy Chủ ----------\n" +
                "1.Đăng Kí\n" +
                "2.Đăng nhập\n" +
                "3.Quên mật khẩu\n" +
                "Mời nhập lựa chọn của bạn !");
    }

    public static void showForgotPwdMenu() {
        System.out.println("------ Menu quên mật khẩu ------\n" +
                "1.Hiển thị tài khoản\n" +
                "2.Đổi mật khẩu\n" +
                "0.Thoát");
    }

    public static void showMainMenu() {
        System.out.println("------ Menu chính ------\n" +
                "1.Đăng kí tài khoản cho khách\n" +
                "2.Nạp tài khoản cho khách\n" +
                "3.Quản lý đồ ăn\n" +
                "4.Tính tiền\n" +
                "5.Xem tài khoản khách\n" +
                "6.Chat\n" +
                "0.Đăng xuất");
    }

    public static void showMenuManagement() {
        System.out.println("------ Menu quản lý đồ ăn ------\n" +
                "1.Xem menu đồ ăn\n" +
                "2.Thêm vào danh đồ ăn\n" +
                "3.Sửa số lượng đồ ăn\n" +
                "4.Odder đồ ăn\n" +
                "0.Thoát");
    }

    // ------------------------------------------ Máy ----------------------------------------------------
    public static void showMenuPlayer(){
        System.out.println("---------- Menu Máy Chơi ----------\n" +
                "1.Xem số tiền trong tài khoản\n" +
                "2.Oder đồ ăn\n" +
                "3.Nạp tài khoản\n" +
                "4.Chat\n" +
                "0.Đăng xuất");
    }
}
