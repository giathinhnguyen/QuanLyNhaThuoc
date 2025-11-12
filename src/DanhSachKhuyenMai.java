import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class DanhSachKhuyenMai implements ChucNang<KhuyenMai>, Doc_Ghi {

    private ArrayList<KhuyenMai> dsKhuyenMai = new ArrayList<>();

    public DanhSachKhuyenMai() {
        dsKhuyenMai = new ArrayList<>();
    }

    public ArrayList<KhuyenMai> getDsKhuyenMai() {
        return dsKhuyenMai;
    }

    public void setDsKhuyenMai(ArrayList<KhuyenMai> dsKhuyenMai) {
        this.dsKhuyenMai = dsKhuyenMai;
    }
    // ================= CHUC NANG CO BAN =================

    // tim theo ma, % giam, ngay bat dau, ngay ket thuc
    @Override
    public ArrayList<KhuyenMai> timKiemDanhSach(String tuKhoa){
        ArrayList<KhuyenMai> ketQua = new ArrayList<>();
        if(tuKhoa == null || tuKhoa.trim().isEmpty()){
            return ketQua;
        }
        tuKhoa = tuKhoa.trim().toLowerCase();
        for(KhuyenMai km : dsKhuyenMai){
            if(km.getMaKhuyenMai().toLowerCase().contains(tuKhoa) ||
                    String.valueOf(km.getPhanTramGiam()).toLowerCase().contains(tuKhoa) ||
                    km.getNgayBatDau().toString().toLowerCase().contains(tuKhoa) ||
                    km.getNgayKetThuc().toString().toLowerCase().contains(tuKhoa) ){
                ketQua.add(km);
            }
        }
        return ketQua;
    }
    @Override
    public KhuyenMai timKiemDoiTuong(String tuKhoa){
        ArrayList<KhuyenMai> ketQua = timKiemDanhSach(tuKhoa);
        if(ketQua.isEmpty()){
            return null;
        }
        if(ketQua.size() > 1){
            System.out.println("Canh bao: Tim thay " + ketQua.size() + " khuyen mai khop tu khoa!");
            System.out.println("Tra ve khuyen mai dau tien:");
            System.out.println(ketQua.get(0).layThongTinDayDu());
        }
        return ketQua.get(0);
    }
    @Override
    public void timKiem(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap ma/ten doi tuong can tim kiem: ");
        String tuKhoa = sc.nextLine();
        KhuyenMai ketQua = timKiemDoiTuong(tuKhoa);
        System.out.println("---------------Ket qua tim kiem---------------");
        System.out.println(ketQua.layThongTinDayDu());
    }
    @Override
    public boolean tonTaiDoiTuong(String maKM){
        if(timKiemDoiTuong(maKM) == null)
            return false;
        return true;
    }
    @Override
    public boolean them(KhuyenMai km){
        if(km == null)
            return false;
        dsKhuyenMai.add(km);
        return true;
    }
    @Override
    public boolean xoa(String ma) {
        KhuyenMai km = timKiemDoiTuong(ma);
        if(km == null){
            System.out.println("Khong tim thay khuyen mai!");
            return false;
        }
        if(dsKhuyenMai.remove(km)){
            System.out.println("Xoa thanh cong!");
            return true;
        } else {
            System.out.println("Xoa that bai");
            return false;
        }
    }
    @Override
    public boolean sua(String ma,KhuyenMai kmMoi){
        for (int i = 0; i < dsKhuyenMai.size(); i++) {
            KhuyenMai km = dsKhuyenMai.get(i);
            if (km.getMaKhuyenMai().equalsIgnoreCase(ma)) {
                dsKhuyenMai.set(i, kmMoi);
                return true;
            }
        }
        return false;
    }


    // ================= GHI / DOC FILE =================
    @Override
    public void ghiFile(String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (KhuyenMai km : dsKhuyenMai) {
                bw.write(km.chuyenSangDinhDangTXT());
                bw.newLine();
            }
            System.out.println("Ghi file thanh cong: " + fileName);
        } catch (IOException e) {
            System.err.println("Loi ghi file: " + e.getMessage());
        }
    }

    @Override
    public void docFile(String fileName) {
        dsKhuyenMai.clear();
        int lineNo = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                lineNo++;
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(";",-1);
                if (p.length != 6) {
                    System.err.println("Dong " + lineNo + " khong hop le: " + line);
                    continue;
                }
                try {
                    KhuyenMai km = new KhuyenMai(
                            p[0].trim(),
                            p[1].trim(),
                            LocalDate.parse(p[2].trim()),
                            LocalDate.parse(p[3].trim()),
                            Double.parseDouble(p[4].trim()),
                            Double.parseDouble(p[5].trim())
                    );
                    dsKhuyenMai.add(km);
                } catch (DateTimeParseException | NumberFormatException ex) {
                    System.err.println("Loi doc dong " + lineNo + ": " + ex.getMessage());
                }
            }
            System.out.println("Doc file thanh cong: " + fileName);
        } catch (FileNotFoundException e) {
            System.err.println("Khong tim thay file: " + fileName);
        } catch (IOException e) {
            System.err.println("Loi doc file: " + e.getMessage());
        }
    }

    // ================= HIEN THI DANH SACH =================
    @Override
    public void inDanhSach() {
        if (dsKhuyenMai.isEmpty()) {
            System.out.println("Danh sach khuyen mai rong!");
            return;
        }
        System.out.println("\n====================================================== DANH SACH KHUYEN MAI ======================================================");
        System.out.printf("%-5s %-10s %-40s %-12s %-12s %-10s %-15s %-15s%n",
                "STT", "Ma KM", "Ten chuong trinh", "Ngay BD", "Ngay KT", "Giam (%)", "Dieu kien", "Trang thai");
        System.out.println("=========================================================================================================================================");

        int stt = 1;
        for (KhuyenMai km : dsKhuyenMai) {
            String thongTinKhuyenMai = km.layThongTinDayDu();
            System.out.printf("%-5d %s%n",
                    stt++,
                    thongTinKhuyenMai
            );
        }
        System.out.println("=========================================================================================================================================");
        System.out.println("Tong so chuong trinh khuyen mai: " + dsKhuyenMai.size());
    }
    //==================== HAM TIEN ICH BO SUNG ====================

    public int demKhuyenMaiConHan() {
        LocalDate now = LocalDate.now();
        int dem = 0;
        for (KhuyenMai km : dsKhuyenMai)
            if (!now.isAfter(km.getNgayKetThuc())) dem++;
        return dem;
    }

    public int demKhuyenMaiHetHan() {
        LocalDate now = LocalDate.now();
        int dem = 0;
        for (KhuyenMai km : dsKhuyenMai)
            if (now.isAfter(km.getNgayKetThuc())) dem++;
        return dem;
    }

    public List<KhuyenMai> locKhuyenMaiConHan() {
        LocalDate now = LocalDate.now();
        List<KhuyenMai> kq = new ArrayList<>();
        for (KhuyenMai km : dsKhuyenMai)
            if (!now.isAfter(km.getNgayKetThuc())) kq.add(km);
        return kq;
    }

    public List<KhuyenMai> locTheoPhanTramGiam(double minGiam) {
        List<KhuyenMai> kq = new ArrayList<>();
        for (KhuyenMai km : dsKhuyenMai)
            if (km.getPhanTramGiam() >= minGiam) kq.add(km);
        return kq;
    }

    public void sapXepTheoPhanTramGiam() {
        dsKhuyenMai.sort((k1, k2) -> Double.compare(k2.getPhanTramGiam(), k1.getPhanTramGiam()));
    }

    public void sapXepTheoNgayHetHan() {
        dsKhuyenMai.sort(Comparator.comparing(KhuyenMai::getNgayKetThuc));
    }

    private void hienThiKetQuaLoc(String tieuDe, List<KhuyenMai> danhSach) {
        if (danhSach.isEmpty()) {
            System.out.println(">>> Khong tim thay " + tieuDe);
        } else {
            System.out.println("\n>>> Ket qua " + tieuDe + " (" + danhSach.size() + " chuong trinh):");
            DanhSachKhuyenMai dsTam = new DanhSachKhuyenMai();
            dsTam.dsKhuyenMai.addAll(danhSach);
            dsTam.inDanhSach();
        }
    }

    public void menuTienIchKhuyenMai() {
        Scanner sc = new Scanner(System.in);
        int chonTienIch;
        do {
            System.out.println("\n========== MENU TIEN ICH KHUYEN MAI ==========");
            System.out.println("1. Loc khuyen mai CON HAN");
            System.out.println("2. Loc khuyen mai theo Phan Tram Giam toi thieu");
            System.out.println("3. Sap xep theo Phan Tram Giam (Giam dan) va in danh sach");
            System.out.println("4. Sap xep theo Ngay Het Han (Tang dan) va in danh sach");
            System.out.println("5. Thong ke so luong khuyen mai Con Han / Het Han");
            System.out.println("0. Quay lai");
            System.out.print("Chon: ");
            try {
                chonTienIch = Integer.parseInt(sc.nextLine());
                switch (chonTienIch) {
                    case 1:
                        hienThiKetQuaLoc("Khuyen mai CON HAN", locKhuyenMaiConHan());
                        break;
                    case 2:
                        System.out.print("Nhap phan tram giam toi thieu (%): ");
                        double minGiam = Double.parseDouble(sc.nextLine());
                        hienThiKetQuaLoc("Khuyen mai giam tu " + minGiam + "% tro len",
                                locTheoPhanTramGiam(minGiam));
                        break;
                    case 3:
                        sapXepTheoPhanTramGiam();
                        System.out.println(">>> Danh sach da duoc sap xep theo Phan Tram Giam (Giam dan).");
                        inDanhSach();
                        break;
                    case 4:
                        sapXepTheoNgayHetHan();
                        System.out.println(">>> Danh sach da duoc sap xep theo Ngay Het Han (Tang dan).");
                        inDanhSach();
                        break;
                    case 5:
                        System.out.println(">>> THONG KE TRANG THAI KHUYEN MAI:");
                        System.out.println(" - Dang con han: " + demKhuyenMaiConHan());
                        System.out.println(" - Da het han: " + demKhuyenMaiHetHan());
                        break;
                    case 0:
                        System.out.println("Quay lai menu truoc...");
                        break;
                    default:
                        System.out.println("Lua chonTienIch khong hop le! Vui long nhap lai.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Loi nhap lieu: Vui long nhap so nguyen hoac so thuc.");
                chonTienIch = -1;
            }
        } while (chonTienIch != 0);
    }
}