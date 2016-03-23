package com.shihang.wifi.server;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WifiTransmissionManager {

    private static SimpleFileServer server;
    public static boolean serverIsRunning = false;
    private static List<TransmissionResultListener> results = new ArrayList<>();

    public interface TransmissionResultListener{
        void transmissionResult(boolean success, String filePath, String msg);
    }


    public static void addListener(TransmissionResultListener listener){
        if(listener != null && !results.contains(listener)){
            results.add(listener);
        }
    }

    public static void removeListener(TransmissionResultListener listener){
        if(listener != null && results.contains(listener)){
            results.remove(listener);
        }
    }

    public static void transmissionResult(boolean success, String filePath, String msg){
        for (TransmissionResultListener listener : results){
            listener.transmissionResult(success, filePath, msg);
        }
    }


    public static SimpleFileServer getInstance(int port) {
        if (server == null) {
            server = new SimpleFileServer(port);
        }
        return server;
    }


    //在指定端口启动server
    public static void startServer(int port) {
        try {
            if (!serverIsRunning) {
                getInstance(port).start();
                serverIsRunning = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stopServer() {
        if (server != null) {
            server.stop();
            serverIsRunning = false;
        }
    }

}
