import io.pyroscope.http.Format;
import io.pyroscope.javaagent.EventType;
import io.pyroscope.javaagent.PyroscopeAgent;
import io.pyroscope.javaagent.Snapshot;
import io.pyroscope.javaagent.api.Exporter;
import io.pyroscope.javaagent.api.Logger;
import io.pyroscope.javaagent.config.Config;
import io.pyroscope.javaagent.impl.DefaultConfigurationProvider;
import io.pyroscope.labels.Pyroscope;
import io.pyroscope.labels.LabelsSet;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    public static final int N_THREADS = 8;

    public static void main(String[] args) {
        PyroscopeAgent.start(
            new PyroscopeAgent.Options.Builder(
                new Config.Builder()
                    .setApplicationName("java.demo.app")
                    // profiling events: itimer, cpu, wall. The default is itimer. (difference between itimer mode and cpu mode: https://github.com/async-profiler/async-profiler/issues/272)
                    .setProfilingEvent(EventType.WALL)
                    // sets the allocation threshold to register the events, in bytes (equivalent to --alloc= in async-profiler). The default value is "" - empty string, which means that allocation profiling is disabled. Setting it to 0 will register all the events.
                    .setProfilingAlloc("0")
                    // sets the lock threshold to register the events, in nanoseconds (equivalent to --lock= in async-profiler). The default value is "" - empty string, which means that lock profiling is disabled. Setting it to 0 will register all the events.
                    .setProfilingLock("0")
                    .setServerAddress("http://logtail-kubernetes-metrics.sls-monitoring:4040")
                    // sets the profiler output format. The default is collapsed, but in order to support multiple formats it must be set to jfr.
                    .setFormat(Format.JFR)
                    .setLogLevel(Logger.Level.DEBUG)
                    // sets static labels
                    .setLabels(mapOf("host", "java-host", "environment", "test", "version", "0.0.0"))
                    .build())
                .build()
        );
        appLogic();
    }

    private static void appLogic() {
        ExecutorService executors = Executors.newFixedThreadPool(N_THREADS);
        for (int i = 0; i < N_THREADS; i++) {
            executors.submit(() -> {
                Pyroscope.LabelsWrapper.run(new LabelsSet("thread_name", Thread.currentThread().getName()), () -> {
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
            });
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

    private static class MyStdoutExporter implements Exporter {
        @Override
        public void export(Snapshot snapshot) {
            System.out.printf("Export %d %d%n", snapshot.data.length, snapshot.labels.toByteArray().length);
        }
    }
}
