package org.transmartproject.common.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.transmartproject.common.config.JacksonConfiguration;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JacksonConfiguration.class})
public class DimensionElementTests {

    private @Autowired ObjectMapper objectMapper;

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

    @Test
    public void testVisitDimensionDateSerialisation() throws IOException {
        Map<String, String> encounterIds = new HashMap<>();
        encounterIds.put("VISIT_ID", "Visit 1");
        VisitDimensionElement element = VisitDimensionElement.builder()
            .encounterNum(1L)
            .startDate(ZonedDateTime.of(2019, 7, 23, 10, 30, 22, 1, ZoneId.of("UTC")))
            .encounterIds(encounterIds)
            .build();
        String value = objectMapper.writeValueAsString(element);
        Assert.assertEquals("{\"encounterNum\":1," +
            "\"startDate\":\"2019-07-23T10:30:22.000000001Z\"," +
            "\"encounterIds\":{\"VISIT_ID\":\"Visit 1\"}}",
            value);
    }
}
