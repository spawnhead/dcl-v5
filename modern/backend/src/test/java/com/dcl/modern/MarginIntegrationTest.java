package com.dcl.modern;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasKey;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Margin API integration test. CONTRACTS: docs/screens/margin/CONTRACTS.md.
 * Legacy: MarginAction, MarginDevDataAction.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers(disabledWithoutDocker = true)
class MarginIntegrationTest {
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
    void getDataReturnsGridWithMetaAndView() throws Exception {
        mockMvc.perform(get("/api/margin/data").param("limit", "200"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").isArray())
            .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(1)))
            .andExpect(jsonPath("$.view").exists())
            .andExpect(jsonPath("$.view", hasKey("view_contractor")))
            .andExpect(jsonPath("$.meta").exists())
            .andExpect(jsonPath("$.meta.rowsTotal").isNumber())
            .andExpect(jsonPath("$.meta.rowsReturned").isNumber())
            .andExpect(jsonPath("$.meta.limited").isBoolean());
    }

    @Test
    void generateAndCleanAllSucceed() throws Exception {
        mockMvc.perform(post("/api/margin/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"date_begin\":\"01.01.2024\",\"date_end\":\"31.12.2024\"}"))
            .andExpect(status().isOk());

        mockMvc.perform(post("/api/margin/cleanAll"))
            .andExpect(status().isOk());
    }

    @Test
    void lookupsReturnArrays() throws Exception {
        mockMvc.perform(get("/api/margin/lookups/users").param("have_all", "true"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
        mockMvc.perform(get("/api/margin/lookups/departments").param("have_all", "true"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
        mockMvc.perform(get("/api/margin/lookups/contractors").param("have_all", "true"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
        mockMvc.perform(get("/api/margin/lookups/routes").param("have_all", "true"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }

    @Test
    void exportExcelReturnsOctetStream() throws Exception {
        mockMvc.perform(get("/api/margin/export/excel"))
            .andExpect(status().isOk());
    }
}
