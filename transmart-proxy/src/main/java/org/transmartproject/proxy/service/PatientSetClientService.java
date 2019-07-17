package org.transmartproject.proxy.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.transmartproject.common.client.PatientSetClient;
import org.transmartproject.common.dto.PatientSetList;
import org.transmartproject.common.dto.PatientSetResult;
import org.transmartproject.common.constraint.Constraint;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Service
public class PatientSetClientService {

    private PatientSetClient patientSetClient;

    public PatientSetClientService(PatientSetClient patientSetClient) {
        this.patientSetClient = patientSetClient;
    }

    public PatientSetList fetchPatientSets() {
        ResponseEntity<PatientSetList> response = patientSetClient.listPatientSets();
        return ResponseEntityHelper.unwrap(response);
    }

    public PatientSetResult fetchPatientSet(Long id) {
        ResponseEntity<PatientSetResult> response = patientSetClient.getPatientSet(id);
        return ResponseEntityHelper.unwrap(response);
    }

    public PatientSetResult createPatientSet(@NotBlank String name, @Valid @NotNull Constraint constraint, Boolean reuse) {
        ResponseEntity<PatientSetResult> response = patientSetClient.createPatientSet(name, reuse, constraint);
        return ResponseEntityHelper.unwrap(response);
    }

}
