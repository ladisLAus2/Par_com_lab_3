import java.util.Random;

public class Main {
    static long start, finish;
    static int threadNumber = 6;

    public static void singleNoBlock(long arr[]){
        start = System.nanoTime();
        long result = 0;
        for(int i = 0; i < arr.length; i++){
            if(i % 2 == 1){
                result ^= arr[i];
            }
        }
        finish = System.nanoTime();
        System.out.println("Time single thread without blocking: " + (double)(finish - start)/1000000 + "ms");
        System.out.println("The result single thread without blocking: " + result);
    }

    public static void multipleThreadsBlock(long arr[]){
        int begin = 0;
        int numberOfElements = arr.length / threadNumber;
        Thread threads[] = new Thread[threadNumber];
        for(int i = 0;i < threadNumber; i++){
            if(i == threadNumber-1){
                threads[i] = new ThreadSynchronized(begin, arr.length,arr);
                continue;
            }
            threads[i] = new ThreadSynchronized(begin, begin + numberOfElements, arr);
            begin += numberOfElements;
        }
        start = System.nanoTime();
        for(int i = 0; i < threadNumber; i++){
            threads[i].start();
        }
        for(int i = 0; i < threadNumber; i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        finish = System.nanoTime();
        System.out.println("Time multiple threads blocking: " + (double)(finish - start)/1000000 + "ms");
        System.out.println("The result multiple threads blocking: " + ThreadSynchronized.result);

    }

    public static void multipleAtomic(long arr[]){
        int begin = 0;
        int numberOfElements = arr.length / threadNumber;
        Thread threads[] = new Thread[threadNumber];
        for(int i = 0;i < threadNumber; i++){
            if(i == threadNumber-1){
                threads[i] = new ThreadAtomic(begin, arr.length,arr);
                continue;
            }
            threads[i] = new ThreadAtomic(begin, begin + numberOfElements, arr);
            begin += numberOfElements;
        }
        start = System.nanoTime();
        for(int i = 0; i < threadNumber; i++){
            threads[i].start();
        }
        for(int i = 0; i < threadNumber; i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        finish = System.nanoTime();
        System.out.println("Time multiple threads atomic: " + (double)(finish - start)/1000000 + "ms");
        System.out.println("The result multiple threads blocking: " + ThreadAtomic.result);

    }

    public static void main(String[] args) {
        long[] array = new long[100000000];
        for(int i = 0; i < array.length; i++){
            array[i] = new Random().nextLong(1000);
        }
//        for(int i = 0; i < array.length; i++){
//            if(i % 2 == 1)
//                System.out.print(array[i]+",");
//        }

        singleNoBlock(array);
        multipleThreadsBlock(array);
        multipleAtomic(array);
    }
}