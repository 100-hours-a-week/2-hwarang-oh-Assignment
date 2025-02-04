import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter implements MyCounter {
    private AtomicInteger value;

    public AtomicCounter() {
        this.value = new AtomicInteger();
    }

    public void increment() {
        this.value.incrementAndGet();
    }

    public int getValue() {
        return this.value.get();
    }

    @Override
    public String toString() {
        return "Atomic Counter";
    }
}