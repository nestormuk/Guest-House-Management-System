package controller;

import org.hibernate.Session;
import util.HibernateUtil;

public class ConnectionController {

    public String establishConnection() {

        try {
            Session session = HibernateUtil.getSession().openSession();
            session.close();
            return "connected";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
