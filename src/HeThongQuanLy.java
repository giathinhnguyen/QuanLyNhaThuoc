import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class HeThongQuanLy {
    public DanhSachSanPham dsSP;
    public DanhSachNhanVien dsNV;
    public DanhSachNhaCungCap dsNCC;
    public DanhSachKhachHang dsKH;
    public DanhSachKhuyenMai dsKM;
    public DanhSachHoaDonBan dsHDB;
    public DanhSachHoaDonNhap dsHDN;

    public HeThongQuanLy(){
        dsSP = new DanhSachSanPham();
        dsNV = new DanhSachNhanVien();
        dsKH = new DanhSachKhachHang();
        dsNCC = new DanhSachNhaCungCap();
        dsKM = new DanhSachKhuyenMai();
        dsHDB = new DanhSachHoaDonBan();
        dsHDN = new DanhSachHoaDonNhap();
    }

    // ================= DANH SACH SAN PHAM =================
    public void themSanPhamTuBanPhim(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap loai: ");
        String loai = sc.nextLine();
        System.out.print("Nhap ma SP: ");
        String ma = sc.nextLine().trim().toUpperCase();
        while (dsSP.tonTaiDoiTuong(ma)) {
            System.out.print("Ma san pham da ton tai! Vui long nhap lai: ");
            ma = sc.nextLine().trim().toUpperCase();
        }
        System.out.print("Nhap ten SP: ");
        String ten = sc.nextLine();
        System.out.print("Nhap han su dung (yyyy-MM-dd): ");
        LocalDate hsd = LocalDate.parse(sc.nextLine());
        System.out.print("Nhap loai san pham: ");
        String loaiSanPham = sc.nextLine();
        System.out.print("Nhap ma nha cung cap: ");
        String maNCC = sc.nextLine().trim().toUpperCase();
        while (!dsNCC.tonTaiDoiTuong(maNCC)){
            System.out.print("Ma nha cung cap phai ton tai! Vui long nhap lai: ");
            ma = sc.nextLine().trim().toUpperCase();
        }
        System.out.print("Nhap gia nhap: ");
        double giaNhap = Double.parseDouble(sc.nextLine());
        while (giaNhap < 0 ){
            System.out.print("Gia nhap khong duoc am! Vui long nhap lai: ");
            giaNhap = Double.parseDouble(sc.nextLine());
        }
        System.out.print("Nhap gia ban: ");
        double giaBan = Double.parseDouble(sc.nextLine());
        while (giaBan < 0 ){
            System.out.print("Gia ban khong duoc am! Vui long nhap lai: ");
            giaBan = Double.parseDouble(sc.nextLine());
        }
        int soLuong = 0;


        SanPham sp = null;
        switch (loai.trim().toLowerCase()) {
            case "thuoc":
                System.out.print("Nhap loai thuoc: ");
                String loaiThuoc = sc.nextLine();
                System.out.print("Nhap cong dung: ");
                String congDung = sc.nextLine();
                System.out.print("Ke toa (1-Co || 2-Khong): ");
                int ketoa = Integer.parseInt(sc.nextLine());;
                if(ketoa != 1 && ketoa != 2){
                    System.out.print("Ke toa chi nhan gia tri (1-Co || 2-Khong) vui long nhap lai: ");
                    ketoa = sc.nextInt();
                }
                sp = new Thuoc(ma, ten, loaiSanPham, maNCC, giaNhap, giaBan, soLuong, hsd, loaiThuoc, congDung , (ketoa==1)?true:false);
                break;
            case "mypham":
                System.out.print("Nhap loai my pham: ");
                String loaiMyPham = sc.nextLine();
                System.out.print("Nhap loai da phu hop: ");
                String loaiDaPhuHop = sc.nextLine();
                sp = new MyPham(ma, ten, loaiSanPham, maNCC, giaNhap, giaBan, soLuong, hsd, loaiMyPham, loaiDaPhuHop);
                break;
            case "tpcn":
                System.out.print("Nhap loai thuc pham chuc nang: ");
                String loaiTPCN = sc.nextLine();
                System.out.print("Bo sung duong chat: ");
                String bsdc = sc.nextLine();
                System.out.print("Do tuoi su dung: ");
                int tuoiSD = Integer.parseInt(sc.nextLine());;
                while (tuoiSD<0){
                    System.out.print("Tuoi lon hon 0 moi nhap lai: ");
                    tuoiSD = Integer.parseInt(sc.nextLine());;
                }
                sp = new ThucPhamChucNang(ma, ten, loaiSanPham, maNCC, giaNhap, giaBan, soLuong, hsd, loaiTPCN, bsdc, tuoiSD);
                break;
            case "dcyt":
                System.out.print("Nhap chat lieu: ");
                String chatLieu = sc.nextLine();
                System.out.print("Nhap xuat xu: ");
                String xuatXu = sc.nextLine();
                sp = new DungCuYTe(ma, ten, loaiSanPham, maNCC, giaNhap, giaBan, soLuong, hsd, chatLieu, xuatXu);
                break;
        }
        if (sp != null) {
            dsSP.them(sp);
            System.out.println("Them san pham thanh cong!");
        } else {
            System.out.println("Loai san pham khong hop le!");
        }
    }
    public void suaSanPhamTuBanPhim(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap ma/ten/loai san pham can sua: ");
        String tuKhoa = sc.nextLine();

        SanPham finalSp = dsSP.timKiemDoiTuong(tuKhoa);
        if(finalSp == null){
            System.out.println("Khong tim thay san pham voi ma!");
            return;
        }
        //Lambda Expression = "Ham an danh" (anonymous function)
        //Bien duoc dung trong lambda phai la final
        System.out.println("San pham tim thay:");
        System.out.println(finalSp.layThongTinDayDu());

        ArrayList<String> thuocTinhChung = new ArrayList<>();
        thuocTinhChung.add("Ten san pham");
        thuocTinhChung.add("Gia nhap");
        thuocTinhChung.add("Gia ban");
        thuocTinhChung.add("So luong ton");
        thuocTinhChung.add("Han su dung (yyyy-MM-dd)");
        thuocTinhChung.add("Loai san pham");
        thuocTinhChung.add("Ma nha cung cap");
        //Runnable la interface co 1 phuong thuc run(),
        // khong nhan tham so, khong tra ve
        ArrayList<Runnable> phuongThucChung = new ArrayList<>();
        phuongThucChung.add(() -> {
            System.out.print("Nhap ten moi: ");
            finalSp.setTenSanPham(sc.nextLine());
        });
        phuongThucChung.add(() -> {
            System.out.print("Nhap gia nhap moi: ");
            double giaNhap = Double.parseDouble(sc.nextLine());
            while (giaNhap < 0 ){
                System.out.print("Gia khong duoc am! Vui long nhap lai: ");
                giaNhap = Double.parseDouble(sc.nextLine());
            }
            finalSp.setGiaNhap(giaNhap);
        });
        phuongThucChung.add(() -> {
            System.out.print("Nhap gia ban moi: ");
            double giaBan = Double.parseDouble(sc.nextLine());
            while (giaBan < 0 ){
                System.out.print("Gia ban khong duoc am! Vui long nhap lai: ");
                giaBan = Double.parseDouble(sc.nextLine());
            }
            finalSp.setGiaBan(giaBan);
        });
        phuongThucChung.add(() -> {
            System.out.print("Nhap so luong moi: ");
            finalSp.setSoLuongTon(Integer.parseInt(sc.nextLine()));
        });
        phuongThucChung.add(() -> {
            System.out.print("Nhap han su dung moi (yyyy-MM-dd): ");
            finalSp.setHSD(LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        });
        phuongThucChung.add(() -> {
            System.out.print("Nhap loai san pham moi: ");
            finalSp.setLoaiSanPham(sc.nextLine());
        });
        phuongThucChung.add(() -> {
            System.out.print("Nhap ma nha cung cap moi: ");
            String maNCC = sc.nextLine().trim().toUpperCase();
            while (!dsNCC.tonTaiDoiTuong(maNCC)){
                System.out.print("Ma nha cung cap khong ton tai! Vui long nhap lai: ");
                maNCC = sc.nextLine().trim().toUpperCase();
            }
            finalSp.setMaNhaCungCap(maNCC);
        });

        ArrayList<String> thuocTinhRieng  = new ArrayList<>();
        ArrayList<Runnable> phuongThucRieng = new ArrayList<>();
        if(finalSp instanceof Thuoc){
            Thuoc thuoc = (Thuoc) finalSp;
            thuocTinhRieng.add("Loai thuoc");
            thuocTinhRieng.add("Cong dung");
            thuocTinhRieng.add("Can toa bac si (true/false)");
            phuongThucRieng.add(() -> {
                System.out.print("Nhap loai thuoc moi: ");
                thuoc.setLoaiThuoc(sc.nextLine());
            });
            phuongThucRieng.add(() -> {
                System.out.print("Nhap cong dung moi: ");
                thuoc.setCongDung(sc.nextLine());
            });
            phuongThucRieng.add(() -> {
                System.out.print("Can toa bac si (true/false): ");
                thuoc.setCanToaBacSi(Boolean.parseBoolean(sc.nextLine()));
            });
        } else if (finalSp instanceof MyPham) {
            MyPham mypham = (MyPham) finalSp;
            thuocTinhRieng.add("Loai my pham");
            thuocTinhRieng.add("Loai da phu hop");
            phuongThucRieng.add(() -> {
                System.out.print("Nhap loai my pham moi: ");
                mypham.setLoaiMyPham(sc.nextLine());
            });
            phuongThucRieng.add(() -> {
                System.out.print("Nhap loai da phu hop moi: ");
                mypham.setLoaiDaPhuHop(sc.nextLine());
            });
        } else if (finalSp instanceof ThucPhamChucNang) {
            ThucPhamChucNang tpcn = (ThucPhamChucNang) finalSp;
            thuocTinhRieng.add("Loai thuc pham chuc nang");
            thuocTinhRieng.add("Bo sung duong chat");
            thuocTinhRieng.add("Do tuoi su dung");
            phuongThucRieng.add(() -> {
                System.out.print("Nhap loai TPCN moi: ");
                tpcn.setLoaiThucPhamChucNang(sc.nextLine());
            });
            phuongThucRieng.add(() -> {
                System.out.print("Nhap duong chat bo sung moi: ");
                tpcn.setBoSungDuongChat(sc.nextLine());
            });
            phuongThucRieng.add(() -> {
                System.out.print("Nhap do tuoi su dung moi: ");
                int tuoiSD = Integer.parseInt(sc.nextLine());;
                while (tuoiSD<0){
                    System.out.print("Tuoi lon hon 0 moi nhap lai: ");
                    tuoiSD = Integer.parseInt(sc.nextLine());;
                }
                tpcn.setTuoiSuDung(tuoiSD);
            });
        } else if (finalSp instanceof  DungCuYTe) {
            DungCuYTe dcyt = (DungCuYTe) finalSp;
            thuocTinhRieng.add("Chat lieu");
            thuocTinhRieng.add("Xuat xu");
            phuongThucRieng.add(() -> {
                System.out.print("Nhap chat lieu moi: ");
                dcyt.setChatLieu(sc.nextLine());
            });
            phuongThucRieng.add(() -> {
                System.out.print("Nhap xuat xu moi: ");
                dcyt.setXuatXu(sc.nextLine());
            });
        }
        // Hien thi menu
        System.out.println("\nChon thuoc tinh muon sua (nhap so, cach nhau bang dau phay hoac khoang trang):");
        for (int i = 0; i < thuocTinhChung.size(); i++) {
            System.out.println((i + 1) + ". " + thuocTinhChung.get(i));
        }
        int startRieng = thuocTinhChung.size() + 1;
        if (!thuocTinhRieng.isEmpty()) {
            for (int i = 0; i < thuocTinhRieng.size(); i++) {
                System.out.println((startRieng + i) + ". " + thuocTinhRieng.get(i));
            }
        }
        System.out.println("0. Thoat");
        System.out.print("Nhap lua chon: ");
        String input = sc.nextLine().trim();
        if (input.equals("0")) return;
        String[] choices = input.split("[,\\s]+");
        //[ ... ]: La mot tap hop cac ky tu.
        //,: Ky tu dau phay.\\s: Bat ky ky tu khoang trang nao (dau cach, tab...).
        boolean daSua = false;

        for(String choice : choices){
            try{
                int chon = Integer.parseInt(choice);
                if (chon == 0) continue;
                if(chon >= 1 && chon <= thuocTinhChung.size()){
                    System.out.println("Sua: " + thuocTinhChung.get(chon - 1));
                    phuongThucChung.get(chon - 1).run();
                    daSua = true;
                } else if (chon >= startRieng && chon <= startRieng + thuocTinhRieng.size()){
                    int idx = chon - startRieng;
                    System.out.println("Sua: " + thuocTinhRieng.get(idx));
                    phuongThucRieng.get(idx).run();
                    daSua = true;
                } else {
                    System.out.println("Lua chon khong hop le: " + chon);
                }
            } catch (Exception e) {
                System.out.println("Loi nhap: " + choice);
            }
        }
        if (daSua) {
            dsSP.sua(finalSp.getMaSanPham(),finalSp);
            System.out.println("Sua san pham thanh cong!");
        } else {
            System.out.println("Khong co thay doi nao duoc thuc hien.");
        }
    }

    public void menuSanPham(){
        Scanner sc = new Scanner(System.in);
        int chon;
        do{
            System.out.println("==========  MENU SAN PHAM ==========");
            System.out.println("1. Doc file du lieu txt");
            System.out.println("2. Ghi vao file txt");
            System.out.println("3. Them san pham");
            System.out.println("4. Sua san pham");
            System.out.println("5. Xoa san pham");
            System.out.println("6. In danh sach san pham");
            System.out.println("7. CHUC NANG TIEN ICH");
            System.out.println("8. Tim kiem 1 san pham");
            System.out.println("0. Quay lai");
            System.out.print("Chon: ");
            chon = Integer.parseInt(sc.nextLine());
            switch (chon){
                case 1 -> dsSP.docFile("DanhSachSanPham.txt");
                case 2 -> dsSP.ghiFile("DanhSachSanPham_Nhan.txt");
                case 3 -> themSanPhamTuBanPhim();
                case 4 -> suaSanPhamTuBanPhim();
                case 5 -> {
                    System.out.print("Nhap ma san pham can xoa: ");
                    String maSPXoa = sc.nextLine();
                    dsSP.xoa(maSPXoa);
                }
                case 6 -> dsSP.inDanhSach();
                case 7 -> {
                    System.out.println("\n--- Truy cap Chuc nang Tien Ich ---");
                    dsSP.menuTienIchSanPham();
                }
                case 8 -> {
                    dsSP.timKiem();
                }
                case 0 -> System.out.println("Quay lai menu chinh...");
                default -> System.out.println("Lua chon khong hop le!");
            }
        } while (chon != 0);
    }
    // ================= DANH SACH NHAN VIEN =================
    public void  themNhanVienTuBanPhim(){
        Scanner sc = new Scanner(System.in);
        System.out.println("1.Nhan vien ban hang");
        System.out.println("2.Quan ly");
        System.out.print("Moi chon loai nhan vien: ");
        int loai = Integer.parseInt(sc.nextLine());
        while (loai != 1 && loai !=2){
            System.out.print("Chi co 2 loai nhan vien! Vui long nhap lai: ");
            loai = Integer.parseInt(sc.nextLine());
        }
        System.out.print("Nhap ma nhan vien: ");
        String ma = sc.nextLine().trim().toUpperCase();
        while (dsNV.tonTaiDoiTuong(ma)) {
            System.out.print("Ma nhan vien da ton tai! Vui long nhap lai: ");
            ma = sc.nextLine().trim().toUpperCase();
        }
        System.out.print("Nhap ten nhan vien: ");
        String ten = sc.nextLine();
        System.out.print("Nhap gioi tinh: ");
        String gioiTinh = sc.nextLine();
        while (!gioiTinh.equalsIgnoreCase("Nam") && !gioiTinh.equalsIgnoreCase("Nu")) {
            System.out.print("Gioi tinh phai la nam hoac nu! Vui long nhap lai: ");
            gioiTinh = sc.nextLine();
        }
        System.out.print("Nhap ngay sinh (yyyy-MM-dd): ");
        LocalDate ngaySinh = LocalDate.parse(sc.nextLine());
        System.out.print("Nhap so dien thoai: ");
        String sdt = sc.nextLine();
        DiaChi diaChi = new DiaChi();
        diaChi.nhapDiaChi();
        System.out.print("Nhap so nam lam: ");
        int soNamLam = Integer.parseInt(sc.nextLine());
        while (soNamLam<0){
            System.out.print("So nam phai lon hon 0! Vui long nhap lai: ");
            soNamLam = Integer.parseInt(sc.nextLine());
        }
        NhanVien nv = null;
        switch (loai){
            case 1:
                System.out.print("Nhap so ngay nghi: ");
                int soNgayNghi = Integer.parseInt(sc.nextLine());
                while (soNgayNghi<0){
                    System.out.print("So ngay nghi phai lon hon 0! Vui long nhap lai: ");
                    soNgayNghi = Integer.parseInt(sc.nextLine());
                }
                nv = new NhanVienBanThuoc(ten, sdt, gioiTinh, ngaySinh, diaChi, ma, soNamLam, soNgayNghi);
                break;
            case 2:
                System.out.print("Nhap he so phu cap quan li: ");
                double phuCapQuanLy = Double.parseDouble(sc.nextLine());
                while (phuCapQuanLy<0){
                    System.out.print("Phu cap quan li phai lon hon 0! Vui long nhap lai: ");
                    phuCapQuanLy = Double.parseDouble(sc.nextLine());
                }
                nv = new QuanLy(ten, sdt, gioiTinh, ngaySinh, diaChi, ma, soNamLam, phuCapQuanLy);
                break;
        }
        if(nv != null){
            dsNV.them(nv);
            System.out.println("Them nhan vien thanh cong!");
        } else {
            System.out.println("Nhan vien khong hop le!");
        }
    }
    public void suaNhanVienTuBanPhim(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap ma/ten/SDT nhan vien can sua: ");
        String tuKhoa = sc.nextLine();
        NhanVien finalNv = dsNV.timKiemDoiTuong(tuKhoa);
        if(finalNv == null){
            System.out.println("Khong tim thay nhan vien!");
            return;
        }
        System.out.println("Nhan vien tim thay: ");
        System.out.print(finalNv.layThongTinDayDu());

        ArrayList<String> thuocTinhChung = new ArrayList<>();
        thuocTinhChung.add("Ten nhan vien");
        thuocTinhChung.add("SDT");
        thuocTinhChung.add("Gioi tinh");
        thuocTinhChung.add("Ngay sinh");
        thuocTinhChung.add("Dia chi");
        thuocTinhChung.add("So nam lam");

        ArrayList<Runnable> phuongThucChung = new ArrayList<>();
        phuongThucChung.add(() -> {
            System.out.print("Nhap ten nhan vien: ");
            String ten = sc.nextLine();
            finalNv.setHoTen(ten);
        });
        phuongThucChung.add(() -> {
            System.out.print("Nhap so dien thoai: ");
            String sdt = sc.nextLine();
            finalNv.setSDT(sdt);
        });
        phuongThucChung.add(() -> {
            System.out.print("Nhap gioi tinh: ");
            String gioiTinh = sc.nextLine();
            while (!gioiTinh.equalsIgnoreCase("Nam") && !gioiTinh.equalsIgnoreCase("Nu")) {
                System.out.print("Gioi tinh phai la nam hoac nu! Vui long nhap lai: ");
                gioiTinh = sc.nextLine();
            }
            finalNv.setGioiTinh(gioiTinh);
        });
        phuongThucChung.add(() -> {
            System.out.print("Nhap ngay sinh (yyyy-MM-dd): ");
            LocalDate ngaySinh = LocalDate.parse(sc.nextLine());
            finalNv.setNgaySinh(ngaySinh);
        });
        phuongThucChung.add(() -> {
            DiaChi diaChi = new DiaChi();
            diaChi.nhapDiaChi();
            finalNv.setDiaChi(diaChi);
        });
        phuongThucChung.add(() -> {
            System.out.print("Nhap so nam lam: ");
            int soNamLam = Integer.parseInt(sc.nextLine());
            while (soNamLam<0){
                System.out.print("So nam phai lon hon 0! Vui long nhap lai: ");
                soNamLam = Integer.parseInt(sc.nextLine());
            }
            finalNv.setSoNamLam(soNamLam);
        });

        ArrayList<String> thuocTinhRieng  = new ArrayList<>();
        ArrayList<Runnable> phuongThucRieng = new ArrayList<>();
        if(finalNv instanceof NhanVienBanThuoc){
            NhanVienBanThuoc nvbt = (NhanVienBanThuoc) finalNv;
            thuocTinhRieng.add("So ngay nghi");
            phuongThucRieng.add(() -> {
                System.out.print("Nhap so ngay nghi: ");
                int soNgayNghi = Integer.parseInt(sc.nextLine());
                while (soNgayNghi<0){
                    System.out.print("So nam phai lon hon 0! Vui long nhap lai: ");
                    soNgayNghi = Integer.parseInt(sc.nextLine());
                }
                nvbt.setSoNgayNghi(soNgayNghi);
            });
        } else if (finalNv instanceof QuanLy){
            QuanLy quanLy = (QuanLy) finalNv;
            thuocTinhRieng.add("He so phu cap quan li");
            phuongThucRieng.add(() -> {
                System.out.print("Nhap he so phu cap quan li: ");
                double phuCapQuanLy = Double.parseDouble(sc.nextLine());
                while (phuCapQuanLy<0){
                    System.out.print("Phu cap quan li phai lon hon 0! Vui long nhap lai: ");
                    phuCapQuanLy = Double.parseDouble(sc.nextLine());
                }
                quanLy.setHeSoPhuCapQuanLy(phuCapQuanLy);
            });
        }
        //HIEN MENU
        System.out.println("\nChon thuoc tinh muon sua (nhap so, cach nhau bang dau phay hoac khoang trang):");
        for (int i = 0; i < thuocTinhChung.size(); i++) {
            System.out.println((i + 1) + ". " + thuocTinhChung.get(i));
        }
        int startRieng = thuocTinhChung.size() + 1;
        if (!thuocTinhRieng.isEmpty()) {
            for (int i = 0; i < thuocTinhRieng.size(); i++) {
                System.out.println((startRieng + i) + ". " + thuocTinhRieng.get(i));
            }
        }
        System.out.println("0. Thoat");
        System.out.print("Nhap lua chon: ");
        String input = sc.nextLine().trim();
        if (input.equals("0")) return;
        String[] choices = input.split("[,\\s]+");
        boolean daSua = false;

        for(String choice : choices){
            try{
                int chon = Integer.parseInt(choice);
                if(chon == 0) continue;
                if(chon >= 1 && chon <= thuocTinhChung.size()){
                    System.out.println("Sua: " + thuocTinhChung.get(chon - 1));
                    phuongThucChung.get(chon - 1).run();
                    daSua = true;
                } else if (chon >= startRieng && chon <= startRieng + thuocTinhRieng.size()){
                    int idx = chon - startRieng;
                    System.out.println("Sua: " + thuocTinhRieng.get(idx));
                    phuongThucRieng.get(idx).run();
                    daSua = true;
                } else {
                    System.out.println("Lua chon khong hop le: " + chon);
                }
            } catch (Exception e) {
                System.out.println("Loi nhap: " + choice);
            }
        }
        if (daSua) {
            dsNV.sua(finalNv.getMaNhanVien(),finalNv);
            System.out.println("Sua nhan vien thanh cong!");
        } else {
            System.out.println("Khong co thay doi nao duoc thuc hien.");
        }
    }

    public void hienThiChiTietHoaDonNhanVien() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n===== HIEN THI CHI TIET HOA DON CUA NHAN VIEN =====");
        System.out.print("Nhap Ma/Ten/SDT cua nhan vien can xem hoa don: ");
        String tuKhoa = sc.nextLine();

        NhanVien nv = dsNV.timKiemDoiTuong(tuKhoa);
        if (nv == null) {
            System.out.println("Khong tim thay nhan vien nao khop voi tu khoa: " + tuKhoa);
            return;
        }

        String maNV = nv.getMaNhanVien();

        System.out.println("Tim thay nhan vien: " + nv.getHoTen() + " (" + maNV + ")");
        System.out.println("---------------------------------------------------");

        if (nv instanceof NhanVienBanThuoc){
            System.out.println("\n>>> HOA DON BAN HANG (Tong so: " + dsHDB.getDsHoaDonBanHang().size() + ")");
            hienThiHoaDonBan(maNV);
        }

        if (nv instanceof QuanLy) {
            System.out.println("\n>>> HOA DON NHAP (Tong so: " + dsHDN.getDsHoaDonNhap().size() + ")");
            hienThiHoaDonNhap(maNV);
        }
    }

    private void hienThiHoaDonBan(String maNV) {
        ArrayList<HoaDonBanHang> hdbTimThay = dsHDB.timKiemDanhSach(maNV);

        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-10s %-12s %-10s %-10s %-10s %-15s %-70s%n",
                "Ma HDB", "Ngay lap", "Ma NVB", "Ma KH", "Ma KM", "Tong tien", "Chi tiet san pham");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (HoaDonBanHang hdb : hdbTimThay) {
                System.out.println(hdb.layThongTinDayDu());
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Tong so HOA DON BAN lap boi " + maNV + ": " + hdbTimThay.size());
    }

    private void hienThiHoaDonNhap(String maNV) {
        ArrayList<HoaDonNhap> hdnTimThay = dsHDN.timKiemDanhSach(maNV);

        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-10s %-12s %-10s %-10s %-15s %-70s%n",
                "Ma HDN", "Ngay lap", "Ma QL", "Ma NCC", "Tong tien", "Chi tiet san pham");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------");

        for (HoaDonNhap hdn : hdnTimThay) {
                System.out.println(hdn.layThongTinDayDu());
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Tong so HOA DON NHAP lap boi " + maNV + ": " + hdnTimThay.size());
    }

    public void menuNhanVien(){
        Scanner sc = new Scanner(System.in);
        int chon;
        do{
            System.out.println("==========  MENU SAN PHAM ==========");
            System.out.println("1. Doc file du lieu txt");
            System.out.println("2. Ghi vao file txt");
            System.out.println("3. Them nhan vien");
            System.out.println("4. Sua nhan vien");
            System.out.println("5. Xoa nhan vien");
            System.out.println("6. In danh sach nhan vien");
            System.out.println("7. Hien thi chi tiet hoa don cua nhan vien");
            System.out.println("8. CHUC NANG TIEN ICH");
            System.out.println("9. Tim kiem theo ma/ten");
            System.out.println("0. Quay lai");
            System.out.print("Chon: ");
            try {
                chon = Integer.parseInt(sc.nextLine());
                switch (chon){
                    case 1 -> dsNV.docFile("DanhSachNhanVien.txt");
                    case 2 -> dsNV.ghiFile("DanhSachNhanVien_Nhan.txt");
                    case 3 -> themNhanVienTuBanPhim();
                    case 4 -> suaNhanVienTuBanPhim();
                    case 5 -> {
                        System.out.print("Nhap ma nhan vien can xoa: ");
                        String maNVXoa = sc.nextLine();
                        dsNV.xoa(maNVXoa);
                    }
                    case 6 -> dsNV.inDanhSach();
                    case 7 -> hienThiChiTietHoaDonNhanVien();
                    case 8 -> {
                        System.out.println("\n--- Truy cap Chuc nang Tien Ich ---");
                        dsNV.menuTienIchNhanVien();
                    }
                    case 9 -> dsNV.timKiem();
                    case 0 -> System.out.println("Quay lai menu chinh...");
                    default -> System.out.println("Lua chon khong hop le!");
                }
            } catch (NumberFormatException e) {
                System.err.println("Loi nhap lieu: Vui long nhap mot so nguyen.");
                chon = -1;
            }
        } while (chon != 0);
    }

    // ================= DANH SACH NHA CUNG CAP =================
    public void themNhaCungCapTuBanPhim() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhap ma nha cung cap: ");
        String maNCC = sc.nextLine().trim().toUpperCase();
        while (dsNCC.tonTaiDoiTuong(maNCC)) {
            System.out.print("Ma nha cung cap da ton tai! Vui long nhap lai: ");
            maNCC = sc.nextLine().trim().toUpperCase();
        }

        System.out.print("Nhap ten nha cung cap: ");
        String tenNCC = sc.nextLine().trim();
        while (tenNCC.isEmpty()) {
            System.out.print("Ten nha cung cap khong duoc rong! Vui long nhap lai: ");
            tenNCC = sc.nextLine().trim();
        }

        System.out.print("Nhap so dien thoai: ");
        String sdt = sc.nextLine().trim();

        DiaChi diaChi = new DiaChi();
        diaChi.nhapDiaChi();

        NhaCungCap ncc = new NhaCungCap(maNCC, tenNCC, diaChi, sdt);
        if (dsNCC.them(ncc)) {
            System.out.println("Them nha cung cap thanh cong!");
        } else {
            System.out.println("Them nha cung cap that bai!");
        }
    }

    public void suaNhaCungCapTuBanPhim() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap ma hoac SDT nha cung cap can sua: ");
        String tuKhoa = sc.nextLine();
        NhaCungCap ncc = dsNCC.timKiemDoiTuong(tuKhoa);

        if (ncc == null) {
            System.out.println("Khong tim thay nha cung cap!");
            return;
        }

        System.out.println("Nha cung cap tim thay:");
        System.out.println(ncc.layThongTinDayDu());

        ArrayList<String> thuocTinh = new ArrayList<>();
        ArrayList<Runnable> hanhDong = new ArrayList<>();

        thuocTinh.add("Ten nha cung cap");
        hanhDong.add(() -> {
            System.out.print("Nhap ten moi: ");
            String ten = sc.nextLine().trim();
            while (ten.isEmpty()) {
                System.out.print("Ten khong duoc rong! Nhap lai: ");
                ten = sc.nextLine().trim();
            }
            ncc.setTenNhaCungCap(ten);
        });

        thuocTinh.add("So dien thoai");
        hanhDong.add(() -> {
            System.out.print("Nhap SDT moi: ");
            String sdt = sc.nextLine().trim();
            ncc.setSDT(sdt);
        });

        thuocTinh.add("Dia chi");
        hanhDong.add(() -> {
            DiaChi diaChi = new DiaChi();
            diaChi.nhapDiaChi();
            ncc.setDiaChi(diaChi);
        });

        System.out.println("\nChon thuoc tinh muon sua (nhap so, cach nhau bang dau phay hoac khoang trang):");
        for (int i = 0; i < thuocTinh.size(); i++) {
            System.out.println((i + 1) + ". " + thuocTinh.get(i));
        }
        System.out.println("0. Thoat");
        System.out.print("Nhap lua chon: ");
        String input = sc.nextLine().trim();
        if (input.equals("0")) return;
        String[] choices = input.split("[,\\s]+");
        boolean daSua = false;

        for (String choice : choices) {
            try {
                int chon = Integer.parseInt(choice);
                if (chon >= 1 && chon <= thuocTinh.size()) {
                    System.out.println("Sua: " + thuocTinh.get(chon - 1));
                    hanhDong.get(chon - 1).run();
                    daSua = true;
                } else {
                    System.out.println("Lua chon khong hop le: " + chon);
                }
            } catch (Exception e) {
                System.out.println("Loi nhap: " + choice);
            }
        }

        if (daSua) {
            dsNCC.sua(ncc.getMaNhaCungCap(), ncc);
            System.out.println("Sua nha cung cap thanh cong!");
        } else {
            System.out.println("Khong co thay doi nao duoc thuc hien.");
        }
    }
    public void menuNhaCungCap() {
        Scanner sc = new Scanner(System.in);
        int chon;
        do {
            System.out.println("==========  MENU NHA CUNG CAP ==========");
            System.out.println("1. Doc file du lieu txt");
            System.out.println("2. Ghi vao file txt");
            System.out.println("3. Them nha cung cap");
            System.out.println("4. Sua nha cung cap");
            System.out.println("5. Xoa nha cung cap");
            System.out.println("6. In danh sach nha cung cap");
            System.out.println("7. CHUC NANG TIEN ICH");
            System.out.println("8. Tim kiem theo ma/ten");
            System.out.println("0. Quay lai");
            System.out.print("Chon: ");
            try {
                chon = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                chon = -1;
            }

            switch (chon) {
                case 1 -> dsNCC.docFile("DanhSachNhaCungCap.txt");
                case 2 -> dsNCC.ghiFile("DanhSachNhaCungCap_Nhap.txt");
                case 3 -> themNhaCungCapTuBanPhim();
                case 4 -> suaNhaCungCapTuBanPhim();
                case 5 -> {
                    System.out.print("Nhap ma nha cung cap can xoa: ");
                    String ma = sc.nextLine().trim();
                    dsNCC.xoa(ma);
                }
                case 6 -> dsNCC.inDanhSach();
                case 7 -> {
                    System.out.println("\n--- Truy cap Chuc nang Tien Ich ---");
                    dsNCC.menuTienIchNhaCungCap();
                }
                case 8 -> dsNCC.timKiem();
                case 0 -> System.out.println("Quay lai menu chinh...");
                default -> System.out.println("Lua chon khong hop le!");
            }
        } while (chon != 0);
    }
    // ================= DANH SACH KHACH HANG =================

    public void themKhachHangTuBanPhim() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n===== THEM KHACH HANG MOI =====");
        System.out.print("Nhap ma khach hang: ");
        String ma = sc.nextLine().trim().toUpperCase();
        while (dsKH.tonTaiDoiTuong(ma)) {
            System.out.print("Ma khach hang da ton tai! Vui long nhap lai: ");
            ma = sc.nextLine().trim().toUpperCase();
        }
        System.out.print("Nhap ho ten khach hang: ");
        String hoTen = sc.nextLine();
        System.out.print("Nhap gioi tinh (Nam/Nu): ");
        String gioiTinh = sc.nextLine();
        while (!gioiTinh.equalsIgnoreCase("Nam") && !gioiTinh.equalsIgnoreCase("Nu")) {
            System.out.print("Chi chap nhan 'Nam' hoac 'Nu'. Nhap lai: ");
            gioiTinh = sc.nextLine();
        }
        System.out.print("Nhap ngay sinh (yyyy-MM-dd): ");
        LocalDate ngaySinh = LocalDate.parse(sc.nextLine());
        System.out.print("Nhap so dien thoai: ");
        String sdt = sc.nextLine();
        DiaChi diaChi = new DiaChi();
        diaChi.nhapDiaChi();
        KhachHang kh = new KhachHang(hoTen, sdt, gioiTinh, ngaySinh, diaChi, ma);
        dsKH.them(kh);
        System.out.println("Them khach hang thanh cong!");
    }

    public void suaKhachHangTuBanPhim() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nNhap Ma/Ten/SDT khach hang can sua: ");
        String tuKhoa = sc.nextLine();
        KhachHang kh = dsKH.timKiemDoiTuong(tuKhoa);
        if (kh == null) {
            System.out.println("Khong tim thay khach hang!");
            return;
        }
        System.out.println("Khach hang tim thay:");
        System.out.println(kh.layThongTinDayDu());
        System.out.println("\nChon thuoc tinh muon sua:");
        System.out.println("1. Ho ten");
        System.out.println("2. Gioi tinh");
        System.out.println("3. Ngay sinh");
        System.out.println("4. So dien thoai");
        System.out.println("5. Dia chi");
        System.out.println("6. Diem tich luy");
        System.out.println("0. Thoat");
        System.out.print("Nhap lua chon: ");

        int chon = Integer.parseInt(sc.nextLine());
        switch (chon) {
            case 1 -> {
                System.out.print("Nhap ho ten moi: ");
                kh.setHoTen(sc.nextLine());
            }
            case 2 -> {
                System.out.print("Nhap gioi tinh (Nam/Nu): ");
                String gt = sc.nextLine();
                while (!gt.equalsIgnoreCase("Nam") && !gt.equalsIgnoreCase("Nu")) {
                    System.out.print("Chi chap nhan 'Nam' hoac 'Nu'. Nhap lai: ");
                    gt = sc.nextLine();
                }
                kh.setGioiTinh(gt);
            }
            case 3 -> {
                System.out.print("Nhap ngay sinh (yyyy-MM-dd): ");
                LocalDate ns = LocalDate.parse(sc.nextLine());
                kh.setNgaySinh(ns);
            }
            case 4 -> {
                System.out.print("Nhap so dien thoai moi: ");
                kh.setSDT(sc.nextLine());
            }
            case 5 -> {
                DiaChi dc = new DiaChi();
                dc.nhapDiaChi();
                kh.setDiaChi(dc);
            }
            case 6 -> {
                System.out.print("Nhap diem tich luy moi: ");
                double diem = Double.parseDouble(sc.nextLine());
                kh.setDiemTichLuy(Math.max(0, diem));
            }
            case 0 -> {
                System.out.println("Huy sua thong tin khach hang.");
                return;
            }
            default -> System.out.println("Lua chon khong hop le!");
        }
        dsKH.sua(kh.getMaKhachHang(), kh);
        System.out.println("Sua thong tin thanh cong!");
    }
    public void hienThiChiTietHoaDonKhachHang() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n===== HIEN THI HOA DON CUA KHACH HANG =====");
        System.out.print("Nhap ma/ten/SDT khach hang: ");
        String tuKhoa = sc.nextLine();

        KhachHang kh = dsKH.timKiemDoiTuong(tuKhoa);
        if (kh == null) {
            System.out.println("Khong tim thay khach hang!");
            return;
        }
        System.out.println("Khach hang: " + kh.getHoTen() + " (" + kh.getMaKhachHang() + ")");
        ArrayList<HoaDonBanHang> dsHoaDon = dsHDB.timKiemDanhSach(kh.getMaKhachHang());

        if (dsHoaDon.isEmpty()) {
            System.out.println("Khach hang chua co hoa don nao!");
            return;
        }
        System.out.println("---------------------------------------------------------------------------------------------------------");
        System.out.printf("%-10s %-12s %-10s %-10s %-15s %-70s%n",
                "Ma HDB", "Ngay lap", "Ma NVB", "Ma KM", "Tong tien", "Chi tiet san pham");
        System.out.println("---------------------------------------------------------------------------------------------------------");
        for (HoaDonBanHang hdb : dsHoaDon) {
            System.out.println(hdb.layThongTinDayDu());
        }
        System.out.println("---------------------------------------------------------------------------------------------------------");
        System.out.println("Tong so hoa don: " + dsHoaDon.size());
    }
    public void menuKhachHang() {
        Scanner sc = new Scanner(System.in);
        int chon;
        do {
            System.out.println("\n==========  MENU KHACH HANG  ==========");
            System.out.println("1. Doc du lieu tu file txt");
            System.out.println("2. Ghi du lieu vao file txt");
            System.out.println("3. Them khach hang");
            System.out.println("4. Sua khach hang");
            System.out.println("5. Xoa khach hang");
            System.out.println("6. In danh sach khach hang");
            System.out.println("7. Hien thi lich su hoa don cua khach hang");
            System.out.println("8. CHUC NANG TIEN ICH");
            System.out.println("9. Tim kiem theo ma/ten");
            System.out.println("0. Quay lai menu chinh");
            System.out.print("Chon: ");

            try {
                chon = Integer.parseInt(sc.nextLine());
                switch (chon) {
                    case 1 -> dsKH.docFile("DanhSachKhachHang.txt");
                    case 2 -> dsKH.ghiFile("DanhSachKhachHang_Nhan.txt");
                    case 3 -> themKhachHangTuBanPhim();
                    case 4 -> suaKhachHangTuBanPhim();
                    case 5 -> {
                        System.out.print("Nhap ma khach hang can xoa: ");
                        String ma = sc.nextLine();
                        dsKH.xoa(ma);
                    }
                    case 6 -> dsKH.inDanhSach();
                    case 7 -> hienThiChiTietHoaDonKhachHang();
                    case 8 -> dsKH.menuTienIchKhachHang();
                    case 9 -> dsKH.timKiem();
                    case 0 -> System.out.println("Quay lai menu chinh...");
                    default -> System.out.println("Lua chon khong hop le!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Loi nhap lieu! Vui long nhap so nguyen.");
                chon = -1;
            }
        } while (chon != 0);
    }
    // ================= DANH SACH KHUYEN MAI  =================
    public void themKhuyenMaiTuBanPhim() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n===== THEM KHUYEN MAI MOI =====");

        System.out.print("Nhap ma khuyen mai: ");
        String maKM = sc.nextLine().trim().toUpperCase();
        while (dsKM.tonTaiDoiTuong(maKM)) {
            System.out.print("Ma khuyen mai da ton tai! Vui long nhap lai: ");
            maKM = sc.nextLine().trim().toUpperCase();
        }

        System.out.print("Nhap ten chuong trinh khuyen mai: ");
        String tenKM = sc.nextLine();

        System.out.print("Nhap phan tram giam gia (%): ");
        double phanTramGiam = 0;
        try {
            phanTramGiam = Double.parseDouble(sc.nextLine());
            if (phanTramGiam < 0 || phanTramGiam > 100) {
                System.out.println("Phan tram giam phai nam trong khoang 0 - 100!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Du lieu khong hop le!");
            return;
        }

        System.out.print("Nhap dieu kien ap dung (gia tri don hang toi thieu): ");
        double dieuKien = Double.parseDouble(sc.nextLine());

        System.out.print("Nhap ngay bat dau (yyyy-MM-dd): ");
        LocalDate ngayBD = LocalDate.parse(sc.nextLine());

        System.out.print("Nhap ngay ket thuc (yyyy-MM-dd): ");
        LocalDate ngayKT = LocalDate.parse(sc.nextLine());

        KhuyenMai km = new KhuyenMai(maKM, tenKM, ngayBD, ngayKT, phanTramGiam, dieuKien);
        dsKM.them(km);

        System.out.println("Them khuyen mai thanh cong!");
    }

    public void suaKhuyenMaiTuBanPhim() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n===== SUA THONG TIN KHUYEN MAI =====");
        System.out.print("Nhap ma hoac ten khuyen mai can sua: ");
        String tuKhoa = sc.nextLine();

        KhuyenMai km = dsKM.timKiemDoiTuong(tuKhoa);
        if (km == null) {
            System.out.println("Khong tim thay khuyen mai!");
            return;
        }

        System.out.println("Khuyen mai tim thay:");
        System.out.println(km.layThongTinDayDu());

        System.out.println("\nChon thuoc tinh muon sua:");
        System.out.println("1. Ten chuong trinh");
        System.out.println("2. Ngay bat dau");
        System.out.println("3. Ngay ket thuc");
        System.out.println("4. Phan tram giam");
        System.out.println("5. Dieu kien ap dung");
        System.out.println("0. Huy sua");
        System.out.print("Chon: ");
        int chon = Integer.parseInt(sc.nextLine());

        switch (chon) {
            case 1 -> {
                System.out.print("Nhap ten chuong trinh moi: ");
                km.setTenKhuyenMai(sc.nextLine());
            }
            case 2 -> {
                System.out.print("Nhap ngay bat dau moi (yyyy-MM-dd): ");
                km.setNgayBatDau(LocalDate.parse(sc.nextLine()));
            }
            case 3 -> {
                System.out.print("Nhap ngay ket thuc moi (yyyy-MM-dd): ");
                km.setNgayKetThuc(LocalDate.parse(sc.nextLine()));
            }
            case 4 -> {
                System.out.print("Nhap phan tram giam moi (%): ");
                km.setPhanTramGiam(Double.parseDouble(sc.nextLine()));
            }
            case 5 -> {
                System.out.print("Nhap dieu kien ap dung moi: ");
                km.setDieuKienApDung(Double.parseDouble(sc.nextLine()));
            }
            case 0 -> {
                System.out.println("Huy sua khuyen mai.");
                return;
            }
            default -> {
                System.out.println("Lua chon khong hop le!");
                return;
            }
        }
        dsKM.sua(km.getMaKhuyenMai(), km);
        System.out.println("Sua khuyen mai thanh cong!");
    }

    public void menuKhuyenMai() {
        Scanner sc = new Scanner(System.in);
        int chon;
        do {
            System.out.println("\n==========  MENU KHUYEN MAI  ==========");
            System.out.println("1. Doc danh sach tu file");
            System.out.println("2. Ghi danh sach ra file");
            System.out.println("3. Them khuyen mai moi");
            System.out.println("4. Sua khuyen mai");
            System.out.println("5. Xoa khuyen mai");
            System.out.println("6. In danh sach khuyen mai");
            System.out.println("7. Tim kiem khuyen mai");
            System.out.println("8. Chuc nang tien ich");
            System.out.println("9. Tim kiem theo ma/ten");
            System.out.println("0. Quay lai menu chinh");
            System.out.print("Chon: ");

            try {
                chon = Integer.parseInt(sc.nextLine());
                switch (chon) {
                    case 1 -> dsKM.docFile("DanhSachKhuyenMai.txt");
                    case 2 -> dsKM.ghiFile("DanhSachKhuyenMai_Nhan.txt");
                    case 3 -> themKhuyenMaiTuBanPhim();
                    case 4 -> suaKhuyenMaiTuBanPhim();
                    case 5 -> {
                        System.out.print("Nhap ma khuyen mai can xoa: ");
                        String ma = sc.nextLine();
                        dsKM.xoa(ma);
                    }
                    case 6 -> dsKM.inDanhSach();
                    case 7 -> {
                        System.out.print("Nhap tu khoa tim kiem: ");
                        String tk = sc.nextLine();
                        ArrayList<KhuyenMai> kq = dsKM.timKiemDanhSach(tk);
                        if (kq.isEmpty())
                            System.out.println("Khong tim thay khuyen mai nao!");
                        else {
                            System.out.println("Tim thay " + kq.size() + " ket qua:");
                            for (KhuyenMai km : kq)
                                System.out.println(km.layThongTinDayDu());
                        }
                    }
                    case 8 -> dsKM.menuTienIchKhuyenMai();
                    case 9 -> dsKM.timKiem();
                    case 0 -> System.out.println("Quay lai menu chinh...");
                    default -> System.out.println("Lua chon khong hop le!");
                }
            } catch (Exception e) {
                System.out.println("Loi nhap lieu! Vui long nhap lai.");
                chon = -1;
            }
        } while (chon != 0);
    }



    // ================= DANH SACH HOA DON BAN  =================
    public void themHoaDonBanTuBanPhim() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhap ma hoa don ban: ");
        String maHDB = sc.nextLine().trim().toUpperCase();
        while (dsHDB.timKiemDoiTuong(maHDB) != null) {
            System.out.print("Ma hoa don da ton tai! Vui long nhap lai: ");
            maHDB = sc.nextLine().trim().toUpperCase();
        }

        // Ngay lap
        LocalDate ngayLap = null;
        while (ngayLap == null) {
            System.out.print("Nhap ngay lap (yyyy-MM-dd): ");
            try {
                ngayLap = LocalDate.parse(sc.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("Ngay khong hop le! Vui long nhap lai.");
            }
        }

        // Ma nhan vien lap
        String maNV;
        while (true) {
            System.out.print("Nhap ma nhan vien ban thuoc: ");
            maNV = sc.nextLine().trim().toUpperCase();
            NhanVien nv = dsNV.timKiemDoiTuong(maNV);
            if (nv != null && nv instanceof NhanVienBanThuoc) break;
            System.out.println("Ma nhan vien khong hop le hoac khong phai NV ban thuoc!");
        }

        // Ma khach hang
        System.out.print("Nhap ma khach hang: ");
        String maKH = sc.nextLine().trim().toUpperCase();
        while (!dsKH.tonTaiDoiTuong(maKH)) {
            System.out.print("Ma khach hang khong ton tai! Vui long nhap lai: ");
            maKH = sc.nextLine().trim().toUpperCase();
        }

        // Ma khuyen mai
        System.out.print("Nhap ma khuyen mai (bo trong neu khong co): ");
        String maKM = sc.nextLine().trim().toUpperCase();
        if (!maKM.isEmpty() && !dsKM.tonTaiDoiTuong(maKM)) {
            System.out.println("Ma khuyen mai khong ton tai -> bo qua.");
            maKM = "";
        }

        // Chi tiet hoa don
        Map<String, Integer> chiTiet = new HashMap<>();
        System.out.println("--- Nhap chi tiet san pham ---");
        while (true) {
            System.out.print("Nhap ma san pham (x de ket thuc): ");
            String maSP = sc.nextLine().trim().toUpperCase();
            if (maSP.equalsIgnoreCase("x")) {
                if (chiTiet.isEmpty()) {
                    System.out.println("Hoa don phai co it nhat mot san pham!");
                    continue;
                }
                break;
            }
            if (!dsSP.tonTaiDoiTuong(maSP)) {
                System.out.println("Ma san pham khong ton tai!");
                continue;
            }
            int sl = 0;
            while (sl <= 0) {
                System.out.print("Nhap so luong cho " + maSP + ": ");
                try {
                    sl = Integer.parseInt(sc.nextLine());
                    if (sl <= 0) System.out.println("So luong phai > 0!");
                } catch (Exception e) {
                    System.out.println("Nhap so nguyen hop le!");
                }
            }
            chiTiet.put(maSP, sl);
        }

        HoaDonBanHang hdb = new HoaDonBanHang(maHDB, ngayLap, 0, chiTiet, maKH, maNV, maKM);
        hdb.tinhTongTien(dsSP, dsKM);

        if (dsHDB.them(hdb)) {
            // Trừ số lượng tồn kho
            for (Map.Entry<String, Integer> e : chiTiet.entrySet()) {
                SanPham sp = dsSP.timKiemDoiTuong(e.getKey());
                if (sp != null) sp.setSoLuongTon(sp.getSoLuongTon() - e.getValue());
            }
            System.out.println("Them hoa don ban thanh cong! Tong tien: " + hdb.getTongTien());
        } else {
            System.out.println("Them hoa don ban that bai!");
        }
    }

    // ================= SUA HOA DON BAN =================
    public void suaHoaDonBanTuBanPhim() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap ma hoa don ban can sua: ");
        String maHDB = sc.nextLine().trim().toUpperCase();

        HoaDonBanHang finalHDB = dsHDB.timKiemDoiTuong(maHDB);
        if (finalHDB == null) {
            System.out.println("Khong tim thay hoa don!");
            return;
        }

        System.out.println("Hoa don tim thay:");
        System.out.println(finalHDB.layThongTinDayDu());
        System.out.println("\nChi duoc phep sua: Ngay lap, Ma NV ban, Ma KH, Ma KM.");
        System.out.println("Chi tiet san pham va tong tien KHONG duoc sua.\n");

        ArrayList<String> thuocTinh = new ArrayList<>(List.of("Ngay lap", "Ma nhan vien", "Ma khach hang", "Ma khuyen mai"));
        ArrayList<Runnable> phuongThuc = new ArrayList<>();

        // Ngay lap
        phuongThuc.add(() -> {
            LocalDate ngayMoi = null;
            while (ngayMoi == null) {
                System.out.print("Nhap ngay moi (yyyy-MM-dd): ");
                try {
                    ngayMoi = LocalDate.parse(sc.nextLine().trim());
                    finalHDB.setNgayLap(ngayMoi);
                } catch (DateTimeParseException e) {
                    System.out.println("Dinh dang sai!");
                }
            }
        });

        // Ma NV
        phuongThuc.add(() -> {
            while (true) {
                System.out.print("Nhap ma nhan vien moi: ");
                String ma = sc.nextLine().trim().toUpperCase();
                NhanVien nv = dsNV.timKiemDoiTuong(ma);
                if (nv != null && nv instanceof NhanVienBanThuoc) {
                    finalHDB.setMaNhanVienLap(ma);
                    break;
                }
                System.out.println("Khong hop le!");
            }
        });

        // Ma KH
        phuongThuc.add(() -> {
            System.out.print("Nhap ma khach hang moi: ");
            String ma = sc.nextLine().trim().toUpperCase();
            while (!dsKH.tonTaiDoiTuong(ma)) {
                System.out.print("Ma khach hang khong ton tai! Nhap lai: ");
                ma = sc.nextLine().trim().toUpperCase();
            }
            finalHDB.setMaKhachHang(ma);
        });

        // Ma KM
        phuongThuc.add(() -> {
            System.out.print("Nhap ma khuyen mai moi (bo trong neu khong co): ");
            String ma = sc.nextLine().trim().toUpperCase();
            if (ma.isEmpty() || dsKM.tonTaiDoiTuong(ma)) {
                finalHDB.setMaKhuyenMai(ma);
            } else {
                System.out.println("Ma KM khong ton tai -> bo qua.");
            }
        });

        // Menu
        System.out.println("Chon thuoc tinh muon sua:");
        for (int i = 0; i < thuocTinh.size(); i++) {
            System.out.println((i + 1) + ". " + thuocTinh.get(i));
        }
        System.out.println("0. Thoat");
        System.out.print("Nhap lua chon: ");
        String input = sc.nextLine().trim();
        if (input.equals("0")) return;

        boolean daSua = false;
        for (String s : input.split("[,\\s]+")) {
            try {
                int chon = Integer.parseInt(s);
                if (chon >= 1 && chon <= thuocTinh.size()) {
                    phuongThuc.get(chon - 1).run();
                    daSua = true;
                }
            } catch (Exception ignored) {}
        }

        if (daSua) {
            dsHDB.sua(finalHDB.getMaHoaDon(), finalHDB);
            System.out.println("Sua thanh cong!");
            System.out.println("Thong tin sau khi sua:");
            System.out.println(finalHDB.layThongTinDayDu());
        } else {
            System.out.println("Khong co thay doi nao.");
        }
    }

    // ================= MENU HOA DON BAN =================
    public void menuHoaDonBan() {
        Scanner sc = new Scanner(System.in);
        int chon;
        do {
            System.out.println("\n==========  MENU HOA DON BAN ==========");
            System.out.println("1. Doc file");
            System.out.println("2. Ghi file");
            System.out.println("3. Them hoa don ban");
            System.out.println("4. Sua thong tin hoa don ban");
            System.out.println("5. Xoa hoa don ban");
            System.out.println("6. In danh sach hoa don ban");
            System.out.println("7. Menu tien ich hoa don ban");
            System.out.println("8. Tim kiem theo ma/ten");
            System.out.println("0. Quay lai");
            System.out.print("Chon: ");

            try {
                chon = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                chon = -1;
            }

            switch (chon) {
                case 1 -> dsHDB.docFile("DanhSachHoaDonBan.txt");
                case 2 -> dsHDB.ghiFile("DanhSachHoaDonBan_Moi.txt");
                case 3 -> themHoaDonBanTuBanPhim();
                case 4 -> suaHoaDonBanTuBanPhim();
                case 5 -> {
                    System.out.print("Nhap ma hoa don ban can xoa: ");
                    String ma = sc.nextLine().trim().toUpperCase();
                    HoaDonBanHang hdb = dsHDB.timKiemDoiTuong(ma);
                    if (hdb != null && dsHDB.xoa(ma)) {
                        System.out.println("Ban co muon cap nhat ton kho (tang lai so luong da ban)? (y/n)");
                        if (sc.nextLine().equalsIgnoreCase("y")) {
                            for (Map.Entry<String, Integer> e : hdb.getChiTietHoaDon().entrySet()) {
                                SanPham sp = dsSP.timKiemDoiTuong(e.getKey());
                                if (sp != null) sp.setSoLuongTon(sp.getSoLuongTon() + e.getValue());
                            }
                            System.out.println("Da cap nhat lai ton kho!");
                        }
                    } else {
                        System.out.println("Khong tim thay hoa don!");
                    }
                }
                case 6 -> dsHDB.inDanhSach();
                case 7 -> dsHDB.menuTienIchHoaDonBan();
                case 8 -> dsHDB.timKiem();
                case 0 -> System.out.println("Quay lai menu chinh...");
                default -> System.out.println("Lua chon khong hop le!");
            }
        } while (chon != 0);
    }
    // ================= DANH SACH HOA DON NHAP  =================
    public void themHoaDonNhapTuBanPhim() {
        Scanner sc = new Scanner(System.in);

        // 1. Nhập và kiểm tra Mã hóa đơn
        System.out.print("Nhap ma hoa don nhap: ");
        String maHDN = sc.nextLine().trim().toUpperCase();
        while (dsHDN.timKiemDoiTuong(maHDN) != null) {
            System.out.print("Ma hoa don da ton tai! Vui long nhap lai: ");
            maHDN = sc.nextLine().trim().toUpperCase();
        }

        // 2. Nhập và kiểm tra Ngày lập
        LocalDate ngayLap = null;
        while (ngayLap == null) {
            System.out.print("Nhap ngay lap (yyyy-MM-dd): ");
            String ngayStr = sc.nextLine().trim();
            try {
                ngayLap = LocalDate.parse(ngayStr);
            } catch (DateTimeParseException e) {
                System.out.println("Dinh dang ngay khong hop le! Vui long nhap lai.");
            }
        }

        // 3. Nhập và kiểm tra Mã quản lý (phải là nhân viên loại QuanLy)
        String maQL;
        while (true) {
            System.out.print("Nhap ma quan ly: ");
            maQL = sc.nextLine().trim().toUpperCase();
            NhanVien nv = dsNV.timKiemDoiTuong(maQL);
            if (nv != null && nv instanceof QuanLy) {
                break; // Hợp lệ, thoát vòng lặp
            } else if (nv != null) {
                System.out.println("Nhan vien nay khong phai la Quan Ly!");
            } else {
                System.out.println("Ma quan ly khong ton tai!");
            }
            System.out.print("Vui long nhap lai: ");
        }

        // 4. Nhập và kiểm tra Mã nhà cung cấp
        System.out.print("Nhap ma nha cung cap: ");
        String maNCC = sc.nextLine().trim().toUpperCase();
        while (!dsNCC.tonTaiDoiTuong(maNCC)) {
            System.out.print("Ma nha cung cap khong ton tai! Vui long nhap lai: ");
            maNCC = sc.nextLine().trim().toUpperCase();
        }

        // 5. Nhập chi tiết hóa đơn (danh sách sản phẩm)
        Map<String, Integer> chiTiet = new HashMap<>();
        System.out.println("--- Nhap chi tiet san pham ---");
        while (true) {
            System.out.print("Nhap ma san pham (nhap 'x' de ket thuc): ");
            String maSP = sc.nextLine().trim().toUpperCase();
            if (maSP.equalsIgnoreCase("x")) {
                if (chiTiet.isEmpty()) {
                    System.out.println("Hoa don phai co it nhat mot san pham! Vui long them tiep.");
                    continue;
                }
                break;
            }

            if (!dsSP.tonTaiDoiTuong(maSP)) {
                System.out.println("Ma san pham khong ton tai!");
                continue;
            }

            int soLuong = 0;
            while (soLuong <= 0) {
                System.out.print("Nhap so luong cho SP [" + maSP + "]: ");
                try {
                    soLuong = Integer.parseInt(sc.nextLine());
                    if (soLuong <= 0) System.out.println("So luong phai la so duong!");
                } catch (NumberFormatException e) {
                    System.out.println("Vui long nhap mot so nguyen hop le!");
                }
            }
            chiTiet.put(maSP, soLuong);
            System.out.println("Da them san pham " + maSP + " voi so luong " + soLuong);
        }

        // 6. Tạo đối tượng và tính tổng tiền
        HoaDonNhap hdn = new HoaDonNhap(maHDN, ngayLap, 0, maQL, maNCC, chiTiet);
        hdn.tinhTongTien(dsSP);

        if (dsHDN.them(hdn)) {
            // Tự động cập nhật số lượng tồn kho cho sản phẩm
            for(Map.Entry<String, Integer> entry : chiTiet.entrySet()){
                SanPham sp = dsSP.timKiemDoiTuong(entry.getKey());
                if(sp != null){
                    sp.setSoLuongTon(sp.getSoLuongTon() + entry.getValue());
                }
            }
            System.out.println("Them hoa don nhap thanh cong! Tong tien: " + hdn.getTongTien());
            System.out.println("Da cap nhat so luong ton kho cho cac san pham.");
        } else {
            System.out.println("Them hoa don nhap that bai!");
        }
    }

    public void suaHoaDonNhapTuBanPhim() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap ma hoa don nhap can sua: ");
        String maHDN = sc.nextLine().trim().toUpperCase();

        HoaDonNhap finalHDN = dsHDN.timKiemDoiTuong(maHDN);
        if (finalHDN == null) {
            System.out.println("Khong tim thay hoa don nhap!");
            return;
        }

        // Hien thi thong tin hoa don
        System.out.println("Hoa don tim thay:");
        System.out.println(finalHDN.layThongTinDayDu());
        System.out.println("\nLuu y: Chi duoc phep sua thong tin phu (ngay lap, ma quan ly, ma nha cung cap).");
        System.out.println("Chi tiet san pham va tong tien KHONG duoc thay doi.\n");

        // Thuoc tinh co the sua
        ArrayList<String> thuocTinh = new ArrayList<>();
        thuocTinh.add("Ngay lap");
        thuocTinh.add("Ma quan ly");
        thuocTinh.add("Ma nha cung cap");

        ArrayList<Runnable> phuongThuc = new ArrayList<>();

        // 1. Sua ngay lap
        phuongThuc.add(() -> {
            LocalDate ngayLapMoi = null;
            while (ngayLapMoi == null) {
                System.out.print("Nhap ngay lap moi (yyyy-MM-dd): ");
                String ngayStr = sc.nextLine().trim();
                try {
                    ngayLapMoi = LocalDate.parse(ngayStr);
                    finalHDN.setNgayLap(ngayLapMoi);
                } catch (DateTimeParseException e) {
                    System.out.println("Dinh dang ngay khong hop le! Vui long nhap lai.");
                }
            }
        });

        // 2. Sua ma quan ly
        phuongThuc.add(() -> {
            String maQLMoi;
            while (true) {
                System.out.print("Nhap ma quan ly moi: ");
                maQLMoi = sc.nextLine().trim().toUpperCase();
                NhanVien nv = dsNV.timKiemDoiTuong(maQLMoi);
                if (nv != null && nv instanceof QuanLy) {
                    finalHDN.setMaQuanLy(maQLMoi);
                    break;
                } else if (nv != null) {
                    System.out.println("Nhan vien nay khong phai la Quan Ly!");
                } else {
                    System.out.println("Ma quan ly khong ton tai!");
                }
            }
        });

        // 3. Sua ma nha cung cap
        phuongThuc.add(() -> {
            System.out.print("Nhap ma nha cung cap moi: ");
            String maNCCMoi = sc.nextLine().trim().toUpperCase();
            while (!dsNCC.tonTaiDoiTuong(maNCCMoi)) {
                System.out.print("Ma nha cung cap khong ton tai! Vui long nhap lai: ");
                maNCCMoi = sc.nextLine().trim().toUpperCase();
            }
            finalHDN.setMaNhaCungCap(maNCCMoi);
        });

        // Menu lua chon
        System.out.println("\nChon thuoc tinh muon sua (nhap so, cach nhau bang dau phay hoac khoang trang):");
        for (int i = 0; i < thuocTinh.size(); i++) {
            System.out.println((i + 1) + ". " + thuocTinh.get(i));
        }
        System.out.println("0. Thoat");
        System.out.print("Nhap lua chon: ");
        String input = sc.nextLine().trim();

        if (input.equals("0")) return;

        String[] choices = input.split("[,\\s]+");
        boolean daSua = false;

        for (String choice : choices) {
            try {
                int chon = Integer.parseInt(choice);
                if (chon >= 1 && chon <= thuocTinh.size()) {
                    System.out.println("Sua: " + thuocTinh.get(chon - 1));
                    phuongThuc.get(chon - 1).run();
                    daSua = true;
                } else {
                    System.out.println("Lua chon khong hop le: " + chon);
                }
            } catch (Exception e) {
                System.out.println("Loi nhap: " + choice);
            }
        }

        // Cap nhat du lieu
        if (daSua) {
            dsHDN.sua(finalHDN.getMaHoaDon(), finalHDN);
            System.out.println("Sua hoa don nhap thanh cong!");
            System.out.println("Thong tin sau khi sua:");
            System.out.println(finalHDN.layThongTinDayDu());
        } else {
            System.out.println("Khong co thay doi nao duoc thuc hien.");
        }
    }


    public void menuHoaDonNhap() {
        Scanner sc = new Scanner(System.in);
        int chon;
        do {
            System.out.println("\n==========  MENU HOA DON NHAP ==========");
            System.out.println("1. Doc file du lieu txt");
            System.out.println("2. Ghi vao file txt");
            System.out.println("3. Them hoa don nhap");
            System.out.println("4. Sua thong tin hoa don nhap");
            System.out.println("5. Xoa hoa don nhap");
            System.out.println("6. In danh sach hoa don nhap");
            System.out.println("7. Menu tien ich hoa don nhap");
            System.out.println("8. Tim kiem theo ma/ten");
            System.out.println("0. Quay lai");
            System.out.print("Chon: ");
            try {
                chon = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                chon = -1;
            }

            switch (chon) {
                case 1 -> dsHDN.docFile("DanhSachHoaDonNhap.txt");
                case 2 -> dsHDN.ghiFile("DanhSachHoaDonNhap_Moi.txt");
                case 3 -> themHoaDonNhapTuBanPhim();
                case 4 -> suaHoaDonNhapTuBanPhim();
                case 5 -> {
                    System.out.print("Nhap ma hoa don nhap can xoa: ");
                    String ma = sc.nextLine().trim().toUpperCase();
                    HoaDonNhap hdnCanXoa = dsHDN.timKiemDoiTuong(ma);
                    if (hdnCanXoa != null) {
                        if (dsHDN.xoa(ma)) {
                            System.out.println("Ban co muon cap nhat (giam) so luong ton kho cua san pham khong? (y/n)");
                            String confirm = sc.nextLine();
                            if(confirm.equalsIgnoreCase("y")){
                                for(Map.Entry<String, Integer> entry : hdnCanXoa.getChiTietHoaDon().entrySet()){
                                    SanPham sp = dsSP.timKiemDoiTuong(entry.getKey());
                                    if(sp != null){
                                        sp.setSoLuongTon(sp.getSoLuongTon() - entry.getValue());
                                    }
                                }
                                System.out.println("Da cap nhat lai so luong ton kho.");
                            }
                        }
                    } else {
                        System.out.println("Khong tim thay hoa don de xoa.");
                    }
                }
                case 6 -> dsHDN.inDanhSach();
                case 7 -> dsHDN.menuTienIchHoaDonNhap();
                case 8 -> dsHDN.timKiem();
                case 0 -> System.out.println("Quay lai menu chinh...");
                default -> System.out.println("Lua chon khong hop le!");
            }
        } while (chon != 0);
    }


}

