package controller;

import model.Room;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

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
}
