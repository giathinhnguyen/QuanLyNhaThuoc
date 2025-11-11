import java.time.LocalDate;

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
                     String maNhaCungCap, double giaNhap, double giaBan, int soLuongTon, LocalDate HSD,
                     String chatLieu, String xuatXu) {
        super(maSanPham, tenSanPham, loaiSanPham, maNhaCungCap, giaNhap, giaBan, soLuongTon, HSD);
        this.chatLieu = chatLieu;
        this.xuatXu = xuatXu;
    }
    @Override
    public String layThongTinDayDu(){
        return String.format(
                "| %-10s | %-30s | Gia Ban: %,.0f | Ton: %-5d | HSD: %-10s | Chat Lieu: %s | Xuat Xu: %s",
                maSanPham,
                tenSanPham,
                giaBan,
                soLuongTon,
                HSD.toString(),
                chatLieu,
                xuatXu
        );
    }
    @Override
    public String chuyenSangDinhDangTXT(){
        return String.join(";",
                "DungCuYTe",
                maSanPham,
                tenSanPham,
                String.valueOf(giaBan),
                String.valueOf(giaNhap),
                String.valueOf(soLuongTon),
                HSD.toString(),
                loaiSanPham,
                maNhaCungCap,
                chatLieu,
                xuatXu
        );
    }

}
