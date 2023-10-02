package com.castlabs.fileanalyser.provider;


import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class MediaFileProviderImpl implements MediaFileProvider {
    private final WebClient webClient;

    public MediaFileProviderImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public byte[] fetchFileContent(String url) {
       if (StringUtils.isEmpty(url)){
           throw new IllegalArgumentException("url could not be null or empty");
       }

           return webClient.get()
                   .uri(url)
                   .retrieve()
                   .bodyToMono(byte[].class).block();

    }
}
