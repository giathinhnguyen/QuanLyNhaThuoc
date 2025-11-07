import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DanhSachNhanVien implements ChucNang, Doc_Ghi {

    private ArrayList<NhanVien> dsNhanVien;

    public DanhSachNhanVien() {
        dsNhanVien = new ArrayList<>();
    }

    // =========================
    // ĐỌC FILE TXT
    // =========================
    @Override
    public void docTuFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(";");
                if (parts.length >= 10) {
                    String maNV = parts[0];
                    int soNamLam = Integer.parseInt(parts[1]);
                    String hoTen = parts[2];
                    LocalDate ngaySinh = LocalDate.parse(parts[3], dtf);
                    String SDT = parts[4];
                    String gioiTinh = parts[5];
                    DiaChi diaChi = new DiaChi(parts[6], Integer.parseInt(parts[7]), parts[8], parts[9]);
                    NhanVien nv = new NhanVienThuong(maNV, soNamLam, hoTen, ngaySinh, SDT, gioiTinh, diaChi);
                    dsNhanVien.add(nv);
                }
            }
            System.out.println("✅ Đọc file thành công (" + dsNhanVien.size() + " nhân viên).");
        } catch (Exception e) {
            System.out.println("❌ Lỗi đọc file: " + e.getMessage());
        }
    }

    // =========================
    // GHI FILE TXT
    // =========================
    @Override
    public void ghiRaFile(String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (NhanVien nv : dsNhanVien) {
                // Mã NV;Số năm làm;Họ tên;Ngày sinh;SDT;Giới tính;Tỉnh;Số nhà;Quận;Phường
                DiaChi dc = nv.getDiaChi();
                String line = nv.getMaNhanVien() + ";" + nv.getSoNamLam() + ";" + nv.getHoTen() + ";" +
                              nv.getNgaySinh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ";" +
                              nv.getSDT() + ";" + nv.getGioiTinh() + ";" +
                              dc.getTinh() + ";" + dc.getSoNha() + ";" + dc.getQuan() + ";" + dc.getPhuong();
                bw.write(line);
                bw.newLine();
            }
            System.out.println("✅ Ghi file thành công: " + fileName);
        } catch (Exception e) {
            System.out.println("❌ Lỗi ghi file: " + e.getMessage());
        }
    }

    // =========================
    // THÊM NHÂN VIÊN
    // =========================
    @Override
    public void them() {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Nhập mã nhân viên: ");
            String maNV = sc.nextLine();
            System.out.print("Nhập số năm làm: ");
            int soNam = Integer.parseInt(sc.nextLine());
            System.out.print("Nhập họ tên: ");
            String hoTen = sc.nextLine();
            System.out.print("Nhập ngày sinh (dd/MM/yyyy): ");
            LocalDate ngaySinh = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            System.out.print("Nhập SDT: ");
            String SDT = sc.nextLine();
            System.out.print("Nhập giới tính: ");
            String gioiTinh = sc.nextLine();
            System.out.print("Nhập tỉnh: ");
            String tinh = sc.nextLine();
            System.out.print("Nhập số nhà: ");
            int soNha = Integer.parseInt(sc.nextLine());
            System.out.print("Nhập quận: ");
            String quan = sc.nextLine();
            System.out.print("Nhập phường: ");
            String phuong = sc.nextLine();

            DiaChi dc = new DiaChi(soNha, phuong, quan, tinh);
            NhanVien nv = new NhanVienThuong(maNV, soNam, hoTen, ngaySinh, SDT, gioiTinh, dc);
            dsNhanVien.add(nv);
            System.out.println("✅ Đã thêm nhân viên: " + nv.layThongTinDayDu());
        } catch (Exception e) {
            System.out.println("❌ Lỗi thêm nhân viên: " + e.getMessage());
        }
    }

    // =========================
    // SỬA NHÂN VIÊN
    // =========================
    @Override
    public void sua() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã nhân viên cần sửa: ");
        String maNV = sc.nextLine();

        NhanVien nvCanSua = null;
        for (NhanVien nv : dsNhanVien) {
            if (nv.getMaNhanVien().equals(maNV)) {
                nvCanSua = nv;
                break;
            }
        }

        if (nvCanSua == null) {
            System.out.println("❌ Không tìm thấy nhân viên với mã: " + maNV);
            return;
        }

        ArrayList<String> thuocTinh = new ArrayList<>();
        ArrayList<Runnable> thaoTac = new ArrayList<>();

        thuocTinh.add("Họ tên");
        thaoTac.add(() -> { System.out.print("Nhập họ tên mới: "); nvCanSua.setHoTen(sc.nextLine()); });

        thuocTinh.add("Ngày sinh (dd/MM/yyyy)");
        thaoTac.add(() -> {
            try {
                System.out.print("Nhập ngày sinh mới: ");
                nvCanSua.setNgaySinh(LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            } catch (Exception e) {
                System.out.println("❌ Lỗi định dạng ngày sinh.");
            }
        });

        thuocTinh.add("Số năm làm");
        thaoTac.add(() -> { System.out.print("Nhập số năm làm mới: "); nvCanSua.setSoNamLam(Integer.parseInt(sc.nextLine())); });

        thuocTinh.add("SDT");
        thaoTac.add(() -> { System.out.print("Nhập SDT mới: "); nvCanSua.setSDT(sc.nextLine()); });

        thuocTinh.add("Giới tính");
        thaoTac.add(() -> { System.out.print("Nhập giới tính mới: "); nvCanSua.setGioiTinh(sc.nextLine()); });

        thuocTinh.add("Địa chỉ - Tỉnh");
        thaoTac.add(() -> { System.out.print("Nhập tỉnh mới: "); nvCanSua.getDiaChi().setTinh(sc.nextLine()); });
        thuocTinh.add("Địa chỉ - Số nhà");
        thaoTac.add(() -> { System.out.print("Nhập số nhà mới: "); nvCanSua.getDiaChi().setSoNha(Integer.parseInt(sc.nextLine())); });
        thuocTinh.add("Địa chỉ - Quận");
        thaoTac.add(() -> { System.out.print("Nhập quận mới: "); nvCanSua.getDiaChi().setQuan(sc.nextLine()); });
        thuocTinh.add("Địa chỉ - Phường");
        thaoTac.add(() -> { System.out.print("Nhập phường mới: "); nvCanSua.getDiaChi().setPhuong(sc.nextLine()); });

        System.out.println("\nChọn thuộc tính muốn sửa (nhập số, cách nhau bằng dấu phẩy hoặc khoảng trắng):");
        for (int i = 0; i < thuocTinh.size(); i++) {
            System.out.println((i + 1) + ". " + thuocTinh.get(i));
        }
        System.out.println("0. Thoát");
        System.out.print("Nhập lựa chọn: ");
        String input = sc.nextLine().trim();
        if (input.equals("0")) return;

        String[] choices = input.split("[,\\s]+");
        boolean daSua = false;
        for (String choice : choices) {
            try {
                int chon = Integer.parseInt(choice);
                if (chon >= 1 && chon <= thaoTac.size()) {
                    thaoTac.get(chon - 1).run();
                    daSua = true;
                } else {
                    System.out.println("❌ Lựa chọn không hợp lệ: " + chon);
                }
            } catch (Exception e) {
                System.out.println("❌ Lỗi nhập: " + choice);
            }
        }

        if (daSua) System.out.println("✅ Sửa nhân viên thành công!");
        else System.out.println("❌ Không có thay đổi nào được thực hiện.");
    }

    // =========================
    // XÓA NHÂN VIÊN
    // =========================
    @Override
    public void xoa() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã nhân viên cần xóa: ");
        String maNV = sc.nextLine();
        boolean found = dsNhanVien.removeIf(nv -> nv.getMaNhanVien().equals(maNV));
        if (found) System.out.println("✅ Đã xóa nhân viên có mã: " + maNV);
        else System.out.println("❌ Không tìm thấy nhân viên với mã: " + maNV);
    }

    // =========================
    // TÌM KIẾM
    // =========================
    @Override
    public void timKiem() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã hoặc tên nhân viên: ");
        String key = sc.nextLine().toLowerCase();
        boolean found = false;
        for (NhanVien nv : dsNhanVien) {
            if (nv.getMaNhanVien().toLowerCase().contains(key) ||
                nv.getHoTen().toLowerCase().contains(key)) {
                System.out.println(nv.layThongTinDayDu());
                found = true;
            }
        }
        if (!found) System.out.println("❌ Không tìm thấy nhân viên nào phù hợp.");
    }

    // =========================
    // IN DANH SÁCH
    // =========================
    @Override
    public void inDanhSach() {
        if (dsNhanVien.isEmpty()) {
            System.out.println("Danh sách nhân viên rỗng.");
            return;
        }
        System.out.println("======== DANH SÁCH NHÂN VIÊN ========");
        for (NhanVien nv : dsNhanVien) {
            System.out.println(nv.layThongTinDayDu());
        }
    }

    // =========================
    // HÀM MỞ RỘNG
    // =========================
    public void locTheoGioiTinh(String gioiTinh) {
        System.out.println("======== NHÂN VIÊN " + gioiTinh.toUpperCase() + " ========");
        boolean found = false;
        for (NhanVien nv : dsNhanVien) {
            if (nv.getGioiTinh().equalsIgnoreCase(gioiTinh)) {
                System.out.println(nv.layThongTinDayDu());
                found = true;
            }
        }
        if (!found) System.out.println("Không có nhân viên giới tính: " + gioiTinh);
    }

    public void locTheoSoNamLam(int min, int max) {
        System.out.println("======== NHÂN VIÊN CÓ SỐ NĂM LÀM TỪ " + min + " - " + max + " ========");
        boolean found = false;
        for (NhanVien nv : dsNhanVien) {
            if (nv.getSoNamLam() >= min && nv.getSoNamLam() <= max) {
                System.out.println(nv.layThongTinDayDu());
                found = true;
            }
        }
        if (!found) System.out.println("Không có nhân viên nào trong khoảng này.");
    }

    public void sapXepTheoLuongGiamDan() {
        dsNhanVien.sort((a, b) -> Double.compare(b.tinhLuong(), a.tinhLuong()));
        System.out.println("======== SẮP XẾP THEO LƯƠNG GIẢM DẦN ========");
        inDanhSach();
    }

    public void sapXepTheoTen() {
        dsNhanVien.sort((a, b) -> a.getHoTen().compareToIgnoreCase(b.getHoTen()));
        System.out.println("======== SẮP XẾP THEO TÊN A-Z ========");
        inDanhSach();
    }

    public void thongKeGioiTinh() {
        long nam = dsNhanVien.stream().filter(nv -> nv.getGioiTinh().equalsIgnoreCase("Nam")).count();
        long nu = dsNhanVien.stream().filter(nv -> nv.getGioiTinh().equalsIgnoreCase("Nữ")).count();
        System.out.println("======== THỐNG KÊ GIỚI TÍNH ========");
        System.out.println("Nam: " + nam);
        System.out.println("Nữ: " + nu);
    }
}

