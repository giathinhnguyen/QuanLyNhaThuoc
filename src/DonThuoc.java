
import java.util.Map;
import java.util.HashMap;

public class DonThuoc{
    public String maDonThuoc;
    public String maKhachHang;
    public String tenBacSi;
    public String ngayKeToa;
    public Map<Thuoc, Integer> dsThuoc = new HashMap<>();

    public void setMaDonThuoc(String maDonThuoc) { this.maDonThuoc = maDonThuoc;}
    public String getMaDonThuoc() { return this.maDonThuoc;}
    public void setMaKhachHang(String maKhachHang) { this.maKhachHang = maKhachHang;}
    public String getMaKhachHang() { return this.maKhachHang;}
    public void setTenBacSi(String tenBacSi) { this.tenBacSi = tenBacSi;}
    public String getTenBacSi() { return this.tenBacSi;}
    public void setNgayKeToa(String ngayKeToa) { this.ngayKeToa = ngayKeToa;}
    public String getNgayKeToa() { return this.ngayKeToa;}
    public Map<Thuoc, Integer> getDsThuoc() { return this.dsThuoc;}
    public void setDsThuoc(Map<Thuoc, Integer> dsThuoc) { this.dsThuoc = dsThuoc;}

    public DonThuoc(String maDonThuoc, String maKhachHang, String tenBacSi, String ngayKeToa) {
        this.maDonThuoc = maDonThuoc;
        this.maKhachHang = maKhachHang;
        this.tenBacSi = tenBacSi;
        this.ngayKeToa = ngayKeToa;
    }

    // kiểm tra tên bác sĩ và ngày kê toa không được để trống
    public boolean kiemTra(){
        if(tenBacSi!=null && !tenBacSi.isEmpty() && ngayKeToa!=null && !ngayKeToa.isEmpty()){
            return true;
        }
        return false;
    }

}
