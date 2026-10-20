package movieticket;

// Member 2: Seat handling
public class Seat {
    // 3 movies, 10 seats each (false = empty, true = booked)
    static boolean[][] seats = new boolean[3][10];

    static void showSeats(int movie) {
        System.out.println("---- Seats (X = booked) ----");
        for (int i = 0; i < 10; i++) {
            if (seats[movie][i] == true) {
                System.out.print((i + 1) + "[X] ");
            } else {
                System.out.print((i + 1) + "[ ] ");
            }
        }
        System.out.println();
    }

    static boolean bookSeat(int movie, int seatNo) {
        if (seatNo < 1 || seatNo > 10) {
            System.out.println("Invalid input! Seat must be 1 to 10.");
            return false;
        }
        if (seats[movie][seatNo - 1] == true) {
            System.out.println("Seat already booked!");
            return false;
        }
        seats[movie][seatNo - 1] = true;
        return true;
    }
}
