package mainpack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TSPServer {

    private  final int PORT = 8888;

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен. Ожидание сообщения...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Клиент подключился.");

                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String message = reader.readLine();
                System.out.println("Получено сообщение от клиента: " + message);

                // печатаем адрес порт и адрес клиента
                System.out.println("Адрес клиента: " + clientSocket.getInetAddress().getHostAddress());

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String currentTime = dateFormat.format(new Date());
                System.out.println("Время приема сообщения на сервере: " + currentTime);

                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                writer.println(currentTime);

                writer.close();
                reader.close();
                clientSocket.close();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
