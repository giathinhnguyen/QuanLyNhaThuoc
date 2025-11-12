
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class DanhSachSanPham implements ChucNang<SanPham>,Doc_Ghi{
    public ArrayList<SanPham> danhSachSanPham = new ArrayList<>();

    public DanhSachSanPham() {
        danhSachSanPham = new ArrayList<>();
    }
    public DanhSachSanPham(ArrayList<SanPham> danhSachSanPham) {
        this.danhSachSanPham = danhSachSanPham;
    }

    public ArrayList<SanPham> getDanhSachSanPham() {
        return danhSachSanPham;
    }
    public void setDanhSachSanPham(ArrayList<SanPham> danhSachSanPham) {
        this.danhSachSanPham = danhSachSanPham;
    }

    @Override
    public ArrayList<SanPham> timKiemDanhSach(String tuKhoa){
        ArrayList<SanPham> ketQua = new ArrayList<>();
        if(tuKhoa == null || tuKhoa.trim().isEmpty()){
            return ketQua;
        }
        tuKhoa = tuKhoa.trim().toLowerCase();
        for(SanPham sp : danhSachSanPham){
            boolean khop = false;
            if (sp.getMaSanPham().toLowerCase().contains(tuKhoa) ||
                    sp.getTenSanPham().toLowerCase().contains(tuKhoa) ||
                    sp.getLoaiSanPham().toLowerCase().contains(tuKhoa) ||
                    sp.getMaNhaCungCap().toLowerCase().contains(tuKhoa)) {
                khop = true;
            }
            if (!khop && sp instanceof Thuoc) {
                Thuoc t = (Thuoc) sp;
                if (t.getLoaiThuoc().toLowerCase().contains(tuKhoa) ||
                        t.getCongDung().toLowerCase().contains(tuKhoa)) {
                    khop = true;
                }
            }
            if (!khop && sp instanceof MyPham) {
                MyPham m = (MyPham) sp;
                if (m.getLoaiMyPham().toLowerCase().contains(tuKhoa) ||
                        m.getLoaiDaPhuHop().toLowerCase().contains(tuKhoa)) {
                    khop = true;
                }
            }
            if (!khop && sp instanceof ThucPhamChucNang) {
                ThucPhamChucNang t = (ThucPhamChucNang) sp;
                if (t.getLoaiThucPhamChucNang().toLowerCase().contains(tuKhoa) ||
                        t.getBoSungDuongChat().toLowerCase().contains(tuKhoa)) {
                    khop = true;
                }
            }
            if (!khop && sp instanceof DungCuYTe) {
                DungCuYTe d = (DungCuYTe) sp;
                if (d.getChatLieu().toLowerCase().contains(tuKhoa) ||
                        d.getXuatXu().toLowerCase().contains(tuKhoa)) {
                    khop = true;
                }
            }
            if (khop) {
                ketQua.add(sp);
            }
        }
        return ketQua;
    }
    @Override
    public SanPham timKiemDoiTuong(String tuKhoa) {
        ArrayList<SanPham> ketQua = timKiemDanhSach(tuKhoa);
        if (ketQua.isEmpty()) {
            return null;
        }
        if (ketQua.size() > 1) {
            System.out.println("Canh bao: Tim thay " + ketQua.size() + " sản phẩm khớp từ khóa!");
            System.out.println("Tra ve san pham đau tien:");
            System.out.println(ketQua.get(0).layThongTinDayDu());
        }
        return ketQua.get(0);
    }

    @Override
    public void timKiem(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap ma/ten doi tuong can tim kiem: ");
        String tuKhoa = sc.nextLine();
        SanPham ketQua = timKiemDoiTuong(tuKhoa);
        System.out.println("---------------Ket qua tim kiem---------------");
        System.out.println(ketQua.layThongTinDayDu());
    }
    @Override
    public boolean tonTaiDoiTuong(String maSP){
        if(timKiemDoiTuong(maSP) == null)
            return false;
        return true;
    }

    @Override
    public void docFile(String fileName) {
        danhSachSanPham.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split(";", -1);
                String loai = parts[0].trim().toLowerCase();

                SanPham sp = null;
                switch (loai) {
                    case "thuoc":
                        sp = new Thuoc(
                                parts[1],parts[2],parts[0],parts[7],Double.parseDouble(parts[4]), Double.parseDouble(parts[3]),
                                Integer.parseInt(parts[5]),LocalDate.parse(parts[6]),parts[8],parts[9], Boolean.parseBoolean(parts[10])
                        );
                        break;

                    case "thucphamchucnang":
                        sp = new ThucPhamChucNang(
                                parts[1], parts[2], parts[0], parts[7],
                                Double.parseDouble(parts[4]), Double.parseDouble(parts[3]),
                                Integer.parseInt(parts[5]), LocalDate.parse(parts[6]),
                                parts[8], parts[9], Integer.parseInt(parts[10])
                        );
                        break;

                    case "mypham":
                        sp = new MyPham(
                                parts[1], parts[2], parts[0], parts[7],
                                Double.parseDouble(parts[4]), Double.parseDouble(parts[3]),
                                Integer.parseInt(parts[5]), LocalDate.parse(parts[6]),
                                parts[8], parts[9]
                        );
                        break;

                    case "dungcuyte":
                        sp = new DungCuYTe(
                                parts[1], parts[2], parts[0],  parts[7],
                                Double.parseDouble(parts[4]), Double.parseDouble(parts[3]),
                                Integer.parseInt(parts[5]), LocalDate.parse(parts[6]),
                                parts[8], parts[9]
                        );
                        break;
                }
                if (sp != null) danhSachSanPham.add(sp);
            }
            System.out.println("Doc file thanh cong (" + danhSachSanPham.size() + " san pham)");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ghiFile(String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (SanPham sp : danhSachSanPham) {
                bw.write(sp.chuyenSangDinhDangTXT());
                bw.newLine();
            }
            System.out.println(" Ghi file thanh cong: " + fileName);
        }  catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean them(SanPham sp) {
        if(sp == null){
            return false;
        }
        danhSachSanPham.add(sp);
        return true;
    }
    @Override
    public boolean sua(String ma, SanPham spMoi) {
        for (int i = 0; i < danhSachSanPham.size(); i++) {
            SanPham sp = danhSachSanPham.get(i);
            if (sp.getMaSanPham().equalsIgnoreCase(ma)) {
                danhSachSanPham.set(i, spMoi);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean xoa(String ma) {
        SanPham sp = timKiemDoiTuong(ma);
        if (sp == null) {
            System.out.println("Khong tim thay san pham!");
            return false;
        }
        if (danhSachSanPham.remove(sp)) {
            System.out.println("Xoa thanh cong!");
            return true;
        } else {
            System.out.println("Xoa that bai!");
            return false;
        }
    }

    @Override
    public void inDanhSach() {
        if (danhSachSanPham.isEmpty()) {
            System.out.println("Danh sach san pham hien dang rong!");
            return;
        }

        System.out.println("\n===============================================================================================================================================================================");
        System.out.println("|                                                      DANH SACH SAN PHAM                                                                                                       |");
        System.out.println("=================================================================================================================================================================================");
        System.out.printf("| %-5s | %-10s | %-30s | %-15s | %-10s | %-10s | %-8s | %-12s | %-40s |%n",
                "STT", "MA SP", "TEN SAN PHAM", "LOAI SP CHUNG", "GIA BAN", "SL TON", "MA NCC", "HSD", "THONG TIN RIENG/CHI TIET");
        System.out.println("=================================================================================================================================================================================");

        int stt = 1;
        for (SanPham sp : danhSachSanPham) {
            String loaiChung = "";
            String chiTietRieng = "";

            if (sp instanceof Thuoc) {
                Thuoc t = (Thuoc) sp;
                loaiChung = "Thuoc";
                chiTietRieng = String.format("Loai: %s | Cong Dung: %s | Toa BS: %s",
                        t.getLoaiThuoc(), t.getCongDung(), (t.getCanToaBacSi() ? "CO" : "KHONG"));
            } else if (sp instanceof ThucPhamChucNang) {
                ThucPhamChucNang tpcn = (ThucPhamChucNang) sp;
                loaiChung = "TPCN";
                chiTietRieng = String.format("Loai: %s | DC: %s | Tuoi SD: %d+",
                        tpcn.getLoaiThucPhamChucNang(), tpcn.getBoSungDuongChat(), tpcn.getTuoiSuDung());
            } else if (sp instanceof MyPham) {
                MyPham mp = (MyPham) sp;
                loaiChung = "My Pham";
                chiTietRieng = String.format("Loai: %s | Cho Da: %s",
                        mp.getLoaiMyPham(), mp.getLoaiDaPhuHop());
            } else if (sp instanceof DungCuYTe) {
                DungCuYTe dc = (DungCuYTe) sp;
                loaiChung = "DC Y Te";
                chiTietRieng = String.format("Chat Lieu: %s | Xuat Xu: %s",
                        dc.getChatLieu(), dc.getXuatXu());
            }

            System.out.printf("| %-5d | %-10s | %-30s | %-15s | %-10.0f | %-8d | %-8s | %-12s | %-40s |%n",
                    stt++,
                    sp.getMaSanPham(),
                    sp.getTenSanPham(),
                    loaiChung,
                    sp.getGiaBan(),
                    sp.getSoLuongTon(),
                    sp.getMaNhaCungCap(),
                    sp.getHSD().toString(),
                    chiTietRieng
            );
        }
        System.out.println("=================================================================================================================================================================================");
        System.out.println("Tong so san pham: " + danhSachSanPham.size());
    }


    public ArrayList<SanPham> locTheoMaNhaCungCap(String maNCC){
        return timKiemDanhSach(maNCC);
    }
    public ArrayList<SanPham> locTheoKhoangGiaBan(double giaMin, double giaMax){
        ArrayList<SanPham> ketQua = new ArrayList<>();
        if(giaMin > giaMax){
            double tam = giaMin;
            giaMin = giaMax;
            giaMax = tam;
        }
        for (SanPham sp : danhSachSanPham){
            double gia = sp.getGiaBan();
            if(gia >= giaMin && gia <= giaMax)
                ketQua.add(sp);
        }
        return ketQua;
    }
    public ArrayList<SanPham> locSanPhamConHan() {
        ArrayList<SanPham> ketQua = new ArrayList<>();
        LocalDate ngayHienTai = LocalDate.now();
        for (SanPham sp : danhSachSanPham) {
            if (sp.getHSD().isAfter(ngayHienTai)) {
                ketQua.add(sp);
            }
        }
        return ketQua;
    }
    public void sapXepTheoGiaBanTangDan() {
        int n = danhSachSanPham.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (danhSachSanPham.get(j).getGiaBan() > danhSachSanPham.get(j + 1).getGiaBan()) {
                    SanPham temp = danhSachSanPham.get(j);
                    danhSachSanPham.set(j, danhSachSanPham.get(j + 1));
                    danhSachSanPham.set(j + 1, temp);
                }
            }
        }
        System.out.println(">>> Danh sach san pham da duoc sap xep theo Gia ban (Tang dan) (Bubble Sort).");
    }
    public void sapXepTheoSLTonGiamDan() {
        int n = danhSachSanPham.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (danhSachSanPham.get(j).getSoLuongTon() < danhSachSanPham.get(j + 1).getSoLuongTon()) {
                    SanPham temp = danhSachSanPham.get(j);
                    danhSachSanPham.set(j, danhSachSanPham.get(j + 1));
                    danhSachSanPham.set(j + 1, temp);
                }
            }
        }
        System.out.println(">>> Danh sach san pham da duoc sap xep theo So luong ton (Giam dan) (Bubble Sort).");
    }
    public SanPham timSanPhamGiaCaoNhat() {
        if (danhSachSanPham.isEmpty()) {
            return null;
        }
        SanPham sanPhamMax = danhSachSanPham.get(0);
        double giaMax = sanPhamMax.getGiaBan();
        for (int i = 1; i < danhSachSanPham.size(); i++) {
            SanPham currentSP = danhSachSanPham.get(i);
            if (currentSP.getGiaBan() > giaMax) {
                giaMax = currentSP.getGiaBan();
                sanPhamMax = currentSP;
            }
        }
        return sanPhamMax;
    }
    private void hienThiKetQuaLoc(String tieuDe, List<SanPham> danhSach) {
        if (danhSach.isEmpty()) {
            System.out.println(">>> Khong tim thay " + tieuDe);
        } else {
            System.out.println("\n>>> Ket qua " + tieuDe + " (" + danhSach.size() + " san pham):");
            DanhSachSanPham dsTam = new DanhSachSanPham();
            dsTam.danhSachSanPham.addAll(danhSach);
            dsTam.inDanhSach();
        }
    }

    public void menuTienIchSanPham() {
        Scanner sc = new Scanner(System.in);
        int chonTienIch;
        do {
            System.out.println("\n========== MENU TIEN ICH SAN PHAM ==========");
            System.out.println("1. Loc san pham theo Ma Nha Cung Cap");
            System.out.println("2. Loc san pham theo Khoang Gia Ban");
            System.out.println("3. Sap xep theo Gia ban (Tang dan) va in danh sach");
            System.out.println("4. Sap xep theo So luong ton (Giam dan) va in danh sach");
            System.out.println("5. Loc san pham CON HAN su dung");
            System.out.println("6. Tim san pham co Gia ban CAO NHAT");
            System.out.println("0. Quay lai");
            System.out.print("Chon chuc nang: ");

            try {
                chonTienIch = Integer.parseInt(sc.nextLine());

                switch (chonTienIch) {
                    case 1: {
                        System.out.print("Nhap Ma Nha Cung Cap can loc: ");
                        String maNCC = sc.nextLine();
                        hienThiKetQuaLoc("san pham tu NCC " + maNCC, locTheoMaNhaCungCap(maNCC));
                        break;
                    }
                    case 2: {
                        System.out.print("Nhap Gia ban Thap nhat: ");
                        double min = Double.parseDouble(sc.nextLine());
                        System.out.print("Nhap Gia ban Cao nhat: ");
                        double max = Double.parseDouble(sc.nextLine());
                        hienThiKetQuaLoc("san pham co gia tu " + min + " den " + max, locTheoKhoangGiaBan(min, max));
                        break;
                    }
                    case 3:
                        sapXepTheoGiaBanTangDan();
                        inDanhSach();
                        break;
                    case 4:
                        sapXepTheoSLTonGiamDan();
                        inDanhSach();
                        break;
                    case 5:
                        hienThiKetQuaLoc("san pham con han su dung", locSanPhamConHan());
                        break;
                    case 6: {
                        SanPham spMax = timSanPhamGiaCaoNhat();
                        if (spMax != null) {
                            System.out.println("\n>>> San pham co Gia ban CAO NHAT:");
                            System.out.println(spMax.layThongTinDayDu());
                        } else {
                            System.out.println("Danh sach san pham rong.");
                        }
                        break;
                    }
                    case 0:
                        System.out.println("Quay lai menu truoc...");
                        break;
                    default:
                        System.out.println("Lua chonTienIch khong hop le! Vui long nhap lai.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Loi nhap lieu: Vui long nhap mot so.");
                chonTienIch = -1;
            }
        } while (chonTienIch != 0);
    }

}
