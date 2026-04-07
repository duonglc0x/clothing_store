package com.clothingstore.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * MainFrame – Khung chính (JFrame) của ứng dụng Quản lý Cửa hàng Quần áo.
 *
 * Thiết kế giao diện gồm 2 phần chính:
 * - Sidebar (bên trái): thanh điều hướng menu tối màu, chứa các nút chuyển panel
 * - Content Area (bên phải): vùng hiển thị nội dung, sử dụng CardLayout
 *   để chuyển đổi giữa các panel con (Dashboard, Product, Customer, Order, Employee)
 *
 * Mẫu thiết kế: CardLayout cho phép "xếp chồng" nhiều panel lên nhau,
 * chỉ hiển thị một panel tại một thời điểm (giống tab browser).
 */
public class MainFrame extends JFrame {

    // ═══════════════════════════════════════════════════════════════════════════
    // HẰNG SỐ MÀU SẮC – Định nghĩa palette màu cho sidebar
    // ═══════════════════════════════════════════════════════════════════════════
    private static final Color SIDEBAR_BG    = new Color(45, 45, 45);    // Nền sidebar (xám đậm)
    private static final Color SIDEBAR_HOVER = new Color(60, 60, 60);    // Màu khi hover chuột
    private static final Color SIDEBAR_ACTIVE = new Color(70, 70, 70);   // Màu nút đang active
    private static final Color CONTENT_BG    = new Color(240, 240, 240); // Nền content area (xám nhạt)
    private static final Color TEXT_WHITE     = new Color(220, 220, 220); // Màu chữ sidebar
    private static final Color ACCENT_BLUE   = new Color(66, 133, 244);  // Màu nhấn (Google Blue)

    /** Panel chứa nội dung chính – sử dụng CardLayout để chuyển đổi */
    private JPanel contentPanel;

    /** Layout quản lý việc chuyển đổi giữa các panel con */
    private CardLayout cardLayout;

    /** Nút sidebar đang được kích hoạt (active) – dùng để quản lý hiệu ứng visual */
    private JButton activeButton;

    // ── Các panel con (mỗi panel ứng với một chức năng) ──
    private DashboardPanel dashboardPanel;  // Tổng quan hệ thống
    private ProductPanel productPanel;      // Quản lý sản phẩm
    private CustomerPanel customerPanel;    // Quản lý khách hàng
    private OrderPanel orderPanel;          // Quản lý đơn hàng
    private EmployeePanel employeePanel;    // Quản lý nhân viên

    /**
     * Constructor – thiết lập cấu hình cửa sổ và khởi tạo giao diện.
     */
    public MainFrame() {
        setTitle("Hệ thống quản lý cửa hàng quần áo");  // Tiêu đề cửa sổ
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // Thoát ứng dụng khi đóng cửa sổ
        setSize(1100, 680);                                // Kích thước ban đầu
        setLocationRelativeTo(null);                       // Canh giữa màn hình
        setMinimumSize(new Dimension(900, 600));           // Kích thước tối thiểu

        initComponents();  // Khởi tạo tất cả component giao diện
    }

