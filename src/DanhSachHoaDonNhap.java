import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DanhSachHoaDonNhap implements ChucNang<HoaDonNhap>, Doc_Ghi {
    private ArrayList<HoaDonNhap> dsHoaDonNhap = new ArrayList<>();

    public ArrayList<HoaDonNhap> getDsHoaDonNhap() {
        return dsHoaDonNhap;
    }
    public void setDsHoaDonNhap(ArrayList<HoaDonNhap> dsHoaDonNhap) {
        this.dsHoaDonNhap = dsHoaDonNhap;
    }

    @Override
    public ArrayList<HoaDonNhap> timKiemDanhSach(String tuKhoa){
        ArrayList<HoaDonNhap> ketQua = new ArrayList<>();
        if(tuKhoa == null || tuKhoa.trim().isEmpty()){
            return ketQua;
        }
        tuKhoa = tuKhoa.trim().toLowerCase();
        for(HoaDonNhap hdn : dsHoaDonNhap){
            if (hdn.getMaHoaDon().toLowerCase().contains(tuKhoa) ||
                    hdn.getMaNhaCungCap().toLowerCase().contains(tuKhoa) ||
                    hdn.getMaQuanLy().toLowerCase().contains(tuKhoa) ||
                    hdn.getNgayLap().toString().toLowerCase().contains(tuKhoa) ){
                ketQua.add(hdn);
            }
        }
        return ketQua;
    }
    @Override
    public HoaDonNhap timKiemDoiTuong(String tuKhoa){
        ArrayList<HoaDonNhap> ketQua = timKiemDanhSach(tuKhoa);
        if(ketQua.isEmpty()){
            return null;
        }
        if(ketQua.size() > 1){
            System.out.println("Cảnh báo: Tìm thấy " + ketQua.size() + " hóa đơn nhập khớp từ khóa!");
            System.out.println("Trả về hóa đơn nhập đầu tiên:");
            System.out.println(ketQua.get(0).layThongTinDayDu());
        }
        return ketQua.get(0);
    }
    @Override
    public boolean tonTaiDoiTuong(String maHDN){
        if(timKiemDoiTuong(maHDN) == null)
            return false;
        return true;
    }
    @Override
    public void timKiem(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap ma/ten doi tuong can tim kiem: ");
        String tuKhoa = sc.nextLine();
        HoaDonNhap ketQua = timKiemDoiTuong(tuKhoa);
        System.out.println("---------------Ket qua tim kiem---------------");
        System.out.println(ketQua.layThongTinDayDu());
    }

    @Override
    public boolean them(HoaDonNhap hdn) {
        if(hdn == null){
            return false;
        }
        dsHoaDonNhap.add(hdn);
        return true;
    }

    @Override
    public boolean xoa(String ma) {
        HoaDonNhap hdn = timKiemDoiTuong(ma);
        if (hdn == null) {
            System.out.println("Khong tim thay hoa don nhap!");
            return false;
        }
        if (dsHoaDonNhap.remove(hdn)) {
            System.out.println("Xoa thanh cong!");
            return true;
        } else {
            System.out.println("Xoa that bai!");
            return false;
        }
    }

    @Override
    public boolean sua(String ma, HoaDonNhap hdnMoi) {
        for (int i = 0; i < dsHoaDonNhap.size(); i++) {
            HoaDonNhap hdn = dsHoaDonNhap.get(i);
            if (hdn.getMaHoaDon().equalsIgnoreCase(ma)) {
                dsHoaDonNhap.set(i, hdnMoi);
                return true;
            }
        }
        return false;
    }


    @Override
    public void inDanhSach() {
        if (dsHoaDonNhap.isEmpty()) {
            System.out.println("Danh sach hoa don nhap rong!");
            return;
        }
        System.out.println("\n=========================================================== DANH SACH HOA DON NHAP ===========================================================");
        System.out.printf("%-5s %-10s %-12s %-10s %-10s %-15s %-70s%n",
                "STT", "Ma HDN", "Ngay lap", "Ma QL", "Ma NCC", "Tong tien", "Chi tiet san pham (MaSP-SL)");
        System.out.println("===============================================================================================================================================================================");

        int stt = 1;
        for (HoaDonNhap hdn : dsHoaDonNhap) {
            StringBuilder chiTietStr = new StringBuilder();
            boolean first = true;
            // Su dung getChiTietHoaDon() da luu Map<String, Integer>
            for (Map.Entry<String, Integer> entry : hdn.getChiTietHoaDon().entrySet()) {
                if (!first) {
                    chiTietStr.append(", "); // Dung ", " de phan tach cac item
                }
                chiTietStr.append(entry.getKey()).append("-").append(entry.getValue());
                first = false;
            }
            System.out.printf("%-5d %-10s %-12s %-10s %-10s %-15.0f %-70s%n",
                    stt++,
                    hdn.getMaHoaDon(),
                    hdn.getNgayLap().toString(),
                    hdn.getMaQuanLy(),
                    hdn.getMaNhaCungCap(),
                    hdn.getTongTien(),
                    chiTietStr.toString()
            );
        }

        System.out.println("===============================================================================================================================================================================");
        System.out.println("Tong so hoa don nhap: " + dsHoaDonNhap.size());
    }
    @Override
    public void docFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";", -1);
                if (parts.length < 6) continue;

                String maHD = parts[0];
                LocalDate ngayLap = LocalDate.parse(parts[1]);
                String maQL = parts[2];
                String maNCC = parts[3];
                double tongTien = Double.parseDouble(parts[4]);

                Map<String, Integer> chiTiet = new HashMap<>();
                String chiTietString = parts[5];

                if (!chiTietString.isEmpty()) {
                    String[] items = chiTietString.split(",");
                    for (String item : items) {
                        String[] detail = item.split("-");
                        if (detail.length == 2) {
                            String maSP = detail[0].trim();
                            int soLuong = Integer.parseInt(detail[1].trim());
                            chiTiet.put(maSP, soLuong);
                        }
                    }
                }

                HoaDonNhap hdn = new HoaDonNhap(maHD, ngayLap, tongTien, maQL, maNCC, chiTiet);
                dsHoaDonNhap.add(hdn);
            }
            System.out.println("Da doc danh sach khach hang tu file " + fileName);
        } catch (IOException e) {
            System.out.println("Loi khi doc file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Loi dinh dang du lieu trong file: " + e.getMessage());
        }
    }

    @Override
    public void ghiFile(String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("HoaDonNhap.txt"))) {
            for (HoaDonNhap hdn : dsHoaDonNhap) {
                bw.write(hdn.chuyenSangDinhDangTXT());
                bw.newLine();
            }
            System.out.println("Ghi file thanh cong!");
        } catch (IOException e) {
            System.out.println("Loi khi ghi file: " + e.getMessage());
        }
    }

    //==================== HAM TIEN ICH BO SUNG ====================

    public double thongKeChiPhiTheoNam(int nam) {
        double tongChiPhi = 0;
        for (HoaDonNhap hdn : dsHoaDonNhap) {
            if (hdn.getNgayLap().getYear() == nam) {
                tongChiPhi += hdn.getTongTien();
            }
        }
        return tongChiPhi;
    }
    public Map<String, Integer> thongKeSoLuongHoaDonTheoNCC() {
        Map<String, Integer> thongKe = new HashMap<>();
        for (HoaDonNhap hdn : dsHoaDonNhap) {
            String maNCC = hdn.getMaNhaCungCap();
            thongKe.put(maNCC, thongKe.getOrDefault(maNCC, 0) + 1);
        }
        return thongKe;
    }
    public ArrayList<HoaDonNhap> locTheoKhoangTongTien(double min, double max) {
        ArrayList<HoaDonNhap> ketQua = new ArrayList<>();
        if (min > max) {
            double temp = min;
            min = max;
            max = temp;
        }

        for (HoaDonNhap hdn : dsHoaDonNhap) {
            double tongTien = hdn.getTongTien();
            if (tongTien >= min && tongTien <= max) {
                ketQua.add(hdn);
            }
        }
        return ketQua;
    }
    public HoaDonNhap timHoaDonChiPhiCaoNhat() {
        if (dsHoaDonNhap.isEmpty()) {
            return null;
        }

        HoaDonNhap maxHDN = dsHoaDonNhap.get(0);
        for (HoaDonNhap hdn : dsHoaDonNhap) {
            if (hdn.getTongTien() > maxHDN.getTongTien()) {
                maxHDN = hdn;
            }
        }
        return maxHDN;
    }
    public Map<String, Integer> thongKeSoLuongHoaDonTheoQL() {
        Map<String, Integer> thongKe = new HashMap<>();
        for (HoaDonNhap hdn : dsHoaDonNhap) {
            String maQL = hdn.getMaQuanLy();
            thongKe.put(maQL, thongKe.getOrDefault(maQL, 0) + 1);
        }
        return thongKe;
    }

    public void menuTienIchHoaDonNhap() {
        Scanner sc = new Scanner(System.in);
        int chonTienIch;
        do {
            System.out.println("\n========== MENU TIEN ICH HOA DON NHAP ==========");
            System.out.println("1. Thong ke Tong Chi phi theo nam");
            System.out.println("2. Thong ke so luong hoa don theo Nha Cung Cap");
            System.out.println("3. Loc hoa don theo khoang Tong Tien");
            System.out.println("4. Tim hoa don co chi phi nhap cao nhat");
            System.out.println("5. Thong ke so luong hoa don theo Quan Ly");
            System.out.println("0. Quay lai");
            System.out.print("Chon: ");

            try {
                chonTienIch = Integer.parseInt(sc.nextLine());

                switch (chonTienIch) {
                    case 1:
                        System.out.print("Nhap nam can thong ke (VD: 2025): ");
                        int nam = Integer.parseInt(sc.nextLine());
                        double chiPhi = thongKeChiPhiTheoNam(nam);
                        System.out.printf("Tong chi phi nhap hang nam %d: %.0f VND%n", nam, chiPhi);
                        break;

                    case 2:
                        Map<String, Integer> tkNCC = thongKeSoLuongHoaDonTheoNCC();
                        System.out.println("--- Thong ke so luong hoa don theo Nha Cung Cap ---");
                        if (!tkNCC.isEmpty()) {
                            tkNCC.forEach((maNCC, soLuong) -> System.out.println("Ma NCC: " + maNCC + " | So luong HDN: " + soLuong));
                        } else {
                            System.out.println("Khong co du lieu hoa don nhap.");
                        }
                        break;

                    case 3:
                        System.out.print("Nhap Tong Tien TOI THIEU: ");
                        double min = Double.parseDouble(sc.nextLine());
                        System.out.print("Nhap Tong Tien TOI DA: ");
                        double max = Double.parseDouble(sc.nextLine());
                        ArrayList<HoaDonNhap> kqTien = locTheoKhoangTongTien(min, max);
                        if (!kqTien.isEmpty()) {
                            System.out.println("Ket qua tim kiem (" + kqTien.size() + " hoa don):");
                            kqTien.forEach(hdn -> System.out.println(" - " + hdn.getMaHoaDon() + " | NCC: " + hdn.getMaNhaCungCap() + " | Tong tien: " + hdn.getTongTien()));
                        } else {
                            System.out.println("Khong tim thay hoa don nao trong khoang chi phi nay.");
                        }
                        break;

                    case 4:
                        HoaDonNhap maxHDN = timHoaDonChiPhiCaoNhat();
                        if (maxHDN != null) {
                            System.out.println("Hoa don nhap co chi phi cao nhat:");
                            System.out.println(maxHDN.layThongTinDayDu());
                        } else {
                            System.out.println("Danh sach hoa don rong.");
                        }
                        break;

                    case 5:
                        Map<String, Integer> tkQL = thongKeSoLuongHoaDonTheoQL();
                        System.out.println("--- Thong ke so luong hoa don theo Quan Ly ---");
                        if (!tkQL.isEmpty()) {
                            tkQL.forEach((maQL, soLuong) -> System.out.println("Ma QL: " + maQL + " | So luong HDN: " + soLuong));
                        } else {
                            System.out.println("Khong co du lieu hoa don.");
                        }
                        break;

                    case 0:
                        System.out.println("Quay lai menu truoc...");
                        break;

                    default:
                        System.out.println("Lua chonTienIch khong hop le! Vui long nhap lai.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Loi nhap lieu: Vui long nhap so nguyen cho năm, hoặc số thực cho tiền.");
                chonTienIch = -1;
            }
        } while (chonTienIch != 0);
    }
}


    
