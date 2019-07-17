package org.transmartproject.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.transmartproject.common.constraint.Constraint;
import org.transmartproject.common.type.TreeNodeType;
import org.transmartproject.common.type.VisualAttribute;

import javax.validation.constraints.NotBlank;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TreeNode {
    private @NotBlank String name;
    private @NotBlank String fullName;
    private String studyId;
    private String conceptCode;
    private String conceptPath;
    private TreeNodeType type;
    private EnumSet<VisualAttribute> visualAttributes;
    private Long observationCount;
    private Long patientCount;
    private Constraint constraint;
    private Map<String, String> metadata;
    private List<TreeNode> children;
}
