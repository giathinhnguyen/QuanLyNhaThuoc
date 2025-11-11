
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public abstract class HoaDon implements LayThongTin{
    public String maHoaDon;
    public LocalDate ngayLap;
    public double tongTien;
    public Map<String, Integer> chiTietHoaDon;

    public String getMaHoaDon() {
        return maHoaDon;
    }
    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }
    public LocalDate getNgayLap() {
        return ngayLap;
    }
    public void setNgayLap(LocalDate ngayLap) {
        this.ngayLap = ngayLap;
    }
    public double getTongTien() {
        return tongTien;
    }
    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public Map<String, Integer> getChiTietHoaDon() {
        return chiTietHoaDon;
    }
    public void setChiTietHoaDon(Map<String, Integer> chiTietHoaDon) {
        this.chiTietHoaDon = chiTietHoaDon;
    }

    public HoaDon(){}
    public HoaDon(String maHoaDon, LocalDate ngayLap, double tongTien, Map<String, Integer> chiTietHoaDon) {
        this.maHoaDon = maHoaDon;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
        this.chiTietHoaDon = chiTietHoaDon;
    }
    public HoaDon(String maHoaDon, LocalDate ngayLap, double tongTien) {
        this.maHoaDon = maHoaDon;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
        this.chiTietHoaDon = new HashMap<>();
    }
    public HoaDon(String maHoaDon, LocalDate ngayLap) {
        this.maHoaDon = maHoaDon;
        this.ngayLap = ngayLap;
        this.tongTien = 0;
        this.chiTietHoaDon = new HashMap<>();
    }
    public double tinhTongTien(DanhSachSanPham dsSanPham) {
        this.tongTien = 0;
        for (Map.Entry<String, Integer> entry : chiTietHoaDon.entrySet()) {
            String maSP = entry.getKey();
            int soLuong = entry.getValue();
            SanPham sp = dsSanPham.timKiemDoiTuong(maSP);
            if (sp != null) {
                this.tongTien += sp.getGiaBan() * soLuong;
            }
        }
        return this.tongTien;
    }
}
