import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;

public class KhoSanPham extends SanPham{
    public Map<SanPham, Integer> dsTonKho = new HashMap<>();

    public Map<SanPham, Integer> getDsTonKho() {return this.dsTonKho;}
    public void setDsTonKho(Map<SanPham, Integer> dsTonKho) {this.dsTonKho = dsTonKho; }

    public KhoSanPham(String maSanPham, String tenSanPham, String loaiSanPham, String maNhaCungCap, double giaNhap, 
                    double giaBan, int soLuongTon, LocalDate HSD) {
        super(maSanPham, tenSanPham, loaiSanPham, maNhaCungCap, giaNhap, giaBan, soLuongTon, HSD);
    }

    public int laySoLuongTon(SanPham sanPham){
        return dsTonKho.getOrDefault(sanPham, 0);
    }
    public void nhapHang(SanPham sanPham, int soLuong){
        int soLuongHienTai = dsTonKho.getOrDefault(sanPham, 0);
        dsTonKho.put(sanPham, soLuongHienTai + soLuong);
    }
    public void xuatHang(SanPham sanPham, int soLuong){
        if(dsTonKho.containsKey(sanPham)){
            int soLuongHienTai = dsTonKho.get(sanPham);
            if(soLuongHienTai >= soLuong){
                dsTonKho.put(sanPham, soLuongHienTai - soLuong);
            } else {
                System.out.println("Không đủ số lượng trong kho để xuất hàng.");
            }
        } else {
            System.out.println("Sản phẩm không tồn tại trong kho.");
        }
    }
    public void inDanhSachTonKho(){
        // Vòng lặp duyệt qua tất cả sản phẩm trong kho
        for(Map.Entry<SanPham, Integer> entry : tonKho.entrySet()){
            SanPham sanPham = entry.getKey();
            int soLuong = entry.getValue();
            
            // Giả định lớp SanPham có phương thức getTenSanPham()
            System.out.println("Sản phẩm: " + sanPham.getTenSanPham() + ", Số lượng tồn kho: " + soLuong);
        }
    }
} 