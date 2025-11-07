import java.util.*;  
import java.text.SimpleDateFormat;


 
public class HoaDonNhap extends HoaDon implements LayThongTin {
    
    // =========================
    // THUỘC TÍNH RIÊNG
    // =========================
    public String maQuanLy;                     
    public String maNhaCungCap;                 
    public Map<SanPham, Integer> dsSanPhamNhap; 

    // =========================
    // CONSTRUCTOR
    // =========================
    public HoaDonNhap(String maHoaDon, Date ngayLap, double tongTien,
                      String maQuanLy, String maNhaCungCap,
                      Map<SanPham, Integer> dsSanPhamNhap) {
        super(maHoaDon, ngayLap, tongTien);
        this.maQuanLy = maQuanLy;
        this.maNhaCungCap = maNhaCungCap;
        this.dsSanPhamNhap = dsSanPhamNhap;
    }

    // =========================
    // GET/SET
    // =========================
    public String getMaQuanLy() { return maQuanLy; }
    public void setMaQuanLy(String maQuanLy) { this.maQuanLy = maQuanLy; }

    public String getMaNhaCungCap() { return maNhaCungCap; }
    public void setMaNhaCungCap(String maNhaCungCap) { this.maNhaCungCap = maNhaCungCap; }

    public Map<SanPham, Integer> getDsSanPhamNhap() { return dsSanPhamNhap; }
    public void setDsSanPhamNhap(Map<SanPham, Integer> dsSanPhamNhap) { this.dsSanPhamNhap = dsSanPhamNhap; }

    // =========================
    // TÍNH TỔNG TIỀN (Override từ HoaDon)
    // =========================
    @Override
    public double tinhTongTien() {
        double tong = 0;
        if (dsSanPhamNhap != null) {
            for (Map.Entry<SanPham, Integer> entry : dsSanPhamNhap.entrySet()) {
                SanPham sp = entry.getKey();
                int soLuong = entry.getValue();
                tong += sp.getGiaNhap() * soLuong; 
            }
        }
        this.tongTien = tong;
        return tong;
    }

   
    public boolean kiemTraHopLe() {
        return maHoaDon != null && !maHoaDon.trim().isEmpty()
                && maQuanLy != null && !maQuanLy.trim().isEmpty()
                && maNhaCungCap != null && !maNhaCungCap.trim().isEmpty()
                && ngayLap != null
                && dsSanPhamNhap != null && !dsSanPhamNhap.isEmpty();
    }

    // =========================
    // CHUYỂN SANG ĐỊNH DẠNG TXT (Interface LayThongTin)
    // =========================
    @Override
    public String chuyenSangDinhDangTXT() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return maHoaDon + ";" + sdf.format(ngayLap) + ";" + maQuanLy + ";"
                + maNhaCungCap + ";" + tinhTongTien();
    }

    // =========================
    // LẤY THÔNG TIN ĐẦY ĐỦ (Interface LayThongTin)
    // =========================
   @Override
    public String layThongTinDayDu() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String thongTin = "Hóa đơn nhập {" +
                "Mã hóa đơn: '" + maHoaDon + '\'' +
                ", Ngày lập: '" + sdf.format(ngayLap) + '\'' +
                ", Mã quản lý: '" + maQuanLy + '\'' +
                ", Mã nhà cung cấp: '" + maNhaCungCap + '\'' +
                ", Tổng tiền: " + tinhTongTien();

        return thongTin;
    }

    // =========================
    // GHI ĐÈ PHƯƠNG THỨC toString()
    // =========================
    @Override
    public String toString() {
        // Chỉ gọi layThongTinDayDu() để hiển thị đầy đủ thông tin
        return layThongTinDayDu();
    }
}
   // ==========



