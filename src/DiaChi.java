public class DiaChi implements KiemTraHopLe,LayThongTin{
    public String tinh, quan, phuong, soNha;

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
    public String getPhuong() {
        return phuong;
    }
    public void setPhuong(String phuong) {
        this.phuong = phuong;
    }
    public String getSoNha() {
        return soNha;
    }
    public void setSoNha(String soNha) {
        this.soNha = soNha;
    }

    public DiaChi(String tinh, String quan, String phuong, String soNha) {
        this.tinh = tinh;
        this.quan = quan;
        this.phuong = phuong;
        this.soNha = soNha;
    }
    @Override
    public boolean kiemTraHopLe() {
        return tinh != null && !tinh.trim().isEmpty()
                && quan != null && !quan.trim().isEmpty()
                && phuong != null && !phuong.trim().isEmpty()
                && soNha != null && !soNha.trim().isEmpty();
    }

    @Override
    public String layThongTinDayDu() {
        return soNha + ", " + phuong + ", " + quan + ", " + tinh;
    }
}
