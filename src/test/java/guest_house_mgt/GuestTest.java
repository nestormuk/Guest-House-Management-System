package guest_house_mgt;

import controller.GuestController;
import model.Guest;
import org.junit.jupiter.api.*;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Ensures test order
public class GuestTest {

    private static GuestController guestController;
    private static UUID guestId;
    private static final String TEST_EMAIL = "gadam@gmail.com";

    @BeforeAll
    static void setup() {
        guestController = new GuestController();
    }

    @Test
    @Order(1)
    void testSaveGuest() {
        Guest guest = new Guest("Gadam", TEST_EMAIL, new Date(), new Date(), null);
        String result = guestController.saveGuest(guest);

        assertEquals("Saved successfully", result);

        // Fetch the guest to store its ID for later tests
        Guest savedGuest = guestController.getGuestByEmail(TEST_EMAIL);
        assertNotNull(savedGuest);
        guestId = savedGuest.getId();
    }

    @Test
    @Order(2)
    void testGetGuestByEmail() {
        Guest guest = guestController.getGuestByEmail(TEST_EMAIL);
        assertNotNull(guest);
        assertEquals(TEST_EMAIL, guest.getEmail());
    }

    @Test
    @Order(3)
    void testUpdateGuest() {
        Guest guest = guestController.getGuestByEmail(TEST_EMAIL);
        assertNotNull(guest);

        guest.setName("Gadam Updated");
        String result = guestController.updateGuest(guest);
        assertEquals("Updated successfully", result);

        // Verify update
        Guest updatedGuest = guestController.getGuestByEmail(TEST_EMAIL);
        assertEquals("Gadam Updated", updatedGuest.getName());
    }

    @Test
    @Order(4)
    void testDeleteGuest() {
        assertNotNull(guestId);
        String result = guestController.deleteGuest(guestId);
        assertEquals("Deleted successfully", result);

        // Verify deletion
        Guest deletedGuest = guestController.getGuestByEmail(TEST_EMAIL);
        assertNull(deletedGuest);
    }
}
