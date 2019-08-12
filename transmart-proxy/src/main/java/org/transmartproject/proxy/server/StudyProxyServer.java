package org.transmartproject.proxy.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.transmartproject.common.dto.Study;
import org.transmartproject.common.dto.StudyList;
import org.transmartproject.common.resource.StudyResource;
import org.transmartproject.proxy.security.CurrentUser;
import org.transmartproject.proxy.service.StudyClientService;

/**
 * Proxy server for studies.
 */
@RestController
@Validated
@CrossOrigin
@Api(value="studies", description="Studies")
public class StudyProxyServer implements StudyResource {

    private Logger log = LoggerFactory.getLogger(StudyProxyServer.class);

    private StudyClientService studyClientService;

    StudyProxyServer(StudyClientService studyProxyService) {
        log.info("Study proxy server initialised.");
        this.studyClientService = studyProxyService;
    }

    @Override
    @ApiOperation(value = "List all studies", produces = "application/json")
    public ResponseEntity<StudyList> listStudies() {
        log.info("List all studies for user {}", CurrentUser.getLogin());
        return ResponseEntity.ok(this.studyClientService.fetchStudies());
    }

    @Override
    @ApiOperation(value = "Get study by id", produces = "application/json")
    public ResponseEntity<Study> getStudy(Long id) {
        log.info("Get study with id {} for user {}", id, CurrentUser.getLogin());
        return ResponseEntity.ok(this.studyClientService.fetchStudy(id));
    }

}
