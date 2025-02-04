public class TimeTest {

    public static void runThreads(MyCounter counter, Thread t1, Thread t2) {

        long start = System.nanoTime();
        t1.start();
        t2.start();
        long end = System.nanoTime();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(counter.toString());
        System.out.println(counter.getValue());
        System.out.println("Time: " + (end - start));
    }
}
