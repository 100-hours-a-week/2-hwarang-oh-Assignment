public class DefaultCounter implements MyCounter {
    private int value;

    public DefaultCounter() {
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
        return "Default Counter";
    }
}
