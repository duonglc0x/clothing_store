package com.clothingstore.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * MainFrame – Khung chính của ứng dụng.
 * Sidebar navigation tối bên trái + Content panel bên phải.
 */
public class MainFrame extends JFrame {

    private static final Color SIDEBAR_BG    = new Color(45, 45, 45);
    private static final Color SIDEBAR_HOVER = new Color(60, 60, 60);
    private static final Color SIDEBAR_ACTIVE = new Color(70, 70, 70);
    private static final Color CONTENT_BG    = new Color(240, 240, 240);
    private static final Color TEXT_WHITE     = new Color(220, 220, 220);
    private static final Color ACCENT_BLUE   = new Color(66, 133, 244);

    private JPanel contentPanel;
    private CardLayout cardLayout;
    private JButton activeButton;

    // Panels
    private DashboardPanel dashboardPanel;
    private ProductPanel productPanel;
    private CustomerPanel customerPanel;
    private OrderPanel orderPanel;
    private EmployeePanel employeePanel;

    public MainFrame() {
        setTitle("Hệ thống quản lý cửa hàng quần áo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 680);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(900, 600));

        initComponents();
    }

    private void initComponents() {
        // ═══════════════ SIDEBAR ═══════════════
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setPreferredSize(new Dimension(190, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Logo / Title
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setBackground(SIDEBAR_BG);
        logoPanel.setMaximumSize(new Dimension(190, 80));
        logoPanel.setPreferredSize(new Dimension(190, 80));

        JLabel logoLabel = new JLabel("CLOTHING STORE");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        logoLabel.setForeground(TEXT_WHITE);
        logoPanel.add(logoLabel);
        sidebar.add(logoPanel);

        // Separator
        JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
        sep.setForeground(new Color(80, 80, 80));
        sep.setMaximumSize(new Dimension(190, 2));
        sidebar.add(sep);
        sidebar.add(Box.createVerticalStrut(10));

        // Menu Buttons
        JButton btnDashboard = createSidebarButton("  Dashboard", "DASHBOARD");
        JButton btnProducts  = createSidebarButton("  Sản phẩm", "PRODUCTS");
        JButton btnCustomers = createSidebarButton("  Khách hàng", "CUSTOMERS");
        JButton btnOrders    = createSidebarButton("  Đơn hàng", "ORDERS");
        JButton btnEmployees = createSidebarButton("  Nhân viên", "EMPLOYEES");

        sidebar.add(btnDashboard);
        sidebar.add(Box.createVerticalStrut(2));
        sidebar.add(btnProducts);
        sidebar.add(Box.createVerticalStrut(2));
        sidebar.add(btnCustomers);
        sidebar.add(Box.createVerticalStrut(2));
        sidebar.add(btnOrders);
        sidebar.add(Box.createVerticalStrut(2));
        sidebar.add(btnEmployees);
        sidebar.add(Box.createVerticalGlue());

        // ═══════════════ CONTENT AREA ═══════════════
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(CONTENT_BG);

        dashboardPanel = new DashboardPanel();
        productPanel   = new ProductPanel();
        customerPanel  = new CustomerPanel();
        orderPanel     = new OrderPanel();
        employeePanel  = new EmployeePanel();

        contentPanel.add(dashboardPanel, "DASHBOARD");
        contentPanel.add(productPanel,   "PRODUCTS");
        contentPanel.add(customerPanel,  "CUSTOMERS");
        contentPanel.add(orderPanel,     "ORDERS");
        contentPanel.add(employeePanel,  "EMPLOYEES");

        // ═══════════════ LAYOUT ═══════════════
        setLayout(new BorderLayout());
        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        // Set Dashboard as default active
        setActiveButton(btnDashboard);
        cardLayout.show(contentPanel, "DASHBOARD");
    }

    private JButton createSidebarButton(String text, String cardKey) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(TEXT_WHITE);
        button.setBackground(SIDEBAR_BG);
        button.setMaximumSize(new Dimension(190, 45));
        button.setPreferredSize(new Dimension(190, 45));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button != activeButton) {
                    button.setBackground(SIDEBAR_HOVER);
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (button != activeButton) {
                    button.setBackground(SIDEBAR_BG);
                }
            }
        });

        // Click → switch panel
        button.addActionListener(e -> {
            setActiveButton(button);
            cardLayout.show(contentPanel, cardKey);

            // Refresh data khi chuyển panel
            switch (cardKey) {
                case "DASHBOARD" -> dashboardPanel.refreshData();
                case "PRODUCTS"  -> productPanel.loadData();
                case "CUSTOMERS" -> customerPanel.loadData();
                case "ORDERS"    -> orderPanel.loadData();
                case "EMPLOYEES" -> employeePanel.loadData();
            }
        });

        return button;
    }

    private void setActiveButton(JButton button) {
        if (activeButton != null) {
            activeButton.setBackground(SIDEBAR_BG);
        }
        activeButton = button;
        activeButton.setBackground(SIDEBAR_ACTIVE);
    }
}
