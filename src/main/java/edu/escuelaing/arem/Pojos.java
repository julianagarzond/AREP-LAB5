package edu.escuelaing.arem;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

public class Pojos {
	 @Web("image")
	  private static void imagen(String element, OutputStream clientOutput, PrintWriter out) throws IOException {
	        try {
	            BufferedImage image = 
	            	ImageIO.read(new File(System.getProperty("user.dir") + element));
	            ByteArrayOutputStream ArrBytes = 
	            		new ByteArrayOutputStream();
	            DataOutputStream writeimg = 
	            		new DataOutputStream(clientOutput);
	            String imagen = "HTTP/1.1 404 Not Found \r\n"
	                    + "Content-Type: text/html; charset=\"utf-8\" \r\n"
	                    + "\r\n";
	            ImageIO.write(image, "PNG", ArrBytes);
	            writeimg.writeBytes("HTTP/1.1 200 OK \r\n");
	            writeimg.writeBytes("Content-Type: image/png \r\n");
	            writeimg.writeBytes("\r\n");
	            writeimg.write(ArrBytes.toByteArray());
	            System.out.println(System.getProperty("user.dir") + element);
	        } catch (IOException e) {
	      	  System.out.println("r" + e.getMessage());
	        }

	    }
	    
	 @Web("html")
	    private static void htmlFile(String element,OutputStream outputStream, PrintWriter out) throws IOException {
	        try {
	            BufferedReader readhtml = new BufferedReader(new FileReader(System.getProperty("user.dir")+element));
	            String cont = "";
	            String line;
	            while ((line = readhtml.readLine()) != null){
	                cont = cont + line;  
	            }
	            outputStream.write(("HTTP/1.1 404 Not Found \r\n"
	                    + "Content-Type: text/html; charset=\"utf-8\" \r\n"
	                    + "\r\n"
	                    + cont).getBytes());
	        } catch (IOException e) {   
	        }
	    } 
	    
	 
	 @Web("js")
	    private static void jsFile(String element,OutputStream outputStream,  PrintWriter out) throws IOException {
	        try {
	            BufferedReader readjs = new BufferedReader(new FileReader(System.getProperty("user.dir")+element));
	            String cont = "";
	            String line;
	            while ((line = readjs.readLine()) != null){
	                cont = cont + line;  
	            }
	            outputStream.write(("HTTP/1.1 404 Not Found \r\n"
	                    + "Content-Type: text/html; charset=\"utf-8\" \r\n"
	                    + "\r\n"
	                    + cont).getBytes());
	        } catch (IOException e) {   
	        }
	    } 
}
