package movieticket;

import java.util.Scanner;

// Member 4: Main menu
public class Main {

    // keeps asking until the user types a real number
    static int readNumber(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input! Please enter a number.");
            sc.next(); // throw away the wrong input
            System.out.print("Try again: ");
        }
        return sc.nextInt();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice = 0;

        while (choice != 3) {
            System.out.println("\n===== MOVIE TICKET BOOKING =====");
            System.out.println("1. Book Ticket");
            System.out.println("2. View Seats");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            choice = readNumber(sc);

            if (choice == 1) {
                Movie.showMovies();
                System.out.print("Select movie: ");
                int movie = readNumber(sc) - 1;

                if (movie < 0 || movie > 2) {
                    System.out.println("Invalid input! Choose movie 1 to 3.");
                } else {
                    Seat.showSeats(movie);
                    System.out.print("Enter your name: ");
                    String name = sc.next();
                    System.out.print("Enter seat number: ");
                    int seatNo = readNumber(sc);

                    if (Seat.bookSeat(movie, seatNo)) {
                        Booking.printTicket(name, movie, seatNo);
                    }
                }
            } else if (choice == 2) {
                Movie.showMovies();
                System.out.print("Select movie: ");
                int movie = readNumber(sc) - 1;
                if (movie >= 0 && movie <= 2) {
                    Seat.showSeats(movie);
                } else {
                    System.out.println("Invalid input! Choose movie 1 to 3.");
                }
            } else if (choice == 3) {
                System.out.println("Thank you! Bye.");
            } else {
                System.out.println("Invalid input! Choose 1, 2 or 3.");
            }
        }
        sc.close();
    }
}
