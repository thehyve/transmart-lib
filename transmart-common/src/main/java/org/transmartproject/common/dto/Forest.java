package org.transmartproject.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Forest {
    @JsonProperty("tree_nodes")
    private List<TreeNode> treeNodes = new ArrayList<>();
}
