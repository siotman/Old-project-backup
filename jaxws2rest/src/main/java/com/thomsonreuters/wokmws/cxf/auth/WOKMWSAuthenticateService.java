
package com.thomsonreuters.wokmws.cxf.auth;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "WOKMWSAuthenticateService", targetNamespace = "http://auth.cxf.wokmws.thomsonreuters.com", wsdlLocation = "http://search.webofknowledge.com/esti/wokmws/ws/WOKMWSAuthenticate?wsdl")
public class WOKMWSAuthenticateService
    extends Service
{

    private final static URL WOKMWSAUTHENTICATESERVICE_WSDL_LOCATION;
    private final static WebServiceException WOKMWSAUTHENTICATESERVICE_EXCEPTION;
    private final static QName WOKMWSAUTHENTICATESERVICE_QNAME = new QName("http://auth.cxf.wokmws.thomsonreuters.com", "WOKMWSAuthenticateService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://search.webofknowledge.com/esti/wokmws/ws/WOKMWSAuthenticate?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        WOKMWSAUTHENTICATESERVICE_WSDL_LOCATION = url;
        WOKMWSAUTHENTICATESERVICE_EXCEPTION = e;
    }

    public WOKMWSAuthenticateService() {
        super(__getWsdlLocation(), WOKMWSAUTHENTICATESERVICE_QNAME);
    }

    public WOKMWSAuthenticateService(WebServiceFeature... features) {
        super(__getWsdlLocation(), WOKMWSAUTHENTICATESERVICE_QNAME, features);
    }

    public WOKMWSAuthenticateService(URL wsdlLocation) {
        super(wsdlLocation, WOKMWSAUTHENTICATESERVICE_QNAME);
    }

    public WOKMWSAuthenticateService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, WOKMWSAUTHENTICATESERVICE_QNAME, features);
    }

    public WOKMWSAuthenticateService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WOKMWSAuthenticateService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns WOKMWSAuthenticate
     */
    @WebEndpoint(name = "WOKMWSAuthenticatePort")
    public WOKMWSAuthenticate getWOKMWSAuthenticatePort() {
        return super.getPort(new QName("http://auth.cxf.wokmws.thomsonreuters.com", "WOKMWSAuthenticatePort"), WOKMWSAuthenticate.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WOKMWSAuthenticate
     */
    @WebEndpoint(name = "WOKMWSAuthenticatePort")
    public WOKMWSAuthenticate getWOKMWSAuthenticatePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://auth.cxf.wokmws.thomsonreuters.com", "WOKMWSAuthenticatePort"), WOKMWSAuthenticate.class, features);
    }

    private static URL __getWsdlLocation() {
        if (WOKMWSAUTHENTICATESERVICE_EXCEPTION!= null) {
            throw WOKMWSAUTHENTICATESERVICE_EXCEPTION;
        }
        return WOKMWSAUTHENTICATESERVICE_WSDL_LOCATION;
    }

}
