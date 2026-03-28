-- ============================================
-- CLOTHING STORE MANAGEMENT - MySQL Schema
-- ============================================
CREATE DATABASE IF NOT EXISTS clothing_store
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE clothing_store;

-- 1. Bảng danh mục sản phẩm
CREATE TABLE IF NOT EXISTS categories (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    description TEXT,
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. Bảng nhà cung cấp
CREATE TABLE IF NOT EXISTS suppliers (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(150) NOT NULL,
    phone        VARCHAR(20),
    email        VARCHAR(100),
    address      TEXT,
    contact_name VARCHAR(100),
    created_at   DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. Bảng sản phẩm (quần áo)
CREATE TABLE IF NOT EXISTS products (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(200) NOT NULL,
    description TEXT,
    price       DECIMAL(15,2) NOT NULL DEFAULT 0,
    image_url   VARCHAR(500),
    category_id INT NOT NULL,
    supplier_id INT,
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES categories(id),
    CONSTRAINT fk_product_supplier FOREIGN KEY (supplier_id)  REFERENCES suppliers(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. Bảng tồn kho (size, màu sắc, số lượng)
CREATE TABLE IF NOT EXISTS inventory (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    size       VARCHAR(10)  NOT NULL COMMENT 'S, M, L, XL, XXL',
    color      VARCHAR(50)  NOT NULL,
    quantity   INT NOT NULL DEFAULT 0,
    CONSTRAINT fk_inventory_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    UNIQUE KEY uq_inventory (product_id, size, color)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 5. Bảng khách hàng
CREATE TABLE IF NOT EXISTS customers (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    full_name  VARCHAR(150) NOT NULL,
    phone      VARCHAR(20)  UNIQUE,
    email      VARCHAR(100),
    address    TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 6. Bảng nhân viên
CREATE TABLE IF NOT EXISTS employees (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    full_name  VARCHAR(150) NOT NULL,
    phone      VARCHAR(20),
    email      VARCHAR(100) UNIQUE,
    position   VARCHAR(100) COMMENT 'Quản lý, Thu ngân, Bán hàng...',
    salary     DECIMAL(15,2) DEFAULT 0,
    hire_date  DATE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 7. Bảng đơn hàng
CREATE TABLE IF NOT EXISTS orders (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    customer_id   INT,
    employee_id   INT,
    order_date    DATETIME DEFAULT CURRENT_TIMESTAMP,
    total_amount  DECIMAL(15,2) DEFAULT 0,
    discount      DECIMAL(15,2) DEFAULT 0,
    status        ENUM('PENDING','CONFIRMED','SHIPPING','DELIVERED','CANCELLED')
                  DEFAULT 'PENDING',
    note          TEXT,
    CONSTRAINT fk_order_customer FOREIGN KEY (customer_id)  REFERENCES customers(id) ON DELETE SET NULL,
    CONSTRAINT fk_order_employee FOREIGN KEY (employee_id)  REFERENCES employees(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 8. Bảng chi tiết đơn hàng
CREATE TABLE IF NOT EXISTS order_details (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    order_id     INT NOT NULL,
    product_id   INT NOT NULL,
    size         VARCHAR(10),
    color        VARCHAR(50),
    quantity     INT NOT NULL DEFAULT 1,
    unit_price   DECIMAL(15,2) NOT NULL,
    CONSTRAINT fk_od_order   FOREIGN KEY (order_id)   REFERENCES orders(id)   ON DELETE CASCADE,
    CONSTRAINT fk_od_product FOREIGN KEY (product_id) REFERENCES products(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ========================================================
-- DỮ LIỆU MẪU
-- ========================================================

INSERT INTO categories (name, description) VALUES
('Áo Thun', 'Áo thun nam nữ các loại'),
('Áo Sơ Mi', 'Áo sơ mi công sở và dạo phố'),
('Quần Jean', 'Quần jean thời trang'),
('Váy & Đầm', 'Váy ngắn, đầm dài các phong cách'),
('Áo Khoác', 'Áo khoác, hoodie, jacket');

INSERT INTO suppliers (name, phone, email, address, contact_name) VALUES
('Thời Trang Việt', '0901234567', 'thoitrangviet@gmail.com', 'TP.HCM', 'Nguyễn Văn A'),
('Fashion World', '0912345678', 'fashionworld@gmail.com', 'Hà Nội', 'Trần Thị B');

INSERT INTO employees (full_name, phone, email, position, salary, hire_date) VALUES
('Nguyễn Thị Lan', '0911111111', 'lan@store.vn', 'Quản lý', 15000000, '2022-01-10'),
('Trần Văn Minh', '0922222222', 'minh@store.vn', 'Thu ngân', 9000000, '2023-03-15'),
('Lê Thị Hoa', '0933333333', 'hoa@store.vn', 'Bán hàng', 8000000, '2023-06-01');

INSERT INTO customers (full_name, phone, email, address) VALUES
('Phạm Thanh Tú', '0971234567', 'tu@gmail.com', 'Q1, TP.HCM'),
('Hoàng Minh Châu', '0987654321', 'chau@gmail.com', 'Q7, TP.HCM'),
('Vũ Thị Ngọc', '0909123456', 'ngoc@gmail.com', 'Bình Dương');

INSERT INTO products (name, description, price, category_id, supplier_id) VALUES
('Áo Thun Trơn Basic', 'Áo thun cotton 100%, form regular', 150000, 1, 1),
('Áo Sơ Mi Kẻ Sọc', 'Áo sơ mi vải thun lạnh cao cấp', 280000, 2, 1),
('Quần Jean Skinny', 'Quần jean co giãn 4 chiều', 350000, 3, 2),
('Đầm Hoa Mùa Hè', 'Đầm voan chiffon họa tiết hoa', 420000, 4, 2),
('Áo Khoác Hoodie', 'Hoodie nỉ dày, giữ nhiệt tốt', 480000, 5, 1);

INSERT INTO inventory (product_id, size, color, quantity) VALUES
(1, 'S', 'Trắng', 20), (1, 'M', 'Trắng', 30), (1, 'L', 'Đen', 25),
(2, 'M', 'Xanh', 15), (2, 'L', 'Trắng', 20),
(3, '28', 'Xanh đậm', 10), (3, '30', 'Xanh đậm', 15),
(4, 'S', 'Hoa đỏ', 12), (4, 'M', 'Hoa đỏ', 10),
(5, 'M', 'Xám', 20), (5, 'L', 'Đen', 18);
