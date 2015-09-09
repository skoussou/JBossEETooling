/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.ejbsecurity.simple;


import org.jboss.as.quickstarts.ejb_security.AgencyManager;
import org.jboss.as.quickstarts.ejbsecurity.simple.jaxws.AgencyServiceAT;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.naming.InitialContext;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.servlet.annotation.WebServlet;

import java.util.UUID;

/**
 * An adapter class that exposes the AgencyManagerEJB business API as a Web Service.
 * 
 * @author stkoussou@redhat.com
 * 
 */
@WebService(serviceName = "AgencyServiceATService", portName = "AgencyServiceAT", name = "AgencyServiceAT", targetNamespace = "http://www.jboss.org/jboss-jdf/jboss-as-quickstart/ejbsecurity/simple/Agency")
//@HandlerChain(file = "/context-handlers.xml", name = "Context Handlers")
//@SOAPBinding(style = SOAPBinding.Style.RPC)
@WebServlet("/AgencyServiceAT")
public class AgencyServiceATImpl extends WSHelper implements AgencyServiceAT {

    //private AgencyManagerEJB mockAgencyManager = MockAgencyManager.getSingletonInstance();
    private MockAgencyManager mockAgencyManager = MockAgencyManager.getSingletonInstance();

    InitialContext ic;
    
    /**
     * Book a number of seats in the Agency. Enrols a Participant, then passes the call through to the business logic.
     */
    @WebMethod
    public String makeBooking(String user, String password, String bookingDetails) throws AgencyException {

        System.out.println("[SERVICE] Agency service invoked to make a booking");
        
        try {
        	//String bookRef = mockAgencyManager.makeBooking(user, password, bookingDetails);
            // CreateLoginContext        	
        	WSHelper helper = new WSHelper();
        	LoginContext lc = helper.createLocalLogin(user, password);
        	
           // makeEJBBookingCall
			InitialContext ic = new InitialContext();
			AgencyManager bean = (AgencyManager) ic.lookup("java:global/ee-security-reproducer/ejb-security-reproducer/AgencyManager!org.jboss.as.quickstarts.ejb_security.AgencyManager");
        	
			
        	//String bookRef = (String) Subject.doAs(lc.getSubject(), new WSHelper());
			// Security Not Required
			bean.getSecurityInfo();
			
			// Security with Role CREATE Required
			String bookRef = bean.makeBooking(bookingDetails);
			
			helper.closeLocalLogin(lc);
			
           // return BookingRef or message
           return bookRef;
            
        } catch (Exception e) {
            throw new AgencyException("Error when calling AgencyManagerEJB.makebooking", e);
        }

        
    }

    /**
     * obtain the number of existing bookings
     * 
     * @return the number of current bookings
     */
    @Override
    public int getBookingCount() {
        return mockAgencyManager.getBookingCount();
    }

    /**
     * Reset the booking count to zero
     * 
     * Note: To simplify this example, this method is not part of the compensation logic, so will not be undone if the AT is
     * compensated. It can also be invoked outside of an active AT.
     */
    @Override
    public void reset() {
        mockAgencyManager.reset();
    }
}
