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
import org.transmartproject.common.client.DimensionClient;
import org.transmartproject.common.dto.Dimension;
import org.transmartproject.common.dto.DimensionList;
import org.transmartproject.proxy.TestApplication;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = MOCK, classes = TestApplication.class)
@AutoConfigureMockMvc
public class DimensionProxyServerTests {

    @MockBean private DimensionClient dimensionClient;

    @Autowired
    private MockMvc mvc;

    private void setupMockData() {
        List<Dimension> dimensions = new ArrayList<>();
        Dimension patientDimension = Dimension.builder().name("patient").build();
        dimensions.add(patientDimension);
        // return list of studies for /v2/dimensions
        ResponseEntity<DimensionList> dimensionListResponse = ResponseEntity.ok(
            DimensionList.builder().dimensions(dimensions).build());
        doReturn(dimensionListResponse)
            .when(dimensionClient).listDimensions();
        // return study for /v2/dimensions/patient
        ResponseEntity<Dimension> dimensionsResponse = ResponseEntity.ok(patientDimension);
        doReturn(dimensionsResponse)
            .when(dimensionClient).getDimension("patient");
        // return 404 for /v2/dimensions/colour
        doReturn(ResponseEntity.notFound().build())
            .when(dimensionClient).getDimension("colour");
    }

    @WithMockUser(username="spring")
    @Test
    public void givenAvailableClient_whenGetDimensions_thenStatus200() throws Exception {
        setupMockData();
        mvc.perform(get("/v2/dimensions")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.dimensions[0].name", is("patient")));
    }

    @WithMockUser(username="spring")
    @Test
    public void givenAvailableClient_whenGetDimension_thenStatus200() throws Exception {
        setupMockData();
        mvc.perform(get("/v2/dimensions/patient")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name", is("patient")));
    }

    @WithMockUser(username="spring")
    @Test
    public void givenAvailableClient_whenGetNonExistingDimension_thenStatus404() throws Exception {
        setupMockData();
        mvc.perform(get("/v2/dimensions/colour")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

}
