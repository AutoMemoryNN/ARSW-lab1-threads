/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

/**
 * @author hcadavid
 */
public class CountThreadsMain {

    public static void main(String[] a) {
        CountThread t1 = new CountThread(0, 99);
        CountThread t2 = new CountThread(100, 199);
        CountThread t3 = new CountThread(200, 299);

        System.out.println("Ejecutando con start():");
        t1.start();
        t2.start();
        t3.start();

        try {
            Thread.sleep(1000); // wait to avoid overlap
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n run()-------------------------");
        t1.run();
        t2.run();
        t3.run();
    }

}
