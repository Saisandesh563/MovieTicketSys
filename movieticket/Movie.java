package movieticket;

// Member 1: Movie details
public class Movie {
    static String[] names = {"Avengers", "Pushpa 2", "Jawan"};
    static int[] prices = {200, 150, 180};

    static void showMovies() {
        System.out.println("---- Movies ----");
        for (int i = 0; i < names.length; i++) {
            System.out.println((i + 1) + ". " + names[i] + " - Rs " + prices[i]);
        }
    }
}
