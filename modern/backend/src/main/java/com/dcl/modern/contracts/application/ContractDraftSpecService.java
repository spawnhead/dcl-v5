package com.dcl.modern.contracts.application;

import com.dcl.modern.contracts.api.*;
import com.dcl.modern.contracts.infrastructure.ContractsFakeProvider;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * N3a2 Draft specification create. Legacy: SpecificationAction.insert, beforeSave.
 * CONTRACTS: docs/screens/contract_spec_create/CONTRACTS.md.
 */
@Service
public class ContractDraftSpecService {

    public SpecCreateOpenResponse open(String currencyName) {
        String currency = currencyName != null && !currencyName.isBlank() ? currencyName : "BYN";
        SpecCreateOpenResponse.SpecCreateDefaultsDto defaults =
            new SpecCreateOpenResponse.SpecCreateDefaultsDto(
                null,
                "",
                "",
                "",
                "",
                "",
                null,
                "",
                "",
                "",
                "",
                "",
                List.of(new SpecPaymentRowDto(100, 0, currency)),
                false,
                false,
                false,
                false,
                "",
                "",
                "",
                "",
                ""
            );
        SpecCreateOpenResponse.SpecCreateLookupsDto lookups =
            new SpecCreateOpenResponse.SpecCreateLookupsDto(
                ContractsFakeProvider.getUsersLookupForSpec(),
                ContractsFakeProvider.getDeliveryTermsLookup()
            );
        List<SpecCreateOpenResponse.TabMetaDto> tabs = List.of(
            new SpecCreateOpenResponse.TabMetaDto("mainPanel", "Главная"),
            new SpecCreateOpenResponse.TabMetaDto("complaintSpecification", "Претензии")
        );
        return new SpecCreateOpenResponse(defaults, lookups, tabs, currency);
    }

    /**
     * Validates and returns redirect + spec row for client to add to grid. No session persistence.
     */
    public SpecCreateSaveResponse save(SpecCreateSaveRequest req) {
        if (req == null || req.spcNumber() == null || req.spcNumber().isBlank()) {
            throw new ContractCreateService.ContractCreateValidationException(
                new ValidationErrorResponse(
                    new ValidationErrorResponse.ErrorDetail("VALIDATION_ERROR",
                        List.of(new ValidationErrorResponse.FieldError("spcNumber", "Введите номер спецификации")))
                ));
        }
        if (req.spcDate() == null || req.spcDate().isBlank()) {
            throw new ContractCreateService.ContractCreateValidationException(
                new ValidationErrorResponse(
                    new ValidationErrorResponse.ErrorDetail("VALIDATION_ERROR",
                        List.of(new ValidationErrorResponse.FieldError("spcDate", "Введите дату спецификации")))
                ));
        }
        if (req.deliveryTerm() == null || req.deliveryTerm().id() == null || req.deliveryTerm().id().isBlank()) {
            throw new ContractCreateService.ContractCreateValidationException(
                new ValidationErrorResponse(
                    new ValidationErrorResponse.ErrorDetail("VALIDATION_ERROR",
                        List.of(new ValidationErrorResponse.FieldError("deliveryTerm.id", "Выберите условие поставки")))
                ));
        }
        String summ = req.spcSumm() != null ? req.spcSumm() : "";
        SpecCreateSaveResponse.SpecRowDto row = new SpecCreateSaveResponse.SpecRowDto(
            req.spcNumber(),
            req.spcDate(),
            summ,
            "",
            req.spcSummNds() != null ? req.spcSummNds() : "",
            summ,
            "0"
        );
        return new SpecCreateSaveResponse(true, "/contracts/new", row);
    }
}
