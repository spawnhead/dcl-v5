/**
 * ExRatesSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package by.nbrb.www;

public interface ExRatesSoap extends java.rmi.Remote {

    /**
     * Начальная дата публикации курсов валют, устанавливаемых Национальным
     * банком Республики Беларусь
     */
    public java.util.Calendar startDate(int periodicity) throws java.rmi.RemoteException;

    /**
     * Последняя дата публикации курсов валют, устанавливаемых Национальным
     * банком Республики Беларусь ежедневно
     */
    public java.util.Calendar lastDailyExRatesDate() throws java.rmi.RemoteException;

    /**
     * Последняя дата публикации курсов валют, устанавливаемых Национальным
     * банком Республики Беларусь ежемесячно
     */
    public java.util.Calendar lastMonthlyExRatesDate() throws java.rmi.RemoteException;

    /**
     * Курсы валют, устанавливаемые Национальным банком Республики
     * Беларусь ежедневно
     */
    public by.nbrb.www.ExRatesDailyResponseExRatesDailyResult exRatesDaily(String onDate) throws java.rmi.RemoteException;

    /**
     * Курсы валют, устанавливаемые Национальным банком Республики
     * Беларусь ежемесячно
     */
    public by.nbrb.www.ExRatesMonthlyResponseExRatesMonthlyResult exRatesMonthly(java.util.Calendar onDate) throws java.rmi.RemoteException;

    /**
     * Полный перечень ежедневных валют, котируемых Национальным банком
     * Республики Беларусь
     */
    public by.nbrb.www.CurrenciesRefDailyResponseCurrenciesRefDailyResult currenciesRefDaily() throws java.rmi.RemoteException;

    /**
     * Полный перечень ежемесячных валют, котируемых Национальным
     * банком Республики Беларусь
     */
    public by.nbrb.www.CurrenciesRefMonthlyResponseCurrenciesRefMonthlyResult currenciesRefMonthly() throws java.rmi.RemoteException;
}
