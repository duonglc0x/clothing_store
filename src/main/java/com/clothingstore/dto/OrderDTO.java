package com.clothingstore.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO: Đơn hàng
 */
public class OrderDTO {

    private Integer id;
    private String customerName;
    private Integer customerId;
    private String employeeName;
    private Integer employeeId;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private BigDecimal discount;
    private String status;
    private String note;
    private List<OrderDetailDTO> details = new ArrayList<>();

    public OrderDTO() {}

    // ── Getters & Setters ─────────────────────────────────────────────────────
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

    // Tính tổng thành tiền sau giảm giá
    public BigDecimal getFinalAmount() {
        if (totalAmount == null) return BigDecimal.ZERO;
        BigDecimal disc = discount != null ? discount : BigDecimal.ZERO;
        return totalAmount.subtract(disc);
    }
}
