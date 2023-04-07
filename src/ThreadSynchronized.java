import java.util.ArrayList;

public class ThreadSynchronized extends Thread{
    static public Long result = 0L;
     public long waitingTime = 0;
    private long array[];
    private int start, elements;
    ThreadSynchronized(int start, int elements, long arr[]){
        this.start = start;
        this.elements = elements;
        this.array = arr;
    }
    public void run(){
        long startWaiting, finishWaiting;
        for(int i = start; i < elements; i++){
            startWaiting = System.nanoTime();
            synchronized (array){
                finishWaiting = System.nanoTime();
                waitingTime+=finishWaiting-startWaiting;
                if(i % 2 == 1){
                    result ^= array[i];
                }
            }
        }
       System.out.println("waiting time of " + Thread.currentThread().getName() + " = " + (double)waitingTime / 1000000 + "ms");
    }
}
