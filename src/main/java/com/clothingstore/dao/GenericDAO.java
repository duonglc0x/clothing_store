package com.clothingstore.dao;

import com.clothingstore.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * GenericDAO – Lớp DAO tổng quát (Generic) cung cấp các thao tác CRUD cơ bản
 * cho TẤT CẢ entity trong hệ thống.
 *
 * Sử dụng Java Generics (tham số kiểu T) để tái sử dụng code:
 * - Thay vì viết riêng findById, findAll, save... cho từng entity,
 *   GenericDAO cung cấp các phương thức chung, các DAO con chỉ cần kế thừa.
 *
 * Mẫu thiết kế: DAO Pattern (Data Access Object)
 * - Tách biệt logic truy cập CSDL khỏi logic nghiệp vụ (Service layer)
 * - Mỗi phương thức mở Session riêng (Session-per-operation)
 * - Sử dụng try-with-resources để tự động đóng Session sau khi xong
 *
 * @param <T> kiểu Entity (VD: Product, Category, Order...)
 */
public class GenericDAO<T> {

    /**
     * Đối tượng Class của entity – cần để Hibernate biết truy vấn bảng nào.
     * Được truyền vào từ constructor của lớp con.
     */
    private final Class<T> entityClass;

    /**
     * Constructor – nhận Class của entity.
     * @param entityClass lớp entity (VD: Product.class, Order.class)
     */
    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Tìm entity theo ID (khóa chính).
     *
     * Sử dụng session.get() – trả về null nếu không tìm thấy
     * (khác session.load() sẽ ném exception).
     *
     * @param id ID cần tìm
     * @return entity tìm thấy hoặc null
     */
    public T findById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(entityClass, id);
        }
    }

    /**
     * Lấy tất cả bản ghi của entity này.
     *
     * Tạo HQL query "FROM TênEntity" – tương đương "SELECT * FROM tên_bảng".
     * entityClass.getSimpleName() trả về tên class (VD: "Product", "Order").
     *
     * @return danh sách tất cả entity
     */
    public List<T> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM " + entityClass.getSimpleName(), entityClass).list();
        }
    }

    /**
     * Lưu entity MỚI vào CSDL (INSERT).
     *
     * Quy trình transaction:
     * 1. Mở Session → bắt đầu Transaction
     * 2. persist() – đánh dấu entity để INSERT
     * 3. commit() – thực thi câu SQL INSERT vào CSDL
     * 4. Nếu lỗi → rollback() để hoàn tác, tránh dữ liệu bị hỏng
     *
     * @param entity đối tượng entity cần lưu
     * @throws RuntimeException nếu lưu thất bại
     */
    public void save(T entity) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();    // Bắt đầu giao dịch
            session.persist(entity);            // Đánh dấu entity mới để INSERT
            tx.commit();                        // Xác nhận giao dịch → thực thi SQL
        } catch (Exception e) {
            if (tx != null) tx.rollback();      // Hoàn tác nếu có lỗi
            throw e;                            // Ném lại exception cho Service/Controller xử lý
        }
    }

    /**
     * Cập nhật entity đã tồn tại trong CSDL (UPDATE).
     *
     * merge() – sao chép trạng thái của entity detached vào một entity managed,
     * sau đó Hibernate tự động tạo câu UPDATE khi commit.
     *
     * @param entity đối tượng entity đã chỉnh sửa
     */
    public void update(T entity) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(entity);              // Merge entity detached → managed, sau đó UPDATE
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    /**
     * Xóa entity khỏi CSDL (DELETE).
     *
     * Lưu ý: entity truyền vào có thể ở trạng thái detached,
     * nên cần merge() trước để chuyển sang managed, rồi mới remove().
     *
     * @param entity đối tượng entity cần xóa
     */
    public void delete(T entity) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            T managed = session.merge(entity);  // Chuyển entity sang trạng thái managed
            session.remove(managed);            // Đánh dấu xóa → tạo câu DELETE
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    /**
     * Xóa entity theo ID (khóa chính).
     *
     * Tìm entity theo ID trước, nếu tồn tại thì xóa.
     * An toàn hơn delete(entity) vì không cần entity object sẵn.
     *
     * @param id ID của entity cần xóa
     */
    public void deleteById(Integer id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            T entity = session.get(entityClass, id);    // Tìm entity theo ID
            if (entity != null) {
                session.remove(entity);                  // Xóa nếu tìm thấy
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    /**
     * Đếm tổng số bản ghi của entity này trong CSDL.
     *
     * HQL: "SELECT COUNT(e) FROM TênEntity e" → trả về Long.
     * Dùng cho Dashboard hiển thị thống kê.
     *
     * @return tổng số bản ghi (kiểu long)
     */
    public long count() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e", Long.class)
                          .uniqueResult();
        }
    }
}
