package com.company;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;

public class Main {
   // static StringBuilder sb = new StringBuilder();
    static ArrayList<Thread> listThread;
    static HashSet<String> answers;
    static HashSet<String> collection;
    static MessageDigest md;


    static {
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args)  throws NoSuchAlgorithmException, InterruptedException{
    collection = new HashSet<>();
    listThread= new ArrayList<>();
    answers = new HashSet<>();
        long startTime = System.currentTimeMillis();



        answers.add("1115dd800feaacefdf481f1f9070374a2a81e27880f187396db67958b207cbad");
        answers.add("3a7bd3e2360a3d29eea436fcfb7e44c735d117c42d1c1835420b6b9942dd4f1b");
        answers.add("74e1bb62f8dabb8125a58852b63bdf6eaef667cb56ac7f7cdba6d7305c50a22f");
        for (char i = 'a'; i <= 'z'; i += 2) {
            listThread.add(new MyThreads(i, (char) (i + 2)));
            listThread.get(listThread.size() - 1).start();
        }

        while (listThread.size() != 0) {
            listThread.removeIf(Thread -> !Thread.isAlive());
        }


        System.out.println("Answers: ");
        for (String var : collection){
            System.out.println(var);
        }

        long timeSpent = System.currentTimeMillis() - startTime;
        System.out.println("Programm was processing " + timeSpent + " millis");

    }





    public static void task(char bound1, char bound2) throws NoSuchAlgorithmException {
        System.out.println(bound1 + " " + bound2);
        MessageDigest mb;
        mb = MessageDigest.getInstance("SHA-256");
        char[] value = new char[5];
        for (char i = bound1; i <= bound2; i++) {
            value[0] = i;
            for (char j = 'a'; j <= 'z'; j++) {
                value[1] = j;
                for (char k = 'a'; k <= 'z'; k++) {
                    value[2] = k;
                    for (char l = 'a'; l <= 'z'; l++) {
                        value[3] = l;
                        for (char m = 'a'; m <= 'z'; m++) {
                            value[4] = m;

                            try {
                                byte[] hashInBytes = mb.digest(new String(value).getBytes(StandardCharsets.UTF_8));
                                //bytesToHex(hashInBytes);
                                if (answers.contains(bytesToHex(hashInBytes))) {
                                    System.out.println("Процесс идет...");
                                    System.out.println(new String(value));
                                    answers.add(new String(value));
                                }
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                    }

                }

            }

        }

    }


    public static char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

   public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
    /*public static String bytesToHex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte var : bytes) {
            stringBuilder.append(String.format("%02x", var));
        }
        return stringBuilder.toString();
    }*/
}



class MyThreads extends Thread {
    char bound1, bound2;
    public MyThreads(char bound1, char bound2) {
        this.bound1 = bound1;
        if (bound2 <= 'z') {
            this.bound2 = bound2;
        } else {
            this.bound2 = 'z';
        }
    }

    public void run() {
        System.out.println("Поток создан");

        try {
            Main.task(bound1, bound2);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
