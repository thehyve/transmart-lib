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
import org.transmartproject.common.client.StudyClient;
import org.transmartproject.common.dto.Study;
import org.transmartproject.common.dto.StudyList;
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
public class StudyProxyServerTests {

    @MockBean private StudyClient studyClient;

    @Autowired
    private MockMvc mvc;

    private void setupMockData() {
        List<Study> studies = new ArrayList<>();
        Study ehrStudy = Study.builder().studyId("EHR").build();
        studies.add(ehrStudy);
        // return list of studies for /v2/studies
        ResponseEntity<StudyList> studyListResponse = ResponseEntity.ok(
            StudyList.builder().studies(studies).build());
        doReturn(studyListResponse)
            .when(studyClient).listStudies();
        // return study for /v2/studies/1
        ResponseEntity<Study> studyResponse = ResponseEntity.ok(ehrStudy);
        doReturn(studyResponse)
            .when(studyClient).getStudy(1L);
        // return 404 for /v2/studies/-1
        doReturn(ResponseEntity.notFound().build())
            .when(studyClient).getStudy(-1L);
    }

    @WithMockUser(username="spring")
    @Test
    public void givenAvailableClient_whenGetStudies_thenStatus200() throws Exception {
        setupMockData();
        mvc.perform(get("/v2/studies")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.studies[0].studyId", is("EHR")));
    }

    @WithMockUser(username="spring")
    @Test
    public void givenAvailableClient_whenGetStudy_thenStatus200() throws Exception {
        setupMockData();
        mvc.perform(get("/v2/studies/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.studyId", is("EHR")));
    }

    @WithMockUser(username="spring")
    @Test
    public void givenAvailableClient_whenGetNonExistingStudy_thenStatus404() throws Exception {
        setupMockData();
        mvc.perform(get("/v2/studies/-1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void givenUnauthorised_whenGetStudies_thenStatus401() throws Exception {
        setupMockData();
        mvc.perform(get("/v2/studies")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
    }

}
