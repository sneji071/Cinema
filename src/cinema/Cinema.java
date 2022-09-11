package cinema;

import java.util.Arrays;
import java.util.Scanner;


public class Cinema {
    protected static int purchasedTicketsNumber = 0;
    protected static int currentIncome = 0;
    protected static int totalIncome = 0;

    protected static int totalSeats = 0;

    protected static void printMenu() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }
    protected static void showSeats(String[][] seatsStore) {
        System.out.println("Cinema:");
        System.out.print("  ");
        for (int i = 0; i < seatsStore.length; i++) {
            if (i == 0) {
                for (int j = 0; j < seatsStore[i].length; j++) {
                    if (!(j + 1 < seatsStore[i].length)) {
                        System.out.print((j + 1) + " \n");
                    } else {
                        System.out.print((j + 1) + " ");
                    }
                }
            }
            System.out.print((i + 1) + " ");
            for (int j = 0; j < seatsStore[i].length; j++) {
                if (!(j + 1 < seatsStore[i].length)) {
                    System.out.print(seatsStore[i][j] + " \n");
                } else {
                    System.out.print(seatsStore[i][j] + " ");
                }
            }
        }
    }

    /**
     * If the total number of seats in the screen room is not more than 60,
     * then the price of each ticket is 10 dollars.
     * In a larger room, the tickets are 10 dollars for the front half of
     * the rows and 8 dollars for the back half. Please note that the number
     * of rows can be odd, for example, 9 rows. In this case, the first half
     * is the first 4 rows, and the second half is the last 5 rows.
     *
     * @param row
     * @param col
     * @param rowSeat
     * @return
     */
    public static int calculateTicketCost(int row, int col, int rowSeat) {
        int ticketCost;

        if (row * col < 60) {
            ticketCost = 10;
        } else {
            if (rowSeat <= row / 2) {
                ticketCost = 10;
            } else {
                ticketCost = 8;
            }
        }

        return ticketCost;
    }

    public static void showStatistics() {
        System.out.println("Number of purchased tickets: " + purchasedTicketsNumber);
        System.out.println(String.format("Percentage: %.2f", ((double) purchasedTicketsNumber / (double) totalSeats) * 100) + "%");
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);
    }

    protected static void buyTicket(String[][] seatsStore, int rows, int seats) {
        Scanner scanner = new Scanner(System.in);
        int row, seat, price;
        while (true) {
            System.out.println("Enter a row number:");
            row = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter a seat number in that row:");
            seat = Integer.parseInt(scanner.nextLine());
            if (seatsStore.length < row || row < 0 || seatsStore[row - 1].length < seat || seat < 0) {
                System.out.println("Wrong input!");
            } else if ("B".equals(seatsStore[row - 1][seat - 1])) {
                System.out.println("That ticket has already been purchased!");
            } else {
                price = calculateTicketCost(rows, seats, row);
                purchasedTicketsNumber++;
                currentIncome += price;
                break;
            }
        }
        seatsStore[row - 1][seat - 1] = "B";
        System.out.println("Ticket price: $" + price);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rows = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter the number of seats in each row:");
        int seats = Integer.parseInt(scanner.nextLine());
        String[][] seatsStore = new String[rows][seats];
        for (String[] seatsRow : seatsStore) {
            Arrays.fill(seatsRow, "S");
        }
        // here we need to calculate total income.
        for(int i = 0 ; i < seatsStore.length; i++) {
            int seatPrice =  calculateTicketCost(rows, seats, i + 1);
            totalIncome += (seatPrice * seats);
        }
        totalSeats = rows * seats;
        while (true) {
            printMenu();
            int userChoice = Integer.parseInt(scanner.nextLine());
            if (userChoice == 0) {
                break;
            }
            switch (userChoice) {
                case 1 -> showSeats(seatsStore);
                case 2 -> {
                    buyTicket(seatsStore, rows, seats);
                    showSeats(seatsStore);
                }
                case 3 -> showStatistics();
            }
        }
    }
}