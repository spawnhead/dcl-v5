package com.dcl.modern.orders.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Order edit/create open response. Legacy: OrderAction input/edit load.
 * CONTRACTS: docs/screens/order_edit/CONTRACTS.md.
 */
public record OrderEditOpenResponse(
    OrderHeaderDto order,
    List<OrderProduceRowDto> produces,
    List<OrderPaymentRowDto> orderPayments,
    List<OrderPaySumRowDto> orderPaySums,
    OrderEditLookupsDto lookups,
    OrderRoleFlagsDto roleFlags,
    boolean formReadOnly
) {
    public record OrderHeaderDto(
        Integer ordId,
        String ordNumber,
        LocalDate ordDate,
        String contractorId,
        String contactPersonId,
        String currencyId,
        String stuffCategoryId,
        String blankId,
        String sellerForWhoId,
        String contractorForId,
        String contractId,
        String specificationId,
        Short ordBlock,
        Short ordAnnul,
        BigDecimal ordSumm,
        String ordComment,
        LocalDate ordSentToProdDate,
        LocalDate ordReceivedConfDate,
        String ordNumConf,
        LocalDate ordDateConf,
        LocalDate ordConfSentDate,
        LocalDate ordExecutedDate,
        LocalDate ordReadyForDelivDate,
        String ordPayCondition,
        String ordAddr,
        String ordDeliveryTerm,
        String ordAddInfo
    ) {}

    public record OrderProduceRowDto(
        Integer oprId,
        String oprProduceName,
        String oprCatalogNum,
        BigDecimal oprCount,
        BigDecimal oprPriceBrutto,
        BigDecimal oprDiscount,
        BigDecimal oprPriceNetto,
        BigDecimal oprSumm,
        String oprComment,
        BigDecimal drpPrice
    ) {}

    /** CONTRACTS §4 orderPayments grid. */
    public record OrderPaymentRowDto(
        Integer orpId,
        BigDecimal orpPercent,
        BigDecimal orpSum,
        LocalDate orpDate
    ) {}

    /** CONTRACTS §5 orderPaySums grid. */
    public record OrderPaySumRowDto(
        Integer opsId,
        BigDecimal opsSum,
        LocalDate opsDate
    ) {}

    public record OrderEditLookupsDto(
        List<LookupItemDto> contractors,
        List<LookupItemDto> sellers,
        List<LookupItemDto> currencies,
        List<LookupItemDto> stuffCategories,
        List<LookupItemDto> blanks,
        List<LookupItemDto> contracts,
        List<LookupItemDto> specifications
    ) {}

    public record OrderRoleFlagsDto(
        boolean admin,
        boolean economist,
        boolean logist,
        boolean manager,
        boolean userInLithuania
    ) {}
}
