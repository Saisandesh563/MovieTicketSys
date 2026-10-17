package com.cineplex.model;

import java.util.ArrayList;
import java.util.Date;

public class Booking {
    private String id;
    private String customerName;
    private String movieTitle;
    private String showtime;
    private ArrayList<String> seats;
    private double total;
    private String timestamp;

    public Booking(String id, String customerName, String movieTitle,
                   String showtime, ArrayList<String> seats, double total) {
        this.id = id;
        this.customerName = customerName;
        this.movieTitle = movieTitle;
        this.showtime = showtime;
        this.seats = seats;
        this.total = total;
        this.timestamp = new Date().toString();
    }

    public String getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getMovieTitle() { return movieTitle; }
    public String getShowtime() { return showtime; }
    public ArrayList<String> getSeats() { return seats; }
    public double getTotal() { return total; }
    public String getTimestamp() { return timestamp; }

    void setId(String id) { this.id = id; }
    void setCustomerName(String customerName) { this.customerName = customerName; }
    void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }
    void setShowtime(String showtime) { this.showtime = showtime; }
    void setSeats(ArrayList<String> seats) { this.seats = seats; }
    void setTotal(double total) { this.total = total; }
    void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
