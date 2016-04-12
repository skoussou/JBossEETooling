package com.dmr.commands;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;

import com.dmr.connection.DMRConnection;

/**
 * 
 * @author stelios@redhat.com
 *
 */
public class WebConnectorStats implements DMRResourceMonitor<String, String> {

	public static final String CONNECTOR = "connector";
	public static final String EXECUTOR = "executor";
	public static final String THREAD_POOL_TYPE = "pooltype";
	public static final String THREAD_POOL_NAME = "poolname";

	/* For testing purposes */
	public static void main(String[] args) {
		String user = "admin";
		String password = "Password640!";
		String host = "127.0.0.1";
		int port = 9999;

		DMRConnection cliConnection = null;
		try {
			cliConnection = new DMRConnection(host, port, user, password);

			WebConnectorStats wc = new WebConnectorStats();
			String threadPoolExecName = wc.findWebExecutorHelper(new HashMap(){{put(CONNECTOR, "http");}}, cliConnection.getClient());
			
			ModelNode cliCommand = wc.monitorCommand(new HashMap(){{put(THREAD_POOL_TYPE, "bounded-queue-thread-pool");put(THREAD_POOL_NAME, threadPoolExecName);}});

			ModelNode returnWebInfo = cliConnection.getClient().execute(cliCommand);
			System.out.println(returnWebInfo.get(RESULT).toString()); 


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
	 * Implementing CLI:  /subsystem=threads/bounded-queue-thread-pool=http-thread-pool:read-resource(include-runtime=true)
	 * 
	 * 
	 * @return 
	 * @throws IOException 
	 */
	@Override
	public ModelNode monitorCommand(Map<String, String> params)
			throws IOException {

		ModelNode readWebInfo = new ModelNode();
		readWebInfo.get("operation").set("read-resource");
		readWebInfo.get("include-runtime").set(true);

		ModelNode addressAjp = readWebInfo.get("address");
		addressAjp.add("subsystem", "threads");
		addressAjp.add(params.get(THREAD_POOL_TYPE), params.get(THREAD_POOL_NAME));

		return readWebInfo;
	}
	
	/* 
	 * Code from: How to create XA DataSource using DMR API in JBoss EAP 6? 
	 * https://access.redhat.com/solutions/527373
	 * 
	 * Implementing CLI:  /subsystem=web/connector=DEFINE:read-resource(recursive=true,include-runtime=true) 
	 */
	public String findWebExecutorHelper(Map<String, String> params, ModelControllerClient client) throws IOException {
		ModelNode readWebInfo = new ModelNode();
		readWebInfo.get("operation").set("read-resource");
		readWebInfo.get("recursive").set(true);
		readWebInfo.get("include-runtime").set(true);

		ModelNode addressAjp = readWebInfo.get("address");
		addressAjp.add("subsystem", "web");
		addressAjp.add("connector", params.get(CONNECTOR));
		
		ModelNode node = client.execute(readWebInfo);
	    System.out.println(node.get(RESULT).get(EXECUTOR).asString());
		
		return node.get(RESULT).get(EXECUTOR).asString();
	}

}
