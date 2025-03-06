package controller;

import enumeration.BookingStatus;
import model.Booking;
import model.Guest;
import model.Room;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class BookingController {

    private final GuestController guestController = new GuestController();
    private final RoomController roomController = new RoomController();

    public String bookRoom(String guestEmail, String roomNumber, Date startDate, Date endDate) {
        Guest guest = guestController.getGuestByEmail(guestEmail);
        if (guest == null) return "Guest not found";

        Room room = roomController.getRoomByNumber(roomNumber);
        if (room == null) return "Room not found";

        if (!room.isAvailable()) return "Room is not available";

        try (Session session = HibernateUtil.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();

            Booking booking = new Booking();
            booking.setGuest(guest);
            booking.setRoom(room);
            booking.setStartDate(startDate);
            booking.setEndDate(endDate);
            booking.setStatus(BookingStatus.PENDING);

            session.persist(booking);

            // Update room availability
            room.setAvailable(false);
            session.merge(room);

            transaction.commit();
            return "Booking successful";
        } catch (Exception e) {
            e.printStackTrace();
            return "Booking failed";
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
