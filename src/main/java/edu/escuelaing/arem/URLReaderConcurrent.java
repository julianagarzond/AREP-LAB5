package edu.escuelaing.arem;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class URLReaderConcurrent extends Thread {
    private static ArrayList<Thread> Threads;
    private static int num;
    private static URL url;

    public URLReaderConcurrent(URL url) throws IOException {
        this.url=url;
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL(args[0]);
        num=Integer.parseInt(args[1]);
        Threads=new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Threads.add(new URLReaderConcurrent(url));
        }
        int cont = 0;
        for (Thread thread : Threads) {
            thread.start();
            cont++;
        }
        System.out.println( cont);

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(url.openStream()))) {
            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);
            }
        } catch (IOException x) {
            System.err.println(x);
        }
    }

}
