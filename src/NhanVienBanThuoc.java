
import java.util.ArrayList;

public class NhanVienBanThuoc extends NhanVien implements LayThongTin{
    public int soNgayNghi;
    public ArrayList<HoaDonBanHang> dsHoaDonDaBan = new ArrayList<>();

    public int getSoNgayNghi() {
        return this.soNgayNghi;
    }
    public void setSoNgayNghi(int soNgayNghi) {
        this.soNgayNghi = soNgayNghi;
    }
    public ArrayList<HoaDonBanHang> getDsHoaDonDaBan() {
        return this.dsHoaDonDaBan;
    }
    public void setDsHoaDonDaBan(ArrayList<HoaDonBanHang> dsHoaDonDaBan) {
        this.dsHoaDonDaBan = dsHoaDonDaBan;
    }

    public NhanVienBanThuoc(String maNhanVien, int soNamLam, int soNgayNghi) {
        super(maNhanVien, soNamLam);
        this.soNgayNghi = soNgayNghi;
    }
    @Override
    public double tinhLuong() {
        double luong = luongCoBan + (soNamLam * 100) - (soNgayNghi * 20);
        return luong;
    }
    @Override
    public String layThongTinDayDu() {
        return "NhanVienBanThuoc {" +
                "Mã nhân viên: '" + maNhanVien + '\'' +
                ", Số năm làm: " + soNamLam +
                ", Số ngày nghỉ: " + soNgayNghi +
                ", Lương: " + tinhLuong() +
                '}';
    }
    @Override
    public String chuyenSangDinhDangTXT() {
        return maNhanVien + "," + soNamLam + "," + soNgayNghi;
    }
    
}
