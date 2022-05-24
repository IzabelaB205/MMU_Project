package server;

import services.CacheUnitController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements IObserverServer{
    private Socket client;
    private ServerSocket server;
    private CacheUnitController<String> controller;
    private boolean serverUp = false;

    public Server(int port) {
        try {
            server = new ServerSocket(port);
            controller = new CacheUnitController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void start() {
        System.out.println("Server started");
        System.out.flush();
        Executor executor = Executors.newFixedThreadPool(2);
        HandleRequest<String> request;

        while (serverUp) {
            try {
                client = server.accept();
                request = new HandleRequest<>(client, controller);
                executor.execute(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ((ExecutorService) executor).shutdown();
    }

    @Override
    public void update(boolean state) {
        serverUp = state;

        if(!serverUp) {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Server stopped");
            System.out.flush();
        }
        else {
            this.start();
        }
    }
}
