import java.util.*;
import java.io.*;
import java.time.LocalDate;

public class DanhSachNhanVien implements ChucNang<NhanVien>, Doc_Ghi {

    private ArrayList<NhanVien> dsNhanVien;

    public ArrayList<NhanVien> getDsNhanVien() {
        return dsNhanVien;
    }
    public void setDsNhanVien(ArrayList<NhanVien> dsNhanVien) {
        this.dsNhanVien = dsNhanVien;
    }
    public DanhSachNhanVien() {
        dsNhanVien = new ArrayList<>();
    }

    @Override
    public void docFile(String fileName) {
        dsNhanVien.clear();
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split(";");
                String loai = parts[0].trim().toLowerCase();
                String maNV = parts[1];
                String hoTen = parts[2];
                LocalDate ngaySinh = LocalDate.parse(parts[3]);
                String sdt = parts[4];
                String gioiTinh = parts[5];
                DiaChi diaChi = new DiaChi(parts[9], parts[8], parts[7], parts[6]);
                int soNamLam = Integer.parseInt(parts[10]);

                NhanVien nv = null;
                switch (loai) {
                    case "nhanvienbanthuoc" -> {
                        int soNgayNghi = Integer.parseInt(parts[11]);
                        nv = new NhanVienBanThuoc(hoTen, sdt, gioiTinh, ngaySinh, diaChi, maNV, soNamLam, soNgayNghi);
                        if(parts.length > 12 && !parts[12].isEmpty()){
                            String[] maHDs = parts[12].split(",");
                            for (String maHD : maHDs) {
                                nv.themHoaDon(maHD.trim().toUpperCase());
                            }
                        }
                    }
                    case  "quanly" -> {
                        double heSoPhuCap = Double.parseDouble(parts[11]);
                        nv = new QuanLy(hoTen, sdt, gioiTinh, ngaySinh, diaChi, maNV, soNamLam, heSoPhuCap);
                        if(parts.length > 12 && !parts[12].isEmpty()){
                            String[] maHDs = parts[12].split(",");
                            for (String maHD : maHDs) {
                                nv.themHoaDon(maHD.trim().toUpperCase());
                            }
                        }
                    }
                }
                if (nv != null) dsNhanVien.add(nv);
            }
            System.out.println("Doc file thanh cong! Co " + dsNhanVien.size() + " nhan vien.");
        }  catch (Exception e){
            System.out.println("Loi khi doc file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    public void ghiFile(String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (NhanVien nv : dsNhanVien) {
                bw.write(nv.chuyenSangDinhDangTXT());
                bw.newLine();
            }
            System.out.println("Ghi file thanh cong! (" + dsNhanVien.size() + " dong)");
        } catch (IOException e) {
            System.out.println("Loi khi ghi file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // tim theo sdt, gioiTinh, ngaySinh, soNamLam, maNhanVien, soNgayNghi, hsQL
    @Override
    public ArrayList<NhanVien> timKiemDanhSach(String tuKhoa){
        ArrayList<NhanVien> ketQua = new ArrayList<>();
        if(tuKhoa == null || tuKhoa.trim().isEmpty()){
            return ketQua;
        }
        tuKhoa = tuKhoa.trim().toLowerCase();
        for(NhanVien nv : dsNhanVien){
            boolean khop = false;
            if (nv.getMaNhanVien().toLowerCase().contains(tuKhoa) ||
                    String.valueOf(nv.getSoNamLam()).toLowerCase().contains(tuKhoa) ||
                    nv.getSDT().toLowerCase().contains(tuKhoa) ||
                    nv.getGioiTinh().toLowerCase().contains(tuKhoa) ||
                    nv.getNgaySinh().toString().toLowerCase().contains(tuKhoa)){
                khop = true;
            }
            if (!khop && nv instanceof NhanVienBanThuoc){
                NhanVienBanThuoc nvbt = (NhanVienBanThuoc) nv;
                if(String.valueOf(nvbt.getSoNgayNghi()).toLowerCase().contains(tuKhoa)){
                    khop = true;
                }
            }
            if(!khop && nv instanceof QuanLy){
                QuanLy ql = (QuanLy) nv;
                if (String.valueOf(ql.getHeSoPhuCapQuanLy()).toLowerCase().contains(tuKhoa)){
                    khop = true;
                }
            }
            if(khop){
                ketQua.add(nv);
            }
        }
        return ketQua;
    }
    @Override
    public NhanVien timKiemDoiTuong(String tuKhoa){
        ArrayList<NhanVien> ketQua = timKiemDanhSach(tuKhoa);
        if (ketQua.isEmpty()) {
            return null;
        }
        if (ketQua.size() > 1) {
            System.out.println("Canh bao: Tim thay " + ketQua.size() + " nhan vien khop tu khoa!");
            System.out.println("Tra ve nhan vien dau tien:");
            System.out.println(ketQua.get(0).layThongTinDayDu());
        }
        return ketQua.get(0);
    }
    @Override
    public void timKiem(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap ma/ten doi tuong can tim kiem: ");
        String tuKhoa = sc.nextLine();
        NhanVien ketQua = timKiemDoiTuong(tuKhoa);
        System.out.println("---------------Ket qua tim kiem---------------");
        System.out.println(ketQua.layThongTinDayDu());
    }
    @Override
    public boolean tonTaiDoiTuong(String maNV){
        if(timKiemDoiTuong(maNV) == null)
            return false;
        return true;
    }


    @Override
    public boolean them(NhanVien nv) {
        if(nv == null){
            return false;
        }
        dsNhanVien.add(nv);
        return true;
    }
    @Override
    public boolean sua(String ma, NhanVien nvMoi) {
        for (int i = 0; i < dsNhanVien.size(); i++) {
            NhanVien nv = dsNhanVien.get(i);
            if (nv.getMaNhanVien().equalsIgnoreCase(ma)) {
                dsNhanVien.set(i, nvMoi);
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean xoa(String ma) {
        NhanVien nv = timKiemDoiTuong(ma);
        if (nv == null) {
            System.out.println("Khong tim thay nhan vien!");
            return false;
        }
        if (dsNhanVien.remove(nv)) {
            System.out.println("Xoa thanh cong!");
            return true;
        } else {
            System.out.println("Xoa that bai!");
            return false;
        }
    }

    @Override
    public void inDanhSach() {
        if (dsNhanVien.isEmpty()) {
            System.out.println("Danh sach nhan vien rong!");
            return;
        }

        System.out.println("=======================================================================================================================================================================");
        System.out.printf("%-5s %-15s %-20s %-10s %-12s %-10s %-35s %-15s %-10s %-15s %-50s%n",
                "STT", "Ma NV", "Ho ten", "Gioi tinh", "Ngay sinh", "SDT", "Dia chi", "Loai", "SNN/HSPCQL", "Luong", "Ma hoa don");
        System.out.println("=======================================================================================================================================================================");

        int stt = 1;
        for (NhanVien nv : dsNhanVien) {
            String thongTinNhanVien = nv.layThongTinDayDu();
            System.out.printf("%-5d %s%n",
                    stt++,
                    thongTinNhanVien
            );
        }
        System.out.println("=======================================================================================================================================================================");
        System.out.println("Tong so nhan vien: " + dsNhanVien.size());
    }

    //==================== HAM TIEN ICH BO SUNG ====================

    public ArrayList<NhanVienBanThuoc> locNhanVienBanHang() {
        ArrayList<NhanVienBanThuoc> ketQua = new ArrayList<>();
        for (NhanVien nv : dsNhanVien) {
            if (nv instanceof NhanVienBanThuoc) {
                ketQua.add((NhanVienBanThuoc) nv);
            }
        }
        return ketQua;
    }
    public ArrayList<QuanLy> locQuanLy() {
        ArrayList<QuanLy> ketQua = new ArrayList<>();
        for (NhanVien nv : dsNhanVien) {
            if (nv instanceof QuanLy) {
                ketQua.add((QuanLy) nv);
            }
        }
        return ketQua;
    }
    public ArrayList<NhanVien> locTheoGioiTinh(String gioiTinh) {
        ArrayList<NhanVien> ketQua = new ArrayList<>();
        if (gioiTinh == null || gioiTinh.trim().isEmpty()) return ketQua;

        gioiTinh = gioiTinh.trim().toLowerCase();
        for (NhanVien nv : dsNhanVien) {
            if (nv.getGioiTinh().toLowerCase().equals(gioiTinh)) {
                ketQua.add(nv);
            }
        }
        return ketQua;
    }
    public ArrayList<NhanVien> locTheoThamNien(int soNamToiThieu) {
        ArrayList<NhanVien> ketQua = new ArrayList<>();
        for (NhanVien nv : dsNhanVien) {
            if (nv.getSoNamLam() >= soNamToiThieu) {
                ketQua.add(nv);
            }
        }
        return ketQua;
    }
    public Map<String, Integer> thongKeSoLuongTheoLoai() {
        Map<String, Integer> thongKe = new HashMap<>();
        thongKe.put("QuanLy", 0);
        thongKe.put("NhanVienBanThuoc", 0);

        for (NhanVien nv : dsNhanVien) {
            if (nv instanceof QuanLy) {
                thongKe.put("QuanLy", thongKe.get("QuanLy") + 1);
            } else if (nv instanceof NhanVienBanThuoc) {
                thongKe.put("NhanVienBanThuoc", thongKe.get("NhanVienBanThuoc") + 1);
            }
        }
        return thongKe;
    }
    public void sapXepTheoSoNamLamGiamDan() {
        int n = this.dsNhanVien.size();
        if (n <= 1) {
            System.out.println("Danh sach khong du phan tu de sap xep.");
            return;
        }
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (this.dsNhanVien.get(j).getSoNamLam() < this.dsNhanVien.get(j + 1).getSoNamLam()) {
                    NhanVien temp = this.dsNhanVien.get(j);
                    this.dsNhanVien.set(j, this.dsNhanVien.get(j + 1));
                    this.dsNhanVien.set(j + 1, temp);
                }
            }
        }
        System.out.println(">>> Da sap xep danh sach nhan vien theo So Nam Lam (giam dan) bang Bubble Sort.");
    }

    private void hienThiKetQuaLoc(String tieuDe, List<? extends NhanVien> danhSach) {
        if (danhSach.isEmpty()) {
            System.out.println(">>> Khong tim thay " + tieuDe);
        } else {
            System.out.println(">>> Ket qua " + tieuDe + " (" + danhSach.size() + " nhan vien):");
            danhSach.forEach(nv -> System.out.println(nv.layThongTinDayDu()));
        }
    }
    public void menuTienIchNhanVien() {
        Scanner sc = new Scanner(System.in);
        int chonTienIch;
        do {
            System.out.println("\n========== MENU TIEN ICH NHAN VIEN ==========");
            System.out.println("1. Loc nhan vien ban hang");
            System.out.println("2. Loc quan ly");
            System.out.println("3. Loc theo gioi tinh");
            System.out.println("4. Loc theo tham nien (So nam lam toi thieu)");
            System.out.println("5. Sap xep theo So Nam Lam giam dan va in danh sach");
            System.out.println("6. Thong ke so luong nhan vien theo loai");
            System.out.println("0. Quay lai");
            System.out.print("Chon: ");
            try {
                chonTienIch = Integer.parseInt(sc.nextLine());

                switch (chonTienIch) {
                    case 1:
                        hienThiKetQuaLoc("Nhan vien Ban Hang", locNhanVienBanHang());
                        break;
                    case 2:
                        hienThiKetQuaLoc("Quan Ly", locQuanLy());
                        break;
                    case 3:
                        System.out.print("Nhap gioi tinh can loc (Nam/Nu): ");
                        String gioiTinh = sc.nextLine();
                        hienThiKetQuaLoc("Nhan vien Gioi tinh " + gioiTinh, locTheoGioiTinh(gioiTinh));
                        break;
                    case 4:
                        System.out.print("Nhap so nam lam toi thieu: ");
                        int soNam = Integer.parseInt(sc.nextLine());
                        hienThiKetQuaLoc("Nhan vien co tham nien tren " + soNam + " nam", locTheoThamNien(soNam));
                        break;
                    case 5:
                        sapXepTheoSoNamLamGiamDan();
                        inDanhSach();
                        break;
                    case 6:
                        Map<String, Integer> tkLoai = thongKeSoLuongTheoLoai();
                        System.out.println(">>> Thong ke so luong nhan vien theo loai:");
                        tkLoai.forEach((loai, soLuong) -> System.out.println(" - " + loai + ": " + soLuong));
                        break;
                    case 0:
                        System.out.println("Quay lai menu truoc...");
                        break;
                    default:
                        System.out.println("Lua chonTienIch khong hop le! Vui long nhap lai.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Loi nhap lieu: Vui long nhap so nguyen.");
                chonTienIch = -1;
            }
        } while (chonTienIch != 0);
    }
}

