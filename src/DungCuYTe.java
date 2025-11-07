import java.time.LocalDate;

public class DungCuYTe extends SanPham implements LayThongTin{
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
                     String maNhaCungCap, double giaNhap, double giaBan, int soLuongTon, LocalDate HSD,
                     String chatLieu, String xuatXu) {
        super(maSanPham, tenSanPham, loaiSanPham, maNhaCungCap, giaNhap, giaBan, soLuongTon, HSD);
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
                ", Hạn sử dụng: '" + HSD + '\'' +
                ", Chất liệu: '" + chatLieu + '\'' +
                ", Xuất xứ: '" + xuatXu + '\'' +
                '}';
    }
    @Override
    public String chuyenSangDinhDangTXT(){
        return String.join(";",
                "DungCuYTe",
                maSanPham, tenSanPham,
                String.valueOf(giaBan),
                String.valueOf(giaNhap),
                String.valueOf(soLuongTon),
                HSD.toString(),
                loaiSanPham, maNhaCungCap,
                chatLieu, xuatXu
        );
    }

    @Override
    public boolean kiemTraHopLe() {
        if (!super.kiemTraHopLe()) return false;
        if (chatLieu == null || chatLieu.trim().isEmpty()) return false;
        if (xuatXu == null || xuatXu.trim().isEmpty()) return false;
        return true;
    }
}
