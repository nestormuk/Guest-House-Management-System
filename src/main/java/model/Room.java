package model;

import enumeration.RoomType;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String roomNumber;

    @Enumerated(EnumType.STRING)
    private RoomType type;
    private boolean available;
    private double price;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Booking> bookings;


    public Room() {
    }

    public Room(String roomNumber, RoomType type, boolean available, double price, List<Booking> bookings) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.available = available;
        this.price = price;
        this.bookings = bookings;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
