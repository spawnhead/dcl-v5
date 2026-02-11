package com.dcl.modern.contractors.application;

import com.dcl.modern.contractors.api.ContractorCreateOpenResponse;
import com.dcl.modern.contractors.api.ContractorCreateSaveRequest;
import com.dcl.modern.contractors.api.ContractorCreateSaveResponse;
import com.dcl.modern.contractors.api.LookupItemDto;
import com.dcl.modern.contractors.infrastructure.ContractorsFakeProvider;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

/**
 * N3a1 Contractor create (from Contract form). Legacy: ContractorAction create, process.
 * CONTRACTS: docs/screens/contractor_create/CONTRACTS.md.
 */
@Service
public class ContractorCreateService {

    public ContractorCreateOpenResponse open(String returnTo) {
        ContractorCreateOpenResponse.ContractorCreateDefaultsDto defaults =
            new ContractorCreateOpenResponse.ContractorCreateDefaultsDto(
                "",
                "",
                (LookupItemDto) null,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                (LookupItemDto) null,
                ContractorsFakeProvider.defaultGridUsers(),
                ContractorsFakeProvider.defaultGridAccounts(),
                List.of(),
                "",
                ""
            );
        ContractorCreateOpenResponse.ContractorCreateLookupsDto lookups =
            new ContractorCreateOpenResponse.ContractorCreateLookupsDto(
                ContractorsFakeProvider.getCountriesLookup(),
                ContractorsFakeProvider.getReputationsLookup(),
                ContractorsFakeProvider.getUsersLookup(),
                ContractorsFakeProvider.getCurrenciesLookup()
            );
        return new ContractorCreateOpenResponse(defaults, lookups, returnTo != null ? returnTo : "contract");
    }

    /**
     * Saves contractor (FAKE). Returns ctrId and redirectTo for Contract form to refresh and set contractor.
     */
    public ContractorCreateSaveResponse save(ContractorCreateSaveRequest req) {
        if (req == null || req.ctrName() == null || req.ctrName().isBlank()) {
            throw new ContractorCreateService.ValidationException("ctrName", "Введите наименование контрагента");
        }
        String ctrId = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        String redirectTo = "/contracts/new?newContractorId=" + ctrId;
        return new ContractorCreateSaveResponse(ctrId, redirectTo, req.returnTo() != null ? req.returnTo() : "contract");
    }

    public static final class ValidationException extends RuntimeException {
        private final String field;
        private final String message;

        public ValidationException(String field, String message) {
            super(message);
            this.field = field;
            this.message = message;
        }
        public String getField() { return field; }
        @Override
        public String getMessage() { return message; }
    }
}
