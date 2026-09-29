package utils;

import com.example.tests.vendorPortal.model.VendorPortalTestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class JsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    public static VendorPortalTestData getTestData(String path) throws IOException {
       try(InputStream stream = ResourceLoader.getResources(path)){
           return mapper.readValue(stream, VendorPortalTestData.class);
       }catch (Exception e){
           logger.error("Error reading test data from path: {}", path, e);
           throw e;
       }
    }

    /*public static FlightReservationTestData getTestData(String path) throws IOException {
        try(InputStream stream = ResourceLoader.getResources(path)){
            return mapper.readValue(stream, FlightReservationTestData.class);
        }catch (Exception e){
            logger.error("Error reading test data from path: {}", path, e);
            throw e;
        }
    }*/

    /*
    if there are multiple getTestData methods, we will end up creating multiple similar methods
    To overcome this, we will use the below approach
    We will make use of Generics
     */


    public static <T> T getTestData(String path, Class<T> type) throws IOException {
        try(InputStream stream = ResourceLoader.getResources(path)){
            return mapper.readValue(stream, type);
        }catch (Exception e){
            logger.error("Error reading test data from path: {}", path, e);
            throw e;
        }
    }
}

/*
Try reading the content from the Demo class we have created
 */