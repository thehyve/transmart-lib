package org.transmartproject.common.dto;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;
import org.transmartproject.common.type.TreeNodeType;
import org.transmartproject.common.type.VisualAttribute;

import java.util.Arrays;
import java.util.EnumSet;

public class ForestTests {

    @Test
    public void testForestEquals() {
        EqualsVerifier
            .forClass(Forest.class)
            .withPrefabValues(TreeNode.class,
                TreeNode.builder()
                    .name("Test")
                    .fullName("\\Test\\")
                    .type(TreeNodeType.Categorical)
                    .visualAttributes(EnumSet.of(
                        VisualAttribute.Categorical,
                        VisualAttribute.Active,
                        VisualAttribute.Leaf))
                    .build(),
                TreeNode.builder()
                    .name("Dummy")
                    .fullName("\\Dummy\\")
                    .type(TreeNodeType.Folder)
                    .visualAttributes(EnumSet.of(
                        VisualAttribute.Active,
                        VisualAttribute.Folder))
                    .children(Arrays.asList(
                        TreeNode.builder()
                            .name("Leaf")
                            .fullName("\\Dummy\\Leaf\\")
                            .type(TreeNodeType.Date)
                            .visualAttributes(EnumSet.of(
                                VisualAttribute.Date,
                                VisualAttribute.Active,
                                VisualAttribute.Leaf))
                            .build()
                    ))
                    .build())
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
    }

}
