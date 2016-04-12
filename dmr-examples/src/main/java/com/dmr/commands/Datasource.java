package com.dmr.commands;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.sasl.RealmCallback;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;

import com.dmr.connection.DMRConnection;

/**
 * Reads Datasource statistics
 * 
 * @author stelios@redhat.com
 *
 */
public class Datasource implements DMRResourceMonitor<String, String> {

	public static final String DATASOURCE = "ds";

	
	/* For testing purposes */
	public static void main(String[] args) {
		String user = "admin";
		String password = "Password640!";
		String host = "127.0.0.1";
		int port = 9999;

		DMRConnection cliConnection = null;
		try {
			cliConnection = new DMRConnection(host, port, user, password);
			
			Datasource ds = new Datasource();
			ModelNode cliCommand = ds.monitorCommand(new HashMap(){{put(DATASOURCE, "ExampleDS");}});

			ModelNode returnDSInfo = cliConnection.getClient().execute(cliCommand);
			System.out.println(returnDSInfo.get(RESULT).toString()); 
			  
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			cliConnection.destroyClient();
		}

	}
	
	

	/**
	 * Code from: How to create XA DataSource using DMR API in JBoss EAP 6? 
	 * https://access.redhat.com/solutions/527373
	 * 
	 * Implementing CLI:  /subsystem=datasources/data-source=ExampleDS:read-resource(recursive=true, include-runtime=true)

	 * 
	 * @return 
	 * @throws IOException 
	 */
	@Override
	public ModelNode monitorCommand(Map<String, String> params)	throws IOException {		
				
		  ModelNode readDSInfo = new ModelNode();
		  readDSInfo.get("operation").set("read-resource");
		  readDSInfo.get("recursive").set(true);
		  readDSInfo.get("include-runtime").set(true);
		  ModelNode addressMemInfo = readDSInfo.get("address");
		  addressMemInfo.add("subsystem", "datasources");
		  addressMemInfo.add("data-source", params.get(DATASOURCE));
		
		  return readDSInfo; 
	
	}
	
	private static final ModelControllerClient createClient(final InetAddress host, final int port, final String username, final String password) {
        final CallbackHandler callackHandler = new CallbackHandler() {
            public void handle(Callback[] callback) throws IOException, UnsupportedCallbackException {
                for (Callback current : callback) {
                    if (current instanceof NameCallback) {
                        NameCallback ncb = (NameCallback) current;
                        ncb.setName(username);
                    }
                    else if (current instanceof PasswordCallback) {
                        PasswordCallback pcb = (PasswordCallback) current;
                        pcb.setPassword(password.toCharArray());
                    }
                    else if (current instanceof RealmCallback) {
                        RealmCallback rcb = (RealmCallback) current;
                        rcb.setText(rcb.getDefaultText());
                    }
                    else {
                        throw new UnsupportedCallbackException(current);
                    }
                }
            }
        };
        return ModelControllerClient.Factory.create(host, port, callackHandler);
    }
}

