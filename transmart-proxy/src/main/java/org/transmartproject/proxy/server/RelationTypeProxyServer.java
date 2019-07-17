package org.transmartproject.proxy.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.transmartproject.common.dto.RelationTypeList;
import org.transmartproject.common.resource.RelationTypeResource;
import org.transmartproject.proxy.security.CurrentUser;
import org.transmartproject.proxy.service.RelationTypeClientService;

/**
 * Proxy server for relation types.
 */
@RestController
@Validated
@CrossOrigin
public class RelationTypeProxyServer implements RelationTypeResource {

    private Logger log = LoggerFactory.getLogger(RelationTypeProxyServer.class);

    private RelationTypeClientService relationTypeClientService;

    RelationTypeProxyServer(RelationTypeClientService relationTypeClientService) {
        log.info("Relation type proxy server initialised.");
        this.relationTypeClientService = relationTypeClientService;
    }

    @Override
    public ResponseEntity<RelationTypeList> listRelationTypes() {
        log.info("List all relation types for user {}", CurrentUser.getLogin());
        return ResponseEntity.ok(this.relationTypeClientService.fetchRelationTypes());
    }

}
