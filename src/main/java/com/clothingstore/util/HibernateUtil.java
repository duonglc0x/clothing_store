package com.clothingstore.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * HibernateUtil – Lớp tiện ích (Utility class) quản lý Hibernate SessionFactory.
 *
 * Lớp này sử dụng mẫu thiết kế Singleton để đảm bảo chỉ có DUY NHẤT MỘT thể hiện
 * (instance) của {@link SessionFactory} tồn tại trong toàn bộ vòng đời ứng dụng.
 *
 * SessionFactory là đối tượng nặng (heavy-weight), chịu trách nhiệm:
 * Đọc cấu hình từ file {@code hibernate.cfg.xml}
 * Tạo connection pool kết nối đến MySQL
 * Quản lý mapping giữa Entity Java ↔ Bảng trong CSDL
 * Cung cấp các {@link org.hibernate.Session} cho mỗi thao tác CRUD
 * 
 * 
 *
 * @author clothing-store
 * @version 1.0
 */
public class HibernateUtil {

    /**
     * Thể hiện duy nhất của SessionFactory, được khởi tạo một lần duy nhất
     * khi lớp được nạp (class loading) nhờ khối static initializer.
     *
     * <p>Từ khóa {@code final} đảm bảo biến này không thể bị gán lại sau khi khởi tạo.</p>
     */
    private static final SessionFactory sessionFactory;

    /*
     * Khối khởi tạo tĩnh (Static Initializer Block):
     * - Được thực thi DUY NHẤT MỘT LẦN khi JVM nạp lớp HibernateUtil lần đầu tiên.
     * - Đọc file cấu hình "hibernate.cfg.xml" từ classpath (thư mục resources).
     * - Xây dựng SessionFactory dựa trên các thông số cấu hình (URL, username, password,
     *   dialect, mapping classes, v.v.).
     * - Nếu xảy ra lỗi (thiếu driver, sai password, file config lỗi...), ném ra
     *   ExceptionInInitializerError để dừng ứng dụng ngay lập tức.
     */
    static {
        try {
            // Tạo đối tượng Configuration, đọc file hibernate.cfg.xml, rồi build SessionFactory
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            // In lỗi ra console để debug
            System.err.println("SessionFactory creation failed: " + ex);
            // Ném lỗi nghiêm trọng – ứng dụng không thể hoạt động nếu không có SessionFactory
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Lấy thể hiện duy nhất của SessionFactory.
     *
     * Phương thức này được gọi bởi tất cả các lớp DAO khi cần mở một {@link org.hibernate.Session}
     * mới để thực hiện truy vấn hoặc cập nhật dữ liệu.
     *
     * @return SessionFactory duy nhất của ứng dụng
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Đóng SessionFactory và giải phóng tất cả tài nguyên liên quan.
     *
     * Phương thức này được gọi khi ứng dụng thoát (thông qua Shutdown Hook trong MainApp)
     * để đóng tất cả connection trong pool, giải phóng bộ nhớ, và đảm bảo
     * không có kết nối MySQL nào bị rò rỉ (connection leak).
     */
    public static void shutdown() {
        // Kiểm tra null trước khi đóng để tránh NullPointerException
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
