package com.clothingstore.view;

import com.clothingstore.controller.EmployeeController;
import com.clothingstore.entity.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * EmployeePanel – Panel quản lý nhân viên (CRUD).
 *
 * Cấu trúc tương tự CustomerPanel nhưng có thêm:
 * - Cột Lương: hiển thị định dạng tiền VNĐ (NumberFormat)
 * - Cột Ngày vào: hiển thị định dạng dd/MM/yyyy (DateTimeFormatter)
 * - Cả 2 trường trên cần parse khi sửa
 */
public class EmployeePanel extends JPanel {

    // ── Hằng số màu ──
    private static final Color BG_COLOR      = new Color(240, 240, 240);
    private static final Color HEADER_COLOR  = new Color(103, 58, 183);   // Tím (phân biệt với panel khác)
    private static final Color BTN_ADD       = new Color(52, 168, 83);
    private static final Color BTN_EDIT      = new Color(251, 188, 4);
    private static final Color BTN_DELETE    = new Color(234, 67, 53);

    private JTable table;
    private DefaultTableModel tableModel;
    private final EmployeeController controller = new EmployeeController();

    /** Định dạng ngày dd/MM/yyyy cho hiển thị và parse */
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public EmployeePanel() {
        setLayout(new BorderLayout());
        setBackground(BG_COLOR);
        initComponents();
    }

