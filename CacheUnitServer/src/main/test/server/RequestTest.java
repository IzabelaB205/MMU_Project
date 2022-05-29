package test.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.dm.DataModel;
import java.io.*;
import java.lang.reflect.Type;
import java.server.Request;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RequestTest {
    private Request<DataModel<String>[]> request;
    private Gson gson;
    private String[] content;
    private Long[] ids;
    private String filePath;

    @BeforeEach
    public void init() {
        gson = new GsonBuilder().create();
        filePath = "src/test/test/server/request.json";
        content = new String[]{"Anna", "Danna", "Banana"};
        ids = new Long[]{1L, 2L, 3L};
        int capacity = 3;
        Map<String, String> header = new HashMap<>();
        DataModel<String>[] body = new DataModel[capacity];

        for(int i = 0; i < capacity; i++) {
            body[i] = new DataModel<>(ids[i], content[i]);
        }

        header.put("action", "UPDATE");
        request = new Request<>(header, body);
    }

    @Test
    public void writeRequestToJsonFileTest() {
        try(Writer writer = new FileWriter(filePath)) {
            gson.toJson(request, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readRequestFromJsonFileTest() {
        try(Reader reader = new FileReader(filePath)) {
            Type ref = new TypeToken<Request<DataModel<String>[]>>() {
            }.getType();
            request = gson.fromJson(reader, ref);

            Map<String, String> headerTest = request.getHeader();
            DataModel<String>[] bodyTest = request.getBody();

            //Header test
            assertTrue(headerTest.containsKey("action"));
            assertEquals("UPDATE", headerTest.get("action"));

            //Body test
            assertEquals(ids[0], bodyTest[0].getId());
            assertEquals(content[0], bodyTest[0].getContent());

            assertEquals(ids[1], bodyTest[1].getId());
            assertEquals(content[1], bodyTest[1].getContent());

            assertEquals(ids[2], bodyTest[2].getId());
            assertEquals(content[2], bodyTest[2].getContent());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}