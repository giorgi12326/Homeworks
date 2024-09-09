package HomeWorks;

import java.util.ArrayList;
import java.util.List;

public class BlockingQueue<T> {

    List<T> items = new ArrayList<>();

    Object mutex = new Object();

    public void put(T item) {
        items.add(item);
        synchronized (mutex) {
            mutex.notify();
        }
    }


    public T get() {
        while (items.isEmpty()) {
            synchronized (mutex) {
                try {
                    mutex.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return items.remove(0);
    }
}
//you can also do it this way
//import java.util.ArrayList;
//import java.util.List;
//
//public class BlockingQueue<T> {
//
//    List<T> items = new ArrayList<>();
//
//    Object mutex = new Object();
//
//    public void put(T item) {
//        synchronized (mutex) {
//            items.add(item);
//            mutex.notify();
//        }
//    }
//
//    public T get() {
//        synchronized (mutex) {
//            while (items.isEmpty()) {
//                try {
//                    mutex.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            return items.remove(0);
//        }
//    }
//}