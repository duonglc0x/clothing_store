package com.clothingstore.controller;

import com.clothingstore.entity.Customer;
import com.clothingstore.entity.Employee;
import com.clothingstore.entity.Order;
import com.clothingstore.entity.OrderDetail;
import com.clothingstore.service.CustomerService;
import com.clothingstore.service.EmployeeService;
import com.clothingstore.service.OrderService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * OrderController – Tầng Controller cho Đơn hàng.
 *
 * Phối hợp 3 Service: OrderService, CustomerService, EmployeeService
 * vì khi tạo đơn hàng cần chọn khách hàng và nhân viên xử lý.
 */
public class OrderController {

    private final OrderService orderService = new OrderService();
    private final CustomerService customerService = new CustomerService();
    private final EmployeeService employeeService = new EmployeeService();

    /**
     * Lấy tất cả đơn hàng kèm thông tin Customer và Employee.
     * @return danh sách đơn hàng đã JOIN FETCH
     */
    public List<Order> getAllOrders() {
        return orderService.getAllOrdersWithDetails();
    }

    /** @return danh sách khách hàng (dùng cho JComboBox khi tạo đơn) */
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    /** @return danh sách nhân viên (dùng cho JComboBox khi tạo đơn) */
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    /**
     * Tạo đơn hàng mới.
     *
     * @param customerId  ID khách hàng (có thể null – khách vãng lai)
     * @param employeeId  ID nhân viên xử lý (có thể null)
     * @param totalAmount tổng tiền
     * @param discount    giảm giá
     * @param status      trạng thái (String → chuyển thành Enum)
     * @param note        ghi chú
     */
    public void addOrder(Integer customerId, Integer employeeId, BigDecimal totalAmount,
                         BigDecimal discount, String status, String note) {
        Order order = new Order();
        // Gán khách hàng nếu có
        if (customerId != null) {
            order.setCustomer(customerService.getCustomerById(customerId));
        }
        // Gán nhân viên nếu có
        if (employeeId != null) {
            order.setEmployee(employeeService.getEmployeeById(employeeId));
        }
        order.setOrderDate(LocalDateTime.now());                              // Gán ngày giờ hiện tại
        order.setTotalAmount(totalAmount != null ? totalAmount : BigDecimal.ZERO);
        order.setDiscount(discount != null ? discount : BigDecimal.ZERO);
        order.setStatus(Order.Status.valueOf(status));                        // Chuyển String → Enum
        order.setNote(note);
        orderService.addOrder(order);
    }

    /**
     * Cập nhật đơn hàng.
     * Tìm đơn hàng cũ theo ID → cập nhật các trường → lưu.
     */
    public void updateOrder(int id, Integer customerId, Integer employeeId,
                            BigDecimal totalAmount, BigDecimal discount, String status, String note) {
        Order order = orderService.getOrderById(id);
        if (order != null) {
            if (customerId != null) {
                order.setCustomer(customerService.getCustomerById(customerId));
            }
            if (employeeId != null) {
                order.setEmployee(employeeService.getEmployeeById(employeeId));
            }
            order.setTotalAmount(totalAmount != null ? totalAmount : BigDecimal.ZERO);
            order.setDiscount(discount != null ? discount : BigDecimal.ZERO);
            order.setStatus(Order.Status.valueOf(status));
            order.setNote(note);
            orderService.updateOrder(order);
        }
    }

    /** Xóa đơn hàng theo ID */
    public void deleteOrder(int id) {
        orderService.deleteOrder(id);
    }

    /** @return tổng số đơn hàng */
    public long countOrders() {
        return orderService.countOrders();
    }

    /**
     * Lấy chi tiết đơn hàng.
     * @param orderId ID đơn hàng
     * @return danh sách chi tiết sản phẩm trong đơn
     */
    public List<OrderDetail> getOrderDetails(int orderId) {
        return orderService.getOrderDetails(orderId);
    }
}
