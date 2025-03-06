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

    public String bookRoom(String guestEmail, String roomNumber, Date startDate, Date endDate) {
        // Step 1: Find the guest by email
        Guest guest = new GuestController().getGuestByEmail(guestEmail);
        if (guest == null) {
            return "Guest not found";
        }

        // Step 2: Find the room by room number
        Room room = new RoomController().getRoomByNumber(roomNumber);
        if (room == null) {
            return "Room not found";
        }

        // Step 3: Check if the room is available
        if (!new RoomController().isRoomAvailable(roomNumber)) {
            return "Room is not available";
        }

        // Step 4: Create the booking
        try (Session session = HibernateUtil.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();

            Booking booking = new Booking();
            booking.setGuest(guest);
            booking.setRoom(room);
            booking.setStartDate(startDate);
            booking.setEndDate(endDate);
            booking.setStatus(BookingStatus.PENDING); // Assuming status is pending for new bookings

            session.persist(booking);
            transaction.commit();

            // Optionally, update room availability after booking
            room.setAvailable(false);
            new RoomController().updateRoomAvailability(roomNumber, false); // Update room to not available

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
