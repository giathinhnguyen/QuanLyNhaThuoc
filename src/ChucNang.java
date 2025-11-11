import java.util.ArrayList;

public interface ChucNang<T> {
    boolean them(T obj);
    boolean xoa(String ma);
    boolean sua(String ma, T newObj);
   ArrayList<T> timKiemDanhSach(String tuKhoa);
   T timKiemDoiTuong(String tuKhoa);
   boolean tonTaiDoiTuong(String ma);
   default void timKiem(){};
}
