package com.clothingstore.view;

import com.clothingstore.controller.OrderController;
import com.clothingstore.entity.Customer;
import com.clothingstore.entity.Employee;
import com.clothingstore.entity.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * OrderPanel – Quản lý đơn hàng.
 */
public class OrderPanel extends JPanel {

    private static final Color BG_COLOR      = new Color(240, 240, 240);
    private static final Color HEADER_COLOR  = new Color(234, 67, 53);
    private static final Color BTN_ADD       = new Color(52, 168, 83);
    private static final Color BTN_EDIT      = new Color(251, 188, 4);
    private static final Color BTN_DELETE    = new Color(234, 67, 53);

    private JTable table;
    private DefaultTableModel tableModel;
    private final OrderController controller = new OrderController();
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public OrderPanel() {
        setLayout(new BorderLayout());
        setBackground(BG_COLOR);
        initComponents();
    }

    private void initComponents() {
        // ═══════════════ HEADER ═══════════════
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        JLabel title = new JLabel("Quản lý đơn hàng");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(33, 33, 33));
        headerPanel.add(title, BorderLayout.WEST);

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        toolbar.setBackground(BG_COLOR);

        JButton btnAdd    = createStyledButton("+ Thêm", BTN_ADD);
        JButton btnEdit   = createStyledButton("✎ Sửa", BTN_EDIT);
        JButton btnDelete = createStyledButton("✕ Xóa", BTN_DELETE);
        JButton btnRefresh= createStyledButton("↻ Tải lại", new Color(66, 133, 244));