    /**
     * Khởi tạo và sắp xếp tất cả các component giao diện.
     * Gồm: Sidebar (menu bên trái) và Content Area (nội dung bên phải).
     */
    private void initComponents() {
        // ═══════════════ SIDEBAR (Thanh điều hướng bên trái) ═══════════════
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));  // Xếp dọc từ trên xuống
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setPreferredSize(new Dimension(190, 0));  // Chiều rộng cố định 190px
        sidebar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // ── Logo / Tiêu đề ứng dụng ──
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setBackground(SIDEBAR_BG);
        logoPanel.setMaximumSize(new Dimension(190, 80));
        logoPanel.setPreferredSize(new Dimension(190, 80));

        JLabel logoLabel = new JLabel("CLOTHING STORE");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        logoLabel.setForeground(TEXT_WHITE);
        logoPanel.add(logoLabel);
        sidebar.add(logoPanel);

        // ── Đường phân cách ngăn logo và menu ──
        JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
        sep.setForeground(new Color(80, 80, 80));
        sep.setMaximumSize(new Dimension(190, 2));
        sidebar.add(sep);
        sidebar.add(Box.createVerticalStrut(10));  // Khoảng cách 10px

        // ── Các nút menu sidebar ──
        // Mỗi nút liên kết với một "card key" tương ứng với panel trong CardLayout
        JButton btnDashboard = createSidebarButton("  Dashboard", "DASHBOARD");
        JButton btnProducts  = createSidebarButton("  Sản phẩm", "PRODUCTS");
        JButton btnCustomers = createSidebarButton("  Khách hàng", "CUSTOMERS");
        JButton btnOrders    = createSidebarButton("  Đơn hàng", "ORDERS");
        JButton btnEmployees = createSidebarButton("  Nhân viên", "EMPLOYEES");

        // Thêm nút vào sidebar với khoảng cách 2px giữa các nút
        sidebar.add(btnDashboard);
        sidebar.add(Box.createVerticalStrut(2));
        sidebar.add(btnProducts);
        sidebar.add(Box.createVerticalStrut(2));
        sidebar.add(btnCustomers);
        sidebar.add(Box.createVerticalStrut(2));
        sidebar.add(btnOrders);
        sidebar.add(Box.createVerticalStrut(2));
        sidebar.add(btnEmployees);
        sidebar.add(Box.createVerticalGlue());  // Đẩy các nút lên trên, phần dưới co giãn

        // ═══════════════ CONTENT AREA (Vùng nội dung chính) ═══════════════
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);  // Sử dụng CardLayout
        contentPanel.setBackground(CONTENT_BG);

        // Khởi tạo các panel con
        dashboardPanel = new DashboardPanel();
        productPanel   = new ProductPanel();
        customerPanel  = new CustomerPanel();
        orderPanel     = new OrderPanel();
        employeePanel  = new EmployeePanel();

        // Thêm panel vào CardLayout với key tương ứng
        contentPanel.add(dashboardPanel, "DASHBOARD");
        contentPanel.add(productPanel,   "PRODUCTS");
        contentPanel.add(customerPanel,  "CUSTOMERS");
        contentPanel.add(orderPanel,     "ORDERS");
        contentPanel.add(employeePanel,  "EMPLOYEES");

        // ═══════════════ LAYOUT CHÍNH ═══════════════
        setLayout(new BorderLayout());
        add(sidebar, BorderLayout.WEST);       // Sidebar bên trái
        add(contentPanel, BorderLayout.CENTER); // Content area ở giữa (chiếm phần còn lại)

        // Mặc định hiển thị Dashboard khi mở ứng dụng
        setActiveButton(btnDashboard);
        cardLayout.show(contentPanel, "DASHBOARD");
    }

    /**
     * Tạo một nút sidebar với style thống nhất và sự kiện click.
     *
     * @param text    văn bản hiển thị trên nút (có 2 dấu cách đầu cho padding)
     * @param cardKey key của panel trong CardLayout (VD: "DASHBOARD", "PRODUCTS")
     * @return JButton đã được style và gắn sự kiện
     */
    private JButton createSidebarButton(String text, String cardKey) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(TEXT_WHITE);
        button.setBackground(SIDEBAR_BG);
        button.setMaximumSize(new Dimension(190, 45));
        button.setPreferredSize(new Dimension(190, 45));
        button.setHorizontalAlignment(SwingConstants.LEFT);  // Căn trái văn bản
        button.setBorderPainted(false);    // Không vẽ viền
        button.setFocusPainted(false);     // Không vẽ viền focus
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));  // Con trỏ tay khi hover

        // ── Hiệu ứng Hover (rê chuột) ──
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Chỉ đổi màu nếu nút KHÔNG phải đang active
                if (button != activeButton) {
                    button.setBackground(SIDEBAR_HOVER);
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                // Trả về màu gốc khi rời chuột (nếu không active)
                if (button != activeButton) {
                    button.setBackground(SIDEBAR_BG);
                }
            }
        });

        // ── Sự kiện Click – chuyển panel ──
        button.addActionListener(e -> {
            setActiveButton(button);                    // Đánh dấu nút active
            cardLayout.show(contentPanel, cardKey);      // Chuyển panel trong CardLayout

            // Refresh dữ liệu khi chuyển panel để hiển thị thông tin mới nhất
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

    /**
     * Đánh dấu nút sidebar đang active (được chọn).
     * Đổi màu nền nút cũ về bình thường, nút mới sang màu active.
     *
     * @param button nút cần đánh dấu active
     */
    private void setActiveButton(JButton button) {
        // Trả nút cũ về màu bình thường
        if (activeButton != null) {
            activeButton.setBackground(SIDEBAR_BG);
        }
        // Gán nút mới là active và đổi màu nền
        activeButton = button;
        activeButton.setBackground(SIDEBAR_ACTIVE);
    }
}
