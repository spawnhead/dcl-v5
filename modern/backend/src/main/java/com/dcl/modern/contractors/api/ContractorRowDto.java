package com.dcl.modern.contractors.api;

/** One row in contractors grid. CONTRACTS §2. */
public record ContractorRowDto(
    String ctrId,
    String ctrName,
    String ctrFullName,
    String ctrAddress,
    String ctrPhone,
    String ctrFax,
    String ctrEmail,
    String ctrBankProps,
    String ctrBlock,
    boolean occupied
) {}
