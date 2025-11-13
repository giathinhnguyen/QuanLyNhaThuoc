import java.time.LocalDate;
import java.util.ArrayList;

public class KhachHang extends Nguoi implements LayThongTin {
    public String maKhachHang, hangThanhVien;
    public double tongChiTieu, diemTichLuy;
    public ArrayList<String> dsMaHoaDon = new ArrayList<>();

    // ======== Getter & Setter ========

    public ArrayList<String> getDsMaHoaDon() {
        return dsMaHoaDon;
    }

    public void setDsMaHoaDon(ArrayList<String> dsMaHoaDon) {
        this.dsMaHoaDon = dsMaHoaDon;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public double getDiemTichLuy() {
        return diemTichLuy;
    }

    public void setDiemTichLuy(double diemTichLuy) {
        this.diemTichLuy = diemTichLuy;
    }

    public String getHangThanhVien() {
        return hangThanhVien;
    }

    public void setHangThanhVien(String hangThanhVien) {
        this.hangThanhVien = hangThanhVien;
    }

    public double getTongChiTieu() {
        return tongChiTieu;
    }

    public void setTongChiTieu(double tongChiTieu) {
        this.tongChiTieu = tongChiTieu;
    }


    // ======== Constructor ========
    public KhachHang(){
        super();
    }

    public KhachHang(String hoTen, String SDT, String gioiTinh, LocalDate ngaySinh, DiaChi diaChi, String maKhachHang, String hangThanhVien, double tongChiTieu, double diemTichLuy, ArrayList<String> dsMaHoaDonBan) {
        super(hoTen, SDT, gioiTinh, ngaySinh, diaChi);
        this.maKhachHang = maKhachHang;
        this.hangThanhVien = hangThanhVien;
        this.tongChiTieu = tongChiTieu;
        this.diemTichLuy = diemTichLuy;
        this.dsMaHoaDon = dsMaHoaDonBan;
    }
    public KhachHang(String hoTen, String SDT, String gioiTinh, LocalDate ngaySinh, DiaChi diaChi, String maKhachHang, String hangThanhVien, double tongChiTieu, double diemTichLuy) {
        super(hoTen, SDT, gioiTinh, ngaySinh, diaChi);
        this.maKhachHang = maKhachHang;
        this.hangThanhVien = hangThanhVien;
        this.tongChiTieu = tongChiTieu;
        this.diemTichLuy = diemTichLuy;
        this.dsMaHoaDon = new ArrayList<>();
    }
    public KhachHang(String hoTen, String SDT, String gioiTinh, LocalDate ngaySinh, DiaChi diaChi, String maKhachHang) {
        super(hoTen, SDT, gioiTinh, ngaySinh, diaChi);
        this.maKhachHang = maKhachHang;
        this.hangThanhVien = "Moi";
        this.tongChiTieu = 0;
        this.diemTichLuy = 0;
        this.dsMaHoaDon = new ArrayList<>();
    }

    public void themHoaDon(String maHD) {
        if (!dsMaHoaDon.contains(maHD)) {
            dsMaHoaDon.add(maHD);
        }
    }

    public void capNhatTongChiTieu(String maHDMoi, DanhSachHoaDonBan dsHDB, DanhSachSanPham dsSP, DanhSachKhuyenMai dsKM) {

        if (maHDMoi == null || dsHDB == null || dsSP == null || dsKM == null) {
            System.out.println("Loi: Thieu thong tin can thiet de cap nhat chi tieu.");
            return;
        }
        themHoaDon(maHDMoi);
        HoaDonBanHang hdMoi = dsHDB.timKiemDoiTuong(maHDMoi);

        if (hdMoi != null) {
            double tongTienThanhToan = hdMoi.tinhTongTienSauGiamGia(dsSP, dsKM);

            if (tongTienThanhToan > 0) {
                this.tongChiTieu += tongTienThanhToan;

                tangDiemTichLuy(tongTienThanhToan*0.00000001);

                System.out.println("Cap nhat tong chi tieu thanh cong. Tong chi tieu moi: " + String.format("%,.0f", this.tongChiTieu));
            } else {
                System.out.println("Canh bao: Hoa don moi khong co tong tien hop le (> 0).");
            }
        } else {
            System.out.println("Canh bao: Khong tim thay Hoa Don co ma " + maHDMoi + " trong danh sach.");
        }
    }

    public void tangDiemTichLuy(double diem) {
        this.diemTichLuy += diem;
        capNhatHangThanhVien();
    }

    // ======== Cập nhật hạng thành viên ========
    public void capNhatHangThanhVien() {
        if (diemTichLuy >= 1000) {
            hangThanhVien = "Vang";
        } else if (diemTichLuy >= 500) {
            hangThanhVien = "Bac";
        } else if (diemTichLuy >= 100) {
            hangThanhVien = "Dong";
        } else {
            hangThanhVien = "Moi";
        }
    }

    // ======== Lấy thông tin đầy đủ ========
    @Override
    public String layThongTinDayDu() {
        String diaChiFull = (this.diaChi != null) ? this.diaChi.layThongTinDayDu() : "";
        String danhSachHD = (this.dsMaHoaDon != null && !this.dsMaHoaDon.isEmpty()) ?
                String.join(", ", this.dsMaHoaDon) :
                "";
        return String.format(
                "%-10s %-20s %-12s %-8s %-12s %-40s %-12s %-12.0f %-15.0f %-50s",
                this.maKhachHang, this.hoTen, this.ngaySinh.toString(),
                this.gioiTinh, this.SDT, diaChiFull, this.hangThanhVien,
                this.diemTichLuy, this.tongChiTieu, danhSachHD
        );
    }

    @Override
    public String chuyenSangDinhDangTXT() {
        // Chuẩn bị chuỗi danh sách mã hóa đơn
        String danhSachHD = (dsMaHoaDon == null || dsMaHoaDon.isEmpty()) ?
                "" :
                String.join(",", dsMaHoaDon);
        String[] diaChiParts = diaChi.getTatCaThuocTinh(); // [soNha, duong, quan, tinh]
        return String.join(";",
                maKhachHang,hoTen,ngaySinh.toString(),gioiTinh,SDT,
                diaChiParts[0],diaChiParts[1],diaChiParts[2],diaChiParts[3]
                ,String.valueOf(diemTichLuy),
                hangThanhVien, String.valueOf(tongChiTieu),
                danhSachHD
        );
    }
}
