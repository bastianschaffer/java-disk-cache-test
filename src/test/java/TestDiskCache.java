import org.apache.commons.jcs3.JCS;
import org.apache.commons.jcs3.access.CacheAccess;
import org.apache.commons.jcs3.access.exception.CacheException;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDiskCache {

    private CacheAccess<String, Set<String>> jcsCache = null;
    private final int cachedCriteriaAmount = 11;
    private final int idsPerCriterion = 1;
    private final boolean fillCache = true;
    private HashMap<String, Set<String>> testData;

    @Test
    public void mainTest() throws InterruptedException {
        testData = generateData();

        try{
            jcsCache = JCS.getInstance("default");
        }catch(CacheException e){
            System.out.printf("Problem initializing cache: %s%n", e.getMessage() );
        }

        if(fillCache){
            putDataToJcs();
            TimeUnit.SECONDS.sleep(1); //without this sleep, the cache would for some reason find all values, although they aren't (/shouldn't be) in the cache
        }

        String keyTemplate = "someKey-";
        float totalTime = 0;
        for(int i = 0; i < cachedCriteriaAmount; i++){
            String key = keyTemplate + i;
            Set<String> valueSet = testData.get(key);

            long startTime = System.nanoTime();
            Set<String> foundValues = jcsGet(key);
            long endTime = System.nanoTime();

            //assertEquals(valueSet, foundValues);
            System.out.println("found for key [" + key + "]: " + foundValues);
            totalTime += (float)(endTime - startTime) / 1000000.0;
        }
        System.out.printf("It took on average %fms to get an entry%n", totalTime / cachedCriteriaAmount);
    }

    private void putDataToJcs(){
        String keyTemplate = "someKey-";
        for(int i = 0; i < cachedCriteriaAmount; i++){
            String key = keyTemplate + i;
            Set<String> valueSet = testData.get(key);
            jcsPut(key, valueSet);
        }
    }


    private HashMap<String, Set<String>> generateData(){
        HashMap<String, Set<String>> data = new HashMap<>();
        String keyTemplate = "someKey-";
        for(int i = 0; i < cachedCriteriaAmount; i++){
            String key = keyTemplate + i;
            String valueTemplate = "val-";
            Set<String> valueSet = new HashSet<>();
            for(int j = 0; j < idsPerCriterion; j++){
                valueSet.add(valueTemplate + (i + j) );
            }

            data.put(key, valueSet);
        }
        return data;
    }

    public void jcsPut(String key, Set<String> values){
        try
        {
            jcsCache.put(key, values);
        }
        catch (CacheException e)
        {
            System.out.printf("Problem putting entry in the cache, for key %s%n%s%n", key, e.getMessage());
        }
    }

    public Set<String> jcsGet(String key){
        return jcsCache.get(key);
    }
}
