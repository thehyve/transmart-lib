package org.transmartproject.common.constraint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.transmartproject.common.config.JacksonConfiguration;
import org.transmartproject.common.type.DataType;
import org.transmartproject.common.type.Operator;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JacksonConfiguration.class})
public class ConstraintSerialisationTests {

    private @Autowired ObjectMapper objectMapper;

    @Test
    public void testConstraintSerialisationAndDeserialisation() throws IOException {
        List<Constraint> constraints = Arrays.asList(
            new TrueConstraint(),
            ConceptConstraint.builder().conceptCode("concept1").build(),
            new StudyNameConstraint("study1"),
            new ValueConstraint(DataType.Numeric, Operator.Less_than, 10.5),
            new ValueConstraint(DataType.String, Operator.Contains, "x"),
            new AndConstraint(Arrays.asList(
                ConceptConstraint.builder().conceptCode("concept1").build(),
                new ValueConstraint(DataType.String, Operator.Equals, "value x"))),
            new RelationConstraint("PAR", null, null, null),
            new RelationConstraint("CHI", new TrueConstraint(), true, false)
        );

        for (Constraint constraint: constraints) {
            String json = objectMapper.writeValueAsString(constraint);
            Constraint object = objectMapper.readValue(json, Constraint.class);
            Assert.assertEquals(constraint, object);
        }
    }

}
