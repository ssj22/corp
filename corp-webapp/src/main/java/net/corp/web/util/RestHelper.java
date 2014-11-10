package net.corp.web.util;

import java.io.IOException;

import net.corp.core.util.PropertyUtil;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

public class RestHelper {
	private static final Logger log = Logger.getLogger(RestHelper.class);
	
	public static String liveWeight() {
		StringBuffer url = new StringBuffer("http://");
		url.append(PropertyUtil.getInstance().getProperty("weightUrlHost"));
		url.append(":");
		url.append(PropertyUtil.getInstance().getProperty("weightUrlPort"));
		url.append("/");
		url.append(PropertyUtil.getInstance().getProperty("weightUrlApi"));
		//url = "http://localhost/weight";
		// Create an instance of HttpClient.
	    HttpClient client = new HttpClient();

	    // Create a method instance.
	    GetMethod method = new GetMethod(url.toString());
	    
	    // Provide custom retry handler is necessary
	    method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
	    		new DefaultHttpMethodRetryHandler(3, false));

	    try {
	      // Execute the method.
	      int statusCode = client.executeMethod(method);

	      if (statusCode != HttpStatus.SC_OK) {
	    	  log.debug("Method failed: " + method.getStatusLine());
	      }

	      // Read the response body.
	      byte[] responseBody = method.getResponseBody();

	      // Deal with the response.
	      // Use caution: ensure correct character encoding and is not binary data
	      String res = (new String(responseBody));
	      //String res = "<string xmlns="http://schemas.microsoft.com/2003/10/Serialization/">10</string>";
	      if (res != null) {
	    	  int endIndex = res.indexOf("</string>");
	    	  int startIndex = res.indexOf(">") + 1;
	    	  return res.substring(startIndex, endIndex);
	      }
	      

	    } catch (HttpException e) {
	    	log.error("Fatal protocol violation: " + e.getMessage());
	    } catch (IOException e) {
	    	log.error("Fatal transport error: " + e.getMessage());
	    } catch (Exception e) {
	    	log.error("Execption: " + e.getMessage());
	    }
	    finally {
	      // Release the connection.
	      client = null;
	      method.releaseConnection();
	    }
	    return null;
	}
	
	public static void main(String[] args) {
		String res = "<string xmlns=\"http://schemas.microsoft.com/2003/10/Serialization/\">20484223</string>";
	      if (res != null) {
	    	  int endIndex = res.indexOf("</string>");
	    	  int startIndex = res.indexOf(">") + 1;
	    	  System.out.println(res.substring(startIndex, endIndex));
	      }
	}
}
