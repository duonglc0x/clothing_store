package com.clothingstore.view;

import com.clothingstore.controller.OrderController;
import com.clothingstore.controller.ProductController;
import com.clothingstore.controller.CustomerController;

import javax.swing.*;
import java.awt.*;

/**
 * DashboardPanel – Tổng quan hệ thống.
 * Hiển thị 3 card: Sản phẩm, Khách hàng, Đơn hàng.
 */
public class DashboardPanel extends JPanel {

    private JLabel lblProductCount;
    private JLabel lblCustomerCount;
    private JLabel lblOrderCount;

    private final ProductController productController = new ProductController();
    private final CustomerController customerController = new CustomerController();
    private final OrderController orderController = new OrderController();

    public DashboardPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));
        initComponents();
        refreshData();
    }

    private void initComponents() {
        // ═══════════════ HEADER ═══════════════
        JLabel header = new JLabel("  Tổng quan hệ thống");
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setForeground(new Color(33, 33, 33));
        header.setPreferredSize(new Dimension(0, 70));
        header.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 0));
        add(header, BorderLayout.NORTH);

        // ═══════════════ CARDS AREA ═══════════════
        JPanel cardsWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        cardsWrapper.setBackground(new Color(240, 240, 240));
        cardsWrapper.setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15));

        lblProductCount  = new JLabel("0");
        lblCustomerCount = new JLabel("0");
        lblOrderCount    = new JLabel("0");

        cardsWrapper.add(createCard("Sản phẩm",  lblProductCount,  new Color(66, 133, 244)));   // Blue
        cardsWrapper.add(createCard("Khách hàng", lblCustomerCount, new Color(52, 168, 83)));    // Green
        cardsWrapper.add(createCard("Đơn hàng",   lblOrderCount,    new Color(234, 67, 53)));    // Red

        add(cardsWrapper, BorderLayout.CENTER);
    }

    /**
     * Tạo một card thống kê với border-left màu sắc.
     */
    private JPanel createCard(String title, JLabel countLabel, Color accentColor) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // White background with subtle shadow
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);

                // Left accent border
                g2.setColor(accentColor);
                g2.fillRoundRect(0, 0, 5, getHeight(), 4, 4);

                g2.dispose();
            }
        };
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(250, 150));
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 20));

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(new Color(100, 100, 100));

        // Count
        countLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        countLabel.setForeground(new Color(33, 33, 33));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.add(Box.createVerticalGlue());
        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(15));
        textPanel.add(countLabel);
        textPanel.add(Box.createVerticalGlue());

        card.add(textPanel, BorderLayout.CENTER);
        return card;
    }

    /**
     * Refresh dữ liệu dashboard.
     */
    public void refreshData() {
        SwingWorker<long[], Void> worker = new SwingWorker<>() {
            @Override
            protected long[] doInBackground() {
                try {
                    long products  = productController.countProducts();
                    long customers = customerController.countCustomers();
                    long orders    = orderController.countOrders();
                    return new long[]{products, customers, orders};
                } catch (Exception e) {
                    e.printStackTrace();
                    return new long[]{0, 0, 0};
                }
            }

            @Override
            protected void done() {
                try {
                    long[] counts = get();
                    lblProductCount.setText(String.valueOf(counts[0]));
                    lblCustomerCount.setText(String.valueOf(counts[1]));
                    lblOrderCount.setText(String.valueOf(counts[2]));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }
}
