package cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CacheImpl {

    public CacheImpl() {

    }

    private LoadingCache<String, LocalDateTime> setupCache() {
        final CacheLoader<String, LocalDateTime> loader =  new CacheLoader<>() {
            @Override
            public LocalDateTime load(String key) throws Exception {
                return null;
            }
        };

        return CacheBuilder.newBuilder()
                .expireAfterAccess(Duration.ofMillis(120000))
                .build(loader);
    }


    public static void main(String[] args) throws Exception {
        final CacheImpl cacheImpl = new CacheImpl();
        final LoadingCache<String, LocalDateTime> cache = cacheImpl.setupCache();

        final Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an id");
        String line;
        while((line = scanner.nextLine())!= null) {
            cache.put("portfolio_" + String.valueOf(line), LocalDateTime.now());
            System.out.println("Size of cache : " + cache.size());
            ConcurrentMap<String, LocalDateTime> map = cache.asMap();
            final Set<String> keySet = map.keySet();
            for (final String s : keySet) {
                System.out.println("key="+s+", value="+cache.get(s));
            }
            System.out.println("=============================================");
        }

    }

}
