package step2_arithmetic_operation.sugar_delivery;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int countOf5KgBag = n / 5;
        int countOf3KgBag = 0;
        int totalBag;

        for (; countOf5KgBag >= 0; countOf5KgBag--) {
            int remain = n - countOf5KgBag * 5;
            if (remain % 3 == 0) {
                countOf3KgBag = remain / 3;
                break;
            }
        }

        totalBag = countOf5KgBag + countOf3KgBag;
        if (totalBag == 0) {
            System.out.println(-1);
        }
        else {
            System.out.println(totalBag);
        }
    }
}