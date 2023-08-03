package ru.rail;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServerTask {
    private final int port;
    private final ExecutorService executorService;

    public HttpServerTask(int port, int poolSize) {
        this.port = port;
        executorService = Executors.newFixedThreadPool(poolSize);
    }

    public void run() {
        try (var serverSocket = new ServerSocket(port)) {
            while (true) {
                var socket = serverSocket.accept();
                System.out.println("Socket accepted");
                executorService.submit(() -> processSocket(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void processSocket(Socket socket) {
        try (socket;
             var inputStream = new DataInputStream(socket.getInputStream());
             var outputStream = new DataOutputStream(socket.getOutputStream())) {

            System.out.println(new String(inputStream.readNBytes(600)));

            Thread.sleep(1000);

            ConvertToObject convertToObject = new ConvertToObject();
            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/client.json")));

            List<Employee> employeeList = convertToObject.convert(json);

            int total_income = 0;
            int total_tax = 0;

            for (Employee employee : employeeList) {
                int currentSalary = employee.getSalary();
                total_income = currentSalary + total_income;
                int currentTax = employee.getTax();
                total_tax = currentTax + total_tax;
            }

            int total_profit = total_income - total_tax;

            String html = new String(Files.readAllBytes(Paths.get("src/main/resources/server.html")));
            html = html.replace("${total_income}", String.valueOf(total_income))
                    .replace("${total_tax}", String.valueOf(total_tax))
                    .replace("${total_profit}", String.valueOf(total_profit));

            byte[] body = html.getBytes();

            outputStream.write("""
                    HTTP/1.1 200 OK
                    content-type: text/html
                    content-length: %s
                    """.formatted(body.length).getBytes());
            outputStream.write(System.lineSeparator().getBytes());
            outputStream.write(body);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}