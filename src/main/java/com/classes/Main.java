package com.classes;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Main {

    public static boolean check(String message) {
        boolean res = false;
        switch (message.toLowerCase()) {
            case "y":
            case "yes":
                res = true;
        }
        return res;
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Consumer? (y/n):");
        String con = scanner.nextLine();
        System.out.println("Producer? (y/n):");
        String pr = scanner.nextLine();
        System.out.println("Publisher? (y/n):");
        String pu = scanner.nextLine();
        System.out.println("Subscriber? (y/n):");
        String su = scanner.nextLine();


        Consumer consumer = (check(con)) ? new Consumer() : null;
        Producer producer = (check(con)) ? new Producer() : null;
        Publisher publisher = (check(con)) ? new Publisher() : null;
        Subscriber subscriber = (check(con)) ? new Subscriber() : null;
    }
}