import java.util.Scanner;

public class DiaChi implements LayThongTin{
    public String tinh, quan, duong, soNha;

    public String getTinh() {
        return tinh;
    }
    public void setTinh(String tinh) {
        this.tinh = tinh;
    }
    public String getQuan() {
        return quan;
    }
    public void setQuan(String quan) {
        this.quan = quan;
    }
    public String getDuong() {
        return duong;
    }
    public void setDuong(String phuong) {
        this.duong = phuong;
    }
    public String getSoNha() {
        return soNha;
    }
    public void setSoNha(String soNha) {
        this.soNha = soNha;
    }

    public DiaChi(){}
    public DiaChi(String tinh, String quan, String duong, String soNha) {
        this.tinh = tinh;
        this.quan = quan;
        this.duong = duong;
        this.soNha = soNha;
    }
    public void nhapDiaChi() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap tinh: ");
        this.tinh = sc.nextLine(); // DÙNG this.tinh
        System.out.print("Nhap quan: ");
        this.quan = sc.nextLine(); // DÙNG this.quan
        System.out.print("Nhap duong: ");
        this.duong = sc.nextLine(); // DÙNG this.duong
        System.out.print("Nhap so nha: ");
        this.soNha = sc.nextLine(); // DÙNG this.soNha
    }
    public String[] getTatCaThuocTinh() {
        return new String[]{soNha, duong, quan, tinh};
    }
    @Override
    public String layThongTinDayDu() {
        return soNha + ", " + duong + ", " + quan + ", " + tinh;
    }

    @Override
    public String chuyenSangDinhDangTXT() {
        return String.join(";",
                soNha,duong,quan,tinh
        );
    }
}
