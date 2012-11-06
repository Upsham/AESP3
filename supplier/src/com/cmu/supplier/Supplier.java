package com.cmu.supplier;

import java.net.URL;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
@WebService(name = "Supplier", targetNamespace = "http://aes/supplier")
public class Supplier {
	   @WebMethod
	   public String getBrands(@WebParam(name = "Command") String Command)
		{
		   System.out.println("In the getBrands now!!");
			//List<String> brands = listBrands();
			//HashMap requestMap = new HashMap();
			//String msgBody = (String) Command.get("Body").toString();
			/*for(int i=0; i<brands.size(); i++)
			{
				brands.set(i, distilBrandName(brands.get(i)));	
			}*/

		      // add paramaters to the web service request map
		    //requestMap.put(" TestString!! ", msgBody);
		    //Command.put("Body", "TestString");
		    //System.out.println(Command);
			return "FromWS!";
		}
		public static List<String> listBrands()
		{
			String w = get("http://bikereviews.com/road-bikes/");
			return match("title=\"[\\w\\s]+\" href=\"(http://bikereviews\\.com/road-bikes/[\\w([-|\\w])?\\s]+/)\"><img", 0, w);
			//brandSrape.substring(brandSrape.indexOf("road-bikes/") + "road-bikes/".length(), brandSrape.lastIndexOf("/"));
		}
		public static String distilBrandName(String brandSrape)
		{
			return brandSrape.substring(brandSrape.indexOf("road-bikes/") + "road-bikes/".length(), brandSrape.lastIndexOf("/"));
		}
		
		public static String get(String url)
		{
			String content = null;
			URLConnection connection = null;
			try {
			  connection =  new URL(url).openConnection();
			  Scanner scanner = new Scanner(connection.getInputStream());
			  scanner.useDelimiter("\\Z");
			  content = scanner.next();
			  scanner.close();
			}catch ( Exception ex ) {
			    ex.printStackTrace();
			}
			return content;
		}
		
		public static List<String> match(String expr, int flags, String search)
		{
			Pattern patt = Pattern.compile(expr);
			Matcher m = patt.matcher(search);
			List<String> list = new LinkedList<String>();
			while (m.find()) {
			  list.add(m.group(1));
			}
			return list;
		}
}
