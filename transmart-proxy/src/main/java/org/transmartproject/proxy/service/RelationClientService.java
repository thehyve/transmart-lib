package org.transmartproject.proxy.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.transmartproject.common.client.RelationClient;
import org.transmartproject.common.dto.RelationList;

@Service
public class RelationClientService {

    private RelationClient relationClient;

    public RelationClientService(RelationClient relationClient) {
        this.relationClient = relationClient;
    }

    public RelationList fetchRelations() {
        ResponseEntity<RelationList> response = relationClient.listRelations();
        return ResponseEntityHelper.unwrap(response);
    }

}
