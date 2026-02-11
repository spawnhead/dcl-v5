package com.dcl.modern.contracts.domain;

import java.time.LocalDate;

/**
 * One row of Contracts list grid. select-contracts / dcl_contract_filter shape.
 * Legacy: ContractsForm.Contract.
 */
public record ContractRow(
    String conId,
    String conNumber,
    LocalDate conDate,
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
    String depIdList,
    boolean oridinalAbsent
) {}
