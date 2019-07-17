package org.transmartproject.common.constraint;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

public class ConstraintRewriterTests {

    @Test
    public void testEliminateDoubleNegation() {
        // given: "a constraint with double negation and its simplified form"
        Constraint constraint = new AndConstraint(Arrays.asList(
                new StudyNameConstraint("SURVEY0"),
                new Negation(new Negation(new AndConstraint(Arrays.asList(
                        ConceptConstraint.builder().conceptCode("height").build(),
                        new StudyNameConstraint("SURVEY0")
                ))))
        ));

        Constraint expected = new AndConstraint(Arrays.asList(
                new StudyNameConstraint("SURVEY0"),
                ConceptConstraint.builder().conceptCode("height").build(),
                new StudyNameConstraint("SURVEY0")
        ));

        // when: "rewriting the first constraint"
        Constraint result = new NormaliseConstraintRewriter().build(constraint);

        // then: "the rewrite result is equal to the preferred form"
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testSimplificationOfSingletonCombinations() {
        // given: "a two level singleton combination with a concept constraint and its simplified form"
        Constraint constraint = new AndConstraint(Arrays.asList(
                new OrConstraint(Arrays.asList(
                        ConceptConstraint.builder().conceptCode("height").build()
                ))
        ));

        Constraint expected = ConceptConstraint.builder().conceptCode("height").build();

        // when: "rewriting the first constraint"
        Constraint result = new NormaliseConstraintRewriter().build(constraint);

        // then: "the rewrite result is equal to the preferred form"
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testSimplificationOfNestedCombinations() {
        // given: "a constraint with double negation"
        Constraint constraint = new AndConstraint(Arrays.asList(
                new AndConstraint(Arrays.asList(
                        new StudyNameConstraint("SURVEY1"),
                        ConceptConstraint.builder().conceptCode("height").build()
                )),
                new AndConstraint(Arrays.asList(
                        PatientSetConstraint.builder().patientSetId(-1L).build(),
                        new TrueConstraint()
                ))
        ));

        Constraint expected = new AndConstraint(Arrays.asList(
                new StudyNameConstraint("SURVEY1"),
                ConceptConstraint.builder().conceptCode("height").build(),
                PatientSetConstraint.builder().patientSetId(-1L).build()
        ));

        // when: "rewriting the first constraint"
        Constraint result = new NormaliseConstraintRewriter().build(constraint);

        // then: "the rewrite result is equal to the preferred form"
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testRewritingOfSubselectWithTrueSubconstraint() {
        // given: "two logically equivalent constraints, the second form preferred"
        Constraint constraint = new SubSelectionConstraint("patient", new TrueConstraint());

        Constraint expected = new TrueConstraint();

        // when: "rewriting the first constraint"
        Constraint result = new NormaliseConstraintRewriter().build(constraint);

        // then: "the rewrite result is equal to the preferred form"
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testRewritingOfConjunctionWithSubselectWithTrueSubconstraint() {
        // given: "two logically equivalent constraints, the second form preferred"
        Constraint constraint = new AndConstraint(Arrays.asList(
                new SubSelectionConstraint("patient", new TrueConstraint()),
                ConceptConstraint.builder().conceptCode("favouritebook").build()
        ));

        Constraint expected = ConceptConstraint.builder().conceptCode("favouritebook").build();

        // when: "rewriting the first constraint"
        Constraint result = new NormaliseConstraintRewriter().build(constraint);

        // then: "the rewrite result is equal to the preferred form"
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testRewritingOfNestedSubselectWithTrueSubconstraint() {
        // given: "two logically equivalent constraints, the second form preferred"
        Constraint constraint = new SubSelectionConstraint("patient",
                new SubSelectionConstraint("patient", new TrueConstraint()));

        Constraint expected = new TrueConstraint();

        // when: "rewriting the first constraint"
        Constraint result = new NormaliseConstraintRewriter().build(constraint);

        // then: "the rewrite result is equal to the preferred form"
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testRewritingOfDisjunctionWithTrueConstraint() {
        // given: "two logically equivalent constraints"
        Constraint constraint = new OrConstraint(Arrays.asList(
                ConceptConstraint.builder().conceptCode("favouritebook").build(),
                new TrueConstraint()
        ));

        Constraint expected = new TrueConstraint();

        // when: "rewriting the first constraint"
        Constraint result = new NormaliseConstraintRewriter().build(constraint);

        // then: "the rewrite result is equal to the preferred form"
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testRewritingDoesNotMutateConstraints() {
        // given: "a disjunction of conjunctions in the wrong order"
        Constraint constraint = new OrConstraint(Arrays.asList(
                new AndConstraint(Arrays.asList(
                        new StudyNameConstraint("SURVEY1"),
                        ConceptConstraint.builder().conceptCode("weight").build()
                )),
                new AndConstraint(Arrays.asList(
                        new StudyNameConstraint("SURVEY1"),
                        ConceptConstraint.builder().conceptCode("height").build()
                ))
        ));

        // when: "rewriting the constraint"
        Constraint normalisedConstraint = new NormaliseConstraintRewriter().build(constraint);

        // then: "the rewrite result is different object than the original"
        Assert.assertNotSame(constraint, normalisedConstraint);
    }

    @Test @Ignore("Not yet implemented")
    public void testConstraintRewritingOfMultipleConceptConstraints() {
        // given: "two logically equivalent constraints, the second form preferred"
        Constraint constraint = new OrConstraint(Arrays.asList(
            ConceptConstraint.builder().conceptCode("height").build(),
            ConceptConstraint.builder().conceptCode("birthdate").build()
        ));

        Constraint expected = ConceptConstraint.builder().conceptCodes(Arrays.asList("height", "birthdate")).build();

        // when: "rewriting the first constraint"
        Constraint result = new NormaliseConstraintRewriter().build(constraint);

        // then: "the rewrite result is equal to the preferred form"
        Assert.assertEquals(expected, result);
    }

    @Test @Ignore("Not yet implemented")
    public void testConstraintRewritingOfStudySpecificConstraints() {
        // given: "two logically equivalent constraints, the second form preferred"
        Constraint constraint = new OrConstraint(Arrays.asList(
            new AndConstraint(Arrays.asList(
                new StudyNameConstraint("SURVEY0"),
                ConceptConstraint.builder().conceptCode("height").build()
            )),
            new AndConstraint(Arrays.asList(
                new StudyNameConstraint("SURVEY0"),
                ConceptConstraint.builder().conceptCode("birthdate").build()
            ))
        ));
        Constraint expected = new AndConstraint(Arrays.asList(
            new StudyNameConstraint("SURVEY0"),
            ConceptConstraint.builder().conceptCodes(Arrays.asList("birthdate", "height")).build()
        ));

        // when: "rewriting the first constraint"
        Constraint result = new NormaliseConstraintRewriter().build(constraint);

        // then: "the rewrite result is equal to the preferred form"
        Assert.assertEquals(expected, result);
    }

}
