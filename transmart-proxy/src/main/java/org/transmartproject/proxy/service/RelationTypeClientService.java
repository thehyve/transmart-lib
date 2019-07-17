package org.transmartproject.proxy.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.transmartproject.common.client.RelationTypeClient;
import org.transmartproject.common.dto.RelationTypeList;

@Service
public class RelationTypeClientService {

    private RelationTypeClient relationTypeClient;

    public RelationTypeClientService(RelationTypeClient relationTypeClient) {
        this.relationTypeClient = relationTypeClient;
    }

    public RelationTypeList fetchRelationTypes() {
        ResponseEntity<RelationTypeList> response = relationTypeClient.listRelationTypes();
        return ResponseEntityHelper.unwrap(response);
    }

}
