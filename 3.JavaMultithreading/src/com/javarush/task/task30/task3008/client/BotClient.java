package com.javarush.task.task30.task3008.client;

import com.javarush.task.task30.task3008.ConsoleHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Волковы on 07.02.2018.
 */
public class BotClient extends Client {

    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    @Override
    protected String getUserName() {
        return "date_bot_" + (int)(Math.random() * 100);
    }

    public static void main(String[] args) {
        new BotClient().run();
    }

    public class BotSocketThread extends SocketThread {
        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
            if (message.contains(": ")) {
                String name = message.split(": ")[0];
                String text = message.split(": ")[1];
                String s = "Информация для " + name + ": ";
                SimpleDateFormat simpleDateFormat;
                switch (text) {
                    case "дата": {
                        simpleDateFormat = new SimpleDateFormat("d.MM.YYYY");
                        sendTextMessage(s + simpleDateFormat.format(Calendar.getInstance().getTime()));
                        break;
                    }
                    case "день": {
                        simpleDateFormat = new SimpleDateFormat("d");
                        sendTextMessage(s + simpleDateFormat.format(Calendar.getInstance().getTime()));
                        break;
                    }
                    case "месяц": {
                        simpleDateFormat = new SimpleDateFormat("MMMM");
                        sendTextMessage(s + simpleDateFormat.format(Calendar.getInstance().getTime()));
                        break;
                    }
                    case "год": {
                        simpleDateFormat = new SimpleDateFormat("YYYY");
                        sendTextMessage(s + simpleDateFormat.format(Calendar.getInstance().getTime()));
                        break;
                    }
                    case "время": {
                        simpleDateFormat = new SimpleDateFormat("H:mm:ss");
                        sendTextMessage(s + simpleDateFormat.format(Calendar.getInstance().getTime()));
                        break;
                    }
                    case "час": {
                        simpleDateFormat = new SimpleDateFormat("H");
                        sendTextMessage(s + simpleDateFormat.format(Calendar.getInstance().getTime()));
                        break;
                    }
                    case "минуты": {
                        simpleDateFormat = new SimpleDateFormat("m");
                        sendTextMessage(s + simpleDateFormat.format(Calendar.getInstance().getTime()));
                        break;
                    }
                    case "секунды": {
                        simpleDateFormat = new SimpleDateFormat("s");
                        sendTextMessage(s + simpleDateFormat.format(Calendar.getInstance().getTime()));
                        break;
                    }
                }
            }
        }
    }
}
