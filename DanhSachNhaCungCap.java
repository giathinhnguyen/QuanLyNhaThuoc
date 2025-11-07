import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class DanhSachNhaCungCap implements ChucNang, Doc_Ghi {

    private ArrayList<NhaCungCap> dsNhaCungCap = new ArrayList<>();
    

    // =====================
    // THÊM NCC
    // =====================
    @Override
    public void them() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhập mã NCC: ");
        String ma = sc.nextLine();

        if (timKiem(ma) != null) {
            System.out.println("⚠ Mã NCC đã tồn tại!");
            return;
        }

        System.out.print("Nhập tên NCC: ");
        String ten = sc.nextLine();

        System.out.print("Nhập SĐT: ");
        String sdt = sc.nextLine();

        System.out.println("Nhập địa chỉ:");
        System.out.print("Tỉnh/Thành: ");
        String tinh = sc.nextLine();
        System.out.print("Quận/Huyện: ");
        String quan = sc.nextLine();
        System.out.print("Phường/Xã: ");
        String phuong = sc.nextLine();
        System.out.print("Số nhà: ");
        int soNha = Integer.parseInt(sc.nextLine());

          // Tạo đối tượng địa chỉ luôn tại đây
        DiaChi diaChi = new DiaChi(tinh, quan, phuong, soNha);

        NhaCungCap ncc = new NhaCungCap(ma, ten, diaChi, sdt);

        if (!ncc.kiemTraHopLe()) {
            System.out.println("⚠ Dữ liệu không hợp lệ, thêm thất bại!");
            return;
        }

        dsNhaCungCap.add(ncc);
        System.out.println("✅ Thêm nhà cung cấp thành công!");
    }

    // =====================
    // XÓA
    // =====================
    @Override
    public void xoa() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã NCC cần xóa: ");
        String ma = sc.nextLine();

        NhaCungCap ncc = timKiem(ma);
        if (ncc == null) {
            System.out.println("⚠ Không tìm thấy NCC!");
            return;
        }

        dsNhaCungCap.remove(ncc);
        System.out.println("✅ Xóa thành công!");
    }

    // =====================
    // SỬA 
    // =====================
    @Override
    public void sua() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã hoặc tên NCC cần sửa: ");
        String tuKhoa = sc.nextLine();

        NhaCungCap ncc = timKiem(tuKhoa);
        if (ncc == null) {
            System.out.println("⚠ Không tìm thấy nhà cung cấp!");
            return;
        }

        System.out.println("\nNhà cung cấp tìm thấy:");
        System.out.println(ncc.layThongTinDayDu());

        ArrayList<String> thuocTinh = new ArrayList<>();
        thuocTinh.add("Tên nhà cung cấp");
        thuocTinh.add("Số điện thoại");
        thuocTinh.add("Địa chỉ");

        ArrayList<Runnable> hanhDong = new ArrayList<>();

        hanhDong.add(() -> {
            System.out.print("Nhập tên mới: ");
            ncc.setTenNhaCungCap(sc.nextLine());
        });

        hanhDong.add(() -> {
            System.out.print("Nhập SĐT mới: ");
            ncc.setSDT(sc.nextLine());
        });
        hanhDong.add(() -> {
            System.out.println("Nhập địa chỉ mới:");
            System.out.print("Tỉnh/Thành: ");
            String tinh = sc.nextLine();
            System.out.print("Quận/Huyện: ");
            String quan = sc.nextLine();
            System.out.print("Phường/Xã: ");
            String phuong = sc.nextLine();
            System.out.print("Số nhà: ");
            int soNha = Integer.parseInt(sc.nextLine());
            DiaChi diaChiMoi = new DiaChi(tinh, quan, phuong, soNha);
            ncc.setDiaChi(diaChiMoi);
        });
        

        System.out.println("\nChọn thuộc tính muốn sửa:");
        for (int i = 0; i < thuocTinh.size(); i++) {
            System.out.println((i + 1) + ". " + thuocTinh.get(i));
        }
        System.out.println("0. Thoát");

        System.out.print("Nhập lựa chọn: ");
        String input = sc.nextLine().trim();
        if (input.equals("0")) return;
        String[] choices = input.split("[,\s]+");

        for (String choice : choices) {
            try {
                int chon = Integer.parseInt(choice);
                if (chon >= 1 && chon <= thuocTinh.size()) {
                    hanhDong.get(chon - 1).run();
                }
            } catch (Exception e) {
                System.out.println("⚠ Lỗi nhập: " + choice);
            }
        }

        System.out.println("✅ Sửa thành công!");
    }

    // =====================
    // TÌM KIẾM
    // =====================
    @Override
    public NhaCungCap timKiem(String maNhaCungCap) {
        for (NhaCungCap ncc : dsNhaCungCap) {
            if (ncc.getMaNhaCungCap().equalsIgnoreCase(maNhaCungCap)
                    || ncc.getTenNhaCungCap().toLowerCase().contains(maNhaCungCap.toLowerCase())) {
                return ncc;
            }
        }
        return null;
    }

    // =====================
    // IN DANH SÁCH
    // =====================
    @Override
    public void inDanhSach() {
        if (dsNhaCungCap.isEmpty()) {
            System.out.println("⚠ Danh sách rỗng!");
            return;
        }
        dsNhaCungCap.forEach(ncc -> System.out.println(ncc.layThongTinDayDu() + "\n"));
    }

    // =====================
    // SẮP XẾP NCC THEO SỐ SP CUNG CẤP
    // =====================
    public void sapXepTheoSoLuongSanPhamCungCap() {
        Collections.sort(dsNhaCungCap,
                Comparator.comparingInt(n -> n.getDsSanPhamCungCap().size()));
        System.out.println("✅ Đã sắp xếp tăng dần theo số lượng sản phẩm.");
    }

    // =====================
    // LỌC THÊM
    // =====================
    public ArrayList<NhaCungCap> timTheoTenGanDung(String keyword) {
        ArrayList<NhaCungCap> result = new ArrayList<>();
        for (NhaCungCap ncc : dsNhaCungCap) {
            if (ncc.getTenNhaCungCap().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(ncc);
            }
        }
        return result;
    }

    // =====================
    // GHI FILE
    // =====================
    @Override
    public void ghiFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (NhaCungCap ncc : dsNhaCungCap) {
                bw.write(ncc.chuyenSangDinhDangTXT());
                bw.newLine();
            }
            System.out.println("✅ Ghi file thành công!");
        } catch (Exception e) {
            System.out.println("❌ Lỗi ghi file!");
        }
    }

        @Override
    public void docFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            dsNhaCungCap.clear();
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                String[] p = data[3].split(",\s*");

                int soNha = Integer.parseInt(p[0]);
                String phuong = p[1];
                String quan = p[2];
                String tinh = p[3];

                DiaChi diaChi = new DiaChi(tinh, quan, phuong, soNha);
                dsNhaCungCap.add(new NhaCungCap(data[0], data[1], diaChi, data[2]));
            }
            System.out.println("✅ Đọc file thành công!");
        } catch (Exception e) {
            System.out.println("❌ Lỗi đọc file!");
        }
    }
}


