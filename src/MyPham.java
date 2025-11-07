import java.time.LocalDate;

public class MyPham extends  SanPham implements LayThongTin{
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
        return "Mỹ Phẩm {" +
                "Mã sản phẩm: '" + maSanPham + '\'' +
                ", Tên: '" + tenSanPham + '\'' +
                ", Loại sản phẩm: '" + loaiSanPham + '\'' +
                ", Mã nhà cung cấp: '" + maNhaCungCap + '\'' +
                ", Giá nhập: " + giaNhap +
                ", Giá bán: " + giaBan +
                ", Số lượng tồn: " + soLuongTon +
                ", Hạn sử dụng: '" + HSD + '\'' +
                ", Loại mỹ phẩm: '" + loaiMyPham + '\'' +
                ", Dành cho da: '" + loaiDaPhuHop + '\'' + +
                '}';
    }
    @Override
    public String chuyenSangDinhDangTXT(){
        return String.join(";",
                "MyPham",
                maSanPham, tenSanPham,
                String.valueOf(giaBan),
                String.valueOf(giaNhap),
                String.valueOf(soLuongTon),
                HSD.toString(),
                loaiSanPham, maNhaCungCap,
                loaiMyPham, loaiDaPhuHop
        );
    }

    @Override
    public boolean kiemTraHopLe() {
        if (!super.kiemTraHopLe()) return false;
        if (loaiMyPham == null || loaiMyPham.trim().isEmpty()) return false;
        if (loaiDaPhuHop == null || loaiDaPhuHop.trim().isEmpty()) return false;
        return true;
    }
}
