package com.dcl.modern.contracts.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 * GET /api/contracts/draft/specifications/new/open response. N3a2 CONTRACTS.
 * docs/screens/contract_spec_create/CONTRACTS.md.
 * TASK-0013: 2 tabs (mainPanel, complaintSpecification) per SNAPSHOT.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SpecCreateOpenResponse(
    SpecCreateDefaultsDto defaults,
    SpecCreateLookupsDto lookups,
    List<TabMetaDto> tabs,
    String currencyName
) {
    public record TabMetaDto(String id, String label) {}
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
        String spcComment,
        String spcLetter1Date,
        String spcLetter2Date,
        String spcLetter3Date,
        String spcComplaintInCourtDate
    ) {}

    public record SpecCreateLookupsDto(
        List<UserLookupDto> users,
        List<LookupItemDto> deliveryTerms
    ) {}
}
