public abstract class NhanVien {
    public String maNhanVien;
    public String tenNhanVien;
    public int soNamLam;
    public static double luongCoBan = 1150;

    public String getMaNhanVien() {
        return maNhanVien;
    }
    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }
    public String getTenNhanVien() {
        return this.tenNhanVien;
    }
    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }
    public int getSoNamLam() {
        return soNamLam;
    }
    public void setSoNamLam(int soNamLam) {
        this.soNamLam = soNamLam;
    }
    public static double getLuongCoBan() {
        return luongCoBan;
    }
    public static void setLuongCoBan(double luongCoBan) {
        NhanVien.luongCoBan = luongCoBan;
    }

    public NhanVien(String maNhanVien, int soNamLam) {
        this.maNhanVien = maNhanVien;
        this.soNamLam = soNamLam;
    }
    public abstract double tinhLuong();
}