    /**
     * Khởi tạo component: header, toolbar, table 7 cột, event listeners.
     */
    private void initComponents() {
        // ═══════════════ HEADER + TOOLBAR ═══════════════
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        JLabel title = new JLabel("Quản lý nhân viên");
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
        String[] columns = {"ID", "Họ tên", "Điện thoại", "Email", "Chức vụ", "Lương", "Ngày vào"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(220, 210, 240));  // Tím nhạt khi chọn
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(new Color(220, 220, 220));
        table.setShowGrid(true);

        // Custom renderer cho header
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
                lbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(80, 40, 160)));
                lbl.setOpaque(true);
                return lbl;
            }
        });

        // Căn giữa cột ID và thiết lập chiều rộng cột
        DefaultTableCellRenderer centerR = new DefaultTableCellRenderer();
        centerR.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerR);
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(140);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(140);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(100);

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

    /**
     * Tải dữ liệu nhân viên. Lương được format theo locale VN, ngày theo dd/MM/yyyy.
     */
    public void loadData() {
        SwingWorker<List<Employee>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Employee> doInBackground() {
                return controller.getAllEmployees();
            }

            @Override
            protected void done() {
                try {
                    List<Employee> list = get();
                    tableModel.setRowCount(0);
                    // NumberFormat locale VN: 12000000 → "12.000.000"
                    NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
                    for (Employee e : list) {
                        tableModel.addRow(new Object[]{
                            e.getId(),
                            e.getFullName(),
                            e.getPhone(),
                            e.getEmail(),
                            e.getPosition(),
                            // Format lương: "12.000.000 đ" hoặc "" nếu null
                            e.getSalary() != null ? nf.format(e.getSalary()) + " đ" : "",
                            // Format ngày: "01/03/2024" hoặc "" nếu null
                            e.getHireDate() != null ? e.getHireDate().format(DF) : ""
                        });
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    /**
     * Dialog thêm nhân viên mới.
     * Parse lương (BigDecimal) và ngày (LocalDate) từ text field.
     */
    private void showAddDialog() {
        JPanel panel = createFormPanel(null);
        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm nhân viên",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                // Lấy giá trị từ form – mỗi component là một JPanel chứa [Label, TextField]
                JTextField tfName     = (JTextField) ((JPanel) panel.getComponent(0)).getComponent(1);
                JTextField tfPhone    = (JTextField) ((JPanel) panel.getComponent(1)).getComponent(1);
                JTextField tfEmail    = (JTextField) ((JPanel) panel.getComponent(2)).getComponent(1);
                JTextField tfPosition = (JTextField) ((JPanel) panel.getComponent(3)).getComponent(1);
                JTextField tfSalary   = (JTextField) ((JPanel) panel.getComponent(4)).getComponent(1);
                JTextField tfHireDate = (JTextField) ((JPanel) panel.getComponent(5)).getComponent(1);

                String name = tfName.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập họ tên!");
                    return;
                }

                // Parse lương: rỗng → ZERO, ngược lại → BigDecimal
                BigDecimal salary = tfSalary.getText().trim().isEmpty()
                    ? BigDecimal.ZERO : new BigDecimal(tfSalary.getText().trim());
                // Parse ngày: rỗng → null, ngược lại → LocalDate
                LocalDate hireDate = tfHireDate.getText().trim().isEmpty()
                    ? null : LocalDate.parse(tfHireDate.getText().trim(), DF);

                controller.addEmployee(name, tfPhone.getText().trim(),
                    tfEmail.getText().trim(), tfPosition.getText().trim(), salary, hireDate);
                loadData();
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!\n" + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Dialog sửa nhân viên. Loại bỏ ký tự format (đ, dấu chấm) trước khi điền vào form.
     */
    private void showEditDialog() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần sửa!");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);

        // Điền giá trị hiện tại, loại bỏ ký tự format lương
        JPanel panel = createFormPanel(new String[]{
            safe(tableModel.getValueAt(row, 1)),
            safe(tableModel.getValueAt(row, 2)),
            safe(tableModel.getValueAt(row, 3)),
            safe(tableModel.getValueAt(row, 4)),
            // Loại bỏ " đ", ".", "," từ chuỗi lương để có số thuần
            safe(tableModel.getValueAt(row, 5)).replace(" đ", "").replace(".", "").replace(",", ""),
            safe(tableModel.getValueAt(row, 6))
        });

        int result = JOptionPane.showConfirmDialog(this, panel, "Sửa nhân viên",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                JTextField tfName     = (JTextField) ((JPanel) panel.getComponent(0)).getComponent(1);
                JTextField tfPhone    = (JTextField) ((JPanel) panel.getComponent(1)).getComponent(1);
                JTextField tfEmail    = (JTextField) ((JPanel) panel.getComponent(2)).getComponent(1);
                JTextField tfPosition = (JTextField) ((JPanel) panel.getComponent(3)).getComponent(1);
                JTextField tfSalary   = (JTextField) ((JPanel) panel.getComponent(4)).getComponent(1);
                JTextField tfHireDate = (JTextField) ((JPanel) panel.getComponent(5)).getComponent(1);

                BigDecimal salary = tfSalary.getText().trim().isEmpty()
                    ? BigDecimal.ZERO : new BigDecimal(tfSalary.getText().trim());
                LocalDate hireDate = tfHireDate.getText().trim().isEmpty()
                    ? null : LocalDate.parse(tfHireDate.getText().trim(), DF);

                controller.updateEmployee(id, tfName.getText().trim(), tfPhone.getText().trim(),
                    tfEmail.getText().trim(), tfPosition.getText().trim(), salary, hireDate);
                loadData();
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!\n" + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /** Xóa nhân viên đang chọn sau khi xác nhận */
    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa!");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        String name = safe(tableModel.getValueAt(row, 1));

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa \"" + name + "\"?", "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            controller.deleteEmployee(id);
            loadData();
            JOptionPane.showMessageDialog(this, "Xóa thành công!");
        }
    }

    /**
     * Tạo form panel 6 trường nhập liệu cho nhân viên.
     * @param values giá trị ban đầu [hoTen, sdt, email, chucVu, luong, ngayVao], null nếu thêm mới
     */
    private JPanel createFormPanel(String[] values) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(420, 330));

        JTextField tfName     = new JTextField(values != null ? values[0] : "", 25);
        JTextField tfPhone    = new JTextField(values != null ? values[1] : "", 25);
        JTextField tfEmail    = new JTextField(values != null ? values[2] : "", 25);
        JTextField tfPosition = new JTextField(values != null ? values[3] : "", 25);
        JTextField tfSalary   = new JTextField(values != null ? values[4] : "", 25);
        JTextField tfHireDate = new JTextField(values != null ? values[5] : "", 25);

        panel.add(createFieldRow("Họ tên:", tfName));
        panel.add(createFieldRow("Điện thoại:", tfPhone));
        panel.add(createFieldRow("Email:", tfEmail));
        panel.add(createFieldRow("Chức vụ:", tfPosition));
        panel.add(createFieldRow("Lương:", tfSalary));
        panel.add(createFieldRow("Ngày vào (dd/MM/yyyy):", tfHireDate));

        return panel;
    }

    /** Tạo dòng trường nhập liệu: [Label | Field] */
    private JPanel createFieldRow(String label, JComponent field) {
        JPanel row = new JPanel(new BorderLayout(10, 5));
        row.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setPreferredSize(new Dimension(150, 30));
        row.add(lbl, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        return row;
    }

    /** Chuyển Object thành String an toàn (null → "") */
    private String safe(Object val) {
        return val != null ? val.toString() : "";
    }
}
