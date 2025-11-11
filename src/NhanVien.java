import java.time.LocalDate;
import java.util.ArrayList;

public abstract class NhanVien extends Nguoi implements LayThongTin{
    public String maNhanVien;
    public int soNamLam;
    public ArrayList<String> dsMaHoaDon = new ArrayList<>();
    public static double luongCoBan = 1150;

    public String getMaNhanVien() {
        return maNhanVien;
    }
    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }
    public int getSoNamLam() {
        return soNamLam;
    }
    public void setSoNamLam(int soNamLam) {
        this.soNamLam = soNamLam;
    }
    public ArrayList<String> getDsMaHoaDon() {
        return dsMaHoaDon;
    }

    public void setDsMaHoaDon(ArrayList<String> dsMaHoaDon) {
        this.dsMaHoaDon = dsMaHoaDon;
    }

    public static double getLuongCoBan() {
        return luongCoBan;
    }
    public static void setLuongCoBan(double luongCoBan) {
        NhanVien.luongCoBan = luongCoBan;
    }

   public  NhanVien(){}

    public NhanVien(String maNhanVien, int soNamLam, ArrayList<String> dsMaHoaDon) {
        this.maNhanVien = maNhanVien;
        this.soNamLam = soNamLam;
        this.dsMaHoaDon = dsMaHoaDon;
    }

    public NhanVien(String hoTen, String SDT, String gioiTinh, LocalDate ngaySinh, DiaChi diaChi, String maNhanVien, int soNamLam, ArrayList<String> dsMaHoaDon) {
        super(hoTen, SDT, gioiTinh, ngaySinh, diaChi);
        this.maNhanVien = maNhanVien;
        this.soNamLam = soNamLam;
        this.dsMaHoaDon = dsMaHoaDon;
    }
    public NhanVien(String hoTen, String SDT, String gioiTinh, LocalDate ngaySinh, DiaChi diaChi, String maNhanVien, int soNamLam) {
        super(hoTen, SDT, gioiTinh, ngaySinh, diaChi);
        this.maNhanVien = maNhanVien;
        this.soNamLam = soNamLam;
        this.dsMaHoaDon = new ArrayList<>();
    }

    public abstract double tinhLuong();
    public  abstract void themHoaDon(String maHD);
}
