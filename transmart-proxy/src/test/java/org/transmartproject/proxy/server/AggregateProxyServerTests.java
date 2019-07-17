package org.transmartproject.proxy.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.transmartproject.common.client.AggregateClient;
import org.transmartproject.common.dto.ConstraintParameter;
import org.transmartproject.common.dto.Counts;
import org.transmartproject.common.constraint.ConceptConstraint;
import org.transmartproject.common.constraint.Constraint;
import org.transmartproject.common.constraint.StudyNameConstraint;
import org.transmartproject.proxy.TestApplication;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = MOCK, classes = TestApplication.class)
@AutoConfigureMockMvc
public class AggregateProxyServerTests {

    @MockBean private AggregateClient aggregateClient;

    @Autowired
    private MockMvc mvc;

    private void setupMockData() {
        Constraint conceptConstraint = ConceptConstraint.builder().conceptCode("Dummy").build();
        // return counts for /v2/observations/counts
        ResponseEntity<Counts> countsResponse = ResponseEntity.ok(
            Counts.builder().patientCount(15).build());
        doReturn(countsResponse)
            .when(aggregateClient).counts(new ConstraintParameter(conceptConstraint));
        // return counts per concept for /v2/observations/counts_per_concept
        Constraint studyConstraint = StudyNameConstraint.builder().studyId("Test").build();
        Map<String, Counts> countsPerConcept = new HashMap<>();
        countsPerConcept.put("ABC", Counts.builder().patientCount(5).build());
        countsPerConcept.put("XYZ", Counts.builder().patientCount(20).build());
        ResponseEntity<Map<String, Counts>> countsPerConceptResponse = ResponseEntity.ok(countsPerConcept);
        doReturn(countsPerConceptResponse)
            .when(aggregateClient).countsPerConcept(new ConstraintParameter(studyConstraint));
    }

    @WithMockUser(username="spring")
    @Test
    public void givenAvailableClient_whenGetCounts_thenStatus200() throws Exception {
        setupMockData();
        mvc.perform(post("/v2/observations/counts")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"constraint\":{\"type\":\"concept\",\"conceptCode\":\"Dummy\"}}"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.patientCount", is(15)))
            .andExpect(jsonPath("$.observationCount", is(-1)));
    }

    @WithMockUser(username="spring")
    @Test
    public void givenAvailableClient_whenGetCountsPerConcept_thenStatus200() throws Exception {
        setupMockData();
        mvc.perform(post("/v2/observations/counts_per_concept")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"constraint\":{\"type\":\"study_name\",\"studyId\":\"Test\"}}"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.ABC.patientCount", is(5)))
            .andExpect(jsonPath("$.ABC.observationCount", is(-1)))
            .andExpect(jsonPath("$.XYZ.patientCount", is(20)))
            .andExpect(jsonPath("$.XYZ.observationCount", is(-1)));
    }

}
