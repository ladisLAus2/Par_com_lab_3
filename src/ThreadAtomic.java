import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class ThreadAtomic extends Thread{
    private long array[];
    static public AtomicLong result = new AtomicLong(0L);
    private int start, elements;
    ThreadAtomic(int start, int elements, long arr[]){
        this.start = start;
        this.elements = elements;
        this.array = arr;
    }
    public void run(){
        for(int i = start; i < elements; i++){
                if(i % 2 == 1){
                    long oldValue, newValue;
                    do{
                        oldValue = result.get();
                        newValue = oldValue ^ array[i];
                    }while(!result.compareAndSet(oldValue, newValue));
                }
        }
    }
}
