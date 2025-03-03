package model;

import enumeration.BookingStatus;
import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    private Date startDate;
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    public double calculateTotalCost() {
        long duration = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);
        return duration * room.getPrice();
    }

    public Booking() {
    }

    public Booking(Guest guest, Room room, Date startDate, Date endDate, BookingStatus status) {
        this.guest = guest;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}

