package org.transmartproject.common.constraint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.transmartproject.common.type.DataType;
import org.transmartproject.common.type.Operator;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ConstraintSerialisationTests {

    @Test
    public void testConstraintSerialisationAndDeserialisation() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

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
            String json = mapper.writeValueAsString(constraint);
            Constraint object = mapper.readValue(json, Constraint.class);
            Assert.assertEquals(constraint, object);
        }
    }

}
