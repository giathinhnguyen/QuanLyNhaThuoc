import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.List;

public class DanhSachNhaCungCap implements ChucNang<NhaCungCap>, Doc_Ghi {

    private ArrayList<NhaCungCap> dsNhaCungCap = new ArrayList<>();

    public ArrayList<NhaCungCap> getDsNhaCungCap() {
        return dsNhaCungCap;
    }
    public void setDsNhaCungCap(ArrayList<NhaCungCap> dsNhaCungCap) {
        this.dsNhaCungCap = dsNhaCungCap;
    }

    @Override
    public ArrayList<NhaCungCap> timKiemDanhSach(String tuKhoa){
        ArrayList<NhaCungCap> ketQua = new ArrayList<>();
        if(tuKhoa == null || tuKhoa.trim().isEmpty()){
            return ketQua;
        }
        tuKhoa = tuKhoa.trim().toLowerCase();
        for(NhaCungCap ncc : dsNhaCungCap){
            if(ncc.getMaNhaCungCap().toLowerCase().contains(tuKhoa) ||
                    ncc.getTenNhaCungCap().toLowerCase().contains(tuKhoa)){
                ketQua.add(ncc);
            }
        }
        return ketQua;
    }
    @Override
    public NhaCungCap timKiemDoiTuong(String tuKhoa){
        ArrayList<NhaCungCap> ketQua = timKiemDanhSach(tuKhoa);
        if(ketQua.isEmpty()){
            return null;
        }
        return ketQua.get(0);
    }
    @Override
    public void timKiem(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap ma/ten doi tuong can tim kiem: ");
        String tuKhoa = sc.nextLine();
        NhaCungCap ketQua = timKiemDoiTuong(tuKhoa);
        System.out.println("---------------Ket qua tim kiem---------------");
        System.out.println(ketQua.layThongTinDayDu());
    }
    @Override
    public boolean tonTaiDoiTuong(String maNCC){
        if(timKiemDoiTuong(maNCC) == null)
            return false;
        return true;
    }

    @Override
    public boolean them(NhaCungCap ncc) {
        if(ncc == null)
            return false;
        dsNhaCungCap.add(ncc);
        return true;
    }
    @Override
    public boolean xoa(String ma) {
        NhaCungCap ncc = timKiemDoiTuong(ma);
        if(ncc == null){
            System.out.println("Khong tim thay nha cung cap!");
            return false;
        }
        if (dsNhaCungCap.remove(ncc)){
            System.out.println("Xoa thanh cong!");
            return true;
        } else {
            System.out.println("Xoa that bai!");
            return false;
        }
    }
    @Override
    public boolean sua(String ma, NhaCungCap nccMoi) {
        for (int i = 0; i < dsNhaCungCap.size(); i++) {
            NhaCungCap ncc = dsNhaCungCap.get(i);
            if (ncc.getMaNhaCungCap().equalsIgnoreCase(ma)) {
                dsNhaCungCap.set(i, nccMoi);
                return true;
            }
        }
        return false;
    }


    @Override
    public void inDanhSach() {
        if (dsNhaCungCap.isEmpty()) {
            System.out.println("Danh sach nha cung cap rong!");
            return;
        }
        System.out.println("\n============================================ DANH SACH NHA CUNG CAP ========================================================================================");
        System.out.printf("%-5s %-10s %-30s %-15s %-40s %-50s%n",
                "STT", "Ma NCC", "Ten nha cung cap", "SDT", "Dia chi", "DS Ma SP Cung Cap");
        System.out.println("===========================================================================================================================================================");

        int stt = 1;
        for (NhaCungCap ncc : dsNhaCungCap) {
            System.out.printf("%-5d %s%n", stt++, ncc.layThongTinDayDu());
        }
        System.out.println("===========================================================================================================================================================");
        System.out.println("Tong so nha cung cap: " + dsNhaCungCap.size());
    }

