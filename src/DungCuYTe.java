public class DungCuYTe extends SanPham{
    public String chatLieu, xuatXu;

    public String getChatLieu() {
        return chatLieu;
    }
    public void setChatLieu(String chatLieu) {
        this.chatLieu = chatLieu;
    }
    public String getXuatXu() {
        return xuatXu;
    }
    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }

    public DungCuYTe(String maSanPham, String tenSanPham, String loaiSanPham,
                     String maNhaCungCap, double giaNhap, double giaBan, int soLuongTon,
                     String chatLieu, String xuatXu) {
        super(maSanPham, tenSanPham, loaiSanPham, maNhaCungCap, giaNhap, giaBan, soLuongTon);
        this.chatLieu = chatLieu;
        this.xuatXu = xuatXu;
    }
    @Override
    public String layThongTinDayDu(){
        return "Dụng cụ y tế {" +
                "Mã sản phẩm: '" + maSanPham + '\'' +
                ", Tên: '" + tenSanPham + '\'' +
                ", Loại sản phẩm: '" + loaiSanPham + '\'' +
                ", Mã nhà cung cấp: '" + maNhaCungCap + '\'' +
                ", Giá nhập: " + giaNhap +
                ", Giá bán: " + giaBan +
                ", Số lượng tồn: " + soLuongTon +
                ", Chất liệu: '" + chatLieu + '\'' +
                ", Xuất xứ: '" + xuatXu + '\'' +
                '}';
    }
}
