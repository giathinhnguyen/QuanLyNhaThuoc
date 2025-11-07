
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;


public class DanhSachSanPham implements ChucNang,Doc_Ghi{
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

    /**
     * Tìm kiếm sản phẩm theo từ khóa (mã, tên, loại, NCC)
     * @param tuKhoa Từ khóa tìm kiếm (không phân biệt hoa thường)
     * @return SanPham nếu tìm thấy, null nếu không
     */
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

    public SanPham timKiemSanPham(String tuKhoa) {
        ArrayList<SanPham> ketQua = timKiemDanhSach(tuKhoa);
        if (ketQua.isEmpty()) {
            return null;
        }
        if (ketQua.size() > 1) {
            System.out.println("Cảnh báo: Tìm thấy " + ketQua.size() + " sản phẩm khớp từ khóa!");
            System.out.println("Trả về sản phẩm đầu tiên:");
            System.out.println(ketQua.get(0).layThongTinDayDu());
        }
        return ketQua.get(0);
    }
    @Override
    public void docFile(String fileName) {
        danhSachSanPham.clear();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split(";", -1);
                String loai = parts[0];

                SanPham sp = null;
                switch (loai) {
                    case "Thuoc":
                        sp = new Thuoc(
                                parts[1],parts[2],parts[7],parts[8],Double.parseDouble(parts[4]), Double.parseDouble(parts[3]),
                                Integer.parseInt(parts[5]),LocalDate.parse(parts[6], fmt),parts[9],parts[10], Boolean.parseBoolean(parts[11])
                        );
                        break;

                    case "ThucPhamChucNang":
                        sp = new ThucPhamChucNang(
                                parts[1], parts[2], parts[7], parts[8],
                                Double.parseDouble(parts[4]), Double.parseDouble(parts[3]),
                                Integer.parseInt(parts[5]), LocalDate.parse(parts[6], fmt),
                                parts[9], parts[10], Integer.parseInt(parts[11])
                        );
                        break;

                    case "MyPham":
                        sp = new MyPham(
                                parts[1], parts[2], parts[7], parts[8],
                                Double.parseDouble(parts[4]), Double.parseDouble(parts[3]),
                                Integer.parseInt(parts[5]), LocalDate.parse(parts[6], fmt),
                                parts[9], parts[10]
                        );
                        break;

                    case "DungCuYTe":
                        sp = new DungCuYTe(
                                parts[1], parts[2], parts[7],  parts[8],
                                Double.parseDouble(parts[4]), Double.parseDouble(parts[3]),
                                Integer.parseInt(parts[5]), LocalDate.parse(parts[6], fmt),
                                parts[9], parts[10]
                        );
                        break;
                }
                if (sp != null) danhSachSanPham.add(sp);
            }
            System.out.println("Đọc file thành công (" + danhSachSanPham.size() + " sản phẩm)");
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
            System.out.println(" Ghi file thành công: " + fileName);
        }  catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void them() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập loại: ");
        String loai = sc.nextLine();
        System.out.print("Nhập mã SP: ");
        String ma = sc.nextLine().toUpperCase();
        System.out.print("Nhập tên SP: ");
        String ten = sc.nextLine();
        System.out.print("Nhập hạn sử dụng (yyyy-MM-dd): ");
        LocalDate hsd = LocalDate.parse(sc.nextLine());
        System.out.print("Nhập loại sản phẩm: ");
        String loaiSanPham = sc.nextLine();
        System.out.print("Nhập mã nhà cung cấp: ");
        String maNhaCungCap = sc.nextLine();
        System.out.print("Nhập giá nhập: ");
        sc.nextLine();
        double giaNhap = Double.parseDouble(sc.nextLine());
        System.out.print("Nhập giá bán: ");
        double giaBan = Double.parseDouble(sc.nextLine());
        System.out.print("Nhập số lượng: ");
        int soLuong = Integer.parseInt(sc.nextLine());


        SanPham sp = null;
        switch (loai.trim().toLowerCase()) {
            case "thuoc":
                System.out.print("Nhập loại thuốc: ");
                String loaiThuoc = sc.nextLine();
                System.out.print("Nhập công dụng: ");
                String congDung = sc.nextLine();
                System.out.print("Kê toa (1-Có || 2-Không): ");
                int ketoa = Integer.parseInt(sc.nextLine());;
                if(ketoa != 1 && ketoa != 2){
                    System.out.print("Kê toa chỉ nhận giá trị (1-Có || 2-Không) vui lòng nhập lại: ");
                    ketoa = sc.nextInt();
                }
                sp = new Thuoc(ma, ten, loaiSanPham, maNhaCungCap, giaNhap, giaBan, soLuong, hsd, loaiThuoc, congDung , (ketoa==1)?true:false);
                break;
            case "mypham":
                System.out.print("Nhập loại mỹ phẩm: ");
                String loaiMyPham = sc.nextLine();
                System.out.print("Nhập loại da phù hợp: ");
                String loaiDaPhuHop = sc.nextLine();
                sp = new MyPham(ma, ten, loaiSanPham, maNhaCungCap, giaNhap, giaBan, soLuong, hsd, loaiMyPham, loaiDaPhuHop);
                break;
            case "tpcn":
                System.out.print("Nhập loại thực phẩm chức năng: ");
                String loaiTPCN = sc.nextLine();
                System.out.print("Bổ sung dưỡng chất: ");
                String bsdc = sc.nextLine();
                System.out.print("Độ tuổi sử dụng: ");
                int tuoiSD = Integer.parseInt(sc.nextLine());;
                if(tuoiSD<1){
                    System.out.print("Tuổi lớn hơn 0 mời nhập lại: ");
                    tuoiSD = Integer.parseInt(sc.nextLine());;
                }
                sp = new ThucPhamChucNang(ma, ten, loaiSanPham, maNhaCungCap, giaNhap, giaBan, soLuong, hsd, loaiTPCN, bsdc, tuoiSD);
                break;
            case "dcyt":
                System.out.print("Nhập chất liệu: ");
                String chatLieu = sc.nextLine();
                System.out.print("Nhập xuất xứ: ");
                String xuatXu = sc.nextLine();
                sp = new DungCuYTe(ma, ten, loaiSanPham, maNhaCungCap, giaNhap, giaBan, soLuong, hsd, chatLieu, xuatXu);
                break;
        }
        if (sp != null) {
            danhSachSanPham.add(sp);
            System.out.println("Thêm sản phẩm thành công!");
        } else {
            System.out.println("Loại sản phẩm không hợp lệ!");
        }
    }

    @Override
    public void sua() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã/tên/loại sản phẩm cần sửa: ");
        String tuKhoa = sc.nextLine();

        SanPham finalSp = timKiemSanPham(tuKhoa);
        if(finalSp == null){
            System.out.println("Không tìm thấy sản phẩm với mã!");
            return;
        }
        //Lambda Expression = "Hàm ẩn danh" (anonymous function)
        //Biến được dùng trong lambda phải là final
        System.out.println("\nSản phẩm tìm thấy:");
        System.out.println(finalSp.layThongTinDayDu());

        ArrayList<String> thuocTinhChung = new ArrayList<>();
        thuocTinhChung.add("Tên sản phẩm");
        thuocTinhChung.add("Giá bán");
        thuocTinhChung.add("Số lượng tồn");
        thuocTinhChung.add("Hạn sử dụng (yyyy-MM-dd)");
        thuocTinhChung.add("Loại sản phẩm");
        thuocTinhChung.add("Mã nhà cung cấp");
        //Runnable là interface có 1 phương thức run(),
        // không nhận tham số, không trả về
        ArrayList<Runnable> phuongThucChung = new ArrayList<>();
        phuongThucChung.add(() -> {
            System.out.print("Nhập tên mới: ");
            finalSp.setTenSanPham(sc.nextLine());
        });
        phuongThucChung.add(() -> {
            System.out.print("Nhập giá bán mới: ");
            finalSp.setGiaBan(Double.parseDouble(sc.nextLine()));
        });
        phuongThucChung.add(() -> {
            System.out.print("Nhập số lượng mới: ");
            finalSp.setSoLuongTon(Integer.parseInt(sc.nextLine()));
        });
        phuongThucChung.add(() -> {
            System.out.print("Nhập hạn sử dụng mới (yyyy-MM-dd): ");
            finalSp.setHSD(LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        });
        phuongThucChung.add(() -> {
            System.out.print("Nhập loại sản phẩm mới: ");
            finalSp.setLoaiSanPham(sc.nextLine());
        });
        phuongThucChung.add(() -> {
            System.out.print("Nhập mã nhà cung cấp mới: ");
            finalSp.setMaNhaCungCap(sc.nextLine());
        });

        ArrayList<String> thuocTinhRieng  = new ArrayList<>();
        ArrayList<Runnable> phuongThucRieng = new ArrayList<>();
        if(finalSp instanceof Thuoc){
            Thuoc thuoc = (Thuoc) finalSp;
            thuocTinhRieng.add("Loại thuốc");
            thuocTinhRieng.add("Công dụng");
            thuocTinhRieng.add("Cần toa bác sĩ (true/false)");
            phuongThucRieng.add(() -> {
                System.out.print("Nhập loại thuốc mới: ");
                thuoc.setLoaiThuoc(sc.nextLine());
            });
            phuongThucRieng.add(() -> {
                System.out.print("Nhập công dụng mới: ");
                thuoc.setCongDung(sc.nextLine());
            });
            phuongThucRieng.add(() -> {
                System.out.print("Cần toa bác sĩ (true/false): ");
                thuoc.setCanToaBacSi(Boolean.parseBoolean(sc.nextLine()));
            });
        } else if (finalSp instanceof MyPham) {
            MyPham mypham = (MyPham) finalSp;
            thuocTinhRieng.add("Loại mỹ phẩm");
            thuocTinhRieng.add("Loại da phù hợp");
            phuongThucRieng.add(() -> {
                System.out.print("Nhập loại mỹ phẩm mới: ");
                mypham.setLoaiMyPham(sc.nextLine());
            });
            phuongThucRieng.add(() -> {
                System.out.print("Nhập loại da phù hợp mới: ");
                mypham.setLoaiDaPhuHop(sc.nextLine());
            });
        } else if (finalSp instanceof ThucPhamChucNang) {
            ThucPhamChucNang tpcn = (ThucPhamChucNang) finalSp;
            thuocTinhRieng.add("Loại thực phẩm chức năng");
            thuocTinhRieng.add("Bổ sung dưỡng chất");
            thuocTinhRieng.add("Độ tuổi sử dụng");
            phuongThucRieng.add(() -> {
                System.out.print("Nhập loại TPCN mới: ");
                tpcn.setLoaiThucPhamChucNang(sc.nextLine());
            });
            phuongThucRieng.add(() -> {
                System.out.print("Nhập dưỡng chất bổ sung mới: ");
                tpcn.setBoSungDuongChat(sc.nextLine());
            });
            phuongThucRieng.add(() -> {
                System.out.print("Nhập độ tuổi sử dụng mới: ");
                tpcn.setTuoiSuDung(Integer.parseInt(sc.nextLine()));
            });
        } else if (finalSp instanceof  DungCuYTe) {
            DungCuYTe dcyt = (DungCuYTe) finalSp;
            thuocTinhRieng.add("Chất liệu");
            thuocTinhRieng.add("Xuất xứ");
            phuongThucRieng.add(() -> {
                System.out.print("Nhập chất liệu mới: ");
                dcyt.setChatLieu(sc.nextLine());
            });
            phuongThucRieng.add(() -> {
                System.out.print("Nhập xuất xứ mới: ");
                dcyt.setXuatXu(sc.nextLine());
            });
        }
        // Hiển thị menu
        System.out.println("\nChọn thuộc tính muốn sửa (nhập số, cách nhau bằng dấu phẩy hoặc khoảng trắng):");
        for (int i = 0; i < thuocTinhChung.size(); i++) {
            System.out.println((i + 1) + ". " + thuocTinhChung.get(i));
        }
        int startRieng = thuocTinhChung.size() + 1;
        if (!thuocTinhRieng.isEmpty()) {
            for (int i = 0; i < thuocTinhRieng.size(); i++) {
                System.out.println((startRieng + i) + ". " + thuocTinhRieng.get(i));
            }
        }
        System.out.println("0. Thoát");
        System.out.print("Nhập lựa chọn: ");
        String input = sc.nextLine().trim();
        if (input.equals("0")) return;
        String[] choices = input.split("[,\\s]+");
        //[ ... ]: Là một tập hợp các ký tự.
        //,: Ký tự dấu phẩy.\\s: Bất kỳ ký tự khoảng trắng nào (dấu cách, tab...).
        boolean daSua = false;

        for(String choice : choices){
            try{
                int chon = Integer.parseInt(choice);
                if (chon == 0) continue;
                if(chon >= 1 && chon <= thuocTinhChung.size()){
                    System.out.println("Sửa: " + thuocTinhChung.get(chon - 1));
                    phuongThucChung.get(chon - 1).run();
                    daSua = true;
                } else if (chon >= startRieng && chon <= startRieng + thuocTinhRieng.size()){
                   int idx = chon - startRieng;
                    System.out.println("Sửa: " + thuocTinhRieng.get(idx));
                    phuongThucRieng.get(idx).run();
                    daSua = true;
                } else {
                    System.out.println("Lựa chọn không hợp lệ: " + chon);
                }
            } catch (Exception e) {
                System.out.println("Lỗi nhập: " + choice);
            }
        }
        if (daSua) {
            System.out.println("Sửa sản phẩm thành công!");
        } else {
            System.out.println("Không có thay đổi nào được thực hiện.");
        }
    }

    @Override
    public void xoa() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã/tên sản phẩm cần xóa: ");
        String tuKhoa = sc.nextLine();

        SanPham sp = timKiemSanPham(tuKhoa);
        if (sp == null) {
            System.out.println("Không tìm thấy sản phẩm!");
            return;
        }
        System.out.println("Xác nhận xóa:");
        System.out.println(sp.layThongTinDayDu());
        System.out.print("Nhập 'YES' để xác nhận xóa: ");
        String xacNhan = sc.nextLine().trim();

        if (!xacNhan.equalsIgnoreCase("YES")) {
            System.out.println("Đã hủy xóa.");
            return;
        }

        boolean removed = danhSachSanPham.remove(sp);
        if (removed) {
            System.out.println("Xóa thành công!");
        } else {
            System.out.println("Xóa thất bại!");
        }
    }

    @Override
    public void timKiem() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập từ khóa tìm kiếm (mã/tên/loại/NCC): ");
        String tuKhoa = sc.nextLine();
        SanPham sp = timKiemSanPham(tuKhoa);
        if (sp != null) {
            System.out.println("TÌM THẤY:");
            System.out.println(sp.layThongTinDayDu());
        } else {
            System.out.println("Không tìm thấy sản phẩm nào!");
        }
    }

    @Override
    public void inDanhSach() {
        if (danhSachSanPham.isEmpty()) {
            System.out.println("Danh sách rỗng!");
            return;
        }
        System.out.println("\n=== DANH SÁCH SẢN PHẨM ===");
        for (SanPham sp : danhSachSanPham) {
            System.out.println(sp.layThongTinDayDu());
        }
    }

//    public  ArrayList<SanPham> locTheoLoaiSanPham(String loai){
//        ArrayList<SanPham> ketQua = new ArrayList<>();
//        if(loai.trim().toLowerCase().)
//    }
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



}
