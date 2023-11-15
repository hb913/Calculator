package Cal;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class CalClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 1234;

        try {
            File configFile = new File("server_info.dat");
            if (configFile.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(configFile))) {
                    serverAddress = br.readLine();
                    serverPort = Integer.parseInt(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Socket socket = new Socket(serverAddress, serverPort);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("Enter an arithmetic expression):");
            String expression = userInput.readLine();
            out.println(expression);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String result = in.readLine();
            System.out.println("Server response: " + result);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
