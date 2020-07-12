package com.github.peaceture.microservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;

import static org.springframework.http.HttpStatus.OK;

@Controller
public class OpenIdConnectEndpoints {

    private final String issuer;

    public OpenIdConnectEndpoints(
            final @Value("${issuer.uri:}") String issuer
            ) {
        this.issuer = issuer;
    }

    @RequestMapping(value = {
            "/.well-known/openid-configuration",
            "/oauth/token/.well-known/openid-configuration"
    })
    public ResponseEntity<OpenIdConfiguration> getOpenIdConfiguration(HttpServletRequest request) throws URISyntaxException {
        OpenIdConfiguration conf = new OpenIdConfiguration(getServerContextPath(request), request.getRequestURL().toString().replace(request.getRequestURI(), ""));
        return new ResponseEntity<>(conf, OK);
    }

    private String getServerContextPath(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        return requestURL.substring(0, requestURL.length() - request.getServletPath().length());
    }


}
