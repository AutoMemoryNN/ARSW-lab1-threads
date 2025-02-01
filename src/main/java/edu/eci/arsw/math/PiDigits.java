package edu.eci.arsw.math;

import java.util.ArrayList;
import java.util.List;
import edu.eci.arsw.threads.*;

public class PiDigits {
    private static List<PiDigitsThreads> threadList = new ArrayList<>();
    private static byte[] digitArray;

    /**
     * Computes Pi digits using multiple threads.
     *
     * @param startIndex The starting index.
     * @param digitCount The number of digits to compute.
     * @param threadCount The number of threads to use.
     * @return A byte array containing the computed Pi digits.
     */
    public static byte[] getDigits(int startIndex, int digitCount, int threadCount) {
        int digitsPerThread = digitCount / threadCount;
        digitArray = new byte[digitCount];
        int extra = digitCount % threadCount;

        threadList.add(new PiDigitsThreads(startIndex, digitsPerThread + extra));
        threadCount--;
        startIndex += extra;

        for (int i = 0; i < threadCount; i++) {
            threadList.add(new PiDigitsThreads(startIndex + digitsPerThread, digitsPerThread));
            startIndex += digitsPerThread;
        }

        for (Thread t : threadList) t.start();

        try {
            int index = 0;
            for (PiDigitsThreads t : threadList) {
                t.join();
                for (byte b : t.getDigitsPi()) {
                    digitArray[index++] = b;
                }
            }
        } catch (Exception e) {
            System.out.println("Error in thread execution");
        }
        return digitArray;
    }

    /**
     * Computes Pi digits using a single thread.
     *
     * @param startIndex The starting index.
     * @param digitCount The number of digits to compute.
     * @return A byte array containing the computed Pi digits.
     */
    public byte[] computeDigits(int startIndex, int digitCount) {
        PiDigitsThreads thread = new PiDigitsThreads(startIndex, digitCount);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return thread.getDigitsPi();
    }

    /**
     * Main method to test Pi digit computation.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        PiDigits piDigits = new PiDigits();
        int startIndex = 0;
        int digitCount = 1_000_000; // One million digits

        long startTime = System.currentTimeMillis();
        byte[] digits = piDigits.computeDigits(startIndex, digitCount);
        long endTime = System.currentTimeMillis();

        System.out.println("Execution time with a single thread: " + (endTime - startTime) + " ms");
    }
}
