package org.transmartproject.common.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public class DimensionElementTests {

    @Test
    public void testDimensionElementEquals() {
        EqualsVerifier
            .forClass(ConceptDimensionElement.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
        EqualsVerifier
            .forClass(PatientDimensionElement.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
        EqualsVerifier
            .forClass(StudyDimensionElement.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
        EqualsVerifier
            .forClass(TrialVisitDimensionElement.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
        EqualsVerifier
            .forClass(VisitDimensionElement.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
    }

    private ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper;
    }

    @Test
    public void testVisitDimensionDateSerialisation() throws IOException {
        Map<String, String> encounterIds = new HashMap<>();
        encounterIds.put("VISIT_ID", "Visit 1");
        VisitDimensionElement element = VisitDimensionElement.builder()
            .encounterNum(1L)
            .startDate(ZonedDateTime.of(2019, 7, 23, 10, 30, 22, 1, ZoneId.of("UTC")))
            .encounterIds(encounterIds)
            .build();
        String value = getMapper().writeValueAsString(element);
        Assert.assertEquals("{\"encounterNum\":1," +
            "\"startDate\":\"2019-07-23T10:30:22.000000001Z\"," +
            "\"encounterIds\":{\"VISIT_ID\":\"Visit 1\"}}",
            value);
    }
}
