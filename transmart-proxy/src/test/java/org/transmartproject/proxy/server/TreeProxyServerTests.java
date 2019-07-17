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
import org.transmartproject.common.client.TreeClient;
import org.transmartproject.common.dto.Forest;
import org.transmartproject.common.dto.TreeNode;
import org.transmartproject.common.type.TreeNodeType;
import org.transmartproject.common.type.VisualAttribute;
import org.transmartproject.proxy.TestApplication;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = MOCK, classes = TestApplication.class)
@AutoConfigureMockMvc
public class TreeProxyServerTests {

    @MockBean private TreeClient treeClient;

    @Autowired
    private MockMvc mvc;

    private void setupMockData() {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("url", "https://example.com");
        List<TreeNode> rootNodes = Arrays.asList(
            TreeNode.builder()
                .name("Conditions")
                .fullName("\\Conditions\\")
                .metadata(metadata)
                .type(TreeNodeType.Folder)
                .visualAttributes(EnumSet.of(
                    VisualAttribute.Active,
                    VisualAttribute.Folder
                ))
                .children(Arrays.asList(
                    TreeNode.builder()
                        .name("Headache")
                        .fullName("\\Conditions\\Headache\\")
                        .conceptCode("https://icd.who.int/2010/R51")
                        .visualAttributes(EnumSet.of(
                            VisualAttribute.Active,
                            VisualAttribute.Leaf,
                            VisualAttribute.Categorical))
                        .build())
                )
                .build());
        // return ontology trees for /v2/tree_nodes
        ResponseEntity<Forest> treeNodesResponse = ResponseEntity.ok(
            Forest.builder().treeNodes(rootNodes).build());
        doReturn(treeNodesResponse)
            .when(treeClient).getForest("\\", 2, null, null, null);
    }

    @WithMockUser(username="spring")
    @Test
    public void givenAvailableClient_whenGetStudies_thenStatus200() throws Exception {
        setupMockData();
        mvc.perform(get("/v2/tree_nodes?root=\\&depth=2")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.tree_nodes[0].name", is("Conditions")))
            .andExpect(jsonPath("$.tree_nodes[0].children[0].conceptCode", is("https://icd.who.int/2010/R51")));
    }

}
