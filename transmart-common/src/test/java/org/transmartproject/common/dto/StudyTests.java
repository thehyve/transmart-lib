package org.transmartproject.common.dto;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class StudyTests {

    @Test
    public void testStudyEquals() {
        EqualsVerifier
            .forClass(Study.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
    }

    @Test
    public void testStudyBuilder() {
        Study expected = new Study(300L, "EHR", null, null, null, null);
        Assert.assertEquals(expected, Study.builder().id(300L).studyId("EHR").build());
    }

    @Test
    public void testStudyListEquals() {
        EqualsVerifier
            .forClass(StudyList.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
    }

    @Test
    public void testStudyListBuilder() {
        List<Study> studies = Arrays.asList(
            Study.builder().studyId("EHR").build(),
            Study.builder()
                .id(1L)
                .studyId("TEST")
                .bioExperimentId(100L)
                .secureObjectToken("PUBLIC")
                .dimensions(Arrays.asList("patient", "concept"))
                .build());
        StudyList expected = new StudyList(studies);
        Assert.assertEquals(expected, StudyList.builder().studies(studies).build());
    }

}
