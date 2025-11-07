
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDate;
public class HoaDonBanHang extends HoaDon implements LayThongTin{
    public String maKhachHang;
    public String maNhanVienLap;
    public String maKhuyenMai;
    public Map<SanPham, Integer> dsSanPham = new HashMap<>();

    public void setMaKhachHang(String maKhachHang) { this.maKhachHang = maKhachHang;}
    public String getMaKhachHang() { return this.maKhachHang;}
    public void setMaNhanVienLap(String maNhanVienLap) { this.maNhanVienLap = maNhanVienLap;}
    public String getMaNhanVienLap() { return this.maNhanVienLap;}
    public void setMaKhuyenMai(String maKhuyenMai) { this.maKhuyenMai = maKhuyenMai;}
    public String getMaKhuyenMai() { return this.maKhuyenMai;}
    public Map<SanPham, Integer> getDsSanPham() { return this.dsSanPham;}
    public void setDsSanPham(Map<SanPham, Integer> dsSanPham) { this.dsSanPham = dsSanPham;}

    public HoaDonBanHang(String maHoaDon, LocalDate ngayLap, double tongTien, String maKhachHang, String maNhanVienLap, String maKhuyenMai) {
        super(maHoaDon, ngayLap, tongTien);
        this.maKhachHang = maKhachHang;
        this.maNhanVienLap = maNhanVienLap;
        this.maKhuyenMai = maKhuyenMai;
    }  

    @Override
    public String layThongTinDayDu() {
        String danhSachSP = "";

        if (dsSanPham.isEmpty()) {
            danhSachSP = "(Không có sản phẩm nào trong hóa đơn)";
        } else {
            for (Map.Entry<SanPham, Integer> entry : dsSanPham.entrySet()) {
                SanPham sp = entry.getKey();
                int soLuong = entry.getValue();
                danhSachSP += "{Tên: '" + sp.getTenSanPham() + "', Mã: '" + sp.getMaSanPham()
                        + "', Đơn giá: " + sp.getGiaBan()
                        + ", Số lượng: " + soLuong
                        + ", Thành tiền: " + (sp.getGiaBan() * soLuong) + "}, ";
            }
            // Xóa dấu phẩy và khoảng trắng cuối nếu có
            if (danhSachSP.endsWith(", ")) {
                danhSachSP = danhSachSP.substring(0, danhSachSP.length() - 2);
            }
        }

        return "HoaDonBanHang {" +
                "Mã hóa đơn: '" + getMaHoaDon() + '\'' +
                ", Ngày lập hóa đơn: '" + getNgayLap() + '\'' +
                ", Mã khách hàng: '" + maKhachHang + '\'' +
                ", Mã nhân viên lập: '" + maNhanVienLap + '\'' +
                ", Mã khuyến mãi: '" + maKhuyenMai + '\'' +
                ", Danh sách sản phẩm: [" + danhSachSP + "]" +
                '}';
    }
    
   @Override
    public String chuyenSangDinhDangTXT() {
        String chuoi = "HoaDonBanHang;" +
                    getMaHoaDon() + ";" +
                    getNgayLap() + ";" +
                    maKhachHang + ";" +
                    maNhanVienLap + ";" +
                    maKhuyenMai + "\n"; // Xuống dòng sau thông tin hóa đơn

    if (dsSanPham.isEmpty()) {
        chuoi += "(Không có sản phẩm)\n";
    } else {
        for (Map.Entry<SanPham, Integer> entry : dsSanPham.entrySet()) {
            SanPham sp = entry.getKey();
            int soLuong = entry.getValue();

            // Mỗi sản phẩm in ra 1 dòng, các thuộc tính cách nhau bởi dấu ;
            chuoi += "SanPham;" +
                     sp.getMaSanPham() + ";" +
                     sp.getTenSanPham() + ";" +
                     sp.getGiaBan() + ";" +
                     soLuong + ";" +
                     (sp.getGiaBan() * soLuong) + "\n";
        }
    }
    return chuoi;
    }
    
    @Override
    public double tinhTongTien() {
        double tong = 0.0;
        for (Map.Entry<SanPham, Integer> entry : dsSanPham.entrySet()) {
            SanPham sp = entry.getKey();
            int soLuong = entry.getValue();
            tong += sp.getGiaBan() * soLuong;
        }
        return tong;
    }
    

}    
