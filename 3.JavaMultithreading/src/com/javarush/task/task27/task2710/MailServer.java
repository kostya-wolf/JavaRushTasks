package com.javarush.task.task27.task2710;

public class MailServer implements Runnable {
    private Mail mail;

    public MailServer(Mail mail) {
        this.mail = mail;
    }

    @Override
    public void run() {
        synchronized (mail) {
            long beforeTime = System.currentTimeMillis();
            //сделайте что-то тут - do something here
            if (mail.getText() == null) {
                try {
                    mail.wait();
                } catch (InterruptedException e){
                    System.out.println("inter");
                }
            }

            String name = Thread.currentThread().getName();
            long afterTime = System.currentTimeMillis();
            System.out.format("%s MailServer has got: [%s] in %d ms after start", name, mail.getText(), (afterTime - beforeTime));
        }
    }
}
