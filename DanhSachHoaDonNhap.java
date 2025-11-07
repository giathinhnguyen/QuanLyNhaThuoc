import java.io.*;
import java.text.SimpleDateFormat; 
import java.util.*;
import java.util.stream.Collectors;

public class DanhSachHoaDonNhap implements ChucNang, Doc_Ghi {
    private ArrayList<HoaDonNhap> dsHoaDonNhap = new ArrayList<>();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public void them() {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Nhập mã hóa đơn: ");
            String maHD = sc.nextLine();
            
            if (timKiem(maHD) != null) {
                System.out.println("❌ Mã hóa đơn đã tồn tại!");
                return;
            }

            System.out.print("Nhập ngày lập (dd/MM/yyyy): ");
            String ngayStr = sc.nextLine();
            Date ngayLap = DATE_FORMAT.parse(ngayStr);

            System.out.print("Nhập mã quản lý: ");
            String maQL = sc.nextLine();

            System.out.print("Nhập mã nhà cung cấp: ");
            String maNCC = sc.nextLine();

            HoaDonNhap hdn = new HoaDonNhap(maHD, ngayLap, 0, maQL, maNCC);
            dsHoaDonNhap.add(hdn);
            System.out.println("✅ Thêm hóa đơn nhập thành công!");
        } catch (Exception e) {
            System.out.println("❌ Lỗi khi thêm hóa đơn: " + e.getMessage());
        }
    }

    public HoaDonNhap timKiem(String tuKhoa) {
        for (HoaDonNhap hdn : dsHoaDonNhap) {
            if (hdn.getMaHoaDon().equalsIgnoreCase(tuKhoa) || 
                hdn.getMaQuanLy().equalsIgnoreCase(tuKhoa) || 
                hdn.getMaNhaCungCap().equalsIgnoreCase(tuKhoa)) {
                return hdn;
            }
        }
        return null;
    }

    @Override
    public void xoa() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã hóa đơn cần xóa: ");
        String ma = sc.nextLine();
        HoaDonNhap found = timKiem(ma);
        if (found == null) {
            System.out.println("❌ Không tìm thấy hóa đơn cần xóa!");
            return;
        }
        dsHoaDonNhap.remove(found);
        System.out.println("✅ Đã xóa hóa đơn có mã: " + ma);
    }

   // ===============================
    // 4️⃣ SỬA
    // ===============================
    @Override
    public void sua() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã hoặc mã quản lý cần sửa: ");
        String tuKhoa = sc.nextLine().trim();
        HoaDonNhap finalHD = timKiem(tuKhoa);
        if (finalHD == null) {
            System.out.println("❌ Không tìm thấy hóa đơn!");
            return;
        }
        System.out.println("\n🔎 Hóa đơn tìm thấy:");
        System.out.println(finalHD.layThongTinDayDu());

        ArrayList<String> thuocTinh = new ArrayList<>(Arrays.asList(
                "Ngày lập (dd/MM/yyyy)", "Mã quản lý", "Mã nhà cung cấp", "Danh sách sản phẩm"
        ));

        ArrayList<Runnable> phuongThuc = new ArrayList<>();
        phuongThuc.add(() -> {
            try {
                System.out.print("Nhập ngày lập mới (dd/MM/yyyy): ");
                finalHD.setNgayLap(DATE_FORMAT.parse(sc.nextLine().trim()));
            } catch (Exception e) {
                System.out.println("❌ Ngày không hợp lệ!");
            }
        });
        phuongThuc.add(() -> {
            System.out.print("Nhập mã quản lý mới: ");
            finalHD.setMaQuanLy(sc.nextLine().trim());
        });
        phuongThuc.add(() -> {
            System.out.print("Nhập mã nhà cung cấp mới: ");
            finalHD.setMaNhaCungCap(sc.nextLine().trim());
        });
        

        System.out.println("\nChọn thuộc tính muốn sửa:");
        for (int i = 0; i < thuocTinh.size(); i++) System.out.println((i + 1) + ". " + thuocTinh.get(i));
        System.out.println("0. Thoát");
        System.out.print("Nhập lựa chọn: ");
        String input = sc.nextLine().trim();
        if (input.equals("0")) return;

        String[] choices = input.split("[,\\s]+");
        boolean daSua = false;
        for (String c : choices) {
            try {
                int chon = Integer.parseInt(c);
                if (chon >= 1 && chon <= thuocTinh.size()) {
                    phuongThuc.get(chon - 1).run();
                    daSua = true;
                } else {
                    System.out.println("❌ Lựa chọn không hợp lệ!");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Lỗi nhập!");
            }
        }

        if (daSua) System.out.println("✅ Sửa thành công!");
        else System.out.println("⚠️ Không có thay đổi.");
    }

    // ===============================
    // 6️⃣ LỌC THEO MÃ QUẢN LÝ
    // ===============================
    public void locTheoMaQuanLy(String maQL) {
        if (maQL == null) {
            System.out.println("❌ Mã quản lý không được để trống!");
            return;
        }
        List<HoaDonNhap> loc = dsHoaDonNhap.stream()
                .filter(h -> maQL.equalsIgnoreCase(h.getMaQuanLy()))
                .collect(Collectors.toList());
        if (loc.isEmpty()) System.out.println("❌ Không có hóa đơn nào!");
        else loc.forEach(h -> System.out.println(h.layThongTinDayDu()));
    }

    @Override
    public void inDanhSach() {
        if (dsHoaDonNhap.isEmpty()) {
            System.out.println("📭 Danh sách hóa đơn nhập trống!");
            return;
        }
        for (HoaDonNhap h : dsHoaDonNhap) {
            System.out.println(h.layThongTinDayDu());
        }
    }

    @Override
    public void docFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("HoaDonNhap.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                HoaDonNhap hdn = HoaDonNhap.chuyenTuDinhDangTXT(line);
                if (hdn != null) {
                    dsHoaDonNhap.add(hdn);
                }
            }
            
            System.out.println("✅ Đọc file thành công!");
        } catch (Exception e) {
            System.out.println("❌ Lỗi đọc file: " + e.getMessage());
        }
    }

    @Override
    public void ghiFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("HoaDonNhap.txt"))) {
            for (HoaDonNhap hdn : dsHoaDonNhap) {
                bw.write(hdn.chuyenSangDinhDangTXT());
                bw.newLine();
            }
            System.out.println("✅ Ghi file thành công!");
        } catch (Exception e) {
            System.out.println("❌ Lỗi ghi file: " + e.getMessage());
        }
    }
}


    
