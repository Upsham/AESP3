/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, JBoss Inc., and others contributors as indicated 
 * by the @authors tag. All rights reserved. 
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors. 
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
 * MA  02110-1301, USA.
 * 
 * (C) 2005-2006,
 * @author JBoss Inc.
 */
package org.jboss.soa.esb.samples.quickstart.webservice_consumer1;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.actions.ActionUtils;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;
import java.util.HashMap;

import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyRequestAction extends AbstractActionLifecycle
{
   protected ConfigTree _config;

   public MyRequestAction(ConfigTree config)
   {
      _config = config;
   }

   public Message noOperation(Message message)
   {
      return message;
   }

   /*
    * Convert the message into a webservice request map.
    */
   public Message process(Message message) throws Exception
   {
      logHeader();
      String msgBody = (String) message.getBody().get();
      HashMap requestMap = new HashMap();

      // add paramaters to the web service request map
      requestMap.put("sayHello.toWhom", message);

      // The "paramsLocation" property was set in jboss-esb.xml to
      // "helloworld-request-parameters"
      message.getBody().add(requestMap);
      System.out.println("Request map is: " + requestMap.toString());

      logFooter();
      return message;
   }

   public void exceptionHandler(Message message, Throwable exception)
   {
      logHeader();
      System.out.println("!ERROR!");
      System.out.println(exception.getMessage());
      System.out.println("For Message: ");
      System.out.println(message.getBody().get());
      logFooter();
   }
 public static List<String> getBrands()
	{
		List<String> brands = listBrands();
		for(int i=0; i<brands.size(); i++)
		{
			brands.set(i, distilBrandName(brands.get(i)));
		}
		return brands;
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
   // This makes it easier to read on the console
   private void logHeader()
   {
      System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&\n");
   }

   private void logFooter()
   {
      System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&\n");
   }

}