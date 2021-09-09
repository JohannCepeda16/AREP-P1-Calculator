package edu.escuelaing.arep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import edu.escuelaing.arep.calculator.Calculator;

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

                String[] requestInfo = in.readLine().split(" ");
                String path = requestInfo[1];
                out.write(processRequest(path));

                out.close();
                in.close();
                client.close();
            }
            ss.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static String processRequest(String params) {
        String res = "";
        try {
            String data = params.substring(1, params.length());
            String[] paramsSplited = data.split("-");
            String op = paramsSplited[0];
            String number = paramsSplited[1];
            if (op.equals("sen"))
                res = Calculator.sen(number);
            else if (op.equals("cos"))
                res = Calculator.cos(number);
            else if (op.equals("tan"))
                res = Calculator.tan(number);
            else
                res = "Operacion no soportada";
        } catch (Exception e) {
            System.out.println("Operacion no soportada");
        }
        return "HTTP/1.1 200 OK\r\n" + "Content-Type: application/json " + "\r\n" + "\r\n" + "<!DOCTYPE html>" + res;
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 8080; // returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
