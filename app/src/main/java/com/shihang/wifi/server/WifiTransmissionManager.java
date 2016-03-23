package com.shihang.wifi.server;


import java.io.IOException;

public class WifiTransmissionManager {

    private static SimpleFileServer server;
    public static boolean serverIsRunning = false;

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
