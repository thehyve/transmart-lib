package org.transmartproject.proxy.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.transmartproject.common.client.ObservationClient;
import org.transmartproject.common.dto.*;
import org.transmartproject.common.constraint.*;
import org.transmartproject.common.type.*;
import org.transmartproject.proxy.TestApplication;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;

import static org.hamcrest.Matchers.closeTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = MOCK, classes = TestApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class ObservationProxyServerTests {

    private final Logger log = LoggerFactory.getLogger(ObservationProxyServerTests.class);

    private @MockBean ObservationClient observationClient;

    private @Autowired MockMvc mvc;

    private @Autowired ObjectMapper objectMapper;

    private void setupMockData() throws JsonProcessingException {
        Constraint relationConstraint = RelationConstraint.builder()
            .relationTypeLabel("Parent")
            .biological(true)
            .relatedSubjectsConstraint(AndConstraint.builder().args(Arrays.asList(
                ConceptConstraint.builder().conceptCode("Age").build(),
                ValueConstraint.builder().operator(Operator.Greater_than).valueType(DataType.Numeric).value(50).build()
            )).build())
            .build();
        // return hypercube for /v2/observations
        Map<String, List<Object>> dimensionElements = new HashMap<>();
        Hypercube hypercube = Hypercube
            .builder()
            .dimensionDeclarations(Arrays.asList(
                DimensionDeclaration.builder().name("patient").dimensionType(DimensionType.Subject).build(),
                DimensionDeclaration.builder().name("concept").build(),
                DimensionDeclaration.builder().name("start time").inline(true).build()
            ))
            .cells(Arrays.asList(
                Cell.builder().dimensionIndexes(Arrays.asList(0, 0)).inlineDimensions(Arrays.asList(1234))
                    .stringValue("Value").build(),
                Cell.builder().dimensionIndexes(Arrays.asList(0, 1)).inlineDimensions(Arrays.asList(5678))
                    .numericValue(new BigDecimal(100)).build()
            ))
            .dimensionElements(dimensionElements)
            .build();
        byte[] serialisedHypercube = objectMapper.writeValueAsString(hypercube).getBytes();
        log.debug("Stream should have {} bytes", serialisedHypercube.length);
        InputStream stream = new ByteArrayInputStream(serialisedHypercube);

        doAnswer(invocation -> {
            Consumer<InputStream> reader = invocation.getArgument(1);
            log.debug("Start streaming mock data to observation client");
            try {
                reader.accept(stream);
            } catch(Exception e) {
                log.error("Error reading observations", e);
            }
            return null;
        })
        .when(observationClient).query(eq(Query.builder().type("clinical").constraint(relationConstraint).build()), any());
    }

    @WithMockUser(username="spring")
    @Test
    public void givenAvailableClient_whenGetObservations_thenStatus200() throws Exception {
        setupMockData();
        mvc.perform(post("/v2/observations")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"type\":\"clinical\",\"constraint\":{\"type\":\"relation\",\"relationTypeLabel\":\"Parent\",\"biological\":true,\"relatedSubjectsConstraint\":{" +
                "\"type\":\"and\",\"args\":[{\"type\":\"concept\",\"conceptCode\":\"Age\"},{" +
                "\"type\":\"value\",\"operator\":\">\",\"valueType\":\"NUMERIC\",\"value\":50}]}}}"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andDo(result -> {
                // Wait until data has been written
                Thread.sleep(100);
                byte[] bytes = result.getResponse().getContentAsByteArray();
                log.debug("Response has {} bytes", bytes.length);
                Hypercube hypercube = objectMapper.readValue(bytes, Hypercube.class);
                Assert.assertEquals("patient", hypercube.getDimensionDeclarations().get(0).getName());
                MatcherAssert.assertThat(hypercube.getCells().get(1).getNumericValue(), closeTo(new BigDecimal(100), new BigDecimal(0.001)));
            });
    }

}
