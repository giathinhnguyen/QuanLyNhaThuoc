public class ThucPhamChucNang extends SanPham{
    public String loaiThucPhamChucNang, boSungDuongChat;
    public String getLoaiThucPhamChucNang() {
        return loaiThucPhamChucNang;
    }
    public void setLoaiThucPhamChucNang(String loaiThucPhamChucNang) {
        this.loaiThucPhamChucNang = loaiThucPhamChucNang;
    }
    public String getBoSungDuongChat() {
        return boSungDuongChat;
    }
    public void setBoSungDuongChat(String boSungDuongChat) {
        this.boSungDuongChat = boSungDuongChat;
    }

    public ThucPhamChucNang(String maSanPham, String tenSanPham, String loaiSanPham, String donViTinh,
                            String maNhaCungCap, double giaNhap, double giaBan, int soLuongTon,
                            String loaiThucPhamChucNang, String boSungDuongChat) {
        super(maSanPham, tenSanPham, loaiSanPham, donViTinh, maNhaCungCap, giaNhap, giaBan, soLuongTon);
        this.loaiThucPhamChucNang = loaiThucPhamChucNang;
        this.boSungDuongChat = boSungDuongChat;
    }

    @Override
    public String layThongTinDayDu() {
        return "Thực phẩm chức năng {" +
                "Mã sản phẩm: '" + maSanPham + '\'' +
                ", Tên: '" + tenSanPham + '\'' +
                ", Loại sản phẩm: '" + loaiSanPham + '\'' +
                ", Đơn vị tính: '" + donViTinh + '\'' +
                ", Mã nhà cung cấp: '" + maNhaCungCap + '\'' +
                ", Giá nhập: " + giaNhap +
                ", Giá bán: " + giaBan +
                ", Số lượng tồn: " + soLuongTon +
                ", Loại thực phẩm chức năng: '" + loaiThucPhamChucNang + '\'' +
                ", Bổ sung dưỡng chất: '" + boSungDuongChat + '\'' +
                '}';
    }
}
