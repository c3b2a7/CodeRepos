package me.lolico.samples.security.gateway;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Predicate;

/**
 * @author lolico
 */
@EnableZuulProxy
@SpringBootApplication
public class ZuulGateway {
    public static void main(String[] args) {
        SpringApplication.run(ZuulGateway.class, args);
    }

    @Component
    static class OAuth2AccessTokenRelayFilter extends ZuulFilter {
        private static final String ACCESS_TOKEN = OAuth2ParameterNames.ACCESS_TOKEN;
        private static final String TOKEN_TYPE = OAuth2ParameterNames.TOKEN_TYPE;
        private final OAuth2AuthorizedClientService authorizedClientService;
        private Predicate<HttpServletRequest> oauth2RouteTester = request -> true;

        public OAuth2AccessTokenRelayFilter(OAuth2AuthorizedClientService authorizedClientService) {
            this.authorizedClientService = authorizedClientService;
        }

        public void setOauth2RouteTester(Predicate<HttpServletRequest> oauth2RouteTester) {
            this.oauth2RouteTester = oauth2RouteTester;
        }

        @Override
        public String filterType() {
            return "pre";
        }

        @Override
        public int filterOrder() {
            return 10;
        }

        @Override
        public boolean shouldFilter() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequestContext ctx = RequestContext.getCurrentContext();
            if (authentication instanceof OAuth2AuthenticationToken && isOauth2Route(ctx)) {
                String registrationId = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
                OAuth2AuthorizedClient authorizedClient = authorizedClientService
                        .loadAuthorizedClient(registrationId, authentication.getName());
                ctx.set(ACCESS_TOKEN, authorizedClient.getAccessToken().getTokenValue());
                ctx.set(TOKEN_TYPE, authorizedClient.getAccessToken().getTokenType() == null ?
                        OAuth2AccessToken.TokenType.BEARER.getValue() :
                        authorizedClient.getAccessToken().getTokenType().getValue());
                return true;
            }
            return false;
        }

        private boolean isOauth2Route(RequestContext ctx) {
            return oauth2RouteTester.test(ctx.getRequest());
        }

        @Override
        public Object run() {
            RequestContext ctx = RequestContext.getCurrentContext();
            ctx.addZuulRequestHeader(HttpHeaders.AUTHORIZATION, ctx.get(TOKEN_TYPE) + " " + ctx.get(ACCESS_TOKEN));
            return null;
        }
    }
}
