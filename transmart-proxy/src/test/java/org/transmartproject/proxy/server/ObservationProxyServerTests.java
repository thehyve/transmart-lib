package org.transmartproject.proxy.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import org.transmartproject.common.type.DataType;
import org.transmartproject.common.type.DimensionType;
import org.transmartproject.common.type.Operator;
import org.transmartproject.proxy.TestApplication;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @MockBean private ObservationClient observationClient;

    @Autowired
    private MockMvc mvc;

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
        byte[] serialisedHypercube = new ObjectMapper().writeValueAsBytes(hypercube);
        InputStream stream = new ByteArrayInputStream(serialisedHypercube);

        doAnswer(invocation -> {
            Consumer<InputStream> reader = invocation.getArgument(1);
            reader.accept(stream);
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
                Hypercube hypercube = new ObjectMapper().readValue(result.getResponse().getContentAsByteArray(), Hypercube.class);
                Assert.assertEquals("patient", hypercube.getDimensionDeclarations().get(0).getName());
                Assert.assertThat(hypercube.getCells().get(1).getNumericValue(), closeTo(new BigDecimal(100), new BigDecimal(0.001)));
            });
    }

}
