
import java.time.LocalDate;
import java.util.ArrayList;

public class NhanVienBanThuoc extends NhanVien implements LayThongTin{
    public int soNgayNghi;

    public int getSoNgayNghi() {
        return this.soNgayNghi;
    }
    public void setSoNgayNghi(int soNgayNghi) {
        this.soNgayNghi = soNgayNghi;
    }

    public NhanVienBanThuoc(){}

    public NhanVienBanThuoc(int soNgayNghi) {
        this.soNgayNghi = soNgayNghi;
    }

    public NhanVienBanThuoc(String maNhanVien, int soNamLam, ArrayList<String> dsMaHoaDon, int soNgayNghi) {
        super(maNhanVien, soNamLam, dsMaHoaDon);
        this.soNgayNghi = soNgayNghi;
    }

    public NhanVienBanThuoc(String hoTen, String SDT, String gioiTinh, LocalDate ngaySinh, DiaChi diaChi, String maNhanVien, int soNamLam, ArrayList<String> dsMaHoaDon, int soNgayNghi) {
        super(hoTen, SDT, gioiTinh, ngaySinh, diaChi, maNhanVien, soNamLam, dsMaHoaDon);
        this.soNgayNghi = soNgayNghi;
    }

    public NhanVienBanThuoc(String hoTen, String SDT, String gioiTinh, LocalDate ngaySinh, DiaChi diaChi, String maNhanVien, int soNamLam, int soNgayNghi) {
        super(hoTen, SDT, gioiTinh, ngaySinh, diaChi, maNhanVien, soNamLam);
        this.soNgayNghi = soNgayNghi;
    }

    @Override
    public void themHoaDon(String maHD) {
        if (!dsMaHoaDon.contains(maHD)) {
            dsMaHoaDon.add(maHD);
        }
    }

    @Override
    public double tinhLuong() {
        double luong = luongCoBan + (soNamLam * 100) - (soNgayNghi * 20);
        return luong;
    }
    @Override
    public String layThongTinDayDu() {
        String diaChiFull = this.getDiaChi() != null ? this.getDiaChi().layThongTinDayDu() : "";
        String danhSachHD = (dsMaHoaDon == null || dsMaHoaDon.isEmpty()) ?
                "" :
                String.join(", ", dsMaHoaDon);
        String thuocTinhRieng = String.valueOf(this.soNgayNghi);
        // Format: %-15s %-20s %-10s %-12s %-10s %-25s %-10s %-10s %-15.0f %-50s
        return String.format(
                "%-15s %-20s %-10s %-12s %-10s %-25s %-10s %-10s %-15.0f %-50s",
                this.maNhanVien, this.hoTen, this.gioiTinh,
                this.ngaySinh.toString(), this.SDT, diaChiFull,
                "NV ban thuoc", thuocTinhRieng, this.tinhLuong(), danhSachHD
        );
    }
    @Override
    public String chuyenSangDinhDangTXT() {
        String danhSachHD = (dsMaHoaDon == null || dsMaHoaDon.isEmpty())?
                "" : String.join(",", dsMaHoaDon);
        String[] diaChiParts = diaChi.getTatCaThuocTinh();
        return String.join(";",
                "NhanVienBanThuoc",maNhanVien,hoTen,ngaySinh.toString(),SDT,gioiTinh,
                diaChiParts[0],diaChiParts[1],diaChiParts[2],diaChiParts[3],
                String.valueOf(soNamLam),String.valueOf(soNgayNghi),danhSachHD
                );
    }
}
