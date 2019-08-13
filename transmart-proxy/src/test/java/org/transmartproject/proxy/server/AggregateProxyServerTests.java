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
import org.transmartproject.common.dto.CategoricalValueAggregates;
import org.transmartproject.common.dto.ConstraintParameter;
import org.transmartproject.common.dto.Counts;
import org.transmartproject.common.constraint.ConceptConstraint;
import org.transmartproject.common.constraint.Constraint;
import org.transmartproject.common.constraint.StudyNameConstraint;
import org.transmartproject.common.dto.NumericalValueAggregates;
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
        ConstraintParameter conceptConstraintParameter = new ConstraintParameter(conceptConstraint);
        // return counts for /v2/observations/counts
        ResponseEntity<Counts> countsResponse = ResponseEntity.ok(
            Counts.builder().patientCount(15).build());
        doReturn(countsResponse)
            .when(aggregateClient).counts(conceptConstraintParameter);
        // return counts per concept for /v2/observations/counts_per_concept
        Constraint studyConstraint = StudyNameConstraint.builder().studyId("Test").build();
        Map<String, Counts> countsPerConcept = new HashMap<>();
        countsPerConcept.put("ABC", Counts.builder().patientCount(5).build());
        countsPerConcept.put("XYZ", Counts.builder().patientCount(20).build());
        ResponseEntity<Map<String, Counts>> countsPerConceptResponse = ResponseEntity.ok(countsPerConcept);
        doReturn(countsPerConceptResponse)
            .when(aggregateClient).countsPerConcept(new ConstraintParameter(studyConstraint));
        // return counts for concept constraint on /v2/observations/counts_per_study
        ResponseEntity<Map<String, Counts>> patientSetCountsPerStudyResponse = ResponseEntity.ok(countsPerConcept);
        doReturn(patientSetCountsPerStudyResponse)
            .when(aggregateClient).countsPerStudy(conceptConstraintParameter);
        // return counts for concept constraint on /v2/observations/counts_per_study_and_concept
        Map<String, Map<String, Counts>> countsMapMap = new HashMap<>();
        countsMapMap.put("STUDY", countsPerConcept);
        ResponseEntity<Map<String, Map<String, Counts>>> patientSetCountsPerStudyAndConceptResponse =
            ResponseEntity.ok(countsMapMap);
        doReturn(patientSetCountsPerStudyAndConceptResponse)
            .when(aggregateClient).countsPerStudyAndConcept(conceptConstraintParameter);
        // return counts for concept constraint on /v2/observations/numerical_aggregates_per_concept
        Map<String, NumericalValueAggregates> numericalAggregates = new HashMap<>();
        numericalAggregates.put("Num 1", NumericalValueAggregates.builder().avg(12.3).build());
        ResponseEntity<Map<String, NumericalValueAggregates>> patientSetNumericalAggregatesResponse =
            ResponseEntity.ok(numericalAggregates);
        doReturn(patientSetNumericalAggregatesResponse)
            .when(aggregateClient).numericalValueAggregatesPerConcept(conceptConstraintParameter);
        // return counts for concept constraint on /v2/observations/categorical_aggregates_per_concept
        Map<String, CategoricalValueAggregates> categoricalAggregates = new HashMap<>();
        Map<String, Integer> valueCounts = new HashMap<>();
        valueCounts.put("A", 123);
        valueCounts.put("B", 456);
        categoricalAggregates.put("Cat A", CategoricalValueAggregates.builder().valueCounts(valueCounts).build());
        ResponseEntity<Map<String, CategoricalValueAggregates>> patientSetCategoricalAggregatesResponse =
            ResponseEntity.ok(categoricalAggregates);
        doReturn(patientSetCategoricalAggregatesResponse)
            .when(aggregateClient).categoricalValueAggregatesPerConcept(conceptConstraintParameter);
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

    @WithMockUser(username="spring")
    @Test
    public void givenAvailableClient_whenGetCountsPerStudy_thenStatus200() throws Exception {
        setupMockData();
        mvc.perform(post("/v2/observations/counts_per_study")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"constraint\":{\"type\":\"concept\",\"conceptCode\":\"Dummy\"}}"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.XYZ.patientCount", is(20)))
            .andExpect(jsonPath("$.XYZ.observationCount", is(-1)));
    }

    @WithMockUser(username="spring")
    @Test
    public void givenAvailableClient_whenGetCountsPerStudyAndConcept_thenStatus200() throws Exception {
        setupMockData();
        mvc.perform(post("/v2/observations/counts_per_study_and_concept")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"constraint\":{\"type\":\"concept\",\"conceptCode\":\"Dummy\"}}"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.STUDY.ABC.patientCount", is(5)));
    }

    @WithMockUser(username="spring")
    @Test
    public void givenAvailableClient_whenGetNumericalAggregatesPerConcept_thenStatus200() throws Exception {
        setupMockData();
        mvc.perform(post("/v2/observations/aggregates_per_numerical_concept")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"constraint\":{\"type\":\"concept\",\"conceptCode\":\"Dummy\"}}"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.['Num 1'].avg", is(12.3)));
    }

    @WithMockUser(username="spring")
    @Test
    public void givenAvailableClient_whenGetCategoricalAggregatesPerConcept_thenStatus200() throws Exception {
        setupMockData();
        mvc.perform(post("/v2/observations/aggregates_per_categorical_concept")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"constraint\":{\"type\":\"concept\",\"conceptCode\":\"Dummy\"}}"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.['Cat A'].valueCounts.A", is(123)));
    }

}
