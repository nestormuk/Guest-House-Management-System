package guest_house_mgt;

import controller.RoomController;
import model.Room;
import org.junit.jupiter.api.*;

import static junit.framework.TestCase.assertNotNull;


public class RoomTest {

    @Test
    public void testSaveRoom() {
        RoomController room = new RoomController();
        Room room1 = new Room();
        assertNotNull(room.saveRoom(room1));
    }
}
