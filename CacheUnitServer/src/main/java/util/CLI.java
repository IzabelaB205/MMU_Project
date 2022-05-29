package java.util;

import java.server.IObserverServer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CLI implements Runnable, IObservableCLI{
    private final BufferedReader inputReader;
    private final OutputStreamWriter outputWriter;
    private boolean state;
    private List<IObserverServer> observers;

    public CLI(InputStream inputStream, OutputStream outputStream) {
        this.inputReader = new BufferedReader(new InputStreamReader(inputStream));
        this.outputWriter = new OutputStreamWriter(outputStream);
        state = false;
        observers = new ArrayList<>();
    }

    private void setState(boolean state) {
        this.state = state;
        notifyObserver();
    }

    @Override
    public void run() {
        String command = "";

        while(!command.equals("shutdown")) {
            try {
                outputWriter.write("Enter command: ");
                outputWriter.flush();

                command = inputReader.readLine();

                if(command.equals("start")) {
                    setState(true);
                }
                else if(command.equals("shutdown")) {
                    inputReader.close();
                    outputWriter.close();
                    setState(false);
                }
                else {
                    outputWriter.write("wrong command\n");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void add(IObserverServer observer) {
        observers.add(observer);
    }

    @Override
    public void remove(IObserverServer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver() {
        for(IObserverServer observerServer : observers) {
            observerServer.update(state);
        }
    }
}
