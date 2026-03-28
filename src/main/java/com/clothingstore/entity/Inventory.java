package com.clothingstore.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory",
       uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "size", "color"}))
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "size", nullable = false, length = 10)
    private String size;

    @Column(name = "color", nullable = false, length = 50)
    private String color;

    @Column(name = "quantity", nullable = false)
    private int quantity = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // ── Constructors ──
    public Inventory() {}

    public Inventory(Product product, String size, String color, int quantity) {
        this.product = product;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
    }

    // ── Getters & Setters ──
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    @Override
    public String toString() {
        return size + " - " + color + " (SL: " + quantity + ")";
    }
}
