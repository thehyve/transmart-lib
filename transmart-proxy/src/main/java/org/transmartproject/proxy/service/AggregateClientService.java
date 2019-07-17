package org.transmartproject.proxy.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.transmartproject.common.client.AggregateClient;
import org.transmartproject.common.dto.CategoricalValueAggregates;
import org.transmartproject.common.dto.ConstraintParameter;
import org.transmartproject.common.dto.Counts;
import org.transmartproject.common.dto.NumericalValueAggregates;

import java.util.Map;

@Service
public class AggregateClientService {

    private AggregateClient aggregateClient;

    public AggregateClientService(AggregateClient aggregateClient) {
        this.aggregateClient = aggregateClient;
    }

    public Counts fetchCounts(ConstraintParameter constraint) {
        ResponseEntity<Counts> response = aggregateClient.counts(constraint);
        return ResponseEntityHelper.unwrap(response);
    }

    public Map<String, Counts> fetchCountsPerConcept(ConstraintParameter constraint) {
        ResponseEntity<Map<String, Counts>> response = aggregateClient.countsPerConcept(constraint);
        return ResponseEntityHelper.unwrap(response);
    }

    public Map<String, Counts> fetchCountsPerStudy(ConstraintParameter constraint) {
        ResponseEntity<Map<String, Counts>> response = aggregateClient.countsPerStudy(constraint);
        return ResponseEntityHelper.unwrap(response);
    }

    public Map<String, Map<String, Counts>> fetchCountsPerStudyAndConcept(ConstraintParameter constraint) {
        ResponseEntity<Map<String, Map<String, Counts>>> response = aggregateClient.countsPerStudyAndConcept(constraint);
        return ResponseEntityHelper.unwrap(response);
    }

    public Map<String, NumericalValueAggregates> fetchNumericalAggregatesPerConcept(ConstraintParameter constraint) {
        ResponseEntity<Map<String, NumericalValueAggregates>> response = aggregateClient.numericalValueAggregatesPerConcept(constraint);
        return ResponseEntityHelper.unwrap(response);
    }

    public Map<String, CategoricalValueAggregates> fetchCategoricalAggregatesPerConcept(ConstraintParameter constraint) {
        ResponseEntity<Map<String, CategoricalValueAggregates>> response = aggregateClient.categoricalValueAggregatesPerConcept(constraint);
        return ResponseEntityHelper.unwrap(response);
    }

}
