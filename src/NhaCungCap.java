import java.util.ArrayList;

public class NhaCungCap implements LayThongTin {
    public String maNhaCungCap;
    public String tenNhaCungCap;
    public DiaChi diaChi;
    public String SDT;
    public ArrayList<String> dsSanPhamCungCap;

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

    public ArrayList<String> getDsSanPhamCungCap() {
        return dsSanPhamCungCap;
    }

    public void setDsSanPhamCungCap(ArrayList<String> dsSanPhamCungCap) {
        this.dsSanPhamCungCap = dsSanPhamCungCap;
    }

    public void themSanPham(String maSP) {
        if (maSP != null && !dsSanPhamCungCap.contains(maSP)) {
            dsSanPhamCungCap.add(maSP);
        }
    }
    @Override
    public String layThongTinDayDu() {
        String danhSachSP = (dsSanPhamCungCap == null || dsSanPhamCungCap.isEmpty()) ?
                "" :
                String.join(",", dsSanPhamCungCap);
        String diaChiFull = (diaChi != null) ?
                diaChi.layThongTinDayDu() :
                "Khong co";
        String format = String.format("%-10s %-30s %-15s %-40s %-50s",
                this.getMaNhaCungCap(),
                this.getTenNhaCungCap(),
                this.getSDT(),
                diaChiFull,
                danhSachSP
        );

        return format;
    }
    @Override
    public String chuyenSangDinhDangTXT() {
        // Chuan bi chuoi danh sach ma san pham
        String danhSachSP = (dsSanPhamCungCap == null || dsSanPhamCungCap.isEmpty()) ?
                "" :
                String.join(",", dsSanPhamCungCap);
        String soNha = "";
        String duong = "";
        String quan = "";
        String tinh = "";

        if (diaChi != null) {
            soNha = diaChi.getSoNha();
            duong = diaChi.getDuong();
            quan = diaChi.getQuan();
            tinh = diaChi.getTinh();
        }
        return String.join(";",
                maNhaCungCap,tenNhaCungCap,SDT,
                soNha,duong,quan,tinh,
                danhSachSP
        );
    }
}




    