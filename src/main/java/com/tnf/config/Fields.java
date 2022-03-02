package com.tnf.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "db")
public class Fields {
    private String dialect;
    private String dbop;
    private String name;
    private String url;
    private String username;
    private String password;
}
