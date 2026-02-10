package com.dcl.modern;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasKey;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Orders list API integration test. CONTRACTS: docs/screens/orders/CONTRACTS.md.
 * Legacy: OrdersAction (filter, grid).
 */
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers(disabledWithoutDocker = true)
class OrdersIntegrationTest {

    @Container
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine")
        .withDatabaseName("dcl")
        .withUsername("dcl")
        .withPassword("dcl");

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listWithoutParamsReturnsOkAndShape() throws Exception {
        mockMvc.perform(get("/api/orders"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.items").isArray())
            .andExpect(jsonPath("$.total").isNumber())
            .andExpect(jsonPath("$.page").value(1))
            .andExpect(jsonPath("$.pageSize").value(25))
            .andExpect(jsonPath("$.items[0]", hasKey("ord_id")))
            .andExpect(jsonPath("$.items[0]", hasKey("ord_number")))
            .andExpect(jsonPath("$.items[0]", hasKey("ord_date")))
            .andExpect(jsonPath("$.items[0]", hasKey("can_edit_clone")))
            .andExpect(jsonPath("$.items[0]", hasKey("can_block")));
    }

    @Test
    void listWithFilterReturnsFiltered() throws Exception {
        mockMvc.perform(get("/api/orders").param("contractor_id", "5001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.items").isArray())
            .andExpect(jsonPath("$.total").isNumber())
            .andExpect(jsonPath("$.page").value(1));
    }

    @Test
    void listWithPaginationAcceptsParams() throws Exception {
        mockMvc.perform(get("/api/orders")
                .param("page", "2")
                .param("pageSize", "10")
                .param("order_by", "ord_date descending"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.page").value(2))
            .andExpect(jsonPath("$.pageSize").value(10))
            .andExpect(jsonPath("$.items.length()", greaterThanOrEqualTo(0)));
    }

    @Test
    void listWithContractorForIdFiltersByContractorFor() throws Exception {
        mockMvc.perform(get("/api/orders").param("contractor_for_id", "5001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.items").isArray())
            .andExpect(jsonPath("$.total").isNumber());
        mockMvc.perform(get("/api/orders").param("contractor_for_id", "5002"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.items").isArray())
            .andExpect(jsonPath("$.total").isNumber());
        mockMvc.perform(get("/api/orders").param("contractor_for_id", "99999"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.items").isArray())
            .andExpect(jsonPath("$.total").value(0));
    }

    @Test
    void listWithOrderByOrdNumberAscReturnsSortedItems() throws Exception {
        var result = mockMvc.perform(get("/api/orders")
                .param("page", "1")
                .param("pageSize", "50")
                .param("order_by", "ord_number asc"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.items").isArray())
            .andExpect(jsonPath("$.items.length()", greaterThanOrEqualTo(2)))
            .andReturn();
        String content = result.getResponse().getContentAsString();
        int idx1 = content.indexOf("\"ord_number\":");
        int idx2 = content.indexOf("\"ord_number\":", idx1 + 1);
        if (idx1 >= 0 && idx2 >= 0) {
            String num1 = content.substring(idx1 + 14, content.indexOf("\"", idx1 + 14));
            String num2 = content.substring(idx2 + 14, content.indexOf("\"", idx2 + 14));
            org.junit.jupiter.api.Assertions.assertTrue(
                num1.compareTo(num2) <= 0,
                "ord_number asc: first=" + num1 + " second=" + num2
            );
        }
    }

    @Test
    void listWithDatePeriodNotOverlappingReturnsEmpty() throws Exception {
        mockMvc.perform(get("/api/orders")
                .param("date_begin", "01.01.2030")
                .param("date_end", "31.12.2030"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.items").isArray())
            .andExpect(jsonPath("$.items.length()").value(0))
            .andExpect(jsonPath("$.total").value(0));
    }

    @Test
    void listWithDatePeriodFeb2025ReturnsTenRows() throws Exception {
        mockMvc.perform(get("/api/orders")
                .param("date_begin", "01.02.2025")
                .param("date_end", "10.02.2025")
                .param("page", "1")
                .param("pageSize", "25"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.items").isArray())
            .andExpect(jsonPath("$.items.length()").value(10))
            .andExpect(jsonPath("$.total").value(10));
    }

    @Test
    void listWithDatePeriodOverlappingReturnsSubset() throws Exception {
        mockMvc.perform(get("/api/orders")
                .param("date_begin", "01.02.2025")
                .param("date_end", "05.02.2025")
                .param("page", "1")
                .param("pageSize", "25"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.items").isArray())
            .andExpect(jsonPath("$.total").value(5));
    }

    @Test
    void lookupsReturnArrays() throws Exception {
        mockMvc.perform(get("/api/orders/lookups/contractors").param("have_all", "true"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
        mockMvc.perform(get("/api/orders/lookups/users").param("have_all", "true"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
        mockMvc.perform(get("/api/orders/lookups/departments").param("have_all", "true"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
        mockMvc.perform(get("/api/orders/lookups/contracts").param("contractor_id", "5001").param("have_all", "true"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
        mockMvc.perform(get("/api/orders/lookups/specifications").param("contract_id", "7001").param("have_all", "true"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }
}
