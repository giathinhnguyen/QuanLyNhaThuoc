public class Thuoc extends SanPham{
    public String loaiThuoc, congDung;
    public boolean canToaBacSi;

    public String getLoaiThuoc() {
        return loaiThuoc;
    }
    public void setLoaiThuoc(String loaiThuoc) {
        this.loaiThuoc = loaiThuoc;
    }
    public String getCongDung() {
        return congDung;
    }
    public void setCongDung(String congDung) {
        this.congDung = congDung;
    }
    public boolean isCanToaBacSi() {
        return canToaBacSi;
    }
    public void setCanToaBacSi(boolean canToaBacSi) {
        this.canToaBacSi = canToaBacSi;
    }

    public Thuoc(String maSanPham, String tenSanPham, String loaiSanPham, String donViTinh,
                 String maNhaCungCap, double giaNhap, double giaBan, int soLuongTon, String loaiThuoc,
                 String congDung, boolean canToaBacSi) {
        super(maSanPham, tenSanPham, loaiSanPham, donViTinh, maNhaCungCap, giaNhap, giaBan, soLuongTon);
        this.loaiThuoc = loaiThuoc;
        this.congDung = congDung;
        this.canToaBacSi = canToaBacSi;
    }


    @Override
    public String layThongTinDayDu() {
            return "Thuoc {" +
                    "Mã sản phẩm: '" + maSanPham + '\'' +
                    ", Tên: '" + tenSanPham + '\'' +
                    ", Loại sản phẩm: '" + loaiSanPham + '\'' +
                    ", Đơn vị tính: '" + donViTinh + '\'' +
                    ", Mã nhà cung cấp: '" + maNhaCungCap + '\'' +
                    ", Giá nhập: " + giaNhap +
                    ", Giá bán: " + giaBan +
                    ", Số lượng tồn: " + soLuongTon +
                    ", Loại thuốc: '" + loaiThuoc + '\'' +
                    ", Công dụng: '" + congDung + '\'' +
                    ", Cần toa bác sĩ: " + (canToaBacSi ? "Có" : "Không") +
                    '}';
    }
}
