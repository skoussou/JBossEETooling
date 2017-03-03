package org.jboss.as.quickstarts.wshelloworld;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class SSLClient {

	public static String CLIENT_WSDL = "https://localhost:8543/jboss-helloworld-ws-ssl/HelloWorldService?wsdl";

    static {
        //for localhost testing only
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
                new javax.net.ssl.HostnameVerifier() {

                    public boolean verify(String hostname,
                                          javax.net.ssl.SSLSession sslSession) {
                        return hostname.equals("localhost");
                    }
                });
    }

    public static void main(String[] args) throws MalformedURLException {
    	Client service = new Client(new URL(CLIENT_WSDL));
        System.out.println(">>>>>>>>>>>>>>>>>>>>> "+service.sayHello());
        System.out.println(">>>>>>>>>>>>>>>>>>>>> "+service.sayHelloToName("Stelios Kousouris"));
        System.out.println(">>>>>>>>>>>>>>>>>>>>> "+service.sayHelloToNames(Arrays.asList("Stelios", "Katie", "Dimitra", "Liliana")));
    }
}