import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

/**
 * Created by UZIELS on 1/3/2018.
 */
public class MyTest {

    private static Map<Long, Long> testMap = null;
    private static Random rnd = new Random();
    private static final Long MAP_SIZE = 10000L;
    private static final int GET_TIMES = 10000000;
    static synchronized Long getByPutAndGet(Long key){
        Long temp = null;
        if (key != null){
            if ((temp = testMap.get(key)) == null){
                temp = key * rnd.nextLong();
                testMap.put(key, temp);
            }
        }
        return temp;
    }

    static Long getByComputeIfAbsent(Long key){
        if (key != null){
            return testMap.computeIfAbsent(key, k -> k * rnd.nextLong());
        }
        return null;
    }

    public static void main (String args[]){
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "70");
        for (int j = 0; j< 100; j++) {
            System.out.println("Cycle #" + (j + 1));
            System.out.println("*** Non-Concurrent ***");
            System.out.println("ConcurrentHashMap");
            testMap = new ConcurrentHashMap<>(1000);

            Long startTime = System.nanoTime();
            for (int i = 0; i < GET_TIMES; i++) {
                Long key = rnd.nextLong() % MAP_SIZE;
                Long val = getByComputeIfAbsent(key);
            }
            System.out.println("Get By compute if absent took ns: " + (System.nanoTime() - startTime));

            testMap = new ConcurrentHashMap<>(1000);
            startTime = System.nanoTime();
            for (int i = 0; i < GET_TIMES; i++) {
                Long key = rnd.nextLong() % MAP_SIZE;
                Long val = getByPutAndGet(key);
            }
            System.out.println("Get By put and get took ns: " + (System.nanoTime() - startTime));
            System.out.println("HashTable");
            testMap = new Hashtable<>(1000);

            startTime = System.nanoTime();
            for (int i = 0; i < GET_TIMES; i++) {
                Long key = rnd.nextLong() % MAP_SIZE;
                Long val = getByComputeIfAbsent(key);
            }
            System.out.println("Get By compute if absent took ns: " + (System.nanoTime() - startTime));

            testMap = new ConcurrentHashMap<>(1000);
            startTime = System.nanoTime();
            for (int i = 0; i < GET_TIMES; i++) {
                Long key = rnd.nextLong() % MAP_SIZE;
                Long val = getByPutAndGet(key);
            }
            System.out.println("Get By put and get took ns: " + (System.nanoTime() - startTime));

            System.out.println("*** Concurrent ***");
            System.out.println("ConcurrentHashMap");
            startTime = System.nanoTime();
            IntStream.of(GET_TIMES)
                    .parallel()
                    .forEach(p -> {
                                Long key = rnd.nextLong() % MAP_SIZE;
                                Long val = getByComputeIfAbsent(key);
                            }
                    );
            System.out.println("Get By compute if absent took ns: " + (System.nanoTime() - startTime));

            testMap = new ConcurrentHashMap<>(1000);
            startTime = System.nanoTime();
            IntStream.of(GET_TIMES)
                    .parallel()
                    .forEach(p -> {
                                Long key = rnd.nextLong() % MAP_SIZE;
                                Long val = getByPutAndGet(key);
                            }
                    );

            System.out.println("Get By put and get took ns: " + (System.nanoTime() - startTime));
            System.out.println("HashTable");
            testMap = new Hashtable<>(1000);

            startTime = System.nanoTime();
            IntStream.of(GET_TIMES)
                    .parallel()
                    .forEach(p -> {
                                Long key = rnd.nextLong() % MAP_SIZE;
                                Long val = getByComputeIfAbsent(key);
                            }
                    );

            System.out.println("Get By compute if absent took ns: " + (System.nanoTime() - startTime));

            testMap = new ConcurrentHashMap<>(1000);
            startTime = System.nanoTime();
            IntStream.of(GET_TIMES)
                    .parallel()
                    .forEach(p -> {
                                Long key = rnd.nextLong() % MAP_SIZE;
                                Long val = getByPutAndGet(key);
                            }
                    );

            System.out.println("Get By put and get took ns: " + (System.nanoTime() - startTime));
        }
    }
}
