package org.transmartproject.proxy.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.transmartproject.common.client.TreeClient;
import org.transmartproject.common.dto.Forest;

@Service
public class TreeClientService {

    private TreeClient treeClient;

    public TreeClientService(TreeClient treeClient) {
        this.treeClient = treeClient;
    }

    public Forest fetchForest(String root, Integer depth, Boolean constraints,
                              Boolean counts, Boolean tags) {
        ResponseEntity<Forest> response = treeClient.getForest(
            root, depth, constraints, counts, tags);
        return ResponseEntityHelper.unwrap(response);
    }

}
