import java.time.LocalDate;
import java.util.*;
import java.io.*;

public class DanhSachKhachHang implements ChucNang<KhachHang>, Doc_Ghi{
    private ArrayList<KhachHang> dsKhachHang = new ArrayList<>();
    public ArrayList<KhachHang> getDsKhachHang() { return this.dsKhachHang; }
    public void setDsKhachHang(ArrayList<KhachHang> dsKhachHang) { this.dsKhachHang = dsKhachHang; }

    // tim kiem theo ma, hang, diem tich luy, tong chi tieu
    @Override
    public ArrayList<KhachHang> timKiemDanhSach(String tuKhoa){
        ArrayList<KhachHang> ketQua = new ArrayList<>();
        if(tuKhoa == null || tuKhoa.trim().isEmpty()){
            return ketQua;
        }
        tuKhoa = tuKhoa.trim().toLowerCase();
        for(KhachHang kh : dsKhachHang){
            if(kh.getMaKhachHang().toLowerCase().contains(tuKhoa) ||
                    kh.getSDT().toLowerCase().contains(tuKhoa) ||
                    kh.getHangThanhVien().toLowerCase().contains(tuKhoa) ||
                    String.valueOf(kh.getDiemTichLuy()).toLowerCase().contains(tuKhoa) ||
                    String.valueOf(kh.getTongChiTieu()).toLowerCase().contains(tuKhoa) ){
                ketQua.add(kh);
            }
        }
        return ketQua;
    }
    @Override
    public KhachHang timKiemDoiTuong(String tuKhoa){
        ArrayList<KhachHang> ketQua = timKiemDanhSach(tuKhoa);
        if(ketQua.isEmpty()){
            return null;
        }
        if(ketQua.size() > 1){
            System.out.println("Canh bao: Tim thay " + ketQua.size() + " khach hang khop tu khoa!");
            System.out.println("Tra ve khach hang dau tien:");
            System.out.println(ketQua.get(0).layThongTinDayDu());
        }
        return ketQua.get(0);
    }
    @Override
    public boolean tonTaiDoiTuong(String maKH){
        if(timKiemDoiTuong(maKH) == null)
            return false;
        return true;
    }
    @Override
    public void timKiem(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap ma/ten doi tuong can tim kiem: ");
        String tuKhoa = sc.nextLine();
        KhachHang ketQua = timKiemDoiTuong(tuKhoa);
        System.out.println("---------------Ket qua tim kiem---------------");
        System.out.println(ketQua.layThongTinDayDu());
    }


