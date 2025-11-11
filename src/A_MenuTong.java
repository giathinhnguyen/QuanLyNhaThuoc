import java.util.Scanner;

public class A_MenuTong {
    private Scanner sc = new Scanner(System.in);
    BaoCao baoCao;
    HeThongQuanLy heThongQuanLy;
    public  A_MenuTong() {
        heThongQuanLy = new HeThongQuanLy();
        baoCao = new BaoCao(heThongQuanLy.dsSP, heThongQuanLy.dsHDB, heThongQuanLy.dsHDN);
    }

    public void hienThiMenuTong() {
        int chon;
        do {
            System.out.println("\n=========== HE THONG QUAN LY NHA THUOC ===========");
            System.out.println("1. Quan ly san pham");
            System.out.println("2. Quan ly khach hang");
            System.out.println("3. Quan ly nhan vien");
            System.out.println("4. Quan ly hoa don");
            System.out.println("5. Quan ly khuyen mai");
            System.out.println("6. Quan ly nha cung cap");
            System.out.println("7. Bao cao thong ke");
            System.out.println("0. Thoat chuong trinh");
            System.out.print("Nhap lua chon: ");
            chon = Integer.parseInt(sc.nextLine());

            switch (chon) {
                case 1 -> heThongQuanLy.menuSanPham();
                case 2 -> heThongQuanLy.menuKhachHang();
                case 3 -> heThongQuanLy.menuNhanVien();
                case 4 -> menuHoaDon();
                case 5 -> heThongQuanLy.menuKhuyenMai();
                case 6 -> heThongQuanLy.menuNhaCungCap();
                case 7 -> baoCao.menuBaoCao();
                case 0 -> System.out.println("Da thoat chuong trinh!");
                default -> System.out.println("Lua chon khong hop le, vui long nhap lai!");
            }
        } while (chon != 0);
    }
    private void menuHoaDon() {
        int chon;
        do {
            System.out.println("\n===== QUAN LY HOA DON =====");
            System.out.println("1. Hoa don ban hang");
            System.out.println("2. Hoa don nhap hang");
            System.out.println("0. Quay lai");
            System.out.print("Nhap lua chon: ");
            chon = Integer.parseInt(sc.nextLine());

            switch (chon) {
                case 1 -> heThongQuanLy.menuHoaDonBan();
                case 2 -> heThongQuanLy.menuHoaDonNhap();
                case 0 -> {}
                default -> System.out.println("Lua chon khong hop le!");
            }
        } while (chon != 0);
    }
    public static void main(String[] args) throws Exception {
        A_MenuTong menuTong = new A_MenuTong();
        menuTong.hienThiMenuTong();
    }
}
