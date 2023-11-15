package Cal;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CalServer {
    public static void main(String[] args) {
        int serverPort = 1234;

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
            System.out.println("Calculator Server is running on port " + serverPort);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                executorService.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String expression = in.readLine();
            double result = evaluateExpression(expression);

            out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double evaluateExpression(String expression) {
        // Implement the logic to evaluate the arithmetic expression
        // For simplicity, let's assume the expression is always valid
        String[] tokens = expression.split(" ");
        double operand1 = Double.parseDouble(tokens[0]);
        double operand2 = Double.parseDouble(tokens[2]);
        String operator = tokens[1];

        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
}
