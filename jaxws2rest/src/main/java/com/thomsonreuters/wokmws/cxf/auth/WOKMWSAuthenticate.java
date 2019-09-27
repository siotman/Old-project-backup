
package com.thomsonreuters.wokmws.cxf.auth;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "WOKMWSAuthenticate", targetNamespace = "http://auth.cxf.wokmws.thomsonreuters.com")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface WOKMWSAuthenticate {


    /**
     * 
     * @throws InvalidInputExceptionException
     * @throws AuthenticationExceptionException
     * @throws SessionExceptionException
     * @throws QueryExceptionException
     * @throws ESTIWSExceptionException
     * @throws InternalServerExceptionException
     */
    @WebMethod
    @RequestWrapper(localName = "closeSession", targetNamespace = "http://auth.cxf.wokmws.thomsonreuters.com", className = "com.thomsonreuters.wokmws.cxf.auth.CloseSession")
    @ResponseWrapper(localName = "closeSessionResponse", targetNamespace = "http://auth.cxf.wokmws.thomsonreuters.com", className = "com.thomsonreuters.wokmws.cxf.auth.CloseSessionResponse")
    public void closeSession()
        throws AuthenticationExceptionException, ESTIWSExceptionException, InternalServerExceptionException, InvalidInputExceptionException, QueryExceptionException, SessionExceptionException
    ;

    /**
     * 
     * @return
     *     returns java.lang.String
     * @throws InvalidInputExceptionException
     * @throws AuthenticationExceptionException
     * @throws SessionExceptionException
     * @throws ESTIWSExceptionException
     * @throws QueryExceptionException
     * @throws InternalServerExceptionException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "authenticate", targetNamespace = "http://auth.cxf.wokmws.thomsonreuters.com", className = "com.thomsonreuters.wokmws.cxf.auth.Authenticate")
    @ResponseWrapper(localName = "authenticateResponse", targetNamespace = "http://auth.cxf.wokmws.thomsonreuters.com", className = "com.thomsonreuters.wokmws.cxf.auth.AuthenticateResponse")
    public String authenticate()
        throws AuthenticationExceptionException, ESTIWSExceptionException, InternalServerExceptionException, InvalidInputExceptionException, QueryExceptionException, SessionExceptionException
    ;

}
