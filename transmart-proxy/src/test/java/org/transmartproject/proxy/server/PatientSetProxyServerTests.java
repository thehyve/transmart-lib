package org.transmartproject.proxy.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.transmartproject.common.client.PatientSetClient;
import org.transmartproject.common.dto.PatientSetList;
import org.transmartproject.common.dto.PatientSetResult;
import org.transmartproject.common.constraint.*;
import org.transmartproject.common.type.DataType;
import org.transmartproject.common.type.Operator;
import org.transmartproject.proxy.TestApplication;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = MOCK, classes = TestApplication.class)
@AutoConfigureMockMvc
public class PatientSetProxyServerTests {

    @MockBean private PatientSetClient patientSetClient;

    @Autowired
    private MockMvc mvc;

    private void setupMockData() throws JsonProcessingException {
        Constraint hypertensionConstraint = AndConstraint.builder()
            .args(Arrays.asList(
                ConceptConstraint.builder().conceptCode("Systolic").build(),
                ValueConstraint.builder().valueType(DataType.Numeric).operator(Operator.Greater_than).value(130).build()
            ))
            .build();
        List<PatientSetResult> patientSets = Arrays.asList(
            PatientSetResult.builder()
                .id(1L)
                .name("Males A")
                .description("Male patients in study A")
                .setSize(1000L)
                .requestConstraints(new ObjectMapper().writeValueAsString(AndConstraint.builder().args(Arrays.asList(
                    StudyNameConstraint.builder().studyId("A").build(),
                    ConceptConstraint.builder().conceptCode("Male").build()
                )).build()))
                .build(),
            PatientSetResult.builder()
                .id(2L)
                .name("Hypertension")
                .description("Patients with blood pressure above threshold")
                .setSize(3000L)
                .requestConstraints(new ObjectMapper().writeValueAsString(hypertensionConstraint))
                .build()
        );
        // return list of patient sets for /v2/patient_sets
        ResponseEntity<PatientSetList> patientSetListResponse = ResponseEntity.ok(
            PatientSetList.builder().patientSets(patientSets).build());
        doReturn(patientSetListResponse)
            .when(patientSetClient).listPatientSets();
        // return patient set for /v2/patient_sets/2
        ResponseEntity<PatientSetResult> patientSetResponse = ResponseEntity.ok(
            patientSets.get(1));
        doReturn(patientSetResponse)
            .when(patientSetClient).getPatientSet(2L);
        // return patient set for POST /v2/patient_sets
        Constraint studyConstraint = StudyNameConstraint.builder().studyId("EHR").build();
        ResponseEntity<PatientSetResult> newPatientSetResponse = ResponseEntity
            .status(HttpStatus.CREATED)
            .body(PatientSetResult.builder()
                .id(3L)
                .name("Test set")
                .setSize(1234L)
                .requestConstraints(new ObjectMapper().writeValueAsString(studyConstraint))
                .build()
        );
        doReturn(newPatientSetResponse)
            .when(patientSetClient).createPatientSet("Test set", true, studyConstraint);
    }

    @WithMockUser(username="spring")
    @Test
    public void givenAvailableClient_whenGetPatientSets_thenStatus200() throws Exception {
        setupMockData();
        mvc.perform(get("/v2/patient_sets")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.patientSets[0].name", is("Males A")))
            .andExpect(jsonPath("$.patientSets[0].setSize", is(1000)))
            .andExpect(jsonPath("$.patientSets[1].name", is("Hypertension")))
            .andExpect(jsonPath("$.patientSets[1].setSize", is(3000)));
    }

    @WithMockUser(username="spring")
    @Test
    public void givenAvailableClient_whenGetPatientSet_thenStatus200() throws Exception {
        setupMockData();
        mvc.perform(get("/v2/patient_sets/2")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name", is("Hypertension")))
            .andExpect(jsonPath("$.requestConstraints", containsString("\"type\":\"and\"")));
    }

    @WithMockUser(username="spring")
    @Test
    public void givenAvailableClient_whenCreatePatientSet_thenStatus201() throws Exception {
        setupMockData();
        mvc.perform(post("/v2/patient_sets?name=Test set&reuse=true")
            .content(new ObjectMapper().writeValueAsString(
                StudyNameConstraint.builder().studyId("EHR").build()))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name", is("Test set")))
            .andExpect(jsonPath("$.setSize", is(1234)))
            .andExpect(jsonPath("$.requestConstraints",  containsString("\"type\":\"study_name\"")));
    }

}
