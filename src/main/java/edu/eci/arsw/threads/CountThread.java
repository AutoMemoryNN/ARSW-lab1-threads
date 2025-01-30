package edu.eci.arsw.threads;

public class CountThread implements Runnable {

    private final int start;
    private final int end;
    private Thread thread;

    public CountThread(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void run() {
        for (int i = start; i <= end; i++) {
            System.out.println(i);
        }
    }

    public void interrupt() {
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
            System.out.println("Thread interrupted.");
        }
    }

}
