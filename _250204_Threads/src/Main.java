public class Main {
    public static void main(String[] args) {

        MyCounter counter = new DefaultCounter();
        Thread thread1 = new Thread(new IncrementThread(counter));
        Thread thread2 = new Thread(new IncrementThread(counter));
        TimeTest.runThreads(counter, thread1, thread2);

        MyCounter atomicCounter = new AtomicCounter();
        Thread thread3 = new Thread(new IncrementThread(atomicCounter));
        Thread thread4 = new Thread(new IncrementThread(atomicCounter));
        TimeTest.runThreads(atomicCounter, thread3, thread4);

        MyCounter synchronizedCounter = new SynchronizedCounter();
        Thread thread5 = new Thread(new IncrementThread(synchronizedCounter));
        Thread thread6 = new Thread(new IncrementThread(synchronizedCounter));
        TimeTest.runThreads(synchronizedCounter, thread5, thread6);

    }
}
