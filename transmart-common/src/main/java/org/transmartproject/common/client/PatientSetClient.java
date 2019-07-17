package org.transmartproject.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.transmartproject.common.resource.PatientSetResource;

@FeignClient(value="patientset", url="${transmart-client.transmart-server-url}")
public interface PatientSetClient extends PatientSetResource {}
