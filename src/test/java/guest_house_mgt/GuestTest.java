package guest_house_mgt;

import controller.GuestController;
import model.Guest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GuestTest {

    GuestController guestController;
    private UUID existingGuestId;

    @BeforeAll
    void setup() {
        guestController = new GuestController();
        Guest guest = new Guest();
        guest.setName("Gadam Mahamat");
        guest.setEmail("0791019191");
        guest.setCheckIn(new Date());
        guest.setCheckOut(new Date(System.currentTimeMillis() + 86400000));
        guestController.saveGuest(guest);
        this.existingGuestId = guest.getId();
    }

    @Test
    void testGetGuestById_WhenGuestExists() {
        Guest guest = guestController.getGuestById(existingGuestId);
        assertNotNull(guest, "Guest should not be null when it exists");
        assertEquals("John Doe", guest.getName(), "Guest name should match the saved one");
    }

    @Test
    void testGetGuestById_WhenGuestDoesNotExist() {
        UUID fakeId = UUID.randomUUID();
        Guest guest = guestController.getGuestById(fakeId);
        assertNull(guest, "Guest should be null when it does not exist");
    }

    @Test
    void testSaveGuest() {
        Guest guest = new Guest();
        guest.setName("Jane Doe");
        guest.setEmail("0987654321");
        guest.setCheckIn(new Date());
        guest.setCheckOut(new Date(System.currentTimeMillis() + 172800000));

        String result = guestController.saveGuest(guest);
        assertEquals("Saved successfully", result);
    }

    @Test
    void testUpdateGuest() {
        Guest guest = guestController.getGuestById(existingGuestId);
        guest.setEmail("1112223333");

        String result = guestController.updateGuest(guest);
        assertEquals("Updated successfully", result, "The updateGuest method should return 'Updated successfully'");
    }

    @Test
    void testDeleteGuest() {
        Guest guest = new Guest();
        guest.setName("Mark Smith");
        guest.setEmail("5556667777");
        guest.setCheckIn(new Date());
        guest.setCheckOut(new Date(System.currentTimeMillis() + 259200000));

        guestController.saveGuest(guest);
        UUID guestId = guest.getId();

        String result = guestController.deleteGuest(guestId);
        assertEquals("Deleted successfully", result);
    }

    @Test
    void testDeleteNonExistentGuest() {
        UUID fakeId = UUID.randomUUID();
        String result = guestController.deleteGuest(fakeId);
        assertEquals("Guest not found", result);
    }


}
