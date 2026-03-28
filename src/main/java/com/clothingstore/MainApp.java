package com.clothingstore;

import com.clothingstore.util.HibernateUtil;
import com.clothingstore.view.MainFrame;

import javax.swing.*;

/**
 * MainApp – Điểm khởi chạy ứng dụng Quản lý Cửa hàng Quần áo.
 */
public class MainApp {

    public static void main(String[] args) {
        // Thiết lập Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        // Thiết lập font mặc định hỗ trợ Unicode tiếng Việt
        UIManager.put("OptionPane.messageFont", new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        UIManager.put("OptionPane.buttonFont", new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));

        // Khởi tạo Hibernate SessionFactory (warm-up)
        System.out.println("Đang khởi tạo kết nối cơ sở dữ liệu...");
        try {
            HibernateUtil.getSessionFactory();
            System.out.println("Kết nối cơ sở dữ liệu thành công!");
        } catch (Exception e) {
            System.err.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                "Không thể kết nối cơ sở dữ liệu!\n" + e.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Khởi chạy giao diện trên EDT
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });

        // Đóng SessionFactory khi thoát ứng dụng
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Đang đóng kết nối cơ sở dữ liệu...");
            HibernateUtil.shutdown();
        }));
    }
}
