package org.transmartproject.proxy.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.transmartproject.common.client.StudyClient;
import org.transmartproject.common.dto.Study;
import org.transmartproject.common.dto.StudyList;
import org.transmartproject.common.exception.AccessDenied;
import org.transmartproject.common.exception.ResourceNotFound;
import org.transmartproject.common.exception.ServiceNotAvailable;
import org.transmartproject.common.exception.Unauthorised;

@Service
public class StudyClientService {

    private StudyClient studyClient;

    public StudyClientService(StudyClient studyClient) {
        this.studyClient = studyClient;
    }

    public StudyList fetchStudies() {
        ResponseEntity<StudyList> response = studyClient.listStudies();
        return ResponseEntityHelper.unwrap(response);
    }

    public Study fetchStudy(Long id) {
        ResponseEntity<Study> response = studyClient.getStudy(id);
        return ResponseEntityHelper.unwrap(response);
    }

}
