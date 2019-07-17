package org.transmartproject.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.transmartproject.common.resource.AggregateResource;

@FeignClient(value="aggregate", url="${transmart-client.transmart-server-url}")
public interface AggregateClient extends AggregateResource {}