        toolbar.add(btnAdd);
        toolbar.add(btnEdit);
        toolbar.add(btnDelete);
        toolbar.add(btnRefresh);
        headerPanel.add(toolbar, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // ═══════════════ TABLE ═══════════════
        String[] columns = {"ID", "Khách hàng", "Nhân viên", "Ngày đặt", "Tổng tiền", "Trạng thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(255, 210, 210));
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(new Color(220, 220, 220));
        table.setShowGrid(true);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setPreferredSize(new Dimension(0, 40));
        tableHeader.setReorderingAllowed(false);
        tableHeader.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
                lbl.setBackground(HEADER_COLOR);
                lbl.setForeground(Color.WHITE);
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                lbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(200, 50, 40)));
                lbl.setOpaque(true);
                return lbl;
            }
        });

        DefaultTableCellRenderer centerR = new DefaultTableCellRenderer();
        centerR.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerR);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(140);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(130);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);

        // Status column renderer – color-coded
        table.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val, boolean sel,
                                                           boolean focus, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, focus, row, col);
                setHorizontalAlignment(SwingConstants.CENTER);
                String status = val != null ? val.toString() : "";
                switch (status) {
                    case "DELIVERED" -> { setForeground(new Color(52, 168, 83)); setFont(getFont().deriveFont(Font.BOLD)); }
                    case "CANCELLED" -> { setForeground(new Color(234, 67, 53)); setFont(getFont().deriveFont(Font.BOLD)); }
                    case "SHIPPING"  -> { setForeground(new Color(66, 133, 244)); setFont(getFont().deriveFont(Font.BOLD)); }
                    case "CONFIRMED" -> { setForeground(new Color(251, 188, 4)); setFont(getFont().deriveFont(Font.BOLD)); }
                    default          -> { setForeground(new Color(100, 100, 100)); }
                }
                return this;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        // ═══════════════ EVENT ═══════════════
        btnAdd.addActionListener(e -> showAddDialog());
        btnEdit.addActionListener(e -> showEditDialog());
        btnDelete.addActionListener(e -> deleteSelected());
        btnRefresh.addActionListener(e -> loadData());

        loadData();
    }

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

    public void loadData() {
        SwingWorker<List<Order>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Order> doInBackground() {
                return controller.getAllOrders();
            }

            @Override
            protected void done() {
                try {
                    List<Order> orders = get();
                    tableModel.setRowCount(0);
                    NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
                    for (Order o : orders) {
                        tableModel.addRow(new Object[]{
                            o.getId(),
                            o.getCustomer() != null ? o.getCustomer().getFullName() : "",
                            o.getEmployee() != null ? o.getEmployee().getFullName() : "",
                            o.getOrderDate() != null ? o.getOrderDate().format(DTF) : "",
                            nf.format(o.getTotalAmount()) + " đ",
                            o.getStatus() != null ? o.getStatus().name() : ""
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    private void showAddDialog() {
        JPanel panel = createFormPanel(null);
        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm đơn hàng",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                JComboBox<Customer> cbCus   = (JComboBox<Customer>) ((JPanel) panel.getComponent(0)).getComponent(1);
                JComboBox<Employee> cbEmp   = (JComboBox<Employee>) ((JPanel) panel.getComponent(1)).getComponent(1);
                JTextField tfTotal          = (JTextField) ((JPanel) panel.getComponent(2)).getComponent(1);
                JTextField tfDiscount       = (JTextField) ((JPanel) panel.getComponent(3)).getComponent(1);
                JComboBox<String> cbStatus  = (JComboBox<String>) ((JPanel) panel.getComponent(4)).getComponent(1);
                JTextField tfNote           = (JTextField) ((JPanel) panel.getComponent(5)).getComponent(1);

                Customer cus = (Customer) cbCus.getSelectedItem();
                Employee emp = (Employee) cbEmp.getSelectedItem();
                BigDecimal total = tfTotal.getText().trim().isEmpty() ? BigDecimal.ZERO : new BigDecimal(tfTotal.getText().trim());
                BigDecimal discount = tfDiscount.getText().trim().isEmpty() ? BigDecimal.ZERO : new BigDecimal(tfDiscount.getText().trim());
                String status = (String) cbStatus.getSelectedItem();

                controller.addOrder(
                    cus != null ? cus.getId() : null,
                    emp != null ? emp.getId() : null,
                    total, discount, status, tfNote.getText().trim()
                );
                loadData();
                JOptionPane.showMessageDialog(this, "Thêm đơn hàng thành công!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số tiền không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showEditDialog() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đơn hàng cần sửa!");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        String customerName = tableModel.getValueAt(row, 1) != null ? tableModel.getValueAt(row, 1).toString() : "";
        String employeeName = tableModel.getValueAt(row, 2) != null ? tableModel.getValueAt(row, 2).toString() : "";
        String totalStr = tableModel.getValueAt(row, 4).toString().replace(" đ", "").replace(".", "").replace(",", "");
        String statusStr = tableModel.getValueAt(row, 5) != null ? tableModel.getValueAt(row, 5).toString() : "PENDING";

        JPanel panel = createFormPanel(new String[]{customerName, employeeName, totalStr, "0", statusStr, ""});
        int result = JOptionPane.showConfirmDialog(this, panel, "Sửa đơn hàng",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                JComboBox<Customer> cbCus   = (JComboBox<Customer>) ((JPanel) panel.getComponent(0)).getComponent(1);
                JComboBox<Employee> cbEmp   = (JComboBox<Employee>) ((JPanel) panel.getComponent(1)).getComponent(1);
                JTextField tfTotal          = (JTextField) ((JPanel) panel.getComponent(2)).getComponent(1);
                JTextField tfDiscount       = (JTextField) ((JPanel) panel.getComponent(3)).getComponent(1);
                JComboBox<String> cbStatus  = (JComboBox<String>) ((JPanel) panel.getComponent(4)).getComponent(1);
                JTextField tfNote           = (JTextField) ((JPanel) panel.getComponent(5)).getComponent(1);

                Customer cus = (Customer) cbCus.getSelectedItem();
                Employee emp = (Employee) cbEmp.getSelectedItem();
                BigDecimal total = tfTotal.getText().trim().isEmpty() ? BigDecimal.ZERO : new BigDecimal(tfTotal.getText().trim());
                BigDecimal discount = tfDiscount.getText().trim().isEmpty() ? BigDecimal.ZERO : new BigDecimal(tfDiscount.getText().trim());
                String status = (String) cbStatus.getSelectedItem();

                controller.updateOrder(id,
                    cus != null ? cus.getId() : null,
                    emp != null ? emp.getId() : null,
                    total, discount, status, tfNote.getText().trim()
                );
                loadData();
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số tiền không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đơn hàng cần xóa!");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa đơn hàng #" + id + "?", "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            controller.deleteOrder(id);
            loadData();
            JOptionPane.showMessageDialog(this, "Xóa thành công!");
        }
    }

    private JPanel createFormPanel(String[] values) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(420, 340));

        JComboBox<Customer> cbCus = new JComboBox<>();
        JComboBox<Employee> cbEmp = new JComboBox<>();
        cbCus.addItem(null);
        cbEmp.addItem(null);

        List<Customer> customers = controller.getAllCustomers();
        for (Customer c : customers) cbCus.addItem(c);

        List<Employee> employees = controller.getAllEmployees();
        for (Employee e : employees) cbEmp.addItem(e);

        JTextField tfTotal    = new JTextField(values != null ? values[2] : "", 20);
        JTextField tfDiscount = new JTextField(values != null ? values[3] : "0", 20);
        JTextField tfNote     = new JTextField(values != null ? values[5] : "", 20);

        String[] statuses = {"PENDING", "CONFIRMED", "SHIPPING", "DELIVERED", "CANCELLED"};
        JComboBox<String> cbStatus = new JComboBox<>(statuses);

        // Pre-select if editing
        if (values != null) {
            for (int i = 0; i < cbCus.getItemCount(); i++) {
                Customer c = cbCus.getItemAt(i);
                if (c != null && c.getFullName().equals(values[0])) {
                    cbCus.setSelectedIndex(i);
                    break;
                }
            }
            for (int i = 0; i < cbEmp.getItemCount(); i++) {
                Employee e = cbEmp.getItemAt(i);
                if (e != null && e.getFullName().equals(values[1])) {
                    cbEmp.setSelectedIndex(i);
                    break;
                }
            }
            cbStatus.setSelectedItem(values[4]);
        }

        panel.add(createFieldRow("Khách hàng:", cbCus));
        panel.add(createFieldRow("Nhân viên:", cbEmp));
        panel.add(createFieldRow("Tổng tiền:", tfTotal));
        panel.add(createFieldRow("Giảm giá:", tfDiscount));
        panel.add(createFieldRow("Trạng thái:", cbStatus));
        panel.add(createFieldRow("Ghi chú:", tfNote));

        return panel;
    }

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
