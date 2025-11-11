
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.time.format.DateTimeParseException;

public class DanhSachHoaDonBan implements ChucNang<HoaDonBanHang>, Doc_Ghi{
    private ArrayList<HoaDonBanHang> dsHoaDonBanHang = new ArrayList<>();

    public ArrayList<HoaDonBanHang> getDsHoaDonBanHang() { return this.dsHoaDonBanHang;}
    public void setDsHoaDonBanHang(ArrayList<HoaDonBanHang> dsHoaDonBanHang) { this.dsHoaDonBanHang = dsHoaDonBanHang;}

    @Override
    public ArrayList<HoaDonBanHang> timKiemDanhSach(String tuKhoa){
        ArrayList<HoaDonBanHang> ketQua = new ArrayList<>();
        if(tuKhoa == null || tuKhoa.trim().isEmpty()){
            return ketQua;
        }
        tuKhoa = tuKhoa.trim().toLowerCase();
        for(HoaDonBanHang hdb : dsHoaDonBanHang){
            if (hdb.getMaHoaDon().toLowerCase().contains(tuKhoa) ||
                    hdb.getMaKhachHang().toLowerCase().contains(tuKhoa) ||
                    hdb.getMaKhuyenMai().toLowerCase().contains(tuKhoa) ||
                    hdb.getMaNhanVienLap().toLowerCase().contains(tuKhoa) ||
                    hdb.getNgayLap().toString().toLowerCase().contains(tuKhoa) ){
                ketQua.add(hdb);
            }
        }
        return ketQua;
    }
    @Override
    public HoaDonBanHang timKiemDoiTuong(String tuKhoa){
        ArrayList<HoaDonBanHang> ketQua = timKiemDanhSach(tuKhoa);
        if(ketQua.isEmpty()){
            return null;
        }
        if(ketQua.size() > 1){
            System.out.println("Cảnh báo: Tìm thấy " + ketQua.size() + " hóa đơn bán khớp từ khóa!");
            System.out.println("Trả về hóa đơn đầu tiên:");
            System.out.println(ketQua.get(0).layThongTinDayDu());
        }
        return ketQua.get(0);
    }
    @Override
    public boolean tonTaiDoiTuong(String maHDB){
        if(timKiemDoiTuong(maHDB) == null)
            return false;
        return true;
    }
    @Override
    public void timKiem(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap ma/ten doi tuong can tim kiem: ");
        String tuKhoa = sc.nextLine();
        HoaDonBanHang ketQua = timKiemDoiTuong(tuKhoa);
        System.out.println("---------------Ket qua tim kiem---------------");
        System.out.println(ketQua.layThongTinDayDu());
    }
    @Override
    public boolean them(HoaDonBanHang hdb){
        if(hdb == null){
            return false;
        }
        dsHoaDonBanHang.add(hdb);
        return true;
    }
    @Override
    public boolean xoa(String ma) {
        HoaDonBanHang hdb = timKiemDoiTuong(ma);
        if (hdb == null) {
            System.out.println("Khong tim thay hoa don!");
            return false;
        }
        if (dsHoaDonBanHang.remove(hdb)) {
            System.out.println("Xoa thanh cong!");
            return true;
        } else {
            System.out.println("Xoa that bai!");
            return false;
        }
    }
    @Override
    public boolean sua(String ma, HoaDonBanHang hdbMoi) {
        for (int i = 0; i < dsHoaDonBanHang.size(); i++) {
            HoaDonBanHang hdb = dsHoaDonBanHang.get(i);
            if (hdb.getMaHoaDon().equalsIgnoreCase(ma)) {
                dsHoaDonBanHang.set(i, hdbMoi);
                return true;
            }
        }
        return false;
    }

