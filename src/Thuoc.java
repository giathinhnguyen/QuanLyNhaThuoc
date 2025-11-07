import java.time.LocalDate;

public class Thuoc extends SanPham implements LayThongTin{
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
    public boolean isCanToaBacSi() {
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
            return "Thuoc {" +
                    "Mã sản phẩm: '" + maSanPham + '\'' +
                    ", Tên: '" + tenSanPham + '\'' +
                    ", Loại sản phẩm: '" + loaiSanPham + '\'' +
                    ", Mã nhà cung cấp: '" + maNhaCungCap + '\'' +
                    ", Giá nhập: " + giaNhap +
                    ", Giá bán: " + giaBan +
                    ", Số lượng tồn: " + soLuongTon +
                    ", Hạn sử dụng: '" + HSD + '\'' +
                    ", Loại thuốc: '" + loaiThuoc + '\'' +
                    ", Công dụng: '" + congDung + '\'' +
                    ", Cần toa bác sĩ: " + (canToaBacSi ? "Có" : "Không") +
                    '}';
    }

    @Override
    public String chuyenSangDinhDangTXT(){
        return String.join(";",
                "Thuoc",
                maSanPham, tenSanPham,
                String.valueOf(giaBan),
                String.valueOf(giaNhap),
                String.valueOf(soLuongTon),
                HSD.toString(),
                loaiSanPham, maNhaCungCap,
                loaiThuoc, congDung,
                String.valueOf(canToaBacSi)
        );
    }

    @Override
    public boolean kiemTraHopLe() {
        if (!super.kiemTraHopLe()) return false;
        if (loaiThuoc == null || loaiThuoc.trim().isEmpty()) return false;
        if (congDung == null || congDung.trim().isEmpty()) return false;
        return true;
    }
}
