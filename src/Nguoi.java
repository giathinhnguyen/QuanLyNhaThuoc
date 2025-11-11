import java.time.LocalDate;

public abstract class Nguoi {
    public String hoTen, SDT, gioiTinh;
    public LocalDate ngaySinh;
    public DiaChi diaChi;

    public String getHoTen() {
        return hoTen;
    }
    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }
    public String getSDT() {
        return SDT;
    }
    public void setSDT(String SDT) {
        this.SDT = SDT;
    }
    public String getGioiTinh() {
        return gioiTinh;
    }
    public void setGioiTinh(String gioiTinh) {
        kiemTraGioiTinh(gioiTinh);
        this.gioiTinh = gioiTinh;
    }
    public LocalDate getNgaySinh() {
        return ngaySinh;
    }
    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public DiaChi getDiaChi() {
        return diaChi;
    }
    public void setDiaChi(DiaChi diaChi) {
        this.diaChi = diaChi;
    }

    public Nguoi(){
    }
    public Nguoi(String hoTen, String SDT, String gioiTinh, LocalDate ngaySinh, DiaChi diaChi) {
        this.hoTen = hoTen;
        this.SDT = SDT;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
    }
    public boolean kiemTraGioiTinh(String gt){
        if(!gioiTinh.equalsIgnoreCase("Nam") && !gioiTinh.equalsIgnoreCase("Nu")){
            gioiTinh = "Nam";
            return false;
        }
        return true;
    }
}
