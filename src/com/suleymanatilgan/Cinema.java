package com.suleymanatilgan;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {
    static Scanner input = new Scanner(System.in);
    private static int totalIncome;
    private static int currentIncome;
    private static boolean isSmall;
    public static void main(String[] args) {
        String[][] room = createRoom();
        Menu(room);
    }

    public static void showSeats(String[][] room) {
        System.out.print("""

                Cinema:
                 \s""");
        for (int i = 0; i < room[0].length; i++)
            System.out.print(i + 1 + " ");
        System.out.println();
        for (int i = 0; i < room.length; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < room[i].length; j++) {
                System.out.print(room[i][j] + " ");
            } System.out.println();
        }
    }

    public static String[][] createRoom() {
        int row = 0;
        int rowSize = 0;
        totalIncome = 0;
        currentIncome = 0;
        boolean isCreated = false;

        while (!isCreated) {
            System.out.print("Enter the number of rows:\n" +
                    ">");
            row = input.nextInt();
            if (row < 1) {
                System.out.println("\nWrong input!\n");
                continue;
            }
            System.out.print("Enter the number of seats in each row:\n" +
                    ">");
            rowSize = input.nextInt();
            if (rowSize < 1) {
                System.out.println("\nWrong input!\n");
                continue;
            }
            isCreated = true;
        }

        if (row * rowSize <= 60) {
            totalIncome = row * rowSize * 10;
            isSmall = true;
        }
        else {
            int firstHalf = row / 2;
            int secondHalf = row - firstHalf;
            totalIncome = (10 * firstHalf + 8 * secondHalf) * rowSize;
            isSmall = false;
        }

        String[][] room = new String[row][rowSize];

        for (String[] strings : room) {
            Arrays.fill(strings, "S");
        }
        return room;
    }

    public static void buyTicket(String[][] room) {
        boolean isSuccess = false;
        while (!isSuccess) {
            System.out.print("""
                        
                    Enter a row number:
                    >""");
            int selectedRow = input.nextInt();
            if (selectedRow < 0 || selectedRow > room.length) {
                System.out.println("Wrong input!");
                return;
            }
            System.out.print("Enter a seat number in that row:\n" +
                    ">");
            int selectedSeat = input.nextInt();
            if (selectedSeat < 0 || selectedSeat > room.length) {
                System.out.println("Wrong input!");
                return;
            }


            if (!room[selectedRow - 1][selectedSeat - 1].equals("B")) {
                if (!isSmall && selectedRow > room.length / 2) {
                    currentIncome += 8;
                    System.out.println("\nTicket price: $8");
                } else {
                    currentIncome += 10;
                    System.out.println("\nTicket price: $10");
                }
                room[selectedRow - 1][selectedSeat - 1] = "B";
                isSuccess = true;
            } else {
                System.out.println("\nThat ticket has already been purchased!\n");
            }
        }
    }

    public static void Menu(String[][] room) {
        boolean isExit = false;
        while (!isExit){
            System.out.print("""
                
                1. Show the seats
                2. Buy a ticket
                3. Statistics
                0. Exit
                >""");
            switch (input.nextInt()) {
                case 1 -> showSeats(room);
                case 2 -> buyTicket(room);
                case 3 -> roomStatistics(room);
                case 0 -> isExit = true;
                default -> System.out.println("Wrong input!");
            }
        }
    }

    private static void roomStatistics(String[][] room) {
        int size = room.length * room[0].length;
        int b = 0;
        for (String[] row: room) {
            for (String seat: row) {
                if (seat.equals("B")) {
                    b++;
                }
            }
        }
        double percentage = 100.0 * b / size;
        System.out.printf("""
                                
                Number of purchased tickets: %d
                Percentage: %.2f%%
                Current income: $%d
                Total income: $%d
                """, b, percentage, currentIncome, totalIncome);
    }
}