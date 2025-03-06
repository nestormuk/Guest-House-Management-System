package controller;

import model.Booking;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;
import java.util.UUID;

public class BookingController {

    public String saveBooking(Booking booking) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(booking);
            transaction.commit();
            return "Saved successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Not saved";
        }
    }

    public Booking getBookingById(UUID id) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.get(Booking.class, id);
        }
    }

    public List<Booking> getAllBookings() {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.createQuery("FROM Booking", Booking.class).list();
        }
    }

    public String updateBooking(Booking booking) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(booking);
            transaction.commit();
            return "Updated successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Update failed";
        }
    }

    public String deleteBooking(UUID id) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            Booking booking = session.get(Booking.class, id);
            if (booking != null) {
                session.remove(booking);
                transaction.commit();
                return "Deleted successfully";
            }
            return "Booking not found";
        } catch (Exception e) {
            e.printStackTrace();
            return "Deletion failed";
        }
    }


}
