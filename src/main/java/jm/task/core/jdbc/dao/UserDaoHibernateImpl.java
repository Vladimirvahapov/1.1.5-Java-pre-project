package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        Util util = new Util();
        sessionFactory = util.getSessionFactory();
    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query createUsersQuery = session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(45), lastName VARCHAR(45), age INT)");
            createUsersQuery.executeUpdate();
            transaction.commit();
            System.out.println("[SERVICE] ������� user �������");
        } catch (Exception e) {
            System.out.println("[WARNING] ������ ��� �������� ������� " + e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query dropUsersQuery = session.createSQLQuery("DROP TABLE IF EXISTS users");
            dropUsersQuery.executeUpdate();
            transaction.commit();
            System.out.println("[SERVICE] ������� user �������");
        } catch (Exception e) {
            System.out.println("[WARNING] ������ ��� �������� ������� " + e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("[SERVICE] User � ������ � " + name + " �������� � ���� ������ ");
        } catch (Exception e) {
            System.out.println("[WARNING] ������ ��� ���������� " + e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                transaction.commit();
                System.out.println("[SERVICE] User � id � " + id + " ������ ");
            } else {
                System.out.println("[SERVICE] ������������ � id " + id + " �� ������");
            }
        } catch (Exception e) {
            System.out.println("[WARNING] ������ ��� �������� ������������ �� id " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<User> query = session.createQuery("FROM User", User.class);
            usersList = query.list();
            transaction.commit();
            return usersList;
        } catch (Exception e) {
            System.out.println("[WARNING] ������ ��� ��������� ���� ������������� �� �������: " + e.getMessage());
        }
        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query cleanUsersQuery = session.createQuery("DELETE FROM User");
            cleanUsersQuery.executeUpdate();
            transaction.commit();
            System.out.println("[SERVICE] ������� �������");
        } catch (Exception e) {
            System.out.println("[WARNING] ������ ��� ������� �������: " + e.getMessage());
        }
    }
}
