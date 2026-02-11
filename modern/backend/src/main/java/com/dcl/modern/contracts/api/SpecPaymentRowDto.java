package com.dcl.modern.contracts.api;

/** One row in specificationPayments. */
public record SpecPaymentRowDto(Integer percent, Integer delayDays, String currencyName) {}
