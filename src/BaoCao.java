import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;

public class BaoCao {
    private DanhSachSanPham dsSanPham;
    private DanhSachHoaDonBan dsHoaDonBan;
    private DanhSachHoaDonNhap dsHoaDonNhap;

    public BaoCao(DanhSachSanPham dsSP, DanhSachHoaDonBan dsHDB, DanhSachHoaDonNhap dsHDN) {
        this.dsSanPham = dsSP;
        this.dsHoaDonBan = dsHDB;
        this.dsHoaDonNhap = dsHDN;
    }


    public void baoCaoTonKho() {
        System.out.println("\n===== BAO CAO TON KHO =====");
        System.out.printf("%-10s %-25s %-15s\n", "Ma SP", "Ten san pham", "So luong ton");
        for (SanPham sp : dsSanPham.getDanhSachSanPham()) {
            System.out.printf("%-10s %-25s %-15d\n",
                    sp.getMaSanPham(), sp.getTenSanPham(), sp.getSoLuongTon());
        }
    }

    public void baoCaoSoLuongBan(LocalDate tuNgay, LocalDate denNgay) {
        System.out.println("\n===== BAO CAO SO LUONG DA BAN =====");
        System.out.printf("Tu ngay %s den ngay %s\n", tuNgay, denNgay);
        System.out.printf("%-10s %-25s %-15s\n", "Ma SP", "Ten san pham", "So luong ban");

        // Map tam luu tong so luong ban tung san pham
        java.util.HashMap<String, Integer> tongBan = new java.util.HashMap<>();

        for (HoaDonBanHang hd : dsHoaDonBan.getDsHoaDonBanHang()) {
            if (!hd.getNgayLap().isBefore(tuNgay) && !hd.getNgayLap().isAfter(denNgay)) {
                for (Map.Entry<String, Integer> entry : hd.getChiTietHoaDon().entrySet()) {
                    tongBan.merge(entry.getKey(), entry.getValue(), Integer::sum);
                }
            }
        }

        for (Map.Entry<String, Integer> entry : tongBan.entrySet()) {
            SanPham sp = dsSanPham.timKiemDoiTuong(entry.getKey());
            if (sp != null) {
                System.out.printf("%-10s %-25s %-15d\n",
                        sp.getMaSanPham(), sp.getTenSanPham(), entry.getValue());
            }
        }
    }

    public void baoCaoSoLuongNhap(LocalDate tuNgay, LocalDate denNgay) {
        System.out.println("\n===== BAO CAO SO LUONG DA NHAP =====");
        System.out.printf("Tu ngay %s den ngay %s\n", tuNgay, denNgay);
        System.out.printf("%-10s %-25s %-15s\n", "Ma SP", "Ten san pham", "So luong nhap");

        java.util.HashMap<String, Integer> tongNhap = new java.util.HashMap<>();

        for (HoaDonNhap hd : dsHoaDonNhap.getDsHoaDonNhap()) {
            if (!hd.getNgayLap().isBefore(tuNgay) && !hd.getNgayLap().isAfter(denNgay)) {
                for (Map.Entry<String, Integer> entry : hd.getChiTietHoaDon().entrySet()) {
                    tongNhap.merge(entry.getKey(), entry.getValue(), Integer::sum);
                }
            }
        }
        for (Map.Entry<String, Integer> entry : tongNhap.entrySet()) {
            SanPham sp = dsSanPham.timKiemDoiTuong(entry.getKey());
            if (sp != null) {
                System.out.printf("%-10s %-25s %-15d\n",
                        sp.getMaSanPham(), sp.getTenSanPham(), entry.getValue());
            }
        }
    }

    public void baoCaoLoiNhuan(LocalDate tuNgay, LocalDate denNgay) {
        double tongDoanhThu = 0;
        double tongGiaVon = 0;

        for (HoaDonBanHang hd : dsHoaDonBan.getDsHoaDonBanHang()) {
            if (!hd.getNgayLap().isBefore(tuNgay) && !hd.getNgayLap().isAfter(denNgay)) {
                for (Map.Entry<String, Integer> entry : hd.getChiTietHoaDon().entrySet()) {
                    SanPham sp = dsSanPham.timKiemDoiTuong(entry.getKey());
                    if (sp != null) {
                        int soLuong = entry.getValue();
                        tongDoanhThu += sp.getGiaBan() * soLuong;
                        tongGiaVon += sp.getGiaNhap() * soLuong;
                    }
                }
            }
        }
        double loiNhuan = tongDoanhThu - tongGiaVon;
        System.out.println("\n===== BAO CAO LOI NHUAN =====");
        System.out.printf("Tu ngay: %s den ngay: %s\n", tuNgay, denNgay);
        System.out.printf("Tong doanh thu: %.2f\n", tongDoanhThu);
        System.out.printf("Tong gia von: %.2f\n", tongGiaVon);
        System.out.printf("=> Loi nhuan: %.2f\n", loiNhuan);
    }

    public void menuBaoCao() {
        Scanner sc = new Scanner(System.in);
        int chonBaoCao;
        do {
            System.out.println("\n=========== MENU BAO CAO ===========");
            System.out.println("1. Bao cao ton kho");
            System.out.println("2. Bao cao so luong da ban theo khoang thoi gian");
            System.out.println("3. Bao cao so luong da nhap theo khoang thoi gian");
            System.out.println("4. Bao cao loi nhuan theo khoang thoi gian");
            System.out.println("0. Thoat");
            System.out.print("Nhap lua chonBaoCao: ");
            chonBaoCao = Integer.parseInt(sc.nextLine());

            switch (chonBaoCao) {
                case 1 -> baoCaoTonKho();
                case 2 -> {
                    LocalDate tuNgay = nhapNgay(sc, "Nhap ngay bat dau (yyyy-MM-dd): ");
                    LocalDate denNgay = nhapNgay(sc, "Nhap ngay ket thuc (yyyy-MM-dd): ");
                    baoCaoSoLuongBan(tuNgay, denNgay);
                }
                case 3 -> {
                    LocalDate tuNgay = nhapNgay(sc, "Nhap ngay bat dau (yyyy-MM-dd): ");
                    LocalDate denNgay = nhapNgay(sc, "Nhap ngay ket thuc (yyyy-MM-dd): ");
                    baoCaoSoLuongNhap(tuNgay, denNgay);
                }
                case 4 -> {
                    LocalDate tuNgay = nhapNgay(sc, "Nhap ngay bat dau (yyyy-MM-dd): ");
                    LocalDate denNgay = nhapNgay(sc, "Nhap ngay ket thuc (yyyy-MM-dd): ");
                    baoCaoLoiNhuan(tuNgay, denNgay);
                }
                case 0 -> System.out.println("Da thoat menu bao cao.");
                default -> System.out.println("Lua chonBaoCao khong hop le, vui long nhap lai.");
            }
        } while (chonBaoCao != 0);
    }
    private LocalDate nhapNgay(Scanner sc, String thongBao) {
        System.out.print(thongBao);
        return LocalDate.parse(sc.nextLine());
    }
}
