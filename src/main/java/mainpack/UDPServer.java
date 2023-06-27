package mainpack;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UDPServer {
    private  final int PORT = 55555;
    private  final int LENGHT = 1024;
    public void start() {

        try (DatagramSocket socket = new DatagramSocket(PORT)){
            System.out.println("Сервер запущен. Ожидание сообщения...");

            while(true) {
                // получаем запрос от клиента
                DatagramPacket packet = new DatagramPacket(new byte[LENGHT], LENGHT);
                socket.receive(packet);

                String msg = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Получено сообщение от клиента: " + msg);

                // печатаем адрес порт и адрес клиента
                System.out.println("Адрес клиента: " + packet.getAddress().getHostAddress());

                // форматируем дату и время для ответа клиенту
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String currentTime = dateFormat.format(new Date());

                // формируем ответ клиенту
                String response = "Время получения сообещния на сервер: " + currentTime;
                System.out.println(response);
                byte[] responseData = response.getBytes();

                // отправляем ответ клиенту
                DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length,
                        packet.getAddress(), packet.getPort());
                socket.send(responsePacket);
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
