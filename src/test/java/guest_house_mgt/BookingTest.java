package guest_house_mgt;

import controller.BookingController;
import controller.GuestController;
import controller.RoomController;
import enumeration.BookingStatus;
import enumeration.RoomType;
import model.Booking;
import model.Guest;
import model.Room;
import org.junit.jupiter.api.*;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingTest {

    private static BookingController bookingController;
    private static GuestController guestController;
    private static RoomController roomController;
    private static UUID bookingId;
    private static final String TEST_GUEST_EMAIL = "gadam@gmail.com";
    private static final String TEST_ROOM_NUMBER = "801";

    @BeforeAll
    static void setup() {
        bookingController = new BookingController();
        guestController = new GuestController();
        roomController = new RoomController();

        // Create a guest
        Guest guest = new Guest("Test Guest", TEST_GUEST_EMAIL, new Date(), new Date(), null);
        guestController.saveGuest(guest);

        // Create a room
        Room room = new Room();
        room.setRoomNumber(TEST_ROOM_NUMBER);
        room.setAvailable(true);
        room.setPrice(100.0);
        room.setType(RoomType.SUITE);
        roomController.saveRoom(room);
    }

    @Test
    @Order(1)
    void testBookRoom() {
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 86400000L); // 1 day later

        String result = bookingController.bookRoom(TEST_GUEST_EMAIL, TEST_ROOM_NUMBER, startDate, endDate);
        assertEquals("Booking successful", result);

        // Fetch the booking to store its ID
        Booking booking = bookingController.getAllBookings().stream()
                .filter(b -> b.getGuest().getEmail().equals(TEST_GUEST_EMAIL))
                .findFirst()
                .orElse(null);

        assertNotNull(booking);
        bookingId = booking.getId();
    }

    @Test
    @Order(2)
    void testGetBookingById() {
        assertNotNull(bookingId);
        Booking booking = bookingController.getBookingById(bookingId);
        assertNotNull(booking);
        assertEquals(TEST_GUEST_EMAIL, booking.getGuest().getEmail());
    }

    @Test
    @Order(3)
    void testUpdateBooking() {
        assertNotNull(bookingId);
        Booking booking = bookingController.getBookingById(bookingId);
        assertNotNull(booking);

        booking.setStatus(BookingStatus.CONFIRMED);
        String result = bookingController.updateBooking(booking);
        assertEquals("Updated successfully", result);

        Booking updatedBooking = bookingController.getBookingById(bookingId);
        assertEquals(BookingStatus.CONFIRMED, updatedBooking.getStatus());
    }

    @Test
    @Order(4)
    void testDeleteBooking() {
        assertNotNull(bookingId);
        String result = bookingController.deleteBooking(bookingId);
        assertEquals("Deleted successfully", result);

        Booking deletedBooking = bookingController.getBookingById(bookingId);
        assertNull(deletedBooking);
    }
}
