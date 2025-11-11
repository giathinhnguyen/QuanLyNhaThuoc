import java.time.LocalDate;

public class KhuyenMai implements LayThongTin{
    public String maKhuyenMai;
    public String tenKhuyenMai;
    public LocalDate ngayBatDau;
    public LocalDate ngayKetThuc;
    public double phanTramGiam;
    public double dieuKienApDung;

    //  Getter & Setter
    public String getMaKhuyenMai() {
        return maKhuyenMai;
    }

    public void setMaKhuyenMai(String maKhuyenMai) {
        this.maKhuyenMai = maKhuyenMai;
    }

    public String getTenKhuyenMai() {
        return tenKhuyenMai;
    }

    public void setTenKhuyenMai(String tenKhuyenMai) {
        this.tenKhuyenMai = tenKhuyenMai;
    }

    public LocalDate getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public double getPhanTramGiam() {
        return phanTramGiam;
    }

    public void setPhanTramGiam(double phanTramGiam) {
        this.phanTramGiam = phanTramGiam;
    }

    public double getDieuKienApDung() {
        return dieuKienApDung;
    }

    public void setDieuKienApDung(double dieuKienApDung) {
        this.dieuKienApDung = dieuKienApDung;
    }


    public KhuyenMai(){}
    public KhuyenMai(String maKhuyenMai, String tenKhuyenMai,
                     LocalDate ngayBatDau, LocalDate ngayKetThuc,
                     double phanTramGiam, double dieuKienApDung) {
        this.maKhuyenMai = maKhuyenMai;
        this.tenKhuyenMai = tenKhuyenMai;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.phanTramGiam = phanTramGiam;
        this.dieuKienApDung = dieuKienApDung;
    }

    public boolean conHan() {
        LocalDate now = LocalDate.now();
        return (now.isAfter(ngayBatDau) || now.isEqual(ngayBatDau))
                && (now.isBefore(ngayKetThuc) || now.isEqual(ngayKetThuc));
    }

    public double apDungKhuyenMai(double tongTien) {
        if (conHan() && tongTien >= dieuKienApDung) {
            return tongTien * (1 - phanTramGiam / 100);
        }
        return tongTien;
    }

    public String layThongTinDayDu() {
        String trangThai = this.conHan() ? "Con han" : "Het han";
        // Định dạng cột (từ Ma KM trở đi): %-10s %-40s %-12s %-12s %-10.1f %-15.0f %-15s
        return String.format(
                "%-10s %-40s %-12s %-12s %-10.1f %-15.0f %-15s",
                this.getMaKhuyenMai(), this.getTenKhuyenMai(),
                this.getNgayBatDau().toString(), this.getNgayKetThuc().toString(),
                this.getPhanTramGiam(), this.getDieuKienApDung(),
                trangThai
        );
    }

    @Override
    public String chuyenSangDinhDangTXT() {
        return String.join(";",
                maKhuyenMai,
                tenKhuyenMai,
                ngayBatDau.toString(),
                ngayKetThuc.toString(),
                String.valueOf(phanTramGiam),
                String.valueOf(dieuKienApDung)
        );
    }

}
