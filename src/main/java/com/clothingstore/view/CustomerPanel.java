package com.clothingstore.view;

import com.clothingstore.controller.CustomerController;
import com.clothingstore.entity.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

/**
 * CustomerPanel – Panel quản lý khách hàng (CRUD).
 *
 * Giao diện gồm:
 * - Header: tiêu đề + toolbar (các nút Thêm, Sửa, Xóa, Tải lại)
 * - Bảng JTable: hiển thị danh sách khách hàng
 * - Dialog JOptionPane: form thêm/sửa khách hàng
 *
 * Dữ liệu được tải bất đồng bộ qua SwingWorker.
 */
public class CustomerPanel extends JPanel {

    // ── Hằng số màu sắc ──
    private static final Color BG_COLOR      = new Color(240, 240, 240);  // Nền panel
    private static final Color HEADER_COLOR  = new Color(52, 168, 83);    // Màu header table (xanh lá)
    private static final Color BTN_ADD       = new Color(52, 168, 83);    // Nút Thêm (xanh lá)
    private static final Color BTN_EDIT      = new Color(251, 188, 4);    // Nút Sửa (vàng)
    private static final Color BTN_DELETE    = new Color(234, 67, 53);    // Nút Xóa (đỏ)

    /** Bảng dữ liệu hiển thị danh sách khách hàng */
    private JTable table;

    /** Model dữ liệu của bảng – quản lý dữ liệu hiển thị trong JTable */
    private DefaultTableModel tableModel;

    /** Controller xử lý nghiệp vụ khách hàng */
    private final CustomerController controller = new CustomerController();

    public CustomerPanel() {
        setLayout(new BorderLayout());
        setBackground(BG_COLOR);
        initComponents();
    }

    /**
     * Khởi tạo tất cả component: header, toolbar, table, event listeners.
     */
    private void initComponents() {
        // ═══════════════ HEADER + TOOLBAR ═══════════════
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        // Tiêu đề panel
        JLabel title = new JLabel("Quản lý khách hàng");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(33, 33, 33));
        headerPanel.add(title, BorderLayout.WEST);

        // Toolbar chứa các nút thao tác
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        toolbar.setBackground(BG_COLOR);

        JButton btnAdd    = createStyledButton("+ Thêm", BTN_ADD);
        JButton btnEdit   = createStyledButton("✎ Sửa", BTN_EDIT);
        JButton btnDelete = createStyledButton("✕ Xóa", BTN_DELETE);
        JButton btnRefresh= createStyledButton("↻ Tải lại", HEADER_COLOR);

