package controller;

import model.Guest;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;
import java.util.UUID;

public class GuestController {

    public String saveGuest(Guest guest) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(guest);
            transaction.commit();
            return "Saved successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Not saved";
        }
    }

    public Guest getGuestByEmail(String email) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.createQuery("FROM Guest WHERE email = :email", Guest.class)
                    .setParameter("email", email)
                    .uniqueResult();
        }
    }


    public Guest getGuestById(UUID id) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.get(Guest.class, id);
        }
    }

    public List<Guest> getAllGuests() {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.createQuery("FROM Guest", Guest.class).list();
        }
    }

    public String updateGuest(Guest guest) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(guest);
            transaction.commit();
            return "Updated successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Update failed";
        }
    }
    public String deleteGuest(UUID id) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            Guest guest = session.get(Guest.class, id);
            if (guest != null) {
                session.remove(guest);
                transaction.commit();
                return "Deleted successfully";
            }
            return "Guest not found";
        } catch (Exception e) {
            e.printStackTrace();
            return "Deletion failed";
        }
    }
}
