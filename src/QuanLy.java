import java.time.LocalDate;
import java.util.ArrayList;

public class QuanLy extends NhanVien implements LayThongTin {

    public double heSoPhuCapQuanLy;


    public double getHeSoPhuCapQuanLy() {
        return heSoPhuCapQuanLy;
    }
    public void setHeSoPhuCapQuanLy(double heSoPhuCapQuanLy) {
        this.heSoPhuCapQuanLy = heSoPhuCapQuanLy;
    }

    public QuanLy(){}

    public QuanLy(double heSoPhuCapQuanLy) {
        this.heSoPhuCapQuanLy = heSoPhuCapQuanLy;
    }
    public QuanLy(String maNhanVien, int soNamLam, ArrayList<String> dsMaHoaDon, double heSoPhuCapQuanLy) {
        super(maNhanVien, soNamLam, dsMaHoaDon);
        this.heSoPhuCapQuanLy = heSoPhuCapQuanLy;
    }
    public QuanLy(String hoTen, String SDT, String gioiTinh, LocalDate ngaySinh, DiaChi diaChi, String maNhanVien, int soNamLam, ArrayList<String> dsMaHoaDon, double heSoPhuCapQuanLy) {
        super(hoTen, SDT, gioiTinh, ngaySinh, diaChi, maNhanVien, soNamLam, dsMaHoaDon);
        this.heSoPhuCapQuanLy = heSoPhuCapQuanLy;
    }
    public QuanLy(String hoTen, String SDT, String gioiTinh, LocalDate ngaySinh, DiaChi diaChi, String maNhanVien, int soNamLam, double heSoPhuCapQuanLy) {
        super(hoTen, SDT, gioiTinh, ngaySinh, diaChi, maNhanVien, soNamLam);
        this.heSoPhuCapQuanLy = heSoPhuCapQuanLy;
    }

    @Override
    public void themHoaDon(String maHD) {
        if (!dsMaHoaDon.contains(maHD)) {
            dsMaHoaDon.add(maHD);
        }
    }

    @Override
    public double tinhLuong() {
        return NhanVien.getLuongCoBan() * heSoPhuCapQuanLy + dsMaHoaDon.size() * 50;
    }
    @Override
    public String layThongTinDayDu() {
        String diaChiFull = this.getDiaChi() != null ? this.getDiaChi().layThongTinDayDu() : "";
        String danhSachHD = (dsMaHoaDon == null || dsMaHoaDon.isEmpty()) ?
                "" :
                String.join(", ", dsMaHoaDon);
        String thuocTinhRieng = String.valueOf(this.heSoPhuCapQuanLy); // HSPCQL
        // Format: %-15s %-20s %-10s %-12s %-10s %-25s %-10s %-10s %-15.0f %-50s
        return String.format(
                "%-15s %-20s %-10s %-12s %-10s %-35s %-15s %-10s %-15.0f %-50s",
                this.maNhanVien, this.hoTen, this.gioiTinh, this.ngaySinh.toString(),
                this.SDT, diaChiFull, "QuanLy",
                thuocTinhRieng, this.tinhLuong(), danhSachHD
        );
    }
    @Override
    public String chuyenSangDinhDangTXT() {
        String danhSachHD = (dsMaHoaDon == null || dsMaHoaDon.isEmpty())?
                "" : String.join(",", dsMaHoaDon);
        String[] diaChiParts = diaChi.getTatCaThuocTinh();
        return String.join(";",
                "QuanLy",maNhanVien,hoTen,ngaySinh.toString(),SDT,gioiTinh,
                diaChiParts[0],diaChiParts[1],diaChiParts[2],diaChiParts[3],
                String.valueOf(soNamLam),String.valueOf(heSoPhuCapQuanLy),danhSachHD
                );
    }
}


    