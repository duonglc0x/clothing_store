package com.clothingstore;

import com.clothingstore.util.HibernateUtil;
import com.clothingstore.view.MainFrame;

import javax.swing.*;

/**
 * MainApp – Điểm khởi chạy (Entry Point) của ứng dụng Quản lý Cửa hàng Quần áo.
 *
 * Lớp này chứa phương thức {@code main()} – nơi JVM bắt đầu thực thi chương trình.
 * Quy trình khởi chạy gồm 4 bước chính:
 *   Thiết lập giao diện Look and Feel (L&F) theo hệ điều hành
 *   Khởi tạo Hibernate SessionFactory (warm-up kết nối CSDL)
 *   Khởi chạy giao diện chính (MainFrame) trên Event Dispatch Thread (EDT)
 *   Đăng ký Shutdown Hook để đóng kết nối CSDL khi thoát ứng dụng

 *
 * @author clothing-store
 * @version 1.0
 */
public class MainApp {

    /**
     * Phương thức main – điểm bắt đầu thực thi chương trình.
     *
     * @param args tham số dòng lệnh (không sử dụng trong ứng dụng này)
     */
    public static void main(String[] args) {

        // ─── BƯỚC 1: Thiết lập Look and Feel ──────────────────────────────────
        // Sử dụng Look and Feel của hệ điều hành (Windows L&F trên Windows,
        // GTK trên Linux, Aqua trên macOS) để giao diện trông tự nhiên hơn.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // Nếu không thể thiết lập L&F hệ thống, sử dụng L&F mặc định của Java (Metal)
        }

        // Thiết lập font mặc định cho các hộp thoại JOptionPane
        // Sử dụng font "Segoe UI" để hỗ trợ hiển thị ký tự Unicode tiếng Việt đúng cách
        UIManager.put("OptionPane.messageFont", new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        UIManager.put("OptionPane.buttonFont", new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));

        // ─── BƯỚC 2: Khởi tạo Hibernate SessionFactory (Warm-up) ─────────────
        // Gọi getSessionFactory() lần đầu sẽ kích hoạt khối static initializer
        // trong HibernateUtil, đọc hibernate.cfg.xml và tạo connection pool đến MySQL.
        // Nếu thất bại (MySQL chưa chạy, sai password...), hiển thị thông báo lỗi và thoát.
        System.out.println("Đang khởi tạo kết nối cơ sở dữ liệu...");
        try {
            HibernateUtil.getSessionFactory();
            System.out.println("Kết nối cơ sở dữ liệu thành công!");
        } catch (Exception e) {
            // In lỗi ra console để debug
            System.err.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
            // Hiển thị hộp thoại lỗi cho người dùng biết không thể kết nối CSDL
            JOptionPane.showMessageDialog(null,
                "Không thể kết nối cơ sở dữ liệu!\n" + e.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            // Kết thúc ứng dụng ngay – không thể hoạt động nếu không có CSDL
            return;
        }

        // ─── BƯỚC 3: Khởi chạy giao diện trên EDT ────────────────────────────
        // Theo nguyên tắc của Swing, mọi thao tác với GUI PHẢI được thực hiện
        // trên Event Dispatch Thread (EDT) để tránh lỗi đa luồng (thread-safety).
        // SwingUtilities.invokeLater() đưa tác vụ vào hàng đợi sự kiện của EDT.
        SwingUtilities.invokeLater(() -> {
            // Tạo khung chính (MainFrame) chứa toàn bộ giao diện ứng dụng
            MainFrame frame = new MainFrame();
            // Hiển thị cửa sổ ứng dụng
            frame.setVisible(true);
        });

        // ─── BƯỚC 4: Đăng ký Shutdown Hook ───────────────────────────────────
        // Shutdown Hook là một Thread được JVM thực thi tự động khi ứng dụng thoát
        // (đóng cửa sổ, Ctrl+C, System.exit(), v.v.).
        // Tại đây, chúng ta đóng SessionFactory để giải phóng tất cả kết nối MySQL
        // trong connection pool, tránh rò rỉ kết nối (connection leak).
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Đang đóng kết nối cơ sở dữ liệu...");
            HibernateUtil.shutdown();
        }));
    }
}
