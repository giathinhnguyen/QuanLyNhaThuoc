
import java.util.ArrayList; 
import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;
import java.time.format.DateTimeParseException;

public class DanhSachHoaDonBan implements ChucNang{
    public int soLuong;
    private ArrayList<HoaDonBanHang> dsHoaDonBanHang = new ArrayList<>();

    public void setSoLuong(int soLuong) { this.soLuong = soLuong;}
    public int getSoLuong() { return this.soLuong;}
    public ArrayList<HoaDonBanHang> getDsHoaDonBanHang() { return this.dsHoaDonBanHang;}
    public void setDsHoaDonBanHang(ArrayList<HoaDonBanHang> dsHoaDonBanHang) { this.dsHoaDonBanHang = dsHoaDonBanHang;}

    public DanhSachHoaDonBan(int soLuong) {
        this.soLuong = soLuong;
    }
    
    @Override
    public void them() {
        Scanner sc = new Scanner(System.in);       
        System.out.print("Nhập mã hóa đơn: ");
        String maHD = sc.nextLine();       
        System.out.print("Nhập ngày lập hóa đơn (yyyy-MM-dd): ");
        String ngayStr = sc.nextLine();
        LocalDate ngayLap;
        try {
            ngayLap = LocalDate.parse(ngayStr); // Chuyển đổi từ chuỗi sang LocalDate
        } catch (DateTimeParseException e) {
            System.out.println("Định dạng ngày không hợp lệ. Vui lòng nhập theo định dạng yyyy-MM-dd.");
            return;
        }

        System.out.print("Nhập tổng tiền hóa đơn: ");
        double tongTien = sc.nextDouble();
        sc.nextLine();
        System.out.print("Nhập mã khách hàng: ");
        String maKH = sc.nextLine();
        System.out.print("Nhập mã nhân viên: ");
        String maNV = sc.nextLine();
        System.out.print("Nhập mã khuyến mãi: ");
        String maKhuyenMai = sc.nextLine();
   
        HoaDonBanHang hoaDon = new HoaDonBanHang(maHD, ngayLap, tongTien, maKH, maNV, maKhuyenMai);
        // Có thể thêm phần nhập danh sách sản phẩm nếu cần

        dsHoaDonBanHang.add(hoaDon);
        soLuong++;
        System.out.println("Đã thêm hóa đơn.");
    }
    @Override
    public void xoa() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã hóa đơn cần xóa: ");
        String maHD = sc.nextLine();

        HoaDonBanHang hoaDonCanXoa = null;
        for (HoaDonBanHang hd : dsHoaDonBanHang) {
            if (hd.getMaHoaDon().equalsIgnoreCase(maHD)) {
                hoaDonCanXoa = hd;
                break;
            }
        }

        if (hoaDonCanXoa != null) {
            dsHoaDonBanHang.remove(hoaDonCanXoa);
            soLuong--;
            System.out.println("Đã xóa hóa đơn.");
        } else {
            System.out.println("Không tìm thấy hóa đơn.");
        }
    }

    @Override
    public void sua() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã hóa đơn cần sửa: ");
        String maHD = sc.nextLine();

        for (int i = 0; i < dsHoaDonBanHang.size(); i++) {
            if (dsHoaDonBanHang.get(i).getMaHoaDon().equalsIgnoreCase(maHD)) {
                System.out.print("Nhập ngày lập mới (yyyy-MM-dd): ");
                String ngayStr = sc.nextLine();
                LocalDate ngayLap;
                try {
                    ngayLap = LocalDate.parse(ngayStr); // Chuyển đổi từ chuỗi sang LocalDate
                } catch (DateTimeParseException e) {
                    System.out.println("Định dạng ngày không hợp lệ. Vui lòng nhập theo định dạng yyyy-MM-dd.");
                    return;
                }
                System.out.print("Nhập tổng tiền hóa đơn: ");
                double tongTien = sc.nextDouble();
                sc.nextLine();
                System.out.print("Nhập mã khách hàng: ");
                String maKH = sc.nextLine();
                System.out.print("Nhập mã nhân viên: ");
                String maNV = sc.nextLine();
                System.out.print("Nhập mã khuyến mãi: ");
                String maKhuyenMai = sc.nextLine();

                HoaDonBanHang hoaDonMoi = new HoaDonBanHang(maHD, ngayLap, tongTien, maKH, maNV, maKhuyenMai);
                dsHoaDonBanHang.set(i, hoaDonMoi);
                System.out.println("Đã cập nhật hóa đơn.");
                return;
            }
        }
        System.out.println("Không tìm thấy hóa đơn.");
    }
    @Override
    public void timKiem() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã hóa đơn cần tìm: ");
        String maHD = sc.nextLine();

        for (HoaDonBanHang hd : dsHoaDonBanHang) {
            if (hd.getMaHoaDon().equalsIgnoreCase(maHD)) {
                System.out.println("Hóa đơn tìm thấy: " + hd.layThongTinDayDu());
                return;
            }
        }
        System.out.println("Không tìm thấy hóa đơn.");
    }

    public double thongKeDoanhThuTheoNgay() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập ngày cần thống kê (yyyy-MM-dd): ");
        String ngayStr = sc.nextLine();

        LocalDate ngay;
        try {
            ngay = LocalDate.parse(ngayStr);
        } catch (DateTimeParseException e) {
            System.out.println("Định dạng ngày không hợp lệ.");
            return 0.0;
        }

        double tongDoanhThu = 0.0;
        for (HoaDonBanHang hoaDonBanHang : dsHoaDonBanHang) {
            if (hoaDonBanHang.getNgayLap().equals(ngay)) {
                for (Map.Entry<SanPham, Integer> entry : hoaDonBanHang.getDsSanPham().entrySet()) {
                    SanPham sp = entry.getKey();
                    int soLuong = entry.getValue();
                    tongDoanhThu += sp.getGiaBan() * soLuong;
                }
            }
        }

        System.out.println("Tổng doanh thu ngày " + ngay + ": " + tongDoanhThu);
        return tongDoanhThu;
    }
    @Override
    public String chuyenSangDinhDangTXT() {
        StringBuilder sb = new StringBuilder();
        for (HoaDonBanHang hd : dsHoaDonBanHang) {
            sb.append(hd.layThongTinDayDu()).append("\n");
        }
        return sb.toString();
    }
    
 }
    
