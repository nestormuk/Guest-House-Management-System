package guest_house_mgt;

import static org.junit.jupiter.api.Assertions.*;

import controller.BookingController;
import model.Booking;
import model.Guest;
import model.Room;
import enumeration.BookingStatus;
import org.junit.jupiter.api.*;
import java.util.List;
import java.util.UUID;
import java.util.Date;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookingTest {

    BookingController bookingController;
    private UUID existingBookingId;

    @BeforeAll
    void setup() {
        bookingController = new BookingController();
        Booking booking = new Booking();
        booking.setGuest(new Guest());
        booking.setRoom(new Room());
        booking.setStartDate(new Date());
        booking.setEndDate(new Date(System.currentTimeMillis() + 86400000));
        booking.setStatus(BookingStatus.PENDING);
        bookingController.saveBooking(booking);
        this.existingBookingId = booking.getId();
    }

    @Test
    void testGetBookingById_WhenBookingExists() {
        Booking booking = bookingController.getBookingById(existingBookingId);
        assertNotNull(booking, "Booking should not be null when it exists");
    }

    @Test
    void testGetBookingById_WhenBookingDoesNotExist() {
        UUID fakeId = UUID.randomUUID();
        Booking booking = bookingController.getBookingById(fakeId);
        assertNull(booking, "Booking should be null when it does not exist");
    }

    @Test
    void testSaveBooking() {
        Booking booking = new Booking();
        booking.setGuest(new Guest());
        booking.setRoom(new Room());
        booking.setStartDate(new Date());
        booking.setEndDate(new Date(System.currentTimeMillis() + 172800000));
        booking.setStatus(BookingStatus.CONFIRMED);

        String result = bookingController.saveBooking(booking);
        assertEquals("Saved successfully", result);
    }

    @Test
    void testUpdateBooking() {
        Booking booking = bookingController.getBookingById(existingBookingId);
        booking.setStatus(BookingStatus.CANCELLED);

        String result = bookingController.updateBooking(booking);
        assertEquals("Updated successfully", result, "The updateBooking method should return 'Updated successfully'");
    }

    @Test
    void testDeleteBooking() {
        Booking booking = new Booking();
        booking.setGuest(new Guest());
        booking.setRoom(new Room());
        booking.setStartDate(new Date());
        booking.setEndDate(new Date(System.currentTimeMillis() + 259200000));
        booking.setStatus(BookingStatus.PENDING);

        bookingController.saveBooking(booking);
        UUID bookingId = booking.getId();

        String result = bookingController.deleteBooking(bookingId);
        assertEquals("Deleted successfully", result);
    }

    @Test
    void testDeleteNonExistentBooking() {
        UUID fakeId = UUID.randomUUID();
        String result = bookingController.deleteBooking(fakeId);
        assertEquals("Booking not found", result);
    }

    @Test
    void testGetAllBookings_WhenBookingsExist() {
        List<Booking> bookings = bookingController.getAllBookings();
        assertFalse(bookings.isEmpty(), "Booking list should not be empty if bookings exist");
    }

    @Test
    void testGetAllBookings_WhenNoBookingsExist() {
        List<Booking> bookings = bookingController.getAllBookings();
        if (bookings.isEmpty()) {
            assertEquals(0, bookings.size(), "Booking list should be empty when no bookings exist");
        }
    }
}