    @Override
    public void ghiFile(String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (HoaDonBanHang hdb : dsHoaDonBanHang) {
                // Sử dụng hàm chuyenSangDinhDangTXT() của đối tượng HoaDonBanHang
                bw.write(hdb.chuyenSangDinhDangTXT());
                bw.newLine();
            }
            System.out.println(">>> Ghi file hoa don ban thanh cong: " + fileName);
        } catch (Exception e) {
            System.err.println("Loi khi ghi file hoa don ban: " + e.getMessage());
        }
    }

    @Override
    public void docFile(String fileName) {
        int lineNo = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                lineNo++;
                String[] parts = line.split(";", -1);
                // Cần 7 phần tử: MãHD, Ngày, NV, KH, KM, TT, ChiTiết
                if (parts.length < 7) {
                    System.err.println("Loi dinh dang dong " + lineNo + ". Thieu cot.");
                    continue;
                }
                try {
                    String maHD = parts[0].trim();
                    LocalDate ngayLap = LocalDate.parse(parts[1].trim());
                    String maNV = parts[2].trim();
                    String maKH = parts[3].trim();
                    String maKM = parts[4].trim();
                    double tongTien = Double.parseDouble(parts[5].trim());
                    String chiTietString = parts[6].trim();
                    // 1. Xử lý Map chi tiết
                    Map<String, Integer> chiTiet = new HashMap<>();
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

                    HoaDonBanHang hdb = new HoaDonBanHang(maHD, ngayLap, tongTien, chiTiet, maKH, maNV, maKM);
                    dsHoaDonBanHang.add(hdb);
                } catch (DateTimeParseException | NumberFormatException ex) {
                    System.err.println("Loi chuyen doi du lieu dong " + lineNo + ": " + ex.getMessage());
                }
            }
            System.out.println(">>> Doc file hoa don ban thanh cong: " + fileName);
        } catch (Exception e) {
            System.err.println("Loi he thong khi doc file: " + e.getMessage());
        }
    }
    @Override
    public void inDanhSach() {
        if (dsHoaDonBanHang.isEmpty()) {
            System.out.println("Danh sach hoa don ban rong!");
            return;
        }
        System.out.println("\n====================================================== DANH SACH HOA DON BAN =======================================================");
        System.out.printf("%-5s %-10s %-12s %-10s %-10s %-10s %-15s %-70s%n",
                "STT", "Ma HDB", "Ngay lap", "Ma NVB", "Ma KH", "Ma KM", "Tong tien", "Chi tiet san pham (MaSP-SL)");
        System.out.println("==========================================================================================================================================================================");
        int stt = 1;
        for (HoaDonBanHang hdb : dsHoaDonBanHang) {
            // Chuyen Map chi tiet thanh chuoi de hien thi
            StringBuilder chiTietStr = new StringBuilder();
            boolean first = true;
            for (Map.Entry<String, Integer> entry : hdb.getChiTietHoaDon().entrySet()) {
                if (!first) {
                    chiTietStr.append(", ");
                }
                chiTietStr.append(entry.getKey()).append("-").append(entry.getValue());
                first = false;
            }

            System.out.printf("%-5d %-10s %-12s %-10s %-10s %-10s %-15.0f %-70s%n",
                    stt++,
                    hdb.getMaHoaDon(),
                    hdb.getNgayLap().toString(),
                    hdb.getMaNhanVienLap(),
                    hdb.getMaKhachHang(),
                    hdb.getMaKhuyenMai(),
                    hdb.getTongTien(),
                    chiTietStr.toString()
            );
        }
        System.out.println("==========================================================================================================================================================================");
        System.out.println("Tong so hoa don ban: " + dsHoaDonBanHang.size());
    }

    //==================== HAM TIEN ICH BO SUNG ====================

    public HoaDonBanHang timHoaDonGiaTriCaoNhat() {
        if (dsHoaDonBanHang.isEmpty()) {
            return null;
        }
        HoaDonBanHang maxHDB = dsHoaDonBanHang.get(0);
        for (HoaDonBanHang hdb : dsHoaDonBanHang) {
            if (hdb.getTongTien() > maxHDB.getTongTien()) {
                maxHDB = hdb;
            }
        }
        return maxHDB;
    }

    public double thongKeDoanhThuTheoNam(int nam) {
        double tongDoanhThu = 0;
        for (HoaDonBanHang hdb : dsHoaDonBanHang) {
            if (hdb.getNgayLap().getYear() == nam) {
                tongDoanhThu += hdb.getTongTien();
            }
        }
        return tongDoanhThu;
    }
    public ArrayList<HoaDonBanHang> timKiemTheoKhoangThoiGian(LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        ArrayList<HoaDonBanHang> ketQua = new ArrayList<>();
        if (ngayBatDau.isAfter(ngayKetThuc)) {
            LocalDate temp = ngayBatDau;
            ngayBatDau = ngayKetThuc;
            ngayKetThuc = temp;
        }
        for (HoaDonBanHang hdb : dsHoaDonBanHang) {
            LocalDate ngayLap = hdb.getNgayLap();
            if (!ngayLap.isBefore(ngayBatDau) && !ngayLap.isAfter(ngayKetThuc)) {
                ketQua.add(hdb);
            }
        }
        return ketQua;
    }
    public ArrayList<HoaDonBanHang> locTheoMaKhuyenMai(String maKM) {
        return timKiemDanhSach(maKM);
    }
    public Map<String, Integer> thongKeSoLuongHoaDonTheoNV() {
        Map<String, Integer> thongKe = new HashMap<>();
        for (HoaDonBanHang hdb : dsHoaDonBanHang) {
            String maNV = hdb.getMaNhanVienLap();
            thongKe.put(maNV, thongKe.getOrDefault(maNV, 0) + 1);
        }
        return thongKe;
    }
    public void sapXepTheoTongTienGiamDan() {
        int n = this.dsHoaDonBanHang.size();
        if (n <= 1) {
            System.out.println("Danh sach khong du phan tu de sap xep.");
            return;
        }
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (this.dsHoaDonBanHang.get(j).getTongTien() < this.dsHoaDonBanHang.get(j + 1).getTongTien()) {
                    HoaDonBanHang temp = this.dsHoaDonBanHang.get(j);
                    this.dsHoaDonBanHang.set(j, this.dsHoaDonBanHang.get(j + 1));
                    this.dsHoaDonBanHang.set(j + 1, temp);
                }
            }
        }
        System.out.println(">>> Da sap xep danh sach hoa don ban theo Tong Tien (giam dan) bang Bubble Sort.");
    }
    public void menuTienIchHoaDonBan() {
        Scanner sc = new Scanner(System.in);
        int chonTienIch;
        do {
            System.out.println("\n========== MENU TIEN ICH HOA DON BAN ==========");
            System.out.println("1. Thong ke Tong Doanh thu theo nam");
            System.out.println("2. Tim kiem hoa don trong khoang thoi gian");
            System.out.println("3. Tim hoa don co tong tien cao nhat");
            System.out.println("4. Thong ke so luong hoa don theo nhan vien");
            System.out.println("5. Loc hoa don theo Ma Khuyen Mai");
            System.out.println("6. Sap xep theo Tong Tien giam dan va in danh sach");
            System.out.println("0. Quay lai");
            System.out.print("Chon: ");

            try {
                chonTienIch = Integer.parseInt(sc.nextLine());

                switch (chonTienIch) {
                    case 1:
                        System.out.print("Nhap nam can thong ke (VD: 2025): ");
                        int nam = Integer.parseInt(sc.nextLine());
                        double doanhThu = thongKeDoanhThuTheoNam(nam);
                        System.out.printf("Tong doanh thu nam %d: %.0f VND%n", nam, doanhThu);
                        break;

                    case 2:
                        System.out.print("Nhap ngay bat dau (yyyy-MM-dd): ");
                        LocalDate start = LocalDate.parse(sc.nextLine());
                        System.out.print("Nhap ngay ket thuc (yyyy-MM-dd): ");
                        LocalDate end = LocalDate.parse(sc.nextLine());
                        ArrayList<HoaDonBanHang> kqTG = timKiemTheoKhoangThoiGian(start, end);
                        if (!kqTG.isEmpty()) {
                            System.out.println("Ket qua tim kiem (" + kqTG.size() + " hoa don):");
                            // Tạm thời in ra thông tin cơ bản
                            kqTG.forEach(hdb -> System.out.println(" - " + hdb.getMaHoaDon() + " | Ngay: " + hdb.getNgayLap() + " | Tong tien: " + hdb.getTongTien()));
                        } else {
                            System.out.println("Khong tim thay hoa don nao trong khoang thoi gian nay.");
                        }
                        break;

                    case 3:
                        HoaDonBanHang maxHDB = timHoaDonGiaTriCaoNhat();
                        if (maxHDB != null) {
                            System.out.println("Hoa don co tong tien cao nhat:");
                            System.out.println(maxHDB.layThongTinDayDu()); // Sử dụng hàm layThongTinDayDu đã được căn chỉnh
                        } else {
                            System.out.println("Danh sach hoa don rong.");
                        }
                        break;

                    case 4:
                        Map<String, Integer> tkNV = thongKeSoLuongHoaDonTheoNV();
                        System.out.println("--- Thong ke so luong hoa don theo Nhan Vien ---");
                        if (!tkNV.isEmpty()) {
                            tkNV.forEach((maNV, soLuong) -> System.out.println("Ma NV: " + maNV + " | So luong HDB: " + soLuong));
                        } else {
                            System.out.println("Khong co du lieu hoa don.");
                        }
                        break;

                    case 5:
                        System.out.print("Nhap Ma Khuyen Mai can loc: ");
                        String maKM = sc.nextLine();
                        ArrayList<HoaDonBanHang> kqKM = locTheoMaKhuyenMai(maKM);
                        if (!kqKM.isEmpty()) {
                            System.out.println("Ket qua loc theo KM " + maKM + " (" + kqKM.size() + " hoa don):");
                            kqKM.forEach(hdb -> System.out.println(" - " + hdb.getMaHoaDon() + " | Ngay: " + hdb.getNgayLap() + " | Tong tien: " + hdb.getTongTien()));
                        } else {
                            System.out.println("Khong tim thay hoa don nao ap dung Ma Khuyen Mai nay.");
                        }
                        break;

                    case 6:
                        if (this.dsHoaDonBanHang.isEmpty()) {
                            System.out.println("Danh sach hoa don ban rong, khong the sap xep.");
                        } else {
                            sapXepTheoTongTienGiamDan();
                            inDanhSach();
                        }
                        break;

                    case 0:
                        System.out.println("Quay lai menu truoc...");
                        break;

                    default:
                        System.out.println("Lua chonTienIch khong hop le! Vui long nhap lai.");
                }
            } catch (NumberFormatException | DateTimeParseException e) {
                System.err.println("Loi nhap lieu: Vui long nhap dung dinh dang (so nguyen/ngay 'yyyy-MM-dd').");
                chonTienIch = -1; // Đảm bảo lặp lại nếu có lỗi
            }
        } while (chonTienIch != 0);
    }
 }
    
