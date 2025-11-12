
import java.time.LocalDate;

public class Thuoc extends SanPham{
    public String loaiThuoc, congDung;
    public boolean canToaBacSi;

    public String getLoaiThuoc() {
        return loaiThuoc;
    }
    public void setLoaiThuoc(String loaiThuoc) {
        this.loaiThuoc = loaiThuoc;
    }
    public String getCongDung() {
        return congDung;
    }
    public void setCongDung(String congDung) {
        this.congDung = congDung;
    }
    public boolean getCanToaBacSi() {
        return canToaBacSi;
    }

    public void setCanToaBacSi(boolean canToaBacSi) {
        this.canToaBacSi = canToaBacSi;
    }

    public Thuoc(String maSanPham, String tenSanPham, String loaiSanPham,
                 String maNhaCungCap, double giaNhap, double giaBan, int soLuongTon, LocalDate HSD,
                 String loaiThuoc,
                 String congDung, boolean canToaBacSi) {
        super(maSanPham, tenSanPham, loaiSanPham, maNhaCungCap, giaNhap, giaBan, soLuongTon, HSD);
        this.loaiThuoc = loaiThuoc;
        this.congDung = congDung;
        this.canToaBacSi = canToaBacSi;
    }

    @Override
    public String layThongTinDayDu() {
        return String.format(
                "| %-10s | %-30s | Gia Ban: %,.0f | Ton: %-5d | HSD: %-10s | Can Toa: %-3s | Loai Thuoc: %s",
                maSanPham,
                tenSanPham,
                giaBan,
                soLuongTon,
                HSD.toString(),
                (canToaBacSi ? "CO" : "KHONG"),
                loaiThuoc
        );
    }
    @Override
    public String chuyenSangDinhDangTXT() {
        return String.join(";",
                "Thuoc", maSanPham, tenSanPham, String.valueOf(giaBan), String.valueOf(giaNhap), String.valueOf(soLuongTon),
                HSD.toString(), maNhaCungCap, loaiThuoc,  congDung,
                (canToaBacSi ? "true" : "false")
        );
    }
}