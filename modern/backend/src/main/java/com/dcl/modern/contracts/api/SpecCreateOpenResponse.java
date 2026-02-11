package com.dcl.modern.contracts.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 * GET /api/contracts/draft/specifications/new/open response. N3a2 CONTRACTS.
 * docs/screens/contract_spec_create/CONTRACTS.md.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SpecCreateOpenResponse(
    SpecCreateDefaultsDto defaults,
    SpecCreateLookupsDto lookups,
    String currencyName
) {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record SpecCreateDefaultsDto(
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

    public record SpecCreateLookupsDto(
        List<UserLookupDto> users,
        List<LookupItemDto> deliveryTerms
    ) {}
}
