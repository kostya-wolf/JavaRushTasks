package com.javarush.task.task30.task3008;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Волковы on 12.01.2018.
 */
public class Server {
    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    public static void sendBroadcastMessage(Message message) {
        for (Connection connection : connectionMap.values()) {
            try {
                connection.send(message);
            } catch (IOException e) {
                System.out.println("Не смогли отправить сообщение");
            }
        }
    }

    public static void main(String[] args) {
        int port = ConsoleHelper.readInt();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Сервер запущен");
            while (true) {
                Socket socket = serverSocket.accept();
                new Handler(socket).start();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            serverSocket.close();
        } catch (IOException e) {

        }
    }

    private static class Handler extends Thread {
        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            ConsoleHelper.writeMessage("Установлено новое соединение с удаленным адресом: " + socket.getRemoteSocketAddress());
            String newClientName = null;
            try (Connection connection = new Connection(socket);) {

                newClientName = serverHandshake(connection);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, newClientName));
                sendListOfUsers(connection, newClientName);
                serverMainLoop(connection, newClientName);
                connection.close();
            } catch (IOException | ClassNotFoundException e) {
                ConsoleHelper.writeMessage("Произошла ошибка при обмене данными с удаленным адресом");
            }
            if (newClientName != null) {
                connectionMap.remove(newClientName);
                sendBroadcastMessage(new Message(MessageType.USER_REMOVED, newClientName));
            }
            ConsoleHelper.writeMessage("Cоединение с удаленным адресом закрыто");

        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            Message message = new Message(MessageType.NAME_REQUEST);
            connection.send(message);
            Message receivedMessage = connection.receive();
            while (receivedMessage.getType() != MessageType.USER_NAME
                    || receivedMessage.getData().isEmpty()
                    || connectionMap.keySet().contains(receivedMessage.getData())) {
                connection.send(message);
                receivedMessage = connection.receive();
            }
            connectionMap.put(receivedMessage.getData(), connection);
            connection.send(new Message(MessageType.NAME_ACCEPTED));
            return receivedMessage.getData();
        }

        private void sendListOfUsers(Connection connection, String userName) throws IOException {
            for (Map.Entry<String, Connection> pair : connectionMap.entrySet()) {
                String clientName = pair.getKey();
                if (!userName.equals(clientName)) {
                    Message message = new Message(MessageType.USER_ADDED, clientName);
                    connection.send(message);
                }
            }
        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while (true) {
                Message receivedMessage = connection.receive();
                MessageType type = receivedMessage.getType();
                if (type == MessageType.TEXT) {
                    String text = userName + ": " + receivedMessage.getData();
                    Message m = new Message(MessageType.TEXT, text);
                    sendBroadcastMessage(m);
                } else {
                    ConsoleHelper.writeMessage("Принятое сообщение не является текстом");
                }
            }
        }
    }
}
