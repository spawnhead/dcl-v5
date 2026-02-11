package com.dcl.modern.contractors.infrastructure;

import com.dcl.modern.contractors.api.ContractorCreateOpenResponse.ContractorAccountRowDto;
import com.dcl.modern.contractors.api.ContractorCreateOpenResponse.ContractorUserRowDto;
import com.dcl.modern.contractors.api.LookupItemDto;
import com.dcl.modern.contractors.api.UserLookupDto;
import java.util.List;

/** Fake lookups for contractor create. N3a1 TEST_DATA_SPEC / payloads. */
public final class ContractorsFakeProvider {

    public static List<LookupItemDto> getCountriesLookup() {
        return List.of(
            new LookupItemDto("1", "Беларусь"),
            new LookupItemDto("2", "Россия"),
            new LookupItemDto("3", "Литва")
        );
    }

    /** Reputation: id + name (description in legacy). */
    public static List<LookupItemDto> getReputationsLookup() {
        return List.of(
            new LookupItemDto("1", "По умолчанию"),
            new LookupItemDto("2", "Надёжный"),
            new LookupItemDto("3", "Новый")
        );
    }

    public static List<UserLookupDto> getUsersLookup() {
        return List.of(
            new UserLookupDto("1", "Admin User"),
            new UserLookupDto("2", "ivanov"),
            new UserLookupDto("3", "petrov")
        );
    }

    public static List<LookupItemDto> getCurrenciesLookup() {
        return List.of(
            new LookupItemDto("1", "BYN"),
            new LookupItemDto("2", "USD"),
            new LookupItemDto("3", "EUR")
        );
    }

    public static List<ContractorUserRowDto> defaultGridUsers() {
        return List.of(new ContractorUserRowDto("1", "Admin User"));
    }

    public static List<ContractorAccountRowDto> defaultGridAccounts() {
        return List.of(
            new ContractorAccountRowDto("Счёт 1", "", null),
            new ContractorAccountRowDto("Счёт 2", "", null),
            new ContractorAccountRowDto("Счёт валютный", "", null)
        );
    }
}
