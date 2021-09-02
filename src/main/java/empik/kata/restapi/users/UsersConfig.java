package empik.kata.restapi.users;

import empik.kata.restapi.common.EventsConfig;
import empik.kata.restapi.users.infrastructure.web.WebConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@ComponentScan
@Import({EventsConfig.class, WebConfiguration.class})
public class UsersConfig {

    @Bean
    public RestTemplate restTemplate(LoggingInterceptor interceptor) {
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        RestTemplate restTemplate = new RestTemplate(factory);
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(interceptor);
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

    @Slf4j
    @Service
    static class LoggingInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest req, byte[] reqBody, ClientHttpRequestExecution ex) throws IOException {
            if (log.isDebugEnabled()) {
                log.info("Request body: {}", new String(reqBody, StandardCharsets.UTF_8));
            }
            ClientHttpResponse response = ex.execute(req, reqBody);
            InputStreamReader isr = new InputStreamReader(response.getBody(), StandardCharsets.UTF_8);
            String body = new BufferedReader(isr).lines().collect(Collectors.joining("\n"));
            if (log.isDebugEnabled()) {
                log.info("Response body: {}", body);
            }
            return response;
        }
    }
}
