package com.clothingstore.view;

import com.clothingstore.controller.ProductController;
import com.clothingstore.entity.Category;
import com.clothingstore.entity.Product;
import com.clothingstore.entity.Supplier;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * ProductPanel – Panel quản lý sản phẩm (CRUD).
 *
 * Khác với CustomerPanel/EmployeePanel:
 * - Form thêm/sửa có JComboBox cho Category và Supplier (thay vì chỉ TextField)
 * - Giá hiển thị định dạng tiền VNĐ
 * - Khi sửa, cần pre-select đúng item trong JComboBox theo tên
 */
public class ProductPanel extends JPanel {

    private static final Color BG_COLOR      = new Color(240, 240, 240);
    private static final Color HEADER_COLOR  = new Color(66, 133, 244);   // Xanh dương
    private static final Color BTN_ADD       = new Color(52, 168, 83);
    private static final Color BTN_EDIT      = new Color(251, 188, 4);
    private static final Color BTN_DELETE    = new Color(234, 67, 53);

    private JTable table;
    private DefaultTableModel tableModel;
    private final ProductController controller = new ProductController();

    public ProductPanel() {
        setLayout(new BorderLayout());
        setBackground(BG_COLOR);
        initComponents();
    }

    private void initComponents() {
        // ═══════════════ HEADER ═══════════════
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        JLabel title = new JLabel("Quản lý sản phẩm");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(33, 33, 33));
        headerPanel.add(title, BorderLayout.WEST);

