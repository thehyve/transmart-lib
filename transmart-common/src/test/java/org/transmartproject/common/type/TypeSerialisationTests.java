package org.transmartproject.common.type;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class TypeSerialisationTests {

    private ObjectMapper mapper = new ObjectMapper();

    private <T extends Enum> void testEnumValues(T[] values) throws IOException {
        for (T value: values) {
            String serialised = mapper.writeValueAsString(value);
            Object deserialised = mapper.readValue(serialised, value.getClass());
            Assert.assertEquals(value, deserialised);
        }
    }

    @Test
    public void testDataType() throws IOException {
        testEnumValues(DataType.values());
    }

    @Test
    public void testDimensionType() throws IOException {
        testEnumValues(DimensionType.values());
    }

    @Test
    public void testOperator() throws IOException {
        testEnumValues(Operator.values());
    }

    @Test(expected = JsonProcessingException.class)
    public void unknownOperator() throws IOException {
        mapper.readValue("invalid", Operator.class);
    }

    @Test
    public void testQueryStatus() throws IOException {
        testEnumValues(QueryStatus.values());
    }

    @Test(expected = JsonProcessingException.class)
    public void unknownQueryStatus() throws IOException {
        mapper.readValue("invalid", QueryStatus.class);
    }

    @Test
    public void testSex() throws IOException {
        testEnumValues(Sex.values());
    }

    @Test
    public void testSortOrder() throws IOException {
        testEnumValues(SortOrder.values());
    }

    @Test
    public void testTreeNodeType() throws IOException {
        testEnumValues(TreeNodeType.values());
    }

    @Test
    public void testValueType() throws IOException {
        testEnumValues(ValueType.values());
    }

    @Test(expected = JsonProcessingException.class)
    public void unknownValueType() throws IOException {
        mapper.readValue("invalid", ValueType.class);
    }

    @Test
    public void testVisualAttribute() throws IOException {
        testEnumValues(VisualAttribute.values());
    }

    @Test(expected = JsonProcessingException.class)
    public void unknownVisualAttribute() throws IOException {
        mapper.readValue("invalid", VisualAttribute.class);
    }

}
