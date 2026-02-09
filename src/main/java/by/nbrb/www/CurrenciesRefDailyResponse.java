/**
 * CurrenciesRefDailyResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package by.nbrb.www;

public class CurrenciesRefDailyResponse  implements java.io.Serializable {
    private by.nbrb.www.CurrenciesRefDailyResponseCurrenciesRefDailyResult currenciesRefDailyResult;

    public CurrenciesRefDailyResponse() {
    }

    public CurrenciesRefDailyResponse(
           by.nbrb.www.CurrenciesRefDailyResponseCurrenciesRefDailyResult currenciesRefDailyResult) {
           this.currenciesRefDailyResult = currenciesRefDailyResult;
    }


    /**
     * Gets the currenciesRefDailyResult value for this CurrenciesRefDailyResponse.
     * 
     * @return currenciesRefDailyResult
     */
    public by.nbrb.www.CurrenciesRefDailyResponseCurrenciesRefDailyResult getCurrenciesRefDailyResult() {
        return currenciesRefDailyResult;
    }


    /**
     * Sets the currenciesRefDailyResult value for this CurrenciesRefDailyResponse.
     * 
     * @param currenciesRefDailyResult
     */
    public void setCurrenciesRefDailyResult(by.nbrb.www.CurrenciesRefDailyResponseCurrenciesRefDailyResult currenciesRefDailyResult) {
        this.currenciesRefDailyResult = currenciesRefDailyResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CurrenciesRefDailyResponse)) return false;
        CurrenciesRefDailyResponse other = (CurrenciesRefDailyResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.currenciesRefDailyResult==null && other.getCurrenciesRefDailyResult()==null) || 
             (this.currenciesRefDailyResult!=null &&
              this.currenciesRefDailyResult.equals(other.getCurrenciesRefDailyResult())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCurrenciesRefDailyResult() != null) {
            _hashCode += getCurrenciesRefDailyResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CurrenciesRefDailyResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.nbrb.by/", ">CurrenciesRefDailyResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currenciesRefDailyResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.nbrb.by/", "CurrenciesRefDailyResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.nbrb.by/", ">>CurrenciesRefDailyResponse>CurrenciesRefDailyResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
