/**
 * ExternalSalesImplService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.virtuemart.www.externalsales;

public interface ExternalSalesImplService extends javax.xml.rpc.Service {
    public java.lang.String getExternalSalesAddress();

    public net.virtuemart.www.externalsales.ExternalSalesImpl getExternalSales() throws javax.xml.rpc.ServiceException;

    public net.virtuemart.www.externalsales.ExternalSalesImpl getExternalSales(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
