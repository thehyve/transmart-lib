package org.transmartproject.proxy.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.transmartproject.common.dto.CategoricalValueAggregates;
import org.transmartproject.common.dto.ConstraintParameter;
import org.transmartproject.common.dto.Counts;
import org.transmartproject.common.dto.NumericalValueAggregates;
import org.transmartproject.common.resource.AggregateResource;
import org.transmartproject.proxy.security.CurrentUser;
import org.transmartproject.proxy.service.AggregateClientService;

import java.util.Map;

/**
 * Proxy server for aggregates.
 */
@RestController
@Validated
@CrossOrigin
@Api(value="aggregates", description="Aggregates based on observations")
public class AggregateProxyServer implements AggregateResource {

    private Logger log = LoggerFactory.getLogger(AggregateProxyServer.class);

    private AggregateClientService aggregateClientService;

    AggregateProxyServer(AggregateClientService aggregateClientService) {
        log.info("Aggregate proxy server initialised.");
        this.aggregateClientService = aggregateClientService;
    }

    @Override
    @ApiOperation(value = "Observation and patient counts", produces = "application/json")
    public ResponseEntity<Counts> counts(ConstraintParameter constraint) {
        log.info("Get counts for user {}. Constraint: {}", CurrentUser.getLogin(), constraint);
        return ResponseEntity.ok(aggregateClientService.fetchCounts(constraint));
    }

    @Override
    @ApiOperation(value = "Observation and patient counts per concept", produces = "application/json")
    public ResponseEntity<Map<String, Counts>> countsPerConcept(ConstraintParameter constraint) {
        log.info("Get counts per concept for user {}. Constraint: {}", CurrentUser.getLogin(), constraint);
        return ResponseEntity.ok(aggregateClientService.fetchCountsPerConcept(constraint));
    }

    @Override
    @ApiOperation(value = "Observation and patient counts per study", produces = "application/json")
    public ResponseEntity<Map<String, Counts>> countsPerStudy(ConstraintParameter constraint) {
        log.info("Get counts per study for user {}. Constraint: {}", CurrentUser.getLogin(), constraint);
        return ResponseEntity.ok(aggregateClientService.fetchCountsPerStudy(constraint));
    }

    @Override
    @ApiOperation(value = "Observation and patient counts per study and concept", produces = "application/json")
    public ResponseEntity<Map<String, Map<String, Counts>>> countsPerStudyAndConcept(ConstraintParameter constraint) {
        log.info("Get counts per study and concept for user {}. Constraint: {}", CurrentUser.getLogin(), constraint);
        return ResponseEntity.ok(aggregateClientService.fetchCountsPerStudyAndConcept(constraint));
    }

    @Override
    @ApiOperation(value = "Calculate numerical values aggregates", produces = "application/json")
    public ResponseEntity<Map<String, NumericalValueAggregates>> numericalValueAggregatesPerConcept(ConstraintParameter constraint) {
        log.info("Get numerical value aggregates for user {}. Constraint: {}", CurrentUser.getLogin(), constraint);
        return ResponseEntity.ok(aggregateClientService.fetchNumericalAggregatesPerConcept(constraint));
    }

    @Override
    @ApiOperation(value = "Calculate categorical values aggregates", produces = "application/json")
    public ResponseEntity<Map<String, CategoricalValueAggregates>> categoricalValueAggregatesPerConcept(ConstraintParameter constraint) {
        log.info("Get categorical value aggregates for user {}. Constraint: {}", CurrentUser.getLogin(), constraint);
        return ResponseEntity.ok(aggregateClientService.fetchCategoricalAggregatesPerConcept(constraint));
    }
}
