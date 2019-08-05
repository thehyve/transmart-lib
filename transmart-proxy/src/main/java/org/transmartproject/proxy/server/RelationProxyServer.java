package org.transmartproject.proxy.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.transmartproject.common.dto.Dimension;
import org.transmartproject.common.dto.DimensionList;
import org.transmartproject.common.dto.RelationList;
import org.transmartproject.common.resource.DimensionResource;
import org.transmartproject.common.resource.RelationResource;
import org.transmartproject.proxy.security.CurrentUser;
import org.transmartproject.proxy.service.DimensionClientService;
import org.transmartproject.proxy.service.RelationClientService;

/**
 * Proxy server for relations.
 */
@RestController
@Validated
@CrossOrigin
@Api(value="relations", description="Relations")
public class RelationProxyServer implements RelationResource {

    private Logger log = LoggerFactory.getLogger(RelationProxyServer.class);

    private RelationClientService relationClientService;

    RelationProxyServer(RelationClientService relationClientService) {
        log.info("Relation proxy server initialised.");
        this.relationClientService = relationClientService;
    }

    @Override
    @ApiOperation(value = "List all relations", produces = "application/json")
    public ResponseEntity<RelationList> listRelations() {
        log.info("List all relations for user {}", CurrentUser.getLogin());
        return ResponseEntity.ok(this.relationClientService.fetchRelations());
    }

}
