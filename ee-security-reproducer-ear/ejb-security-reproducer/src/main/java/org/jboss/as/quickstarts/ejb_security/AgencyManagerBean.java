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
package org.jboss.as.quickstarts.ejb_security;

import java.security.Principal;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.jboss.ejb3.annotation.SecurityDomain;

/**
 * Simple secured EJB using EJB security annotations
 * 
 * @author stkousso
 * 
 */
/**
 * 
 * Annotate this EJB methods for authorization. 
 * 
 */


@Stateless(name = "AgencyManager")
@org.jboss.ejb3.annotation.SecurityDomain(value = "travelio-ejb-security-domain")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AgencyManagerBean implements AgencyManager {

    // Inject the Session Context
    @Resource
    private SessionContext ctx;

    /**
     * Secured EJB method using security annotations
     */
    public String getSecurityInfo() {
    	// Session context injected using the resource annotation
        Principal principal = ctx.getCallerPrincipal();
        
    	System.out.println("[EJB] ["+principal.toString()+"] Reached without security AgencyManager.getSecurityInfo()");

     
        return principal.toString();
    }

    @RolesAllowed("createRole")
    public String makeBooking(String bookingDetails) {
    	// Session context injected using the resource annotation
        Principal principal = ctx.getCallerPrincipal();
    	
       System.out.println("[EJB] ["+principal.toString()+"]  Reached securily AgencyManager.makeBooking()");
       
       System.out.println("[EJB] makeBooking invoked to make a booking ["+bookingDetails+"]");
       return "Ref:9293912";
    }

    /**
     * obtain the number of existing bookings
     * 
     * @return the number of current bookings
     */
    @RolesAllowed("queryRole")
    public int getBookingCount() {
       System.out.println("[EJB] getBookingCount invoked to COUNT Bookings");
       return 0;
    }


    /**
     * Reset the booking count to zero
     */
    @RolesAllowed("modifyRole")
    public void reset() {
        System.out.println("[EJB] reset to remove bookings");
    }
}