        toolbar.add(btnAdd);
        toolbar.add(btnEdit);
        toolbar.add(btnDelete);
        toolbar.add(btnRefresh);
        headerPanel.add(toolbar, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // ═══════════════ TABLE (Bảng dữ liệu) ═══════════════
        String[] columns = {"ID", "Họ tên", "Điện thoại", "Email", "Địa chỉ"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            // Không cho phép sửa trực tiếp trên bảng – phải dùng dialog
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(35);                                   // Chiều cao mỗi dòng
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(200, 235, 210));   // Màu dòng được chọn
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(new Color(220, 220, 220));              // Đường kẻ bảng
        table.setShowGrid(true);

        // ── Custom renderer cho header bảng ──
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setPreferredSize(new Dimension(0, 40));
        tableHeader.setReorderingAllowed(false);  // Không cho kéo đổi cột
        tableHeader.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
                lbl.setBackground(HEADER_COLOR);                         // Nền xanh lá
                lbl.setForeground(Color.WHITE);                          // Chữ trắng
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
                lbl.setHorizontalAlignment(SwingConstants.CENTER);       // Căn giữa
                lbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(30, 140, 60)));
                lbl.setOpaque(true);
                return lbl;
            }
        });

        // Căn giữa cột ID
        DefaultTableCellRenderer centerR = new DefaultTableCellRenderer();
        centerR.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerR);

        // Thiết lập chiều rộng ưu tiên cho từng cột
        table.getColumnModel().getColumn(0).setPreferredWidth(50);   // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(150);  // Họ tên
        table.getColumnModel().getColumn(2).setPreferredWidth(120);  // Điện thoại
        table.getColumnModel().getColumn(3).setPreferredWidth(150);  // Email
        table.getColumnModel().getColumn(4).setPreferredWidth(200);  // Địa chỉ

        // Đặt bảng trong ScrollPane để có thanh cuộn khi nhiều dữ liệu
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        // ═══════════════ EVENT LISTENERS ═══════════════
        btnAdd.addActionListener(e -> showAddDialog());       // Nút Thêm → mở dialog thêm
        btnEdit.addActionListener(e -> showEditDialog());     // Nút Sửa → mở dialog sửa
        btnDelete.addActionListener(e -> deleteSelected());   // Nút Xóa → xóa đã chọn
        btnRefresh.addActionListener(e -> loadData());        // Nút Tải lại → refresh bảng

        loadData();  // Tải dữ liệu lần đầu
    }

    /**
     * Tạo nút phong cách thống nhất cho toolbar.
     * @param text    văn bản trên nút
     * @param bgColor màu nền nút
     * @return JButton đã được style
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(100, 35));
        return button;
    }

    /**
     * Tải dữ liệu khách hàng từ CSDL và hiển thị lên bảng.
     * Sử dụng SwingWorker để không block EDT.
     */
    public void loadData() {
        SwingWorker<List<Customer>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Customer> doInBackground() {
                return controller.getAllCustomers();  // Truy vấn trên background thread
            }

            @Override
            protected void done() {
                try {
                    List<Customer> list = get();
                    tableModel.setRowCount(0);  // Xóa dữ liệu cũ
                    for (Customer c : list) {
                        // Thêm từng dòng khách hàng vào bảng
                        tableModel.addRow(new Object[]{
                            c.getId(),
                            c.getFullName(),
                            c.getPhone(),
                            c.getEmail(),
                            c.getAddress()
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    /**
     * Hiển thị dialog thêm khách hàng mới.
     * Sử dụng JOptionPane.showConfirmDialog với panel form tùy chỉnh.
     */
    private void showAddDialog() {
        JPanel panel = createFormPanel(null);  // Tạo form rỗng (không có giá trị ban đầu)
        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm khách hàng",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            // Lấy giá trị từ các text field trong form
            JTextField tfName    = (JTextField) ((JPanel)panel.getComponent(0)).getComponent(1);
            JTextField tfPhone   = (JTextField) ((JPanel)panel.getComponent(1)).getComponent(1);
            JTextField tfEmail   = (JTextField) ((JPanel)panel.getComponent(2)).getComponent(1);
            JTextField tfAddress = (JTextField) ((JPanel)panel.getComponent(3)).getComponent(1);

            String name = tfName.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập họ tên!");
                return;
            }

            // Gọi Controller để thêm vào CSDL
            controller.addCustomer(name, tfPhone.getText().trim(),
                    tfEmail.getText().trim(), tfAddress.getText().trim());
            loadData();  // Refresh bảng
            JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
        }
    }

    /**
     * Hiển thị dialog sửa khách hàng đã chọn.
     * Lấy dữ liệu hiện tại từ dòng đang chọn trong bảng, điền vào form.
     */
    private void showEditDialog() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần sửa!");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);  // Lấy ID từ cột đầu tiên

        // Tạo form với giá trị hiện tại của dòng đang chọn
        JPanel panel = createFormPanel(new String[]{
            (String) tableModel.getValueAt(row, 1),                                          // Họ tên
            tableModel.getValueAt(row, 2) != null ? tableModel.getValueAt(row, 2).toString() : "", // SĐT
            tableModel.getValueAt(row, 3) != null ? tableModel.getValueAt(row, 3).toString() : "", // Email
            tableModel.getValueAt(row, 4) != null ? tableModel.getValueAt(row, 4).toString() : ""  // Địa chỉ
        });

        int result = JOptionPane.showConfirmDialog(this, panel, "Sửa khách hàng",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            JTextField tfName    = (JTextField) ((JPanel)panel.getComponent(0)).getComponent(1);
            JTextField tfPhone   = (JTextField) ((JPanel)panel.getComponent(1)).getComponent(1);
            JTextField tfEmail   = (JTextField) ((JPanel)panel.getComponent(2)).getComponent(1);
            JTextField tfAddress = (JTextField) ((JPanel)panel.getComponent(3)).getComponent(1);

            controller.updateCustomer(id, tfName.getText().trim(), tfPhone.getText().trim(),
                    tfEmail.getText().trim(), tfAddress.getText().trim());
            loadData();
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
        }
    }

    /**
     * Xóa khách hàng đang chọn trong bảng.
     * Hiển thị dialog xác nhận trước khi xóa.
     */
    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần xóa!");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        String name = (String) tableModel.getValueAt(row, 1);

        // Dialog xác nhận xóa
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa \"" + name + "\"?", "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            controller.deleteCustomer(id);
            loadData();
            JOptionPane.showMessageDialog(this, "Xóa thành công!");
        }
    }

    /**
     * Tạo panel form nhập liệu cho dialog thêm/sửa khách hàng.
     *
     * @param values giá trị ban đầu [hoTen, sdt, email, diaChi], null nếu thêm mới
     * @return JPanel chứa các trường nhập liệu
     */
    private JPanel createFormPanel(String[] values) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(400, 220));

        // Tạo các text field, điền giá trị nếu đang sửa
        JTextField tfName    = new JTextField(values != null ? values[0] : "", 25);
        JTextField tfPhone   = new JTextField(values != null ? values[1] : "", 25);
        JTextField tfEmail   = new JTextField(values != null ? values[2] : "", 25);
        JTextField tfAddress = new JTextField(values != null ? values[3] : "", 25);

        panel.add(createFieldRow("Họ tên:", tfName));
        panel.add(createFieldRow("Điện thoại:", tfPhone));
        panel.add(createFieldRow("Email:", tfEmail));
        panel.add(createFieldRow("Địa chỉ:", tfAddress));

        return panel;
    }

    /**
     * Tạo một dòng trường nhập liệu: [Label | TextField].
     *
     * @param label nhãn bên trái (VD: "Họ tên:")
     * @param field component nhập liệu bên phải
     * @return JPanel dòng trường
     */
    private JPanel createFieldRow(String label, JComponent field) {
        JPanel row = new JPanel(new BorderLayout(10, 5));
        row.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setPreferredSize(new Dimension(100, 30));
        row.add(lbl, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        return row;
    }
}