        // Toolbar buttons
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
        String[] columns = {"ID", "Tên sản phẩm", "Giá", "Danh mục", "Nhà cung cấp"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(200, 220, 255));
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(new Color(220, 220, 220));
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(0, 1));

        // Header custom renderer
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
                lbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(50, 110, 220)));
                lbl.setOpaque(true);
                return lbl;
            }
        });

        // Căn giữa cột ID + thiết lập chiều rộng
        DefaultTableCellRenderer centerR = new DefaultTableCellRenderer();
        centerR.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerR);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);

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
     * Tải dữ liệu sản phẩm. Giá format locale VN ("350.000 đ").
     * Category & Supplier hiển thị tên (JOIN FETCH từ DAO).
     */
    public void loadData() {
        SwingWorker<List<Product>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Product> doInBackground() {
                return controller.getAllProducts();
            }

            @Override
            protected void done() {
                try {
                    List<Product> products = get();
                    tableModel.setRowCount(0);
                    NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
                    for (Product p : products) {
                        tableModel.addRow(new Object[]{
                            p.getId(),
                            p.getName(),
                            nf.format(p.getPrice()) + " đ",
                            p.getCategory() != null ? p.getCategory().getName() : "",
                            p.getSupplier() != null ? p.getSupplier().getName() : ""
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
     * Dialog thêm sản phẩm mới.
     * Form có JComboBox cho Category (bắt buộc) và Supplier (tùy chọn).
     */
    private void showAddDialog() {
        JPanel panel = createFormPanel(null);
        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm sản phẩm",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                JTextField tfName  = (JTextField) ((JPanel)panel.getComponent(0)).getComponent(1);
                JTextField tfDesc  = (JTextField) ((JPanel)panel.getComponent(1)).getComponent(1);
                JTextField tfPrice = (JTextField) ((JPanel)panel.getComponent(2)).getComponent(1);
                @SuppressWarnings("unchecked")
                JComboBox<Category> cbCat = (JComboBox<Category>) ((JPanel)panel.getComponent(3)).getComponent(1);
                @SuppressWarnings("unchecked")
                JComboBox<Supplier> cbSup = (JComboBox<Supplier>) ((JPanel)panel.getComponent(4)).getComponent(1);

                String name = tfName.getText().trim();
                String desc = tfDesc.getText().trim();
                BigDecimal price = new BigDecimal(tfPrice.getText().trim());
                Category cat = (Category) cbCat.getSelectedItem();
                Supplier sup = (Supplier) cbSup.getSelectedItem();

                if (name.isEmpty() || cat == null) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                    return;
                }

                controller.addProduct(name, desc, price, cat.getId(), sup != null ? sup.getId() : null);
                loadData();
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Giá không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Dialog sửa sản phẩm. Pre-select Category/Supplier theo tên hiện tại.
     */
    private void showEditDialog() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần sửa!");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        String name = (String) tableModel.getValueAt(row, 1);

        // Loại bỏ ký tự format giá (" đ", ".", ",")
        JPanel panel = createFormPanel(new String[]{
            name,
            "",  // Mô tả không hiển thị trên bảng
            tableModel.getValueAt(row, 2).toString().replace(" đ", "").replace(".", "").replace(",", ""),
            (String) tableModel.getValueAt(row, 3),  // Tên danh mục để pre-select
            (String) tableModel.getValueAt(row, 4)   // Tên NCC để pre-select
        });

        int result = JOptionPane.showConfirmDialog(this, panel, "Sửa sản phẩm",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                JTextField tfName  = (JTextField) ((JPanel)panel.getComponent(0)).getComponent(1);
                JTextField tfDesc  = (JTextField) ((JPanel)panel.getComponent(1)).getComponent(1);
                JTextField tfPrice = (JTextField) ((JPanel)panel.getComponent(2)).getComponent(1);
                @SuppressWarnings("unchecked")
                JComboBox<Category> cbCat = (JComboBox<Category>) ((JPanel)panel.getComponent(3)).getComponent(1);
                @SuppressWarnings("unchecked")
                JComboBox<Supplier> cbSup = (JComboBox<Supplier>) ((JPanel)panel.getComponent(4)).getComponent(1);

                BigDecimal price = new BigDecimal(tfPrice.getText().trim());
                Category cat = (Category) cbCat.getSelectedItem();
                Supplier sup = (Supplier) cbSup.getSelectedItem();

                controller.updateProduct(id, tfName.getText().trim(), tfDesc.getText().trim(),
                        price, cat.getId(), sup != null ? sup.getId() : null);
                loadData();
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Giá không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /** Xóa sản phẩm đang chọn */
    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa!");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        String name = (String) tableModel.getValueAt(row, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa \"" + name + "\"?", "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            controller.deleteProduct(id);
            loadData();
            JOptionPane.showMessageDialog(this, "Xóa thành công!");
        }
    }

    /**
     * Tạo form panel có 3 TextField + 2 JComboBox.
     * Load danh sách Category và Supplier từ CSDL vào JComboBox.
     * Nếu đang sửa, pre-select item phù hợp theo tên.
     *
     * @param values [name, desc, price, categoryName, supplierName], null nếu thêm mới
     */
    private JPanel createFormPanel(String[] values) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(400, 280));

        JTextField tfName  = new JTextField(values != null ? values[0] : "", 25);
        JTextField tfDesc  = new JTextField(values != null ? values[1] : "", 25);
        JTextField tfPrice = new JTextField(values != null ? values[2] : "", 25);

        // JComboBox cho danh mục và nhà cung cấp
        JComboBox<Category> cbCat = new JComboBox<>();
        JComboBox<Supplier> cbSup = new JComboBox<>();

        // Load danh mục từ CSDL
        List<Category> categories = controller.getAllCategories();
        for (Category c : categories) cbCat.addItem(c);

        // Load nhà cung cấp từ CSDL (có item null đầu tiên = "không chọn")
        cbSup.addItem(null);
        List<Supplier> suppliers = controller.getAllSuppliers();
        for (Supplier s : suppliers) cbSup.addItem(s);

        // Pre-select item khi sửa – so sánh theo tên
        if (values != null) {
            for (int i = 0; i < cbCat.getItemCount(); i++) {
                if (cbCat.getItemAt(i).getName().equals(values[3])) {
                    cbCat.setSelectedIndex(i);
                    break;
                }
            }
            for (int i = 0; i < cbSup.getItemCount(); i++) {
                Supplier s = cbSup.getItemAt(i);
                if (s != null && s.getName().equals(values[4])) {
                    cbSup.setSelectedIndex(i);
                    break;
                }
            }
        }

        panel.add(createFieldRow("Tên sản phẩm:", tfName));
        panel.add(createFieldRow("Mô tả:", tfDesc));
        panel.add(createFieldRow("Giá:", tfPrice));
        panel.add(createFieldRow("Danh mục:", cbCat));
        panel.add(createFieldRow("Nhà cung cấp:", cbSup));

        return panel;
    }

    /** Tạo dòng trường nhập liệu: [Label | Component] */
    private JPanel createFieldRow(String label, JComponent field) {
        JPanel row = new JPanel(new BorderLayout(10, 5));
        row.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setPreferredSize(new Dimension(110, 30));
        row.add(lbl, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        return row;
    }
}
