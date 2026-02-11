package com.dcl.modern.contracts.api;

import com.fasterxml.jackson.annotation.JsonInclude;

/** Single contract row in grid. CONTRACTS §1.2 response; dates DD.MM.YYYY. */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ContractRowDto(
    String conId,
    String conNumber,
    String conDate,
    String conContractor,
    String conSumm,
    String conCurrency,
    String notes,
    String conExecuted,
    String conUser,
    String conReminder,
    String conAnnul,
    int attachIdx,
    int spcCount,
    String usrIdList,
    String depIdList
) {}