    @Override
    public void ghiFile(String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (NhaCungCap ncc : dsNhaCungCap) {
                bw.write(ncc.chuyenSangDinhDangTXT());
                bw.newLine();
            }
            System.out.println("Ghi file thanh cong!");
        } catch (Exception e) {
            System.out.println("Loi ghi file!");
            e.printStackTrace();
        }
    }


    @Override
    public void docFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            dsNhaCungCap.clear();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";", -1);
                if (parts.length < 8) {
                    System.err.println("Loi dinh dang: Dong khong du 8 truong. Bo qua: " + line);
                    continue;
                }
                String maNCC = parts[0];
                String tenNCC = parts[1];
                String SDT = parts[2];

                DiaChi diaChi = new DiaChi(parts[6], parts[5], parts[4], parts[3]);
                ArrayList<String> dsMaSP = new ArrayList<>();
                String dsMaSPString = parts[7];

                if (!dsMaSPString.isEmpty()) {
                    String[] maSPs = dsMaSPString.split(",");
                    for (String ma : maSPs) {
                        dsMaSP.add(ma.trim());
                    }
                }
                NhaCungCap ncc = new NhaCungCap(maNCC, tenNCC, diaChi, SDT);
                ncc.setDsSanPhamCungCap(dsMaSP);

                dsNhaCungCap.add(ncc);
            }
            System.out.println("Da doc danh sach nha cung cap tu file " + fileName + ". Tong so NCC: " + dsNhaCungCap.size());
        } catch (IOException e) {
            System.out.println("Loi khi doc file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Loi dinh dang du lieu trong file hoac khoi tao doi tuong: " + e.getMessage());
        }
    }


    // ================= HAM TIEN ICH BO SUNG =================
    private void hienThiKetQuaLoc(String tieuDe, List<NhaCungCap> danhSach) {
        if (danhSach.isEmpty()) {
            System.out.println(">>> Khong tim thay " + tieuDe);
        } else {
            System.out.println("\n>>> Ket qua " + tieuDe + " (" + danhSach.size() + " nha cung cap):");
            DanhSachNhaCungCap dsTam = new DanhSachNhaCungCap();
            dsTam.dsNhaCungCap.addAll(danhSach);
            dsTam.inDanhSach();
        }
    }public List<NhaCungCap> locTheoTinh(String tenTinh) {
        List<NhaCungCap> kq = new ArrayList<>();
        tenTinh = tenTinh.trim().toLowerCase();
        for (NhaCungCap ncc : dsNhaCungCap) {
            if (ncc.getDiaChi() != null && ncc.getDiaChi().getTinh().toLowerCase().contains(tenTinh)) {
                kq.add(ncc);
            }
        }
        return kq;
    }
    public List<NhaCungCap> locTheoSDTBatDau(String dauSo) {
        List<NhaCungCap> kq = new ArrayList<>();
        dauSo = dauSo.trim();
        for (NhaCungCap ncc : dsNhaCungCap) {
            if (ncc.getSDT() != null && ncc.getSDT().startsWith(dauSo)) {
                kq.add(ncc);
            }
        }
        return kq;
    }
    public void sapXepTheoTenTangDan() {
        // Su dung Comparator de sap xep theo ten
        dsNhaCungCap.sort(Comparator.comparing(NhaCungCap::getTenNhaCungCap));
        System.out.println(">>> Danh sach nha cung cap da duoc sap xep theo Ten (Tang dan).");
    }

    // ================= MENU TIEN ICH =================
    public void menuTienIchNhaCungCap() {
        Scanner sc = new Scanner(System.in);
        int chonTienIch;
        do {
            System.out.println("\n========== MENU TIEN ICH NHA CUNG CAP ==========");
            System.out.println("1. Loc nha cung cap theo Tinh/Thanh pho");
            System.out.println("2. Loc nha cung cap theo Dau so dien thoai");
            System.out.println("3. Sap xep theo Ten nha cung cap (Tang dan) va in danh sach");
            System.out.println("0. Quay lai");
            System.out.print("Chon: ");

            try {
                chonTienIch = Integer.parseInt(sc.nextLine());

                switch (chonTienIch) {
                    case 1:
                        System.out.print("Nhap Ten Tinh/Thanh pho can loc: ");
                        String tinh = sc.nextLine();
                        hienThiKetQuaLoc("Nha cung cap tai " + tinh, locTheoTinh(tinh));
                        break;
                    case 2:
                        System.out.print("Nhap Dau so dien thoai (vi du: 090): ");
                        String dauSo = sc.nextLine();
                        hienThiKetQuaLoc("Nha cung cap co SDT bat dau bang " + dauSo, locTheoSDTBatDau(dauSo));
                        break;
                    case 3:
                        sapXepTheoTenTangDan();
                        inDanhSach();
                        break;
                    case 0:
                        System.out.println("Quay lai menu truoc...");
                        break;
                    default:
                        System.out.println("Lua chonTienIch khong hop le! Vui long nhap lai.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Loi nhap lieu: Vui long nhap so.");
                chonTienIch = -1;
            }
        } while (chonTienIch != 0);
    }
}


