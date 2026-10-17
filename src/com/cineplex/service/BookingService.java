package com.cineplex.service;

import com.cineplex.model.Booking;
import java.util.ArrayList;

public class BookingService {
    private ArrayList<Booking> bookings = new ArrayList<>();
    private int bookingCounter = 1000;

    public Booking createBooking(String id, String customerName, String movieTitle,
                                  String showtime, ArrayList<String> seats, double total) {
        Booking booking = new Booking(id, customerName, movieTitle, showtime, seats, total);
        bookings.add(booking);
        return booking;
    }

    public String generateBookingId() {
        bookingCounter++;
        return "BK" + bookingCounter;
    }

    public boolean cancelBooking(String bookingId) {
        Booking found = findBookingById(bookingId);
        if (found != null) {
            bookings.remove(found);
            return true;
        }
        return false;
    }

    public Booking findBookingById(String bookingId) {
        for (Booking b : bookings) {
            if (b.getId().equalsIgnoreCase(bookingId)) {
                return b;
            }
        }
        return null;
    }

    public ArrayList<Booking> getAllBookings() {
        return bookings;
    }
}
