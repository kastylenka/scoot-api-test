package com.kastylenka.scootapi.scootapitest.config.security;

import static java.util.Collections.emptyList;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class ApiKeyAuthentication extends AbstractAuthenticationToken {

    private final String apiKey;

    public ApiKeyAuthentication(final String apiKey) {
        super(emptyList());
        this.apiKey = apiKey;
    }

    @Override
    public Object getCredentials() {
        return apiKey;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
