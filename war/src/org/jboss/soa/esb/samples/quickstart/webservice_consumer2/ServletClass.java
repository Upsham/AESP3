package org.jboss.soa.esb.samples.quickstart.webservice_consumer2;
import java.net.URL;
import java.util.HashMap;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;

import org.jboss.internal.soa.esb.util.StreamUtils;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.WebParam;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;
import org.jboss.internal.soa.esb.rosetta.pooling.JmsConnectionPoolContainer;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;
import org.jboss.soa.esb.message.format.MessageType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
@WebService(name = "Servlet", targetNamespace = "http://webservice_consumer2/servlet")
public class ServletClass extends HttpServlet{

	/**
	 * @param args
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
	    Object data = "Some data, can be a String or a Javabean";
	    System.out.println(request.getContextPath());
	    try{
	    	request.getSession().setAttribute("data", Brandname(request.getParameter("brandName")));
	    }catch(Exception e){
	    	request.getSession().setAttribute("data", e.getMessage());
	    }
	    request.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
	}
	  @WebMethod
	 public String Brandname (@WebParam(name = "toWhom")
	   String toWhom)  throws Exception
	   {
		  if ("".equals(toWhom)) throw new Exception("To Whom is invalid");
		  System.setProperty("javax.xml.registry.ConnectionFactoryClass", "org.apache.ws.scout.registry.ConnectionFactoryImpl");
		  org.jboss.soa.esb.message.Message esbMessage = MessageFactory.getInstance().getMessage(MessageType.JBOSS_XML);
		  //esbMessage.getBody().add("getBrands.Command", new java.util.HashMap());
		  esbMessage.getBody().add("Send Brands");
		  //Map map = new HashMap();
		  //map.put("getBrands.Command",getMessage("01"));
		  //esbMessage.getBody().add(map);
		  //System.out.println(esbMessage.toString());
		  System.out.println(esbMessage.getBody().toString());
		  ServiceInvoker invoke = new ServiceInvoker("AESService","Supplier");
		  invoke.deliverAsync(esbMessage);
		  //System.out.print(response.getBody().get().toString());
		  
		  return "Check";//response.getBody().get().toString();
	   }
	  
	    private static String getMessage(String messageNum) {
	        String msg = new String(StreamUtils.readStream(ServletClass.class.getResourceAsStream("soap_message_" + messageNum + ".xml")));
	        System.out.println("Working!");
	        return msg;//"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:good=\"http://aes/Supplier\">   <soapenv:Header/> <soapenv:Body> <good:getBrands><message>Goodbye!!</message> </good:getBrands> </soapenv:Body></soapenv:Envelope>";
	    }

}
