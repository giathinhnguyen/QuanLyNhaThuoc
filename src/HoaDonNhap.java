import java.time.LocalDate;
import java.util.*;
import java.text.SimpleDateFormat;


public class HoaDonNhap extends HoaDon{
    public String maQuanLy;                     
    public String maNhaCungCap;

    public HoaDonNhap(String maHoaDon, LocalDate ngayLap, double tongTien, String maQuanLy, String maNhaCungCap) {
        super(maHoaDon, ngayLap, tongTien);
        this.maQuanLy = maQuanLy;
        this.maNhaCungCap = maNhaCungCap;
        this.chiTietHoaDon = new HashMap<>();
    }
    public HoaDonNhap(String maHoaDon, LocalDate ngayLap, double tongTien, String maQuanLy, String maNhaCungCap, Map<String, Integer> dsSanPhamNhap) {
        super(maHoaDon, ngayLap, tongTien);
        this.maQuanLy = maQuanLy;
        this.maNhaCungCap = maNhaCungCap;
        this.chiTietHoaDon = dsSanPhamNhap;
    }

    public String getMaQuanLy() { return maQuanLy; }
    public void setMaQuanLy(String maQuanLy) { this.maQuanLy = maQuanLy; }

    public String getMaNhaCungCap() { return maNhaCungCap; }
    public void setMaNhaCungCap(String maNhaCungCap) { this.maNhaCungCap = maNhaCungCap; }
    @Override
    public String chuyenSangDinhDangTXT() {
        // 1. Chuyển Map chi tiết (MaSP-SL) thành chuỗi: T001-5,T006-3,...
        StringBuilder chiTietStr = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, Integer> entry : this.getChiTietHoaDon().entrySet()) {
            if (!first) {
                chiTietStr.append(",");
            }
            chiTietStr.append(entry.getKey()).append("-").append(entry.getValue());
            first = false;
        }
        return String.join(";",
                this.getMaHoaDon(),
                this.getNgayLap().toString(),
                this.getMaQuanLy(),
                this.getMaNhaCungCap(),
                String.valueOf(this.getTongTien()),
                chiTietStr.toString()
        );
    }
   @Override
    public String layThongTinDayDu() {
       StringBuilder chiTietStr = new StringBuilder();
       boolean first = true;

       for (Map.Entry<String, Integer> entry : this.getChiTietHoaDon().entrySet()) {
           if (!first) {
               chiTietStr.append(", ");
           }
           chiTietStr.append(entry.getKey()).append("-").append(entry.getValue());
           first = false;
       }

       String format = String.format("%-10s %-12s %-10s %-10s %-15.0f %-70s",
               this.getMaHoaDon(),
               this.getNgayLap().toString(),
               this.getMaQuanLy(),
               this.getMaNhaCungCap(),
               this.getTongTien(),
               chiTietStr.toString()
       );

       return format;
    }

}



