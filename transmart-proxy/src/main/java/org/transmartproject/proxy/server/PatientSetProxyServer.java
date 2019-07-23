package org.transmartproject.proxy.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.transmartproject.common.dto.PatientSetList;
import org.transmartproject.common.dto.PatientSetResult;
import org.transmartproject.common.constraint.Constraint;
import org.transmartproject.common.resource.PatientSetResource;
import org.transmartproject.proxy.security.CurrentUser;
import org.transmartproject.proxy.service.PatientSetClientService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * Proxy server for patient sets.
 */
@RestController
@Validated
@CrossOrigin
public class PatientSetProxyServer implements PatientSetResource {

    private Logger log = LoggerFactory.getLogger(PatientSetProxyServer.class);

    private PatientSetClientService patientSetClientService;

    PatientSetProxyServer(PatientSetClientService patientSetClientService) {
        log.info("Patient set proxy server initialised.");
        this.patientSetClientService = patientSetClientService;
    }

    @Override
    public ResponseEntity<PatientSetList> listPatientSets() {
        log.info("List all patient sets for user {}", CurrentUser.getLogin());
        return ResponseEntity.ok(this.patientSetClientService.fetchPatientSets());
    }

    @Override
    public ResponseEntity<PatientSetResult> getPatientSet(Long id) {
        log.info("Get patient set with id {} for user {}", id, CurrentUser.getLogin());
        return ResponseEntity.ok(this.patientSetClientService.fetchPatientSet(id));
    }

    @Override
    public ResponseEntity<PatientSetResult> createPatientSet(
        @NotBlank String name, Boolean reuse, @Valid Constraint constraint) {
        log.info("Create patient set with name {} for user {}. Reuse: {}. Constraint: {}",
            name, CurrentUser.getLogin(), reuse, constraint);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(this.patientSetClientService.createPatientSet(name, reuse, constraint));
    }

}
