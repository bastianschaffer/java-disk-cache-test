# java-disk-cache-test

This is a test implementation of the [Apache Commons Java Caching System](https://commons.apache.org/proper/commons-jcs/)

To run the test, run the "mainTest()" method in [TestDiskCache.java](src/test/java/TestDiskCache.java).The cache is in the [caching.ccf](src/main/resources/cache.ccf) file configured to cache 0 objects on memory and 100 on the disk. To check if the data is really on the disk, you can set "fillCache" to false and run the test again, but this only works if the test ran at least once with "fillCache" set to true. The cached data is automatically saved at target/jcs-cache-db/

## helpful links
https://commons.apache.org/proper/commons-jcs/IndexedDiskAuxCache.html

https://commons.apache.org/proper/commons-jcs/IndexedDiskCacheProperties.html



