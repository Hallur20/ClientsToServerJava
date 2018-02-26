package Stuff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *hey
 * @author hvn15
 */
public class Client {

    public static void main(String[] args) throws Exception {
        Client client = new Client("195.249.186.237", 3333);
        //new TcpClient("127.0.0.1", 9876);
        //new TcpClient("example.com", 9876); //Domain name
        //new TcpClient("111.111.111.111", 9876); //Public IP
    }

    private Socket clientSocket;
    private PrintWriter toServer;
    private BufferedReader fromServer;

    private String input, output;
    private BufferedReader clientInput;

    public Client(String ip, int port) throws IOException {
        clientInput = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("Client message: Attemping to connect to host " + ip + " on port " + port);

            clientSocket = new Socket(ip, port);
            toServer = new PrintWriter(clientSocket.getOutputStream(), true);
            fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            System.out.println("Client message: Connected to the server!");

            System.out.println("Server message: " + fromServer.readLine());

            System.out.print("type a username: ");

            while ((input = clientInput.readLine()) != null) {
                toServer.println(input);

                output = fromServer.readLine();

                System.out.println("Server message: " + output);

                if (output.equals("GOODBYE...")) {
                    System.out.println("Client message: Disconnected from the server!");

                    break;
                }

                System.out.print("Client input: ");
            }

            clientInput.close();
            toServer.close();
            fromServer.close();
            clientSocket.close();
        } catch (Exception e) {
            System.out.println("Client message: Unabe to connect to host: " + ip + " on port " + port);
        }
    }
}