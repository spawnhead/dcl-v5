package com.dcl.modern.contracts.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 * POST /api/contracts/draft/specifications/save body. N3a2 CONTRACTS.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SpecCreateSaveRequest(
    UserLookupDto user,
    String spcNumber,
    String spcDate,
    String spcSumm,
    String spcSummNds,
    String spcDeliveryCond,
    LookupItemDto deliveryTerm,
    String spcAdditionalDaysCount,
    String spcDeliveryPercent,
    String spcDeliverySum,
    String spcDeliveryDate,
    String spcAddPayCond,
    List<SpecPaymentRowDto> specificationPayments,
    Boolean spcMontage,
    Boolean spcPayAfterMontage,
    Boolean spcFaxCopy,
    Boolean spcOriginal,
    String spcComment
) {}
