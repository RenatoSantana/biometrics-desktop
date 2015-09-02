/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometrics.teste;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.json.JSONObject;

/**
 *
 * @author 205406813
 */
public class JerseyClientGet {
 /*    public static void main(String[] args) {
	try {
 
		Client client = Client.create();
 
		WebResource webResource = client
		   .resource("http://10.41.1.144:8080/useraccount/login/dologin");
                            
		ClientResponse response = webResource.accept("application/json")
                   .get(ClientResponse.class);
 
		if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
			+ response.getStatus());
		}
   MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
                        queryParams.add("username", "renato");
                        queryParams.add("password", "123456");
                        
                           //        System.out.println(queryParams.get("username"));
   String s = webResource.queryParams(queryParams).get(String.class);

                     //   String output = response.getEntity(String.class);
                         JSONObject obj = new JSONObject(s);
                        System.out.println(obj);
 
	  } catch (Exception e) {
 
		e.printStackTrace();
 
	  }
 
	}*/

}
