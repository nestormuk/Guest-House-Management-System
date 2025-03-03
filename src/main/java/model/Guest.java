package model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String contact;
    private Date checkIn;
    private Date checkOut;

    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL)
    private List<Booking> bookings;

    public Guest() {
    }

    public Guest(String name, String contact, Date checkIn, Date checkOut, List<Booking> bookings) {
        this.name = name;
        this.contact = contact;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.bookings = bookings;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
