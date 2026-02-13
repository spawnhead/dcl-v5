package com.dcl.modern.orders.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Order save request. Legacy: OrderAction process. CONTRACTS: docs/screens/order_edit/CONTRACTS.md.
 */
public record OrderEditSaveRequest(
    boolean isNewDoc,
    Integer ordId,
    OrderSaveDto order,
    List<OrderProduceSaveDto> produces,
    List<OrderPaymentSaveDto> orderPayments,
    List<OrderPaySumSaveDto> orderPaySums
) {
    public record OrderSaveDto(
        String ordNumber,
        LocalDate ordDate,
        Integer contractorId,
        Integer contactPersonId,
        Integer currencyId,
        Integer stuffCategoryId,
        Integer blankId,
        Integer sellerForWhoId,
        Integer contractorForId,
        Integer contractId,
        Integer specificationId,
        LocalDate ordSentToProdDate,
        LocalDate ordReceivedConfDate,
        String ordNumConf,
        LocalDate ordDateConf,
        LocalDate ordConfSentDate,
        LocalDate ordReadyForDelivDate,
        LocalDate ordExecutedDate,
        String ordPayCondition,
        String ordAddr,
        String ordDeliveryTerm,
        String ordAddInfo,
        String ordComment
    ) {}

    public record OrderProduceSaveDto(
        Integer oprId,
        String oprProduceName,
        String oprCatalogNum,
        BigDecimal oprCount,
        BigDecimal oprPriceBrutto,
        BigDecimal oprDiscount,
        BigDecimal oprPriceNetto,
        String oprComment,
        BigDecimal drpPrice
    ) {}

    /** CONTRACTS §4. */
    public record OrderPaymentSaveDto(
        Integer orpId,
        BigDecimal orpPercent,
        BigDecimal orpSum,
        LocalDate orpDate
    ) {}

    /** CONTRACTS §5. */
    public record OrderPaySumSaveDto(
        Integer opsId,
        BigDecimal opsSum,
        LocalDate opsDate
    ) {}
}
