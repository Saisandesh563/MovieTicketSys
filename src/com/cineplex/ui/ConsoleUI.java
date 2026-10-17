package com.cineplex.ui;

import com.cineplex.model.Booking;
import com.cineplex.service.BookingService;
import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleUI {
    private static final String[] MOVIE_TITLES = {
        "Inception", "The Dark Knight", "Interstellar", "Parasite", "Spirited Away"
    };
    private static final String[] MOVIE_GENRES = {
        "Sci-Fi", "Action", "Sci-Fi", "Thriller", "Animation"
    };
    private static final String[] MOVIE_DURATIONS = {
        "2h 28m", "2h 32m", "2h 49m", "2h 12m", "2h 5m"
    };
    private static final String[] MOVIE_RATINGS = {
        "PG-13", "PG-13", "PG-13", "R", "PG"
    };
    private static final String[][] SHOWTIMES = {
        {"10:00 AM", "1:30 PM", "5:00 PM", "8:30 PM"},
        {"11:00 AM", "2:30 PM", "6:00 PM", "9:30 PM"},
        {"9:30 AM", "1:00 PM", "4:30 PM", "8:00 PM"},
        {"10:30 AM", "2:00 PM", "5:30 PM", "9:00 PM"},
        {"10:00 AM", "12:30 PM", "3:00 PM", "5:30 PM"}
    };

    private static final int ROWS = 10;
    private static final int SEATS_PER_ROW = 10;
    private static final String ROW_LABELS = "ABCDEFGHIJ";
    private static final double PRICE_FRONT = 8.00;
    private static final double PRICE_MIDDLE = 12.00;
    private static final double PRICE_BACK = 15.00;

    private Scanner scanner = new Scanner(System.in);
    private BookingService bookingService = new BookingService();

    public void start() {
        displayWelcome();
        mainMenu();
    }

    private void displayWelcome() {
        System.out.println("=".repeat(55));
        System.out.println("       WELCOME TO CINEPLEX TICKET BOOKING");
        System.out.println("=".repeat(55));
        System.out.println();
    }

    private void mainMenu() {
        while (true) {
            System.out.println("\n" + "-".repeat(55));
            System.out.println("       MAIN MENU");
            System.out.println("-".repeat(55));
            System.out.println("  1. Book a Ticket");
            System.out.println("  2. View All Bookings");
            System.out.println("  3. Cancel a Booking");
            System.out.println("  4. Exit");
            System.out.println("-".repeat(55));
            System.out.print("Select an option (1-4): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": bookTicketFlow(); break;
                case "2": viewAllBookings(); break;
                case "3": cancelBooking(); break;
                case "4":
                    System.out.println("\nThank you for using Cineplex Booking. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void displayMovies() {
        System.out.println("-".repeat(55));
        System.out.println("NOW SHOWING:");
        System.out.println("-".repeat(55));
        for (int i = 0; i < MOVIE_TITLES.length; i++) {
            System.out.printf("  [%d] %s%n", i + 1, MOVIE_TITLES[i]);
            System.out.printf("      Genre: %s | Duration: %s | Rating: %s%n",
                    MOVIE_GENRES[i], MOVIE_DURATIONS[i], MOVIE_RATINGS[i]);
        }
        System.out.println("-".repeat(55));
        System.out.println();
    }

    private void displayShowtimes(int movieIndex) {
        System.out.println("\nAvailable showtimes:");
        for (int i = 0; i < SHOWTIMES[movieIndex].length; i++) {
            System.out.printf("  %d. %s%n", i + 1, SHOWTIMES[movieIndex][i]);
        }
    }

    private void displaySeats(char[][] seatMap) {
        System.out.println();
        System.out.println("            ".substring(0, 11) + "SCREEN");
        System.out.println("      " + "=".repeat(24));
        System.out.println();
        System.out.print("     ");
        for (int num = 1; num <= SEATS_PER_ROW; num++) {
            System.out.printf("%3d", num);
        }
        System.out.println();
        System.out.println();
        for (int i = 0; i < ROWS; i++) {
            System.out.printf("  %c  ", ROW_LABELS.charAt(i));
            for (int j = 0; j < SEATS_PER_ROW; j++) {
                System.out.printf("[%c]", seatMap[i][j]);
            }
            System.out.println();
        }
        System.out.println("\n  [O] = Available   [X] = Booked");
        System.out.printf("  Front (A-C): $%.2f | Middle (D-G): $%.2f | Back (H-J): $%.2f%n",
                PRICE_FRONT, PRICE_MIDDLE, PRICE_BACK);
        System.out.println();
    }

    private char[][] initializeSeats() {
        char[][] seatMap = new char[ROWS][SEATS_PER_ROW];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < SEATS_PER_ROW; j++) {
                seatMap[i][j] = 'O';
            }
        }
        return seatMap;
    }

    private double getSeatPrice(char rowLabel) {
        if (rowLabel >= 'A' && rowLabel <= 'C') return PRICE_FRONT;
        else if (rowLabel >= 'D' && rowLabel <= 'G') return PRICE_MIDDLE;
        else return PRICE_BACK;
    }

    private void bookTicketFlow() {
        displayMovies();

        System.out.print("Select a movie (enter number): ");
        int movieId;
        try {
            movieId = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }

        if (movieId < 1 || movieId > MOVIE_TITLES.length) {
            System.out.println("Invalid movie selection.");
            return;
        }

        int movieIndex = movieId - 1;
        System.out.println("\nYou selected: " + MOVIE_TITLES[movieIndex]);

        displayShowtimes(movieIndex);
        System.out.print("Select showtime (enter number): ");
        int timeChoice;
        try {
            timeChoice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        if (timeChoice < 1 || timeChoice > SHOWTIMES[movieIndex].length) {
            System.out.println("Invalid showtime selection.");
            return;
        }
        String selectedTime = SHOWTIMES[movieIndex][timeChoice - 1];

        char[][] seatMap = initializeSeats();
        displaySeats(seatMap);

        System.out.print("How many seats would you like to book? ");
        int numSeats;
        try {
            numSeats = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        if (numSeats < 1 || numSeats > 10) {
            System.out.println("You can book 1-10 seats at a time.");
            return;
        }

        ArrayList<String> seatsSelected = new ArrayList<>();
        for (int i = 0; i < numSeats; i++) {
            System.out.printf("Enter seat %d (e.g., A5, B3, J10): ", i + 1);
            String seatInput = scanner.nextLine().trim().toUpperCase();

            if (seatInput.length() < 2) {
                System.out.println("Invalid seat format. Use format like 'A5'.");
                return;
            }

            char row = seatInput.charAt(0);
            int seatNum;
            try {
                seatNum = Integer.parseInt(seatInput.substring(1));
            } catch (NumberFormatException e) {
                System.out.println("Invalid seat number.");
                return;
            }

            if (ROW_LABELS.indexOf(row) == -1) {
                System.out.println("Invalid row. Choose from " + ROW_LABELS + ".");
                return;
            }
            if (seatNum < 1 || seatNum > SEATS_PER_ROW) {
                System.out.printf("Invalid seat number. Choose 1-%d.%n", SEATS_PER_ROW);
                return;
            }

            int rowIndex = ROW_LABELS.indexOf(row);
            if (seatMap[rowIndex][seatNum - 1] == 'X') {
                System.out.printf("Seat %c%d is already booked. Choose another.%n", row, seatNum);
                return;
            }

            seatMap[rowIndex][seatNum - 1] = 'X';
            seatsSelected.add(row + "" + seatNum);
            System.out.printf("  Seat %c%d reserved!%n", row, seatNum);
        }

        System.out.print("\nEnter your name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        double total = 0;
        for (String seat : seatsSelected) {
            total += getSeatPrice(seat.charAt(0));
        }

        String bookingId = bookingService.generateBookingId();
        Booking booking = bookingService.createBooking(bookingId, name, MOVIE_TITLES[movieIndex],
                selectedTime, seatsSelected, total);
        displayBookingConfirmation(booking);
    }

    private void displayBookingConfirmation(Booking booking) {
        System.out.println("\n" + "=".repeat(55));
        System.out.println("         BOOKING CONFIRMATION");
        System.out.println("=".repeat(55));
        System.out.printf("  Booking ID  : %s%n", booking.getId());
        System.out.printf("  Name        : %s%n", booking.getCustomerName());
        System.out.printf("  Movie       : %s%n", booking.getMovieTitle());
        System.out.printf("  Showtime    : %s%n", booking.getShowtime());
        System.out.printf("  Date        : %s%n", booking.getTimestamp());
        System.out.println("-".repeat(55));
        System.out.println("  Seats Booked:");
        for (String seat : booking.getSeats()) {
            double price = getSeatPrice(seat.charAt(0));
            System.out.printf("    %s - $%.2f%n", seat, price);
        }
        System.out.println("-".repeat(55));
        System.out.printf("  TOTAL       : $%.2f%n", booking.getTotal());
        System.out.println("=".repeat(55));
        System.out.println("  Thank you for booking with Cineplex!");
        System.out.println("=".repeat(55));
    }

    private void cancelBooking() {
        System.out.print("\nEnter booking ID to cancel (e.g., BK1001): ");
        String bookingId = scanner.nextLine().trim().toUpperCase();

        Booking found = bookingService.findBookingById(bookingId);
        if (found != null) {
            System.out.printf("%nBooking Found:%n");
            System.out.printf("  Movie: %s | Showtime: %s%n", found.getMovieTitle(), found.getShowtime());
            System.out.printf("  Total: $%.2f%n", found.getTotal());

            System.out.print("Confirm cancellation? (yes/no): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            if (confirm.equals("yes")) {
                bookingService.cancelBooking(bookingId);
                System.out.printf("%nBooking %s has been cancelled successfully.%n", bookingId);
            } else {
                System.out.println("\nCancellation aborted.");
            }
        } else {
            System.out.printf("%nBooking %s not found.%n", bookingId);
        }
    }

    private void viewAllBookings() {
        ArrayList<Booking> allBookings = bookingService.getAllBookings();
        if (allBookings.isEmpty()) {
            System.out.println("\nNo bookings found.");
            return;
        }

        System.out.println("\n" + "=".repeat(55));
        System.out.println("         ALL BOOKINGS");
        System.out.println("=".repeat(55));
        for (Booking b : allBookings) {
            System.out.printf("%n  %s | %s | %s%n", b.getId(), b.getCustomerName(), b.getMovieTitle());
            System.out.printf("    Showtime: %s | Total: $%.2f%n", b.getShowtime(), b.getTotal());
        }
        System.out.println("\n" + "=".repeat(55));
    }
}
