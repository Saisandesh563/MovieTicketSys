package movieticket;

// Member 3: Ticket printing
public class Booking {
    static void printTicket(String name, int movie, int seatNo) {
        System.out.println("\n===== TICKET =====");
        System.out.println("Name  : " + name);
        System.out.println("Movie : " + Movie.names[movie]);
        System.out.println("Seat  : " + seatNo);
        System.out.println("Price : Rs " + Movie.prices[movie]);
        System.out.println("==================");
    }
}
