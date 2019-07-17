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
import org.transmartproject.common.client.RelationClient;
import org.transmartproject.common.dto.Relation;
import org.transmartproject.common.dto.RelationList;
import org.transmartproject.proxy.TestApplication;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = MOCK, classes = TestApplication.class)
@AutoConfigureMockMvc
public class RelationProxyServerTests {

    @MockBean private RelationClient relationClient;

    @Autowired
    private MockMvc mvc;

    private void setupMockData() {
        List<Relation> relations = Arrays.asList(
            Relation.builder()
                .relationTypeLabel("Parent")
                .leftSubjectId(1L)
                .rightSubjectId(2L)
                .biological(true)
                .build(),
            Relation.builder()
                .relationTypeLabel("Parent")
                .leftSubjectId(1L)
                .rightSubjectId(3L)
                .shareHousehold(true)
                .build(),
            Relation.builder()
                .relationTypeLabel("Sibling")
                .leftSubjectId(2L)
                .rightSubjectId(3L)
                .biological(true)
                .shareHousehold(false)
                .build(),
            Relation.builder()
                .relationTypeLabel("Twin")
                .leftSubjectId(4L)
                .rightSubjectId(5L)
                .build()
        );
        // return list of relations for /v2/relations
        ResponseEntity<RelationList> relationListResponse = ResponseEntity.ok(
            RelationList.builder().relations(relations).build());
        doReturn(relationListResponse)
            .when(relationClient).listRelations();
    }

    @WithMockUser(username="spring")
    @Test
    public void givenAvailableClient_whenGetRelations_thenStatus200() throws Exception {
        setupMockData();
        mvc.perform(get("/v2/relations")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.relations[0].relationTypeLabel", is("Parent")))
            .andExpect(jsonPath("$.relations[3].relationTypeLabel", is("Twin")))
            .andExpect(jsonPath("$.relations[2].biological", is(true)))
            .andExpect(jsonPath("$.relations[2].shareHousehold", is(false)));
    }

}
