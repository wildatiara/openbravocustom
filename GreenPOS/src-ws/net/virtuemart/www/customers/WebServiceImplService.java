/**
 * WebServiceImplService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.virtuemart.www.customers;

public interface WebServiceImplService extends javax.xml.rpc.Service {
    public java.lang.String getWebServiceAddress();

    public net.virtuemart.www.customers.WebServiceImpl getWebService() throws javax.xml.rpc.ServiceException;

    public net.virtuemart.www.customers.WebServiceImpl getWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
