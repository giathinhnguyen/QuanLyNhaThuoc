public class ThucPhamChucNang extends SanPham{
    public String loaiThucPhamChucNang, boSungDuongChat;
    public int tuoiSuDung;

    public int getTuoiSuDung() {
        return tuoiSuDung;
    }
    public void setTuoiSuDung(int tuoiSuDung) {
        this.tuoiSuDung = tuoiSuDung;
    }
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

    public ThucPhamChucNang(String maSanPham, String tenSanPham, String loaiSanPham,
                            String maNhaCungCap, double giaNhap, double giaBan, int soLuongTon,
                            String loaiThucPhamChucNang, String boSungDuongChat, int tuoiSuDung) {
        super(maSanPham, tenSanPham, loaiSanPham,maNhaCungCap, giaNhap, giaBan, soLuongTon);
        this.loaiThucPhamChucNang = loaiThucPhamChucNang;
        this.boSungDuongChat = boSungDuongChat;
        this.tuoiSuDung = tuoiSuDung;
    }

    @Override
    public String layThongTinDayDu() {
        return "Thực phẩm chức năng {" +
                "Mã sản phẩm: '" + maSanPham + '\'' +
                ", Tên: '" + tenSanPham + '\'' +
                ", Loại sản phẩm: '" + loaiSanPham + '\'' +
                ", Độ tuổi sử dụng: '" + tuoiSuDung + '\'' +
                ", Mã nhà cung cấp: '" + maNhaCungCap + '\'' +
                ", Giá nhập: " + giaNhap +
                ", Giá bán: " + giaBan +
                ", Số lượng tồn: " + soLuongTon +
                ", Loại thực phẩm chức năng: '" + loaiThucPhamChucNang + '\'' +
                ", Bổ sung dưỡng chất: '" + boSungDuongChat + '\'' +
                '}';
    }
}
