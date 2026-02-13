package com.dcl.modern.contracts.infrastructure;

import com.dcl.modern.contracts.domain.Contract;
import com.dcl.modern.contracts.domain.ContractRow;
import com.dcl.modern.contractors.domain.Contractor;
import com.dcl.modern.contractors.infrastructure.ContractorRepository;
import com.dcl.modern.contracts.domain.Seller;
import com.dcl.modern.currency.domain.Currency;
import com.dcl.modern.currency.infrastructure.CurrencyRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * Loads contracts list from Postgres. TASK-0024: contracts list shows real data after create.
 * Replaces fake data for N3 list so newly created contracts appear.
 */
@Component
public class ContractListProvider {

    private static final DateTimeFormatter DD_MM_YYYY = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final ContractRepository contractRepository;
    private final ContractorRepository contractorRepository;
    private final CurrencyRepository currencyRepository;
    private final SellerRepository sellerRepository;

    public ContractListProvider(ContractRepository contractRepository,
            ContractorRepository contractorRepository,
            CurrencyRepository currencyRepository,
            SellerRepository sellerRepository) {
        this.contractRepository = contractRepository;
        this.contractorRepository = contractorRepository;
        this.currencyRepository = currencyRepository;
        this.sellerRepository = sellerRepository;
    }

    public List<ContractRow> loadAll() {
        List<Contract> contracts = contractRepository.findAllByOrderByDateDescNumberDesc();
        Set<Integer> ctrIds = contracts.stream().map(Contract::getContractorId).filter(c -> c != null).collect(Collectors.toSet());
        Set<Integer> curIds = contracts.stream().map(Contract::getCurrencyId).filter(c -> c != null).collect(Collectors.toSet());
        Set<Integer> slnIds = contracts.stream().map(Contract::getSellerId).filter(s -> s != null).collect(Collectors.toSet());

        Map<Integer, String> contractorNames = contractorRepository.findAllById(ctrIds).stream()
            .collect(Collectors.toMap(Contractor::getId, Contractor::getName));
        Map<Integer, String> currencyNames = currencyRepository.findAllById(curIds).stream()
            .collect(Collectors.toMap(Currency::getId, Currency::getName));
        Map<Integer, String> sellerNames = sellerRepository.findAllById(slnIds).stream()
            .collect(Collectors.toMap(Seller::getId, Seller::getName));

        return contracts.stream()
            .map(c -> toRow(c, contractorNames, currencyNames, sellerNames))
            .toList();
    }

    private static ContractRow toRow(Contract c,
            Map<Integer, String> contractorNames,
            Map<Integer, String> currencyNames,
            Map<Integer, String> sellerNames) {
        String conId = String.valueOf(c.getId());
        String conNumber = c.getNumber();
        LocalDate conDate = c.getDate();
        String conContractor = c.getContractorId() != null ? contractorNames.getOrDefault(c.getContractorId(), "") : "";
        String conSumm = formatSum(c.getSum());
        String conCurrency = c.getCurrencyId() != null ? currencyNames.getOrDefault(c.getCurrencyId(), "") : "";
        String notes = c.getComment() != null ? c.getComment() : "";
        String conExecuted = c.getExecuted() != null ? (c.getExecuted() == 1 ? "1" : "0") : "0";
        String conUser = "admin";
        String conReminder = "";
        String conAnnul = c.getAnnul() != null && c.getAnnul() == 1 ? "1" : "";
        return new ContractRow(conId, conNumber, conDate, conContractor, conSumm, conCurrency, notes, conExecuted, conUser, conReminder, conAnnul, 0, 0, "", "", false);
    }

    private static String formatSum(BigDecimal sum) {
        if (sum == null) return "";
        return sum.setScale(2, java.math.RoundingMode.HALF_UP).toString().replace(".", " ");
    }
}
