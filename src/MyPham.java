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

    public MyPham(String maSanPham, String tenSanPham, String loaiSanPham,String maNhaCungCap,
                  double giaNhap, double giaBan, int soLuongTon, String loaiMyPham, String loaiDaPhuHop) {
        super(maSanPham, tenSanPham, loaiSanPham,maNhaCungCap, giaNhap, giaBan, soLuongTon);
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
                ", Loại mỹ phẩm: '" + loaiMyPham + '\'' +
                ", Dành cho da: '" + loaiDaPhuHop + '\'' + +
                '}';
    }
}
