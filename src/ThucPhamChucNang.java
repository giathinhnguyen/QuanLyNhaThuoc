import java.time.LocalDate;

public class ThucPhamChucNang extends SanPham implements LayThongTin{
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
                            String maNhaCungCap, double giaNhap, double giaBan, int soLuongTon, LocalDate HSD,
                            String loaiThucPhamChucNang, String boSungDuongChat, int tuoiSuDung) {
        super(maSanPham, tenSanPham, loaiSanPham,maNhaCungCap, giaNhap, giaBan, soLuongTon, HSD);
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
                ", Hạn sử dụng: '" + HSD + '\'' +
                ", Loại thực phẩm chức năng: '" + loaiThucPhamChucNang + '\'' +
                ", Bổ sung dưỡng chất: '" + boSungDuongChat + '\'' +
                '}';
    }
    @Override
    public String chuyenSangDinhDangTXT(){
        return String.join(";",
                "ThucPhamChucNang",
                maSanPham, tenSanPham,
                String.valueOf(giaBan),
                String.valueOf(giaNhap),
                String.valueOf(soLuongTon),
                HSD.toString(),
                loaiSanPham, maNhaCungCap,
                loaiThucPhamChucNang, boSungDuongChat,
                String.valueOf(tuoiSuDung)
        );
    }
    @Override
    public boolean kiemTraHopLe() {
        if (!super.kiemTraHopLe()) return false; // kiểm tra chung
        if (loaiThucPhamChucNang == null || loaiThucPhamChucNang.trim().isEmpty()) return false;
        if (boSungDuongChat == null || boSungDuongChat.trim().isEmpty()) return false;
        return true;
    }
}
