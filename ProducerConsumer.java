package com.java;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProducerConsumer {

    public static void main(String args[]) {
        Vector sharedQueue = new Vector();
        int size = 4;
        Thread prodThread = new Thread(new Producer(sharedQueue, size), "Producer");
        Thread consThread = new Thread(new Consumer(sharedQueue, size), "Consumer");
        prodThread.start();
        consThread.start();
    }
}

class Producer implements Runnable {

    private final Vector sharedVariable;
    private final int SIZE;

    public Producer(Vector sharedVariable, int size) {
        this.sharedVariable = sharedVariable;
        this.SIZE = size;
    }

    @Override
    public void run() {
        for (int i = 0; i < 7; i++) {
            System.out.println("Produced: " + i);
            try {
                producer(i);
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void producer(int i) throws InterruptedException {

        //wait if queue is full
        while (sharedVariable.size() == SIZE) {
            synchronized (sharedVariable) {
                System.out.println("Queue is full " + Thread.currentThread().getName()
                        + " is waiting , size: " + sharedVariable.size());

                sharedVariable.wait();
            }
        }
        synchronized (sharedVariable) {
            sharedVariable.add(i);
            sharedVariable.notifyAll();
        }
    }
}

class Consumer implements Runnable {

    private final Vector sharedVariable;
    private final int SIZE;

    public Consumer(Vector sharedVariable, int size) {
        this.sharedVariable = sharedVariable;
        this.SIZE = size;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Consumed: " + consumer());
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private int consumer() throws InterruptedException {
        //wait if queue is empty
        while (sharedVariable.isEmpty()) {
            synchronized (sharedVariable) {
                System.out.println("Queue is empty " + Thread.currentThread().getName()
                        + " is waiting , size: " + sharedVariable.size());

                sharedVariable.wait();
            }
        }
        synchronized (sharedVariable) {
            sharedVariable.notifyAll();
            return (Integer) sharedVariable.remove(0);
        }
    }
}


