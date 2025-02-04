public class SynchronizedCounter implements MyCounter {
    private int value;

    public SynchronizedCounter() {
        this.value = 0;
    }

    public synchronized void increment() {
        this.value++;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "Synchronized Counter";
    }
}
