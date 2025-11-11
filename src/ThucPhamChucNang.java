import java.time.LocalDate;

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
                            String maNhaCungCap, double giaNhap, double giaBan, int soLuongTon, LocalDate HSD,
                            String loaiThucPhamChucNang, String boSungDuongChat, int tuoiSuDung) {
        super(maSanPham, tenSanPham, loaiSanPham,maNhaCungCap, giaNhap, giaBan, soLuongTon, HSD);
        this.loaiThucPhamChucNang = loaiThucPhamChucNang;
        this.boSungDuongChat = boSungDuongChat;
        this.tuoiSuDung = tuoiSuDung;
    }

    @Override
    public String layThongTinDayDu() {
        // Dinh dang hien thi chi tiet cho nguoi dung de doc
        return String.format(
                "| %-10s | %-30s | Gia Ban: %,.0f | Ton: %-5d | HSD: %-10s | Tuoi SD: %-5d | Loai TPCN: %s | Duong Chat: %s",
                maSanPham,
                tenSanPham,
                giaBan,
                soLuongTon,
                HSD.toString(),
                tuoiSuDung,
                loaiThucPhamChucNang,
                boSungDuongChat
        );
    }
    @Override
    public String chuyenSangDinhDangTXT(){
        return String.join(";",
                "ThucPhamChucNang",
                maSanPham,
                tenSanPham,
                String.valueOf(giaBan),
                String.valueOf(giaNhap),
                String.valueOf(soLuongTon),
                HSD.toString(),
                loaiSanPham,
                maNhaCungCap,
                loaiThucPhamChucNang,
                boSungDuongChat,
                String.valueOf(tuoiSuDung)
        );
    }
}
