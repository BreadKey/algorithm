package step1_io.print_input;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
    }
}
