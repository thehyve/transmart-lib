package org.transmartproject.proxy.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.transmartproject.common.dto.Forest;
import org.transmartproject.common.dto.Study;
import org.transmartproject.common.dto.StudyList;
import org.transmartproject.common.resource.StudyResource;
import org.transmartproject.common.resource.TreeResource;
import org.transmartproject.proxy.security.CurrentUser;
import org.transmartproject.proxy.service.StudyClientService;
import org.transmartproject.proxy.service.TreeClientService;

/**
 * Proxy server for the ontology tree.
 */
@RestController
@Validated
@CrossOrigin
@Api(value="tree", description="Ontology tree nodes")
public class TreeProxyServer implements TreeResource {

    private Logger log = LoggerFactory.getLogger(TreeProxyServer.class);

    private TreeClientService treeClientService;

    TreeProxyServer(TreeClientService treeClientService) {
        log.info("Tree proxy server initialised.");
        this.treeClientService = treeClientService;
    }


    @Override
    @ApiOperation(value = "Fetch ontology", produces = "application/json")
    public ResponseEntity<Forest> getForest(
        String root, Integer depth, Boolean constraints, Boolean counts, Boolean tags) {
        log.info("Fetch ontology for user {}", CurrentUser.getLogin());
        return ResponseEntity.ok(treeClientService.fetchForest(root, depth, constraints, counts, tags));
    }

}
