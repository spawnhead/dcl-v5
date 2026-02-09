/**
 * ExRates.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package by.nbrb.www;

public interface ExRates extends javax.xml.rpc.Service {
    public java.lang.String getExRatesSoap12Address();

    public by.nbrb.www.ExRatesSoap getExRatesSoap12() throws javax.xml.rpc.ServiceException;

    public by.nbrb.www.ExRatesSoap getExRatesSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getExRatesSoapAddress();

    public by.nbrb.www.ExRatesSoap getExRatesSoap() throws javax.xml.rpc.ServiceException;

    public by.nbrb.www.ExRatesSoap getExRatesSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
