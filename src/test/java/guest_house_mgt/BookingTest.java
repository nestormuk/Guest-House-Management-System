package guest_house_mgt;


import controller.BookingController;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class BookingTest {

    @Test
    public void testBookRoom() {
        // Test case 1: Booking a room successfully
        String guestEmail = "guest@example.com";
        String roomNumber = "101";
        Date startDate = new Date(); // Use current date for testing
        Date endDate = new Date(startDate.getTime() + 86400000L); // 1 day later

        BookingController bookingController = new BookingController();
        String result = bookingController.bookRoom(guestEmail, roomNumber, startDate, endDate);

        assertEquals("Booking successful", result);
    }

    @Test
    public void testRoomNotAvailable() {
        // Test case 2: Room is not available
        String guestEmail = "guest@example.com";
        String roomNumber = "101";
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 86400000L); // 1 day later

        // First book the room
        BookingController bookingController = new BookingController();
        bookingController.bookRoom(guestEmail, roomNumber, startDate, endDate);

        // Now attempt to book again, it should return "Room is not available"
        String result = bookingController.bookRoom(guestEmail, roomNumber, startDate, endDate);
        assertEquals("Room is not available", result);
    }

    @Test
    public void testGuestNotFound() {
        // Test case 3: Guest not found
        String guestEmail = "nonexistent@example.com";
        String roomNumber = "101";
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 86400000L); // 1 day later

        BookingController bookingController = new BookingController();
        String result = bookingController.bookRoom(guestEmail, roomNumber, startDate, endDate);

        assertEquals("Guest not found", result);
    }

    @Test
    public void testRoomNotFound() {
        // Test case 4: Room not found
        String guestEmail = "guest@example.com";
        String roomNumber = "999"; // Non-existent room number
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 86400000L); // 1 day later

        BookingController bookingController = new BookingController();
        String result = bookingController.bookRoom(guestEmail, roomNumber, startDate, endDate);

        assertEquals("Room not found", result);
    }
}
