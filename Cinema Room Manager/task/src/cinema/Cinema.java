package cinema;

import java.util.Scanner;

class CinemaRoom {
    private int currentIncome = 0;
    private int soldTickets = 0;
    private final int numSeats;
    private final int numRows;
    private final boolean[][] seatsMatrix;
    public CinemaRoom(int numRows, int numSeats) {
        this.numRows = numRows;
        this.numSeats = numSeats;
        this.seatsMatrix = new boolean[numRows][numSeats];
    }

    public int getPrice(int row) {
        boolean isBigRoom = this.numSeats * this.numRows > 60;
        int price;
        if (!isBigRoom) {
            price = 10;
        } else {
            price = row <= (this.numRows / 2)? 10 : 8;
        }
        return price;
    }

    public boolean buyTicket(int row, int seat) {
        if ((row > this.numRows) || (seat > this.numSeats) || (seat < 1) || (row < 1)) {
            System.out.println("Wrong input!");
            return false;
        } else {
            if (this.seatsMatrix[row - 1][seat - 1]) {
                System.out.println("That ticket has already been purchased!");
                return false;
            } else {
                this.seatsMatrix[row - 1][seat - 1] = true;
                this.soldTickets += 1;
                this.currentIncome += this.getPrice(row);
                return true;
            }
        }
    }

    private double getSoldPercentage() {
        return (double) this.soldTickets * 100 / (this.numRows * this.numSeats);
    }

    private long getTotalPrice() {
        long totalPrice = 0;
        for (int i = 1; i <= numRows; i++) {
            totalPrice += this.getPrice(i);
        }
        totalPrice *= numSeats;
        return totalPrice;
    }
    public void printStatistics() {
        System.out.printf("Number of purchased tickets: %d\n", this.soldTickets);
        System.out.printf("Percentage: %.2f%%\n", this.getSoldPercentage());
        System.out.printf("Current income: $%d\n", this.currentIncome);
        System.out.printf("Total income: $%d\n", this.getTotalPrice());
    }
    public void printSeatsMatrix() {
        System.out.println("\nCinema:");
        System.out.print(" ");
        for (int i = 1; i <= this.numSeats; i++) {
            System.out.print(" " + i);
        }
        System.out.print('\n');
        for (int i = 0; i < numRows; i++) {
            System.out.print(i + 1);
            for (int j = 0; j < numSeats; j++) {
                String seatSymbol = this.seatsMatrix[i][j] ? "B" : "S";
                System.out.print(' ' + seatSymbol);
            }
            System.out.println();
        }
    }
}

public class Cinema {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int numRows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int numSeats = scanner.nextInt();

        CinemaRoom cinemaRoom = new CinemaRoom(numRows, numSeats);
        while (true) {
            System.out.println("\n1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            int nextCommand = scanner.nextInt();
            if (nextCommand == 0) {
                break;
            } else if (nextCommand == 1) {
                cinemaRoom.printSeatsMatrix();
            } else if (nextCommand == 2) {
                while (true) {
                    System.out.println("Enter a row number:");
                    int row = scanner.nextInt();
                    System.out.println("Enter a seat number in that row:");
                    int seat = scanner.nextInt();
                    if (cinemaRoom.buyTicket(row, seat)) {
                        System.out.println("Ticket price: $" + cinemaRoom.getPrice(row));
                        break;
                    }

                }
            } else if (nextCommand == 3) {
                cinemaRoom.printStatistics();
            }
        }
    }
}