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
import org.transmartproject.common.client.RelationTypeClient;
import org.transmartproject.common.dto.RelationType;
import org.transmartproject.common.dto.RelationTypeList;
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
public class RelationTypeProxyServerTests {

    @MockBean private RelationTypeClient relationTypeClient;

    @Autowired
    private MockMvc mvc;

    private void setupMockData() {
        List<RelationType> relationTypes = Arrays.asList(
            RelationType.builder()
                .label("Parent")
                .biological(true)
                .description("Parent of")
                .build(),
            RelationType.builder()
                .label("Sibling")
                .description("Brother or sister of")
                .biological(true)
                .symmetrical(true)
                .build()
        );
        // return list of relation types for /v2/relation_types
        ResponseEntity<RelationTypeList> relationTypeListResponse = ResponseEntity.ok(
            RelationTypeList.builder().relationTypes(relationTypes).build());
        doReturn(relationTypeListResponse)
            .when(relationTypeClient).listRelationTypes();
    }

    @WithMockUser(username="spring")
    @Test
    public void givenAvailableClient_whenGetRelationTypes_thenStatus200() throws Exception {
        setupMockData();
        mvc.perform(get("/v2/relation_types")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.relationTypes[0].label", is("Parent")))
            .andExpect(jsonPath("$.relationTypes[1].label", is("Sibling")))
            .andExpect(jsonPath("$.relationTypes[1].symmetrical", is(true)));
    }

}
