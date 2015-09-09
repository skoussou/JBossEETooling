ws-security-reproducer
==================================================
Author: Stelios Kousouris
Technologies: WS, Security, EAP
Summary: Custom (non-container) LoginContext generation for the propagation of it to a container resource (EJB)
Target Product: EAP  
Product Versions: EAP 6.1, EAP 6.2, EAP 6.3  
Source: 



System requirements
-------------------

The application this project produces is designed to be run on Red Hat JBoss Enterprise Application Platform 6.1 or later. 

All you need to build this project is Java 6.0 (Java SDK 1.6) or later, Maven 3.0 or later.

 
Configure Maven
---------------

If you have not yet done so, you must [Configure Maven](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/CONFIGURE_MAVEN.md#configure-maven-to-build-and-deploy-the-quickstarts) before testing the quickstarts.


Start the JBoss EAP Server with the Custom Options
----------------------

Add the security domains as follows (included standalone.xml)
                <security-domain name="client-login">
                    <authentication>
                        <login-module name="LocalClient" code="Client" flag="required">
                            <module-option name="multi-threaded" value="true"/>
                            <module-option name="restore-login-identity" value="false"/>
                        </login-module>
                    </authentication>
                </security-domain>
                <security-domain name="travelio-ejb-security-domain" cache-type="default">
                    <authentication>
                        <login-module code="Remoting" flag="optional">
                            <module-option name="password-stacking" value="useFirstPass"/>
                        </login-module>
                        <login-module code="org.jboss.security.auth.spi.UsersRolesLoginModule" flag="required">
                            <module-option name="defaultUsersProperties" value="file:${jboss.server.config.dir}/ejb-users.properties"/>
                            <module-option name="defaultRolesProperties" value="file:${jboss.server.config.dir}/ejb-roles.properties"/>
                            <module-option name="usersProperties" value="file:${jboss.server.config.dir}/ejb-users.properties"/>
                            <module-option name="rolesProperties" value="file:${jboss.server.config.dir}/ejb-roles.properties"/>
                            <module-option name="password-stacking" value="useFirstPass"/>
                        </login-module>
                    </authentication>
                </security-domain>
            </security-domains>  


Code
--------------------
AgencyServiceATImpl: 	Non container secured webservice endpoint makes secured EJB call
WSHelper:		Creates LoginContext with the LocalClient module above



Test with the provided ejb-security-soapui-project.xml
--------------------

