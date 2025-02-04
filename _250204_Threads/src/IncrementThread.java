
public class IncrementThread implements Runnable {
    private MyCounter counter;

    public IncrementThread(MyCounter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000000; i++) {
            this.counter.increment();
        }
    }
}
