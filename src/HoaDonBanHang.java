
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDate;
public class HoaDonBanHang extends HoaDon implements LayThongTin{
    public String maKhachHang;
    public String maNhanVienLap;
    public String maKhuyenMai;
    public void setMaKhachHang(String maKhachHang) { this.maKhachHang = maKhachHang;}
    public String getMaKhachHang() { return this.maKhachHang;}
    public void setMaNhanVienLap(String maNhanVienLap) { this.maNhanVienLap = maNhanVienLap;}
    public String getMaNhanVienLap() { return this.maNhanVienLap;}
    public void setMaKhuyenMai(String maKhuyenMai) { this.maKhuyenMai = maKhuyenMai;}
    public String getMaKhuyenMai() { return this.maKhuyenMai;}

    public HoaDonBanHang(String maHoaDon, LocalDate ngayLap, double tongTien, String maKhachHang, String maNhanVienLap, String maKhuyenMai) {
        super(maHoaDon, ngayLap, tongTien);
        this.maKhachHang = maKhachHang;
        this.maNhanVienLap = maNhanVienLap;
        this.maKhuyenMai = maKhuyenMai;
        this.chiTietHoaDon = new HashMap<>();
    }
    public HoaDonBanHang(String maHoaDon, LocalDate ngayLap, String maKhachHang, String maNhanVienLap, String maKhuyenMai) {
        super(maHoaDon, ngayLap);
        this.maKhachHang = maKhachHang;
        this.maNhanVienLap = maNhanVienLap;
        this.maKhuyenMai = maKhuyenMai;
    }
    public HoaDonBanHang(String maHoaDon, LocalDate ngayLap, double tongTien, Map<String, Integer> chiTietHoaDon, String maKhachHang, String maNhanVienLap, String maKhuyenMai) {
        super(maHoaDon, ngayLap, tongTien, chiTietHoaDon);
        this.maKhachHang = maKhachHang;
        this.maNhanVienLap = maNhanVienLap;
        this.maKhuyenMai = maKhuyenMai;
    }

    @Override
    public String layThongTinDayDu() {
        StringBuilder chiTietStr = new StringBuilder();
        for (Map.Entry<String, Integer> entry : chiTietHoaDon.entrySet()) {
            chiTietStr.append(entry.getKey()).append("-").append(entry.getValue()).append(", ");
        }
        String chiTietFormatted = chiTietStr.length() > 2 ? chiTietStr.substring(0, chiTietStr.length() - 2) : "";

        // 2. Sử dụng String.format() với các chiều rộng cột ước tính
        String format = String.format("%-10s %-12s %-10s %-10s %-10s %-15.0f %-70s",
                this.getMaHoaDon(),
                this.getNgayLap().toString(),
                this.getMaNhanVienLap(),
                this.getMaKhachHang(),
                this.getMaKhuyenMai(),
                this.getTongTien(),
                chiTietFormatted
        );

        return format;
    }
    
   @Override
    public String chuyenSangDinhDangTXT() {
       StringBuilder chiTietStr = new StringBuilder();
       boolean first = true;

       for (Map.Entry<String, Integer> entry : chiTietHoaDon.entrySet()) {
           if (!first) {
               chiTietStr.append(",");
           }
           chiTietStr.append(entry.getKey()).append("-").append(entry.getValue());
           first = false;
       }
       return String.join(";",
               this.getMaHoaDon(),
               this.getNgayLap().toString(),
               this.getMaNhanVienLap(),
               this.getMaKhachHang(),
               this.getMaKhuyenMai(),
               String.valueOf(this.getTongTien()),
               chiTietStr.toString()
       );
   }

   //ghi de de tranh loi
    @Override
    public double tinhTongTien(DanhSachSanPham dsSanPham) {
        return this.tongTien;
    }
    public double tinhTongTien(DanhSachSanPham dsSanPham, DanhSachKhuyenMai dsKhuyenMai) {
        double tongTienGoc = 0;
        for (Map.Entry<String, Integer> entry : chiTietHoaDon.entrySet()) {
            String maSP = entry.getKey();
            int soLuong = entry.getValue();
            SanPham sp = dsSanPham.timKiemDoiTuong(maSP);
            if (sp != null) {
                tongTienGoc += sp.getGiaBan() * soLuong;
            }
        }
        double tongTienThucTe = tongTienGoc;
        KhuyenMai km = dsKhuyenMai.timKiemDoiTuong(this.maKhuyenMai);

        if (km != null) {
            tongTienThucTe = km.apDungKhuyenMai(tongTienGoc);
        }
        this.tongTien = tongTienThucTe;
        return this.tongTien;
    }

}    
