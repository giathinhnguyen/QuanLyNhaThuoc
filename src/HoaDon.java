import java.time.LocalDate;

public abstract class HoaDon {
    public String maHoaDon;
    public LocalDate ngayLap;
    public double tongTien;

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

    public HoaDon(String maHoaDon, LocalDate ngayLap, double tongTien) {
        this.maHoaDon = maHoaDon;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
    }

    public abstract double tinhTongTien();
}
