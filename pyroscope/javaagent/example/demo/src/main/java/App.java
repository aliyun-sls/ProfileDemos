import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    public static final int N_THREADS = 8;

    public static void main(String[] args) {
        appLogic();
    }

    private static void appLogic() {
        ExecutorService executors = Executors.newFixedThreadPool(N_THREADS);
        for (int i = 0; i < N_THREADS; i++) {
            executors.submit(() -> {
                    while (true) {
                        try {
                            fib(32L);
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                }
            );
        }
    }

    private static Map<String, String> mapOf(String... args) {
        Map<String, String> staticLabels = new HashMap<>();
        for (int i = 0; i < args.length; i += 2) {
            staticLabels.put(args[i], args[i + 1]);
        }
        return staticLabels;
    }

    private static long fib(Long n) throws InterruptedException {
        if (n == 0L) {
            return 0L;
        }
        if (n == 1L) {
            return 1L;
        }
        Thread.sleep(100);
        return fib(n - 1) + fib(n - 2);
    }
}
