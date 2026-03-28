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
 * CustomerPanel – Quản lý khách hàng.
 */
public class CustomerPanel extends JPanel {

    private static final Color BG_COLOR      = new Color(240, 240, 240);
    private static final Color HEADER_COLOR  = new Color(52, 168, 83);
    private static final Color BTN_ADD       = new Color(52, 168, 83);
    private static final Color BTN_EDIT      = new Color(251, 188, 4);
    private static final Color BTN_DELETE    = new Color(234, 67, 53);

    private JTable table;
    private DefaultTableModel tableModel;
    private final CustomerController controller = new CustomerController();

    public CustomerPanel() {
        setLayout(new BorderLayout());
        setBackground(BG_COLOR);
        initComponents();
    }

    private void initComponents() {
        // ═══════════════ HEADER ═══════════════
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        JLabel title = new JLabel("Quản lý khách hàng");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(33, 33, 33));
        headerPanel.add(title, BorderLayout.WEST);

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

        // ═══════════════ TABLE ═══════════════
        String[] columns = {"ID", "Họ tên", "Điện thoại", "Email", "Địa chỉ"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(200, 235, 210));
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
                lbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(30, 140, 60)));
                lbl.setOpaque(true);
                return lbl;
            }
        });

        DefaultTableCellRenderer centerR = new DefaultTableCellRenderer();
        centerR.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerR);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(200);

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
        SwingWorker<List<Customer>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Customer> doInBackground() {
                return controller.getAllCustomers();
            }

            @Override
            protected void done() {
                try {
                    List<Customer> list = get();
                    tableModel.setRowCount(0);
                    for (Customer c : list) {
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

    private void showAddDialog() {
        JPanel panel = createFormPanel(null);
        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm khách hàng",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            JTextField tfName    = (JTextField) ((JPanel)panel.getComponent(0)).getComponent(1);
            JTextField tfPhone   = (JTextField) ((JPanel)panel.getComponent(1)).getComponent(1);
            JTextField tfEmail   = (JTextField) ((JPanel)panel.getComponent(2)).getComponent(1);
            JTextField tfAddress = (JTextField) ((JPanel)panel.getComponent(3)).getComponent(1);

            String name = tfName.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập họ tên!");
                return;
            }

            controller.addCustomer(name, tfPhone.getText().trim(),
                    tfEmail.getText().trim(), tfAddress.getText().trim());
            loadData();
            JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
        }
    }

    private void showEditDialog() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần sửa!");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);

        JPanel panel = createFormPanel(new String[]{
            (String) tableModel.getValueAt(row, 1),
            tableModel.getValueAt(row, 2) != null ? tableModel.getValueAt(row, 2).toString() : "",
            tableModel.getValueAt(row, 3) != null ? tableModel.getValueAt(row, 3).toString() : "",
            tableModel.getValueAt(row, 4) != null ? tableModel.getValueAt(row, 4).toString() : ""
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

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần xóa!");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        String name = (String) tableModel.getValueAt(row, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa \"" + name + "\"?", "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            controller.deleteCustomer(id);
            loadData();
            JOptionPane.showMessageDialog(this, "Xóa thành công!");
        }
    }

    private JPanel createFormPanel(String[] values) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(400, 220));

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
