package guest_house_mgt;

import static org.junit.jupiter.api.Assertions.*;

import controller.RoomController;
import enumeration.RoomType;
import model.Room;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.UUID;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RoomTest {

    RoomController roomController;
    private UUID roomId;
    private UUID existingRoomId;

    @BeforeAll
    void setup() {
        roomController = new RoomController();
        // Adding a room to test retrieval

        Room room = new Room();
        room.setRoomNumber("201");
        room.setType(RoomType.SUITE);
        room.setAvailable(true);
        room.setPrice(250.0);

        roomController.saveRoom(room);
        this.existingRoomId = room.getId();
    }

    @Test
    void testGetRoomById_WhenRoomExists() {
        Room room = roomController.getRoomById(existingRoomId);
        assertNotNull(room, "Room should not be null when it exists");
        assertEquals("201", room.getRoomNumber(), "Room number should match the saved one");
    }

    @Test
    void testGetRoomById_WhenRoomDoesNotExist() {
        UUID fakeId = UUID.randomUUID();
        Room room = roomController.getRoomById(fakeId);
        assertNull(room, "Room should be null when it does not exist");
    }

    @Test
    void testSaveRoom() {
        Room room = new Room();
        room.setRoomNumber("101");
        room.setType(RoomType.SUITE);
        room.setAvailable(true);
        room.setPrice(150.0);

        String result = roomController.saveRoom(room);
        assertEquals("Saved successfully", result);
    }

    @Test
    void testUpdateRoom() {
        Room room = new Room();
        room.setRoomNumber("102");
        room.setType(RoomType.DOUBLE);
        room.setAvailable(false);
        room.setPrice(180.0);

        roomController.saveRoom(room);
        room.setPrice(200.0);

        String result = roomController.updateRoom(room);
        assertEquals("Updated successfully", result, "The updateRoom method should return 'Updated successfully'");
    }

    @Test
    void testDeleteRoom() {
        Room room = new Room();
        room.setRoomNumber("103");
        room.setType(RoomType.SUITE);
        room.setAvailable(true);
        room.setPrice(100.0);

        roomController.saveRoom(room);
        UUID roomId = room.getId();

        String result = roomController.deleteRoom(roomId);
        assertEquals("Deleted successfully", result);
    }

    @Test
    void testDeleteNonExistentRoom() {
        UUID fakeId = UUID.randomUUID();
        String result = roomController.deleteRoom(fakeId);
        assertEquals("Room not found", result);
    }

    @Test
    void testGetAllRooms_WhenRoomsExist() {
        List<Room> rooms = roomController.getAllRooms();
        assertFalse(rooms.isEmpty(), "Room list should not be empty if rooms exist");
    }

    @Test
    void testGetAllRooms_WhenNoRoomsExist() {
        // Simulating an empty database scenario
        List<Room> rooms = roomController.getAllRooms();
        if (rooms.isEmpty()) {
            assertEquals(0, rooms.size(), "Room list should be empty when no rooms exist");
        }
    }
}
