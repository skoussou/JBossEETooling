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
package org.jboss.as.quickstarts.ejbsecurity.simple.jaxws;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**

 * 
 */
@WebServiceClient(name = "AgencyServiceATService", targetNamespace = "http://www.jboss.org/jboss-jdf/jboss-as-quickstart/ejbsecurity/simple/Agency")
public class AgencyServiceATService extends Service {

    private final static URL AGENCYSERVICEATSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(AgencyServiceATService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = AgencyServiceATService.class.getResource(".");
            url = new URL(baseUrl, "AgencyServiceATService.wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'AgencyServiceATService.wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        AGENCYSERVICEATSERVICE_WSDL_LOCATION = url;
    }

    public AgencyServiceATService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AgencyServiceATService() {
        super(AGENCYSERVICEATSERVICE_WSDL_LOCATION, new QName(
                "http://www.jboss.org/jboss-jdf/jboss-as-quickstart/ejbsecurity/simple/Agency", "AgencyServiceATService"));
    }

    /**
     * 
     * @return returns AgencyServiceAT
     */
    @WebEndpoint(name = "AgencyServiceAT")
    public AgencyServiceAT getAgencyServiceAT() {
        return super.getPort(new QName("http://www.jboss.org/jboss-jdf/jboss-as-quickstart/ejbsecurity/simple/Agency",
                "AgencyServiceAT"), AgencyServiceAT.class);
    }

    /**
     * 
     * @param features A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy. Supported features not in the
     *        <code>features</code> parameter will have their default values.
     * @return returns RestaurantServiceAT
     */
    @WebEndpoint(name = "AgencyServiceAT")
    public AgencyServiceAT getRestaurantServiceAT(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.jboss.org/jboss-jdf/jboss-as-quickstart/ejbsecurity/simple/Agency",
                "AgencyServiceAT"), AgencyServiceAT.class, features);
    }

}
