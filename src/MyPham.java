import java.time.LocalDate;

public class MyPham extends  SanPham{
    public String loaiMyPham, loaiDaPhuHop;

    public String getLoaiMyPham() {
        return loaiMyPham;
    }
    public void setLoaiMyPham(String loaiMyPham) {
        this.loaiMyPham = loaiMyPham;
    }
    public String getLoaiDaPhuHop() {
        return loaiDaPhuHop;
    }
    public void setLoaiDaPhuHop(String loaiDaPhuHop) {
        this.loaiDaPhuHop = loaiDaPhuHop;
    }

    public MyPham(String maSanPham, String tenSanPham, String loaiSanPham, String maNhaCungCap,
                  double giaNhap, double giaBan, int soLuongTon, LocalDate HSD, String loaiMyPham, String loaiDaPhuHop) {
        super(maSanPham, tenSanPham, loaiSanPham,maNhaCungCap, giaNhap, giaBan, soLuongTon,HSD);
        this.loaiMyPham = loaiMyPham;
        this.loaiDaPhuHop = loaiDaPhuHop;
    }

    @Override
    public String layThongTinDayDu() {
        return String.format(
                "| %-10s | %-30s | Gia Ban: %,.0f | Ton: %-5d | HSD: %-10s | Loai MP: %s | Cho Da: %s",
                maSanPham,
                tenSanPham,
                giaBan,
                soLuongTon,
                HSD.toString(),
                loaiMyPham,
                loaiDaPhuHop
        );
    }

    @Override
    public String chuyenSangDinhDangTXT(){
        return String.join(";",
                "MyPham",
                maSanPham,
                tenSanPham,
                String.valueOf(giaBan),
                String.valueOf(giaNhap),
                String.valueOf(soLuongTon),
                HSD.toString(),
                maNhaCungCap,
                loaiMyPham,
                loaiDaPhuHop
        );
    }

}
