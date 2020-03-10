package edu.escuelaing.arem;
	
import java.net.*;
import java.nio.file.Path;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.SpringLayout.Constraints;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

	
		import java.net.*;
		import java.nio.file.Path;

		import javax.imageio.ImageIO;

		import java.awt.Desktop;
		import java.awt.image.BufferedImage;
		import java.io.*;

		public class HttpServer implements Runnable {
			 public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			        while(true){
			            ServerSocket serverSocket = null;
			            int puerto = getPort();
			            try {
			                serverSocket = new ServerSocket(puerto);
			                System.out.println(puerto);
			            } catch (IOException e) {
			                System.err.println("Could not listen on port:" + getPort());
			                System.exit(1);
			                System.out.println(puerto);
			            }

			            Socket clientSocket = null;
			            try {
			                System.out.println("Listo para recibir ...");
			                clientSocket = serverSocket.accept();

			            } catch (IOException e) {
			                System.err.println("Accept failed.");
			                System.exit(1);
			            }
			            PrintWriter out = new PrintWriter(
			                    clientSocket.getOutputStream(), true);
			            BufferedReader in = new BufferedReader(
			                    new InputStreamReader(clientSocket.getInputStream()));
			            String inputLine;
			            while ((inputLine = in.readLine()) != null) {
			                request(inputLine,clientSocket.getOutputStream(),out);
			          
			                if (!in.ready()) {break; }
			            }

			            out.close();
			            in.close();
			            clientSocket.close();
			            serverSocket.close();
			        }
			    }
		    
		    private static void requesst(String element, OutputStream clientOutput, PrintWriter out) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		             	 
	                    
		    	 String[] listaURL = element.split(" ");
				String[] get = listaURL[1].split("/");
				if (listaURL[1].contains("/app")) {
				    Class<?> c = Class.forName("edu.eci.arem.Pojos." + get[1]);
				    for (Method metodo : c.getMethods()) {
				        if (metodo.isAnnotationPresent(Web.class)) {
				            String[] ans = get[2].split("[, ?.@]+");
				            if (metodo.getName().equals(ans[1])) {
				                metodo.invoke(c, "/src/main/resources/" + get[2],clientOutput);
				            }   	
				        }
				    }
				    
				}
		    	 }
		                        
		   
		    
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
		    
		    private static void htmlFile(String element,OutputStream outputStream) throws IOException {
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
		    
		    private static void jsFile(String element,OutputStream outputStream) throws IOException {
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
		    
		    private static String headline(String annotation) {
		    	return "HTTP/1.1 200 OK \r\n"
		                + "Content-Type: text/html; charset=\"utf-8\" \r\n"
		                + "\r\n"
		                + annotation;
		    }
		    	
		    private static void request(String element, OutputStream clientOutput, PrintWriter out) throws IOException {
		    
		    if(element.contains("GET")){
                String[] linea = element.split("/");
                String[] val = linea[1].split(" ");
       
                if(val[0].contains("png")|| val[0].contains("PNG")){
                    imagen("/src/main/resources/static/"+val[0],clientOutput,out);
                }
                if(val[0].contains("html")) {
                        htmlFile("/src/main/resources/static/"+val[0],clientOutput);
                }
                if(val[0].contains("js")) {
                        jsFile("/src/main/resources/static/"+val[0],clientOutput);	
                        
                }
                
		    }
		    
		    }
		  
		    static int getPort() {
		        if (System.getenv("PORT") != null) {
		            return Integer.parseInt(System.getenv("PORT"));
		        }
		        return 36000;
		    }

			@Override
			public void run() {

			}
		}

