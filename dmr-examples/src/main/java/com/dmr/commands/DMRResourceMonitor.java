package com.dmr.commands;

import java.io.IOException;
import java.util.Map;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;

import com.dmr.connection.DMRConnection;

/**
 * 
 * @author stelios@redhat.com
 * @param <V>
 *
 */
public interface DMRResourceMonitor<K, V> {

	public static final String RESULT = "result";

	
	ModelNode monitorCommand(Map<K, V> params) throws IOException;
}
