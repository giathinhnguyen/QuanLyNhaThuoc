import java.time.LocalDate;

public abstract class SanPham implements LayThongTin {
    public String maSanPham, tenSanPham, loaiSanPham;
    public String maNhaCungCap;
    public double giaNhap, giaBan;
    public int soLuongTon;
    public LocalDate HSD;
    public String getMaSanPham() {
        return maSanPham;
    }
    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }
    public String getTenSanPham() {
        return tenSanPham;
    }
    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }
    public String getLoaiSanPham() {
        return loaiSanPham;
    }
    public void setLoaiSanPham(String loaiSanPham) {
        this.loaiSanPham = loaiSanPham;
    }
    public String getMaNhaCungCap() {
        return maNhaCungCap;
    }
    public void setMaNhaCungCap(String maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }
    public double getGiaNhap() {
        return giaNhap;
    }
    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }
    public double getGiaBan() {
        return giaBan;
    }
    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }
    public int getSoLuongTon() {
        return soLuongTon;
    }
    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public SanPham(String maSanPham, String tenSanPham, String loaiSanPham,
                   String maNhaCungCap, double giaNhap, double giaBan, int soLuongTon) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.loaiSanPham = loaiSanPham;
        this.maNhaCungCap = maNhaCungCap;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.soLuongTon = soLuongTon;
    }

    public double tinhLoiNhuan1SanPham(){
        return this.giaBan-this.giaNhap;
    }
}
