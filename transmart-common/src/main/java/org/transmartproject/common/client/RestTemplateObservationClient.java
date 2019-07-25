package org.transmartproject.common.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.transmartproject.common.config.TransmartClientProperties;
import org.transmartproject.common.dto.Query;

import javax.validation.Valid;
import java.io.InputStream;
import java.util.function.Consumer;

@Component
public class RestTemplateObservationClient implements ObservationClient {

    private Logger log = LoggerFactory.getLogger(RestTemplateObservationClient.class);

    private RestTemplateBuilder restTemplateBuilder;
    private TransmartClientProperties transmartClientProperties;
    private ObjectMapper objectMapper;

    RestTemplateObservationClient(RestTemplateBuilder restTemplateBuilder,
                                  TransmartClientProperties transmartClientProperties,
                                  ObjectMapper objectMapper) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.transmartClientProperties = transmartClientProperties;
        this.objectMapper = objectMapper;
    }

    @Override
    public void query(@Valid Query query, Consumer<InputStream> reader) {
        restTemplateBuilder.build().execute(
            String.format("%s/v2/observations", transmartClientProperties.getTransmartServerUrl()),
            HttpMethod.POST,
            request -> objectMapper.writeValue(request.getBody(), query),
            response -> {
                try {
                    reader.accept(response.getBody());
                } catch(Exception e) {
                    log.error("Error reading observations", e);
                }
                return null;
            }
        );
    }

}
