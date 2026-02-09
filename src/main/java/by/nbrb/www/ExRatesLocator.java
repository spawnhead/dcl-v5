/**
 * ExRatesLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package by.nbrb.www;

public class ExRatesLocator extends org.apache.axis.client.Service implements by.nbrb.www.ExRates {

    public ExRatesLocator() {
    }


    public ExRatesLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ExRatesLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ExRatesSoap12
    private java.lang.String ExRatesSoap12_address = "http://www.nbrb.by/Services/ExRates.asmx";

    public java.lang.String getExRatesSoap12Address() {
        return ExRatesSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ExRatesSoap12WSDDServiceName = "ExRatesSoap12";

    public java.lang.String getExRatesSoap12WSDDServiceName() {
        return ExRatesSoap12WSDDServiceName;
    }

    public void setExRatesSoap12WSDDServiceName(java.lang.String name) {
        ExRatesSoap12WSDDServiceName = name;
    }

    public by.nbrb.www.ExRatesSoap getExRatesSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ExRatesSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getExRatesSoap12(endpoint);
    }

    public by.nbrb.www.ExRatesSoap getExRatesSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            by.nbrb.www.ExRatesSoap12Stub _stub = new by.nbrb.www.ExRatesSoap12Stub(portAddress, this);
            _stub.setPortName(getExRatesSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setExRatesSoap12EndpointAddress(java.lang.String address) {
        ExRatesSoap12_address = address;
    }


    // Use to get a proxy class for ExRatesSoap
    private java.lang.String ExRatesSoap_address = "http://www.nbrb.by/Services/ExRates.asmx";

    public java.lang.String getExRatesSoapAddress() {
        return ExRatesSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ExRatesSoapWSDDServiceName = "ExRatesSoap";

    public java.lang.String getExRatesSoapWSDDServiceName() {
        return ExRatesSoapWSDDServiceName;
    }

    public void setExRatesSoapWSDDServiceName(java.lang.String name) {
        ExRatesSoapWSDDServiceName = name;
    }

    public by.nbrb.www.ExRatesSoap getExRatesSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ExRatesSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getExRatesSoap(endpoint);
    }

    public by.nbrb.www.ExRatesSoap getExRatesSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            by.nbrb.www.ExRatesSoapStub _stub = new by.nbrb.www.ExRatesSoapStub(portAddress, this);
            _stub.setPortName(getExRatesSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setExRatesSoapEndpointAddress(java.lang.String address) {
        ExRatesSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (by.nbrb.www.ExRatesSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                by.nbrb.www.ExRatesSoap12Stub _stub = new by.nbrb.www.ExRatesSoap12Stub(new java.net.URL(ExRatesSoap12_address), this);
                _stub.setPortName(getExRatesSoap12WSDDServiceName());
                return _stub;
            }
            if (by.nbrb.www.ExRatesSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                by.nbrb.www.ExRatesSoapStub _stub = new by.nbrb.www.ExRatesSoapStub(new java.net.URL(ExRatesSoap_address), this);
                _stub.setPortName(getExRatesSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ExRatesSoap12".equals(inputPortName)) {
            return getExRatesSoap12();
        }
        else if ("ExRatesSoap".equals(inputPortName)) {
            return getExRatesSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.nbrb.by/", "ExRates");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.nbrb.by/", "ExRatesSoap12"));
            ports.add(new javax.xml.namespace.QName("http://www.nbrb.by/", "ExRatesSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ExRatesSoap12".equals(portName)) {
            setExRatesSoap12EndpointAddress(address);
        }
        else 
if ("ExRatesSoap".equals(portName)) {
            setExRatesSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
