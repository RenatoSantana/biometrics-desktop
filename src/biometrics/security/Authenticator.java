
package biometrics.security;

import biometrics.util.URLService;
import biometrics.util.Utility;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class Authenticator {
    private static final Map<String, String> USERS = new HashMap<String, String>();
    static {
        USERS.put("demo", "demo");
    }
    public static int validate(String user, String password){
          // System.setProperty ("http.proxyHost", "10.41.1.252");
        //   System.setProperty ("http.proxyPort", "80");
        try {
             if(Utility.isNotNull(user)&& Utility.isNotNull(password)){
                        Client client = Client.create();
                      
                        password = CriptografiaSHA256.encode(password);
                        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
                        queryParams.add("username", user);
                        queryParams.add("password", password);
                    
                        WebResource webResource = client
                           .resource(URLService.LOCALHOST+"/biometricsws/service/login/dologin");
                        
   String s = webResource.queryParams(queryParams).get(String.class);
                       
   ClientResponse response = webResource.accept("application/json")
                           .get(ClientResponse.class);

                        if (response.getStatus() != 200) {
                           throw new RuntimeException("Failed : HTTP error code : "
                                + response.getStatus());
                        }
                        

                        JSONObject obj = new JSONObject(s);
                        System.out.println(obj);
                        
                        if(obj.getBoolean("status")){
                            return 1;
                        }else {
                            return 0;
                        }
                
              }else{
                 return 0;
             }
 
	  } catch (com.sun.jersey.api.client.ClientHandlerException e) {
 
		e.printStackTrace();
                return 3;
 
	  }catch (Exception e) {
 
		e.printStackTrace();
 
	  }
          return 0;
    }
        //String validUserPassword = USERS.get(user);
        //return validUserPassword != null && validUserPassword.equals(password);
    
}