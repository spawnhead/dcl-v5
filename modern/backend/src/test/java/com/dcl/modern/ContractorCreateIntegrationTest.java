package com.dcl.modern;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * TASK-0066: contractor_create contact persons persistence smoke.
 * Create contractor with 1 contact person, open/edit returns it.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@Testcontainers(disabledWithoutDocker = true)
class ContractorCreateIntegrationTest {

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
    void createContractorWithContactPersonAndOpenReturnsIt() throws Exception {
        String saveBody = """
            {
              "ctrName": "Test Contractor",
              "ctrFullName": "Test Contractor Full",
              "country": {"id": "1", "name": "Беларусь"},
              "reputation": {"id": "1", "name": "По умолчанию"},
              "gridUsers": [{"usrId": "1", "userFullName": "admin"}],
              "gridAccounts": [
                {"accName": "Счёт 1", "accAccount": "", "currency": null},
                {"accName": "Счёт 2", "accAccount": "", "currency": null},
                {"accName": "Счёт валютный", "accAccount": "", "currency": null}
              ],
              "gridContactPersons": [
                {"cpsName": "Иван Иванов", "cpsPosition": "Директор", "cpsOnReason": "", "cpsPhone": "+375291234567", "cpsMobPhone": "", "cpsFax": "", "cpsEmail": "ivan@test.by", "cpsContractComment": "", "cpsFire": "0", "cpsBlock": "0"}
              ],
              "ctrBankProps": "",
              "ctrComment": "",
              "returnTo": "contractors"
            }
            """;

        var saveResult = mockMvc.perform(post("/api/contractors/create/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(saveBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.ctrId").isNotEmpty())
            .andExpect(jsonPath("$.redirectTo").value("/contractors"))
            .andReturn();

        Object ctrIdObj = com.jayway.jsonpath.JsonPath.read(saveResult.getResponse().getContentAsString(), "$.ctrId");
        String ctrId = String.valueOf(ctrIdObj);

        mockMvc.perform(get("/api/contractors/create/open")
                .param("returnTo", "contractors")
                .param("ctrId", ctrId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.defaults.gridContactPersons", org.hamcrest.Matchers.hasSize(1)))
            .andExpect(jsonPath("$.defaults.gridContactPersons[0].cpsName").value("Иван Иванов"))
            .andExpect(jsonPath("$.defaults.gridContactPersons[0].cpsPosition").value("Директор"))
            .andExpect(jsonPath("$.defaults.gridContactPersons[0].cpsEmail").value("ivan@test.by"));
    }
}
