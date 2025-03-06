package controller;

import model.Room;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;
import java.util.UUID;

public class RoomController {

    public String saveRoom(Room room) {

        try {
            Session session = HibernateUtil.getSession().openSession();
            Transaction transaction = null;
            transaction = session.beginTransaction();
            session.persist(room);
            transaction.commit();
            session.close();

            return "Saved successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "not saved";
        }
    }

    public Room getRoomByNumber(String roomNumber) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.createQuery("FROM Room WHERE roomNumber = :roomNumber", Room.class)
                    .setParameter("roomNumber", roomNumber)
                    .uniqueResult();
        }
    }


    public Room getRoomById(UUID id) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.get(Room.class, id);
        }
    }

    public List<Room> getAllRooms() {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.createQuery("FROM Room", Room.class).list();
        }
    }

    public String updateRoom(Room room) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(room);
            transaction.commit();
            return "Updated successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Update failed";
        }
    }

    public String deleteRoom(UUID id) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            Room room = session.get(Room.class, id);
            if (room != null) {
                session.remove(room);
                transaction.commit();
                return "Deleted successfully";
            }
            return "Room not found";
        } catch (Exception e) {
            e.printStackTrace();
            return "Deletion failed";
        }
    }

    public boolean isRoomAvailable(String roomNumber) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Room room = session.createQuery("FROM Room WHERE roomNumber = :roomNumber", Room.class)
                    .setParameter("roomNumber", roomNumber)
                    .uniqueResult();
            return room != null && room.isAvailable();
        }
    }

    public String updateRoomAvailability(String roomNumber, boolean availability) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            Room room = session.createQuery("FROM Room WHERE roomNumber = :roomNumber", Room.class)
                    .setParameter("roomNumber", roomNumber)
                    .uniqueResult();
            if (room != null) {
                room.setAvailable(availability);
                session.merge(room);
                transaction.commit();
                return "Room availability updated successfully";
            }
            return "Room not found";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to update room availability";
        }
    }



}
