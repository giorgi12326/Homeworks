package HomeWorks;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class BlockingQueueSemaphores<T> {

    private final Queue<T> items = new LinkedList<>();
    private final int capacity;

    // Semaphore to track the number of available items
    private final Semaphore itemsSemaphore = new Semaphore(0);

    // Semaphore to track the available space (optional if there is a capacity limit)
    private final Semaphore spaceSemaphore;

    // Semaphore for exclusive access to the queue (binary semaphore)
    private final Semaphore accessSemaphore = new Semaphore(1);

    public BlockingQueueSemaphores(int capacity) {
        this.capacity = capacity;
        this.spaceSemaphore = new Semaphore(capacity);  // Initialize with the max capacity
    }

    public void put(T item) throws InterruptedException {
        spaceSemaphore.acquire();
        accessSemaphore.acquire();

        items.add(item);

        accessSemaphore.release();
        itemsSemaphore.release();
    }

    public T get() throws InterruptedException {
        itemsSemaphore.acquire();
        accessSemaphore.acquire();

        T item = items.poll();

        accessSemaphore.release();
        spaceSemaphore.release();
        return item;
    }
}
