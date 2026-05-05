package com.clothingstore.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection – Lớp tiện ích kết nối trực tiếp đến cơ sở dữ liệu MySQL thông qua JDBC.
 *
 *  Lưu ý: Lớp này là phương thức kết nối JDBC thuần (raw JDBC), được giữ lại
 * như một phương án dự phòng. Trong dự án hiện tại, kết nối chính được quản lý
 * thông qua {@link HibernateUtil} (sử dụng Hibernate ORM).
 *
 *  Cấu hình kết nối:
 *  
 *      URL: jdbc:mysql://localhost:3306/clothing_store – kết nối đến MySQL
 *       chạy trên máy cục bộ (localhost), cổng mặc định 3306, database "clothing_store"
 *      USER: root – tài khoản quản trị MySQL
 *      PASSWORD: sqlduong – mật khẩu MySQL (cần thay đổi cho phù hợp môi trường)
 * 
 * 
 *
 * @author clothing-store
 * @version 1.0
 */
public class DBConnection {

    /** URL kết nối JDBC đến database MySQL "clothing_store" trên localhost */
    private static final String URL = "jdbc:mysql://localhost:3306/clothing_store";

    /** Tên tài khoản đăng nhập MySQL */
    private static final String USER = "root";

    /** Mật khẩu đăng nhập MySQL – cần thay đổi nếu MySQL của bạn có mật khẩu khác */
    private static final String PASSWORD = "sqlduong";

    /**
     * Tạo và trả về một kết nối (Connection) mới đến cơ sở dữ liệu MySQL.
     *
     * <p>Mỗi lần gọi phương thức này sẽ tạo một kết nối MỚI. Người gọi có trách nhiệm
     * đóng kết nối sau khi sử dụng xong (nên dùng try-with-resources).</p>
     *
     * @return đối tượng {@link Connection} đại diện cho kết nối đến MySQL
     * @throws SQLException nếu không thể kết nối (sai URL, sai mật khẩu,
     *         MySQL chưa chạy, database không tồn tại, v.v.)
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
