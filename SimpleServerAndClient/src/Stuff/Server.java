package Stuff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    public static void main(String[] args) throws IOException {
        ArrayList<String> messages = new ArrayList<>();
        ServerSocket ss = new ServerSocket(3333,3333, InetAddress.getByName("195.249.186.237"));
        Socket socket;
        while (true) {

            socket = ss.accept();
            // new thread for a client
            Thread t1 = new Thread1(socket, messages);
            t1.start();
        }
    }

}

class Thread1 extends Thread {

    private Socket s;
    private ArrayList<String> msgs;

    Thread1(Socket s, ArrayList<String> messages) {
        this.s = s;
        this.msgs = messages;
    }

    @Override
    public void run() {
        try {
            System.out.println("thread started!");

            PrintWriter toClient = new PrintWriter(s.getOutputStream(), true);
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(s.getInputStream()));
            toClient.println("hello from server!");
            String userName = "";
            while (true) {
                String clientInput = fromClient.readLine();
                if (userName.equals("")) {
                    userName = clientInput;
                    toClient.println("username made");
                } else {
                    if (!clientInput.equals("")) {
                        System.out.println("message received!");
                        msgs.add(userName + ": " + clientInput);
                        toClient.println(msgs);
                        System.out.println(msgs);
                    } else {
                        toClient.println("that was an empty input...");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
