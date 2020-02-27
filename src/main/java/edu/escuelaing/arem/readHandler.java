package edu.escuelaing.arem;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class readHandler {
	
	public static Class frame;
	public static Map<String, Method> UrlMethod;//Esta variable permite almacenar el path con el metodo asignado en el POJO
	/**
	 * Constructor de la clase el cual contiene toda la logica para crear la asociacion entre metodo y anotacion
	 */
	public readHandler() {
		try {
			UrlMethod = new HashMap();
			frame = Class.forName("edu.escuelaing.arem.Pojos");
			Method[] m = frame.getDeclaredMethods();
			for (Method ml : m) {
				System.out.println(ml.getDeclaredAnnotations()[0]);
				String anotacionM = ml.getDeclaredAnnotations()[0].toString();
				if (anotacionM.contains(".Web")) {
					String data = anotacionM.substring(anotacionM.indexOf('/'), anotacionM.length() - 1);
					System.out.println(data);
					UrlMethod.put(data, ml);

				}
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
