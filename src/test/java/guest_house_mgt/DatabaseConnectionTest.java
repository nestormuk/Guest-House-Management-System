package guest_house_mgt;

import static org.junit.jupiter.api.Assertions.*;

import controller.ConnectionController;

import org.junit.jupiter.api.*;


class DatabaseConnectionTest {

    @Test
    public void test() {
        ConnectionController connection = new ConnectionController();

        assertNotNull(connection.establishConnection());

    }
}
