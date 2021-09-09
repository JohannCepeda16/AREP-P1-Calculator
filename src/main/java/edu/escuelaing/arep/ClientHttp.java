package edu.escuelaing.arep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHttp {
    public static void main(String... args) throws IOException {
        ServerSocket ss = null;
        try {
            int port = getPort();
            ss = new ServerSocket(port);
            boolean running = true;
            while (running) {
                Socket client = ss.accept();

                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream());

                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println("Listo para recibir...");
                    if (line.length() == 0)
                        break;
                    out.print(line + "\r\n");
                }

                out.close();
                in.close();
                client.close();
            }
            ss.close();
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    public String processRequest(String params) {
        return "HTTP/1.1 200 \r\n" 
                + "Content-Type: application/json\r\n" 
                + "Connection: close\r\n";
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 8080; // returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
