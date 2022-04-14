package main.java.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class CacheUnitClient {
    private final int PORT = 12345;
    private Socket socket;

    public CacheUnitClient() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            socket = new Socket(address.getHostAddress(), PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sendRequest(String request) {
        return sendClientRequest(request);
    }

    private String sendClientRequest(String request) {
        String response = "";
        try {
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            output.writeObject(request);
            output.flush();

            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            Object responseObject = input.readObject();

            if(responseObject instanceof Boolean) {
                boolean responseStatus = (Boolean) responseObject;
                return responseStatus ? "Succeeded" : "Failed";
            }

            else if(responseObject instanceof HashMap) {
                Map<String, String> dataResponse = (HashMap<String, String>) responseObject;

                if(dataResponse.get("type").equals("data")) {
                    StringBuilder responseResult = new StringBuilder();

                    for(String data : dataResponse.values()) {
                        responseResult.append(data);
                        responseResult.append('\n');
                    }

                    return "Succeeded";
                }
                else if(dataResponse.get("type").equals("statistic")) {
                    response = String.format("Capacity: %s\n", dataResponse.get("capacity")) +
                            String.format("Algorithm: %s\n", dataResponse.get("algorithm")) +
                            String.format("Total number of request: %s\n", dataResponse.get("requests")) +
                            String.format("Total number of DataModels: %s\n", dataResponse.get("dataModels"));
                }
            }

            output.close();
            input.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return response;
    }
}
