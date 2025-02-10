package com.ktb.automessage.utils;

import java.util.Scanner;

import com.ktb.automessage.exception.DefaultEmptyException;

public class ConsoleIOUtil {
    private final Scanner scanner;
    private String userInput;

    public ConsoleIOUtil() {
        this.scanner = new Scanner(System.in);
    }

    public ConsoleIOUtil(Scanner scanner) {
        this.scanner = scanner;
    }

    public void defaultPrint(String message) {
        System.out.println(message);
    }

    public void delayPrint(String message) throws InterruptedException {
        printWithDelay(message, 50);
    }

    public String defaultPrintWithInput(String prompt) {
        return getInput(prompt);
    }

    public String defaultPrintWithInput(String prompt, Exception customException) {
        return getInput(prompt, customException);
    }

    public void closeScanner() {
        this.scanner.close();
    }

    private void printWithDelay(String message, int delay) throws InterruptedException {
        for (char c : message.toCharArray()) {
            System.out.print(c);
            System.out.flush();
            Thread.sleep(delay);
        }
    }

    private String getInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            this.userInput = scanner.nextLine().trim();
            try {
                if (this.userInput.isEmpty())
                    throw new DefaultEmptyException();
                else
                    break;
            } catch (DefaultEmptyException e) {
                System.out.println(e.getMessage());
            }
        }
        return this.userInput;
    }

    private String getInput(String prompt, Exception customException) {
        while (true) {
            System.out.print(prompt);
            this.userInput = scanner.nextLine().trim();
            try {
                if (this.userInput.isEmpty())
                    throw customException;
                else
                    break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return this.userInput;
    }
}