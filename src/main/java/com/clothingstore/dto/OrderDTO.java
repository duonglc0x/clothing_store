package com.clothingstore.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * OrderDTO – Data Transfer Object cho Đơn hàng.
 *
 * Chứa thông tin đơn hàng đã flatten: tên khách hàng, tên nhân viên
 * (thay vì embed entity), trạng thái dạng String, và danh sách chi tiết đơn.
 */
public class OrderDTO {

    private Integer id;                  // ID đơn hàng
    private String customerName;         // Tên khách hàng – flatten từ Customer
    private Integer customerId;          // ID khách hàng – dùng khi cập nhật
    private String employeeName;         // Tên nhân viên xử lý – flatten từ Employee
    private Integer employeeId;          // ID nhân viên
    private LocalDateTime orderDate;     // Ngày giờ đặt hàng
    private BigDecimal totalAmount;      // Tổng tiền trước giảm giá
    private BigDecimal discount;         // Số tiền giảm giá
    private String status;               // Trạng thái dạng String (VD: "PENDING")
    private String note;                 // Ghi chú đơn hàng

    /** Danh sách chi tiết đơn hàng – dùng để hiển thị/tạo đơn hàng mới */
    private List<OrderDetailDTO> details = new ArrayList<>();

    /** Constructor mặc định */
    public OrderDTO() {}

    // ── Getters & Setters ──
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public Integer getEmployeeId() { return employeeId; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public BigDecimal getDiscount() { return discount; }
    public void setDiscount(BigDecimal discount) { this.discount = discount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public List<OrderDetailDTO> getDetails() { return details; }
    public void setDetails(List<OrderDetailDTO> details) { this.details = details; }

    /**
     * Tính tổng thành tiền sau giảm giá.
     * Công thức: finalAmount = totalAmount - discount
     * @return thành tiền cuối cùng (BigDecimal), trả về ZERO nếu totalAmount null
     */
    public BigDecimal getFinalAmount() {
        if (totalAmount == null) return BigDecimal.ZERO;
        BigDecimal disc = discount != null ? discount : BigDecimal.ZERO;
        return totalAmount.subtract(disc);
    }
}
