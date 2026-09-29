package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/*
This class is used to load resources from the classpath. It provides a method to get an
InputStream for a given resource path. classpath means its stored under src/test/resources folder.
If classpath resource is not found, it will check the filesystem. Filesystem means we have
to provide the absolute path of the file. For example, if we have a file in /home/user/test.txt, we can provide the path as /home/user/test.txt.
 */

public class ResourceLoader {
    private static final Logger logger = LoggerFactory.getLogger(ResourceLoader.class);
    public static InputStream getResources(String path) throws IOException {
        logger.info("reading resource from path: {}", path);
        InputStream resourceStream = ResourceLoader.class.getClassLoader().getResourceAsStream(path);
        if (Objects.nonNull(resourceStream)) {
            return resourceStream;
        }
        return Files.newInputStream(Path.of(path)); //this will check in filesystem
    }


    //create a test class to check how it works
}
