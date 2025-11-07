import java.util.ArrayList;

public class QuanLy extends NhanVien implements LayThongTin {

    // =========================
    // THUỘC TÍNH RIÊNG
    // =========================
    public String hoTen;
    public String ngaySinh;
    public String SDT;
    public String gioiTinh;
    public DiaChi diaChi;
    public double heSoPhuCapQuanLy;
    public ArrayList<HoaDonNhap> dsHoaDonDaNhap;

    // =========================
    // CONSTRUCTOR
    // =========================
    public QuanLy(String maNhanVien, int soNamLam, String hoTen, String ngaySinh,
                  String SDT, String gioiTinh, DiaChi diaChi, double heSoPhuCapQuanLy) {
        super(maNhanVien, soNamLam);
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.SDT = SDT;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.heSoPhuCapQuanLy = heSoPhuCapQuanLy;
        this.dsHoaDonDaNhap = new ArrayList<>();
    }

    // =========================
    // THÊM HÓA ĐƠN NHẬP
    // =========================
    public void themHoaDon(HoaDonNhap hd) {
        if (hd != null) {
            dsHoaDonDaNhap.add(hd);
        }
    }

    // =========================
    // KIỂM TRA HỢP LỆ
    // =========================
    public boolean kiemTraHopLe() {
        return maNhanVien != null && !maNhanVien.isEmpty()
                && hoTen != null && !hoTen.isEmpty()
                && SDT != null && SDT.matches("\\d{9,11}")
                && heSoPhuCapQuanLy > 0;
    }

    // =========================
    // TÍNH LƯƠNG
    // =========================
    @Override
    public double tinhLuong() {
        // Giữ đúng công thức bạn đã dùng
        return NhanVien.getLuongCoBan() * heSoPhuCapQuanLy + dsHoaDonDaNhap.size() * 50;
    }

    // =========================
    // HIỂN THỊ THÔNG TIN
    // =========================
    @Override
    public String layThongTinDayDu() {
        return "Quản lý { " +
                "Mã NV: " + maNhanVien +
                ", Tên: " + hoTen +
                ", Ngày sinh: " + ngaySinh +
                ", Giới tính: " + gioiTinh +
                ", SDT: " + SDT +
                ", Địa chỉ: " + (diaChi != null ? diaChi.layThongTinDayDu() : "Chưa có") +
                ", Hệ số phụ cấp: " + heSoPhuCapQuanLy +
                ", Số hóa đơn phụ trách: " + dsHoaDonDaNhap.size() +
                ", Lương: " + tinhLuong() +
                " }";
    }

    // =========================
    // CHUYỂN SANG ĐỊNH DẠNG TXT
    // =========================
    @Override
    public String chuyenSangDinhDangTXT() {
        return maNhanVien + ";" + soNamLam + ";" + hoTen + ";" + ngaySinh + ";" +
                SDT + ";" + gioiTinh + ";" +
                (diaChi != null ? diaChi.chuyenSangDinhDangTXT() : "null") + ";" +
                heSoPhuCapQuanLy;
    }

    @Override
    public String toString() {
        return layThongTinDayDu();
    }
}


    