    @Override
    public boolean them(KhachHang kh) {
        if(kh == null)
            return false;
        dsKhachHang.add(kh);
        return true;
    }
    @Override
    public boolean xoa(String ma){
        KhachHang kh = timKiemDoiTuong(ma);
        if(kh == null){
            System.out.println("Khong tim thay khach hang!");
            return false;
        }
        if (dsKhachHang.remove(kh)){
            System.out.println("Xoa thanh cong!");
            return true;
        } else {
            System.out.println("Xoa that bai!");
            return false;
        }
    }
    @Override
    public boolean sua(String ma, KhachHang khMoi) {
        for (int i = 0; i < dsKhachHang.size(); i++) {
            KhachHang kh = dsKhachHang.get(i);
            if (kh.getMaKhachHang().equalsIgnoreCase(ma)) {
                dsKhachHang.set(i, khMoi);
                return true;
            }
        }
        return false;
    }
    @Override
    public void inDanhSach() {
            if (dsKhachHang.isEmpty()) {
                System.out.println("Danh sach khach hang rong!");
                return;
            }
            System.out.println("\n============================================ DANH SACH KHACH HANG ==============================================");
            System.out.printf("%-5s %-10s %-20s %-12s %-8s %-12s %-40s %-12s %-12s %-15s %-50s%n",
                    "STT", "Ma KH", "Ho ten", "Ngay sinh", "GT", "SDT", "Dia chi", "Hang TV", "Diem TL", "Tong chi tieu", "Ma hoa don da mua");
            System.out.println("=========================================================================================================================================================================");

            int stt = 1;
            for (KhachHang kh : dsKhachHang) {
                String thongTinKhachHang = kh.layThongTinDayDu();
                System.out.printf("%-5d %s%n",
                        stt++,
                        thongTinKhachHang
                );
            }
            System.out.println("=========================================================================================================================================================================");
            System.out.println("Tong so khach hang: " + dsKhachHang.size());
    }
    @Override
    public void ghiFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (KhachHang kh : dsKhachHang) {
                writer.write(kh.chuyenSangDinhDangTXT());
                writer.newLine();
            }
            System.out.println("Da ghi danh sach khach hang ra file " + fileName);
        } catch (IOException e) {
            System.out.println("Loi khi ghi file: " + e.getMessage());
        }
    }

    public void docFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            dsKhachHang.clear();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                    String maKH = parts[0];
                    String hoTen = parts[1];
                    LocalDate ngaySinh = LocalDate.parse(parts[2]);
                    String gioiTinh = parts[3];
                    DiaChi diachi = new DiaChi(parts[8],  parts[7], parts[6], parts[5]);
                    String sdt = parts[4];
                    double diemTichLuy = Double.parseDouble(parts[9]);
                    String hangThanhVien = parts[10];
                    double tongChiTieu = Double.parseDouble(parts[11]);

                    KhachHang kh = new KhachHang(hoTen, sdt, gioiTinh, ngaySinh,
                            diachi, maKH, hangThanhVien, tongChiTieu, diemTichLuy);

                if (parts.length > 12 && !parts[12].isEmpty()) {
                    String[] maHDs = parts[12].split(",");
                    for (String maHD : maHDs) {
                        kh.themHoaDon(maHD.trim().toUpperCase());
                    }
                }
                dsKhachHang.add(kh);
            }
            System.out.println("Da doc danh sach khach hang tu file " + fileName);
        } catch (IOException e) {
            System.out.println("Loi khi doc file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Loi dinh dang du lieu trong file: " + e.getMessage());
        }
    }


    //==================== HAM TIEN ICH BO SUNG ====================
    private void hienThiKetQuaLoc(String tieuDe, List<KhachHang> danhSach) {
        if (danhSach.isEmpty()) {
            System.out.println(">>> Khong tim thay " + tieuDe);
        } else {
            System.out.println("\n>>> Ket qua " + tieuDe + " (" + danhSach.size() + " khach hang):");
            int stt = 1;
            for (KhachHang kh : danhSach) {
                System.out.println("--- STT " + stt++ + " ---");
                System.out.println(kh.layThongTinDayDu());
            }
            System.out.println("Tong so khach hang: " + danhSach.size());
        }
    }
    public ArrayList<KhachHang> locTheoHangThanhVien(String hang) {
        ArrayList<KhachHang> ketQua = new ArrayList<>();
        hang = hang.trim().toLowerCase();
        for (KhachHang kh : dsKhachHang) {
            if (kh.getHangThanhVien().toLowerCase().equals(hang)) {
                ketQua.add(kh);
            }
        }
        return ketQua;
    }
    public ArrayList<KhachHang> locTheoTongChiTieuToiThieu(double chiTieuToiThieu) {
        ArrayList<KhachHang> ketQua = new ArrayList<>();
        for (KhachHang kh : dsKhachHang) {
            if (kh.getTongChiTieu() >= chiTieuToiThieu) {
                ketQua.add(kh);
            }
        }
        return ketQua;
    }
    public void sapXepTheoDiemTichLuyGiamDan() {
        int n = dsKhachHang.size();
        if (n <= 1) return;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                KhachHang kh1 = dsKhachHang.get(j);
                KhachHang kh2 = dsKhachHang.get(j + 1);
                if (kh1.getDiemTichLuy() < kh2.getDiemTichLuy()) {
                    dsKhachHang.set(j, kh2);
                    dsKhachHang.set(j + 1, kh1);
                }
            }
        }
        System.out.println(">>> Danh sach khach hang da duoc sap xep theo Diem Tich Luy (Giam dan) thu cong.");
    }

    public ArrayList<KhachHang> timKhachHangDiemCaoNhat() {
        ArrayList<KhachHang> ketQua = new ArrayList<>();
        if (dsKhachHang.isEmpty()) {
            return ketQua;
        }
        double maxDiem = 0;
        for (KhachHang kh : dsKhachHang) {
            if (kh.getDiemTichLuy() > maxDiem) {
                maxDiem = kh.getDiemTichLuy();
            }
        }
        for (KhachHang kh : dsKhachHang) {
            if (kh.getDiemTichLuy() == maxDiem) {
                ketQua.add(kh);
            }
        }
        return ketQua;
    }

    public Map<String, Integer> thongKeTheoHang() {
        Map<String, Integer> thongKe = new HashMap<>();
        for (KhachHang kh : dsKhachHang) {
            String hang = kh.getHangThanhVien();
            thongKe.put(hang, thongKe.getOrDefault(hang, 0) + 1);
        }
        return thongKe;
    }

    public void menuTienIchKhachHang() {
        Scanner sc = new Scanner(System.in);
        int chonTienIch;
        do {
            System.out.println("\n========== MENU TIEN ICH KHACH HANG ==========");
            System.out.println("1. Loc khach hang theo Hang Thanh Vien");
            System.out.println("2. Loc khach hang theo Tong Chi Tieu toi thieu");
            System.out.println("3. Sap xep theo Diem Tich Luy giam dan va in danh sach");
            System.out.println("4. Tim Khach hang co Diem Tich Luy cao nhat");
            System.out.println("5. Thong ke so luong khach hang theo Hang");
            System.out.println("0. Quay lai");
            System.out.print("Chon: ");

            try {
                chonTienIch = Integer.parseInt(sc.nextLine());

                switch (chonTienIch) {
                    case 1:
                        System.out.print("Nhap Hang Thanh Vien can loc (Dong/Bac/Vang/Moi): ");
                        String hang = sc.nextLine();
                        hienThiKetQuaLoc("Khach hang Hang " + hang, locTheoHangThanhVien(hang));
                        break;
                    case 2:
                        System.out.print("Nhap Tong Chi Tieu toi thieu (VND): ");
                        double chiTieu = Double.parseDouble(sc.nextLine());
                        hienThiKetQuaLoc("Khach hang chi tieu tu " + String.format("%,.0f", chiTieu) + " VND tro len",
                                locTheoTongChiTieuToiThieu(chiTieu));
                        break;
                    case 3:
                        sapXepTheoDiemTichLuyGiamDan();
                        inDanhSach();
                        break;
                    case 4:
                        ArrayList<KhachHang> khMaxDiem = timKhachHangDiemCaoNhat();
                        hienThiKetQuaLoc("Khach hang co Diem Tich Luy cao nhat", khMaxDiem);
                        break;
                    case 5:
                        Map<String, Integer> tkHang = thongKeTheoHang();
                        System.out.println(">>> Thong ke so luong khach hang theo Hang:");
                        tkHang.forEach((hangTV, soLuong) -> System.out.println(" - Hang " + hangTV + ": " + soLuong + " khach hang"));
                        break;
                    case 0:
                        System.out.println("Quay lai menu truoc...");
                        break;
                    default:
                        System.out.println("Lua chonTienIch khong hop le! Vui long nhap lai.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Loi nhap lieu: Vui long nhap so hoac so tien hop le.");
                chonTienIch = -1;
            }
        } while (chonTienIch != 0);
    }
}
