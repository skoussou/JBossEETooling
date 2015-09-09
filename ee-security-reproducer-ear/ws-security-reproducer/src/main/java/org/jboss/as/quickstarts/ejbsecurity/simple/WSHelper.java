package org.jboss.as.quickstarts.ejbsecurity.simple;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.jboss.as.quickstarts.ejb_security.AgencyManager;
import org.jboss.security.auth.callback.UsernamePasswordHandler;


public class WSHelper implements java.security.PrivilegedAction {

	private static final String CLIENT_DOMAIN = "client-database-login";
	
	/*
	 * JNDI bindings for session bean named AgencyManager in deployment unit subdeployment "ejb-security-reproducer.jar" of deployment "ee-security-reproducer.ear" are as follows:

	java:global/ee-security-reproducer/ejb-security-reproducer/AgencyManager!org.jboss.as.quickstarts.ejb_security.AgencyManager
	java:app/ejb-security-reproducer/AgencyManager!org.jboss.as.quickstarts.ejb_security.AgencyManager
	java:module/AgencyManager!org.jboss.as.quickstarts.ejb_security.AgencyManager
	java:global/ee-security-reproducer/ejb-security-reproducer/AgencyManager
	java:app/ejb-security-reproducer/AgencyManager
	java:module/AgencyManager
 
	 * 
	 */
	
	protected LoginContext createLocalLogin(String userName, String paswd) throws AgencyException {
		try {
//			UsernamePasswordHandler handler = new UsernamePasswordHandler(userName, paswd.toCharArray());
			UsernamePasswordHandler handler = new UsernamePasswordHandler(userName, paswd);
	        LoginContext lc = new LoginContext(CLIENT_DOMAIN, handler);
	        lc.login(); 
			
	        return lc;

		}  catch (LoginException e) {
			System.out.println("ERROR login in WS");
			throw new AgencyException("Couldn't connect with the login service [LoginContext]. Contact your service provider.", e);
		}
	}
	
	protected void closeLocalLogin(LoginContext lc) throws AgencyException {
		try {
			lc.logout();
		} catch (LoginException e) {
			System.out.println("ERROR logout in WS");
			throw new AgencyException("Couldn't connect with the login service [LoginContext]. Contact your service provider.", e);
		}

	}

	@Override
	public Object run() {
		InitialContext ic;
		String refBook = "FAILED";
		try {
			ic = new InitialContext();

		AgencyManager bean = (AgencyManager) ic.lookup("java:global/ee-security-reproducer/ejb-security-reproducer/AgencyManager!org.jboss.as.quickstarts.ejb_security.AgencyManager");
    	
		refBook = bean.getSecurityInfo();
		
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return refBook;
	}

	
}
