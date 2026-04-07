package com.clothingstore.view;

import com.clothingstore.controller.OrderController;
import com.clothingstore.controller.ProductController;
import com.clothingstore.controller.CustomerController;

import javax.swing.*;
import java.awt.*;

/**
 * DashboardPanel – Panel tổng quan hệ thống (trang chủ).
 *
 * Hiển thị 3 card thống kê nhanh:
 * - Số lượng Sản phẩm (màu xanh dương)
 * - Số lượng Khách hàng (màu xanh lá)
 * - Số lượng Đơn hàng (màu đỏ)
 *
 * Dữ liệu được tải bất đồng bộ qua SwingWorker để không block UI thread.
 */
public class DashboardPanel extends JPanel {

    // ── Label hiển thị số liệu thống kê ──
    private JLabel lblProductCount;   // Hiển thị tổng sản phẩm
    private JLabel lblCustomerCount;  // Hiển thị tổng khách hàng
    private JLabel lblOrderCount;     // Hiển thị tổng đơn hàng

    // ── Controller để gọi nghiệp vụ đếm ──
    private final ProductController productController = new ProductController();
    private final CustomerController customerController = new CustomerController();
    private final OrderController orderController = new OrderController();

    /**
     * Constructor – khởi tạo giao diện và tải dữ liệu lần đầu.
     */
    public DashboardPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));
        initComponents();   // Xây dựng giao diện
        refreshData();       // Tải dữ liệu thống kê
    }

    /**
     * Khởi tạo component giao diện: header + 3 card thống kê.
     */
    private void initComponents() {
        // ═══════════════ HEADER (Tiêu đề) ═══════════════
        JLabel header = new JLabel("  Tổng quan hệ thống");
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setForeground(new Color(33, 33, 33));
        header.setPreferredSize(new Dimension(0, 70));
        header.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 0));
        add(header, BorderLayout.NORTH);

        // ═══════════════ CARDS AREA (Vùng card thống kê) ═══════════════
        JPanel cardsWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        cardsWrapper.setBackground(new Color(240, 240, 240));
        cardsWrapper.setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15));

        // Khởi tạo label cho mỗi card (ban đầu hiển thị "0")
        lblProductCount  = new JLabel("0");
        lblCustomerCount = new JLabel("0");
        lblOrderCount    = new JLabel("0");

        // Tạo 3 card với màu accent khác nhau
        cardsWrapper.add(createCard("Sản phẩm",  lblProductCount,  new Color(66, 133, 244)));   // Xanh dương
        cardsWrapper.add(createCard("Khách hàng", lblCustomerCount, new Color(52, 168, 83)));    // Xanh lá
        cardsWrapper.add(createCard("Đơn hàng",   lblOrderCount,    new Color(234, 67, 53)));    // Đỏ

        add(cardsWrapper, BorderLayout.CENTER);
    }

    /**
     * Tạo một card thống kê với thiết kế custom:
     * - Nền trắng bo góc (rounded rectangle)
     * - Viền accent bên trái (left border) với màu sắc riêng
     *
     * @param title      tiêu đề card (VD: "Sản phẩm")
     * @param countLabel label hiển thị con số
     * @param accentColor màu accent của viền trái
     * @return JPanel card đã hoàn chỉnh
     */
    private JPanel createCard(String title, JLabel countLabel, Color accentColor) {
        // Tạo panel với custom painting (vẽ tùy chỉnh)
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                // Bật anti-aliasing để bo góc mượt mà
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Vẽ nền trắng bo góc (radius 8px)
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);

                // Vẽ viền accent bên trái (rộng 5px)
                g2.setColor(accentColor);
                g2.fillRoundRect(0, 0, 5, getHeight(), 4, 4);

                g2.dispose();
            }
        };
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(250, 150));
        card.setOpaque(false);  // Nền trong suốt (custom painting tự vẽ nền)
        card.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 20));

        // ── Tiêu đề card ──
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(new Color(100, 100, 100));  // Xám nhạt

        // ── Số liệu (font lớn, đậm) ──
        countLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        countLabel.setForeground(new Color(33, 33, 33));

        // Xếp tiêu đề và số liệu theo cột dọc
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);           // Nền trong suốt
        textPanel.add(Box.createVerticalGlue());
        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(15));
        textPanel.add(countLabel);
        textPanel.add(Box.createVerticalGlue());

        card.add(textPanel, BorderLayout.CENTER);
        return card;
    }

    /**
     * Refresh (tải lại) dữ liệu thống kê từ CSDL.
     *
     * Sử dụng SwingWorker để thực hiện truy vấn CSDL trên background thread,
     * tránh block EDT (Event Dispatch Thread) khiến giao diện bị đơ/lag.
     *
     * Luồng hoạt động:
     * 1. doInBackground() – chạy trên worker thread → gọi controller đếm
     * 2. done() – chạy trên EDT → cập nhật label hiển thị
     */
    public void refreshData() {
        SwingWorker<long[], Void> worker = new SwingWorker<>() {
            @Override
            protected long[] doInBackground() {
                try {
                    // Đếm số lượng từ CSDL (chạy trên background thread)
                    long products  = productController.countProducts();
                    long customers = customerController.countCustomers();
                    long orders    = orderController.countOrders();
                    return new long[]{products, customers, orders};
                } catch (Exception e) {
                    e.printStackTrace();
                    return new long[]{0, 0, 0};  // Trả về 0 nếu lỗi
                }
            }

            @Override
            protected void done() {
                try {
                    // Cập nhật UI trên EDT (thread-safe)
                    long[] counts = get();  // Lấy kết quả từ doInBackground
                    lblProductCount.setText(String.valueOf(counts[0]));
                    lblCustomerCount.setText(String.valueOf(counts[1]));
                    lblOrderCount.setText(String.valueOf(counts[2]));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();  // Khởi chạy worker
    }
}
