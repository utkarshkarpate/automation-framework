package utils;

import com.example.tests.vendorPortal.model.VendorPortalTestData;

import java.io.IOException;

/*
This is just a demo class to check how the ResourceLoader class works.
It will read a file from the classpath and print its content to the console.
 */
public class Demo {
    public static void main(String[] args) throws IOException {
        /*InputStream stream_fileSystem = ResourceLoader.getResources("dummy.txt");
        InputStream stream_classPath     = ResourceLoader.getResources("test-suites/dummy.txt");

        //Keeping the file in resources folder
        String content_fileSystem = IOUtils.toString(stream_fileSystem, StandardCharsets.UTF_8);
        System.out.println(content_fileSystem);

        String content_classPath = IOUtils.toString(stream_classPath, StandardCharsets.UTF_8);
        System.out.println(content_classPath);*/

        //reading the json
        VendorPortalTestData testData = JsonUtils.getTestData("test-data/vendorportal/mike.json");

        System.out.println(testData.username());

        /*Config.init();*/

        //we can also set the property from command line using -DpropertyName=propertyValue
        //System.setProperty("browser", "firefox");
        //Config.init();

        System.setProperty("browser", "firefox");
        System.out.println("After: " + System.getProperty("browser"));
        Config.init();
        System.out.println("After: " + System.getProperty("browser"));


    }


}
