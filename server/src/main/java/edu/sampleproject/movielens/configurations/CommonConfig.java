package edu.sampleproject.movielens.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommonConfig {
    @Value("${image.url}")
    private String imageURL;

    public String getImageURI() {
        return imageURL;
    }
}
