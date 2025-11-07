import java.util.ArrayList;

public class NhaCungCap implements LayThongTin {

    // =========================
    // THUỘC TÍNH
    // =========================
    public String maNhaCungCap;
    public String tenNhaCungCap;
    public DiaChi diaChi;
    public String SDT;
    public ArrayList<SanPham> dsSanPhamCungCap;

    // =========================
    // CONSTRUCTOR
    // =========================
    public NhaCungCap() {
        dsSanPhamCungCap = new ArrayList<>();
    }

    public NhaCungCap(String maNhaCungCap, String tenNhaCungCap, DiaChi diaChi, String SDT) {
        this.maNhaCungCap = maNhaCungCap;
        this.tenNhaCungCap = tenNhaCungCap;
        this.diaChi = diaChi;
        this.SDT = SDT;
        this.dsSanPhamCungCap = new ArrayList<>();
    }

    // =========================
    // GETTER / SETTER
    // =========================
    public String getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(String maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public String getTenNhaCungCap() {
        return tenNhaCungCap;
    }

    public void setTenNhaCungCap(String tenNhaCungCap) {
        this.tenNhaCungCap = tenNhaCungCap;
    }

    public DiaChi getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(DiaChi diaChi) {
        this.diaChi = diaChi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public ArrayList<SanPham> getDsSanPhamCungCap() {
        return dsSanPhamCungCap;
    }

    public void setDsSanPhamCungCap(ArrayList<SanPham> dsSanPhamCungCap) {
        this.dsSanPhamCungCap = dsSanPhamCungCap;
    }

    // =========================
    // KIỂM TRA HỢP LỆ
    // =========================
    public boolean kiemTraHopLe() {
        if (maNhaCungCap == null || maNhaCungCap.isEmpty()) return false;
        if (tenNhaCungCap == null || tenNhaCungCap.isEmpty()) return false;
        if (SDT == null || !SDT.matches("\\d{9,11}")) return false; // SDT chỉ chứa 9-11 số
        if (diaChi == null) return false;
        return true;
    }

    // =========================
    // THÊM SẢN PHẨM CUNG CẤP
    // =========================
    public void cungCapThemSanPham(SanPham sp) {
        if (sp != null) {
            dsSanPhamCungCap.add(sp);
            System.out.println("✅ Đã thêm sản phẩm '" + sp.getTenSanPham() + "' vào nhà cung cấp: " + tenNhaCungCap);
        } else {
            System.out.println("⚠️ Sản phẩm không hợp lệ!");
        }
    }

    // =========================
    // HIỂN THỊ THÔNG TIN
    // =========================
    @Override
    public String layThongTinDayDu() {
        String thongTin = "Nhà cung cấp { " +
                "\n  Mã NCC: " + maNhaCungCap +
                "\n  Tên: " + tenNhaCungCap +
                "\n  Địa chỉ: " + (diaChi != null ? diaChi.layThongTinDayDu() : "Chưa có") +
                "\n  SĐT: " + SDT +
                "\n  Số lượng SP cung cấp: " + dsSanPhamCungCap.size() +
                "\n}";
        return thongTin;
    }

    // =========================
    // CHUYỂN DỮ LIỆU ĐỂ GHI FILE TXT
    // =========================
    @Override
    public String chuyenSangDinhDangTXT() {
        return maNhaCungCap + ";" + tenNhaCungCap + ";" + SDT + ";" +
                (diaChi != null ? diaChi.toString() : "null");
    }

    // =========================
    // toString()
    // =========================
    @Override
    public String toString() {
        return layThongTinDayDu();
    }
}




    