package learn.springboot.oauth2webclient;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class CouponResource {

    @Getter
    @Autowired
    private  WebPropertiesConfig config;

    @Autowired
    private OAuth2AuthorizedClientService oauth2ClientService;

    private RestTemplate restTemplate = new RestTemplate();


    public String getCouponResourceStatus(){
        String url = config.getUrl();
        String couponUrl = config.getCouponUrl();
        String fullUrl = url+couponUrl;
        try{
            HttpEntity<String> entity = new HttpEntity<>(addJwtToken());
            ResponseEntity<String> responseEntity = getRestTemplate()
                .exchange(fullUrl, HttpMethod.GET, entity, String.class);
            if(responseEntity.getStatusCode().is2xxSuccessful()){
                return responseEntity.getBody();
            }
        }catch (Exception e){
            log.error("Error in getting response from coupon resource",e);
        }
        return "Coupon Resource not working !";
    }



    public RestTemplate getRestTemplate(){
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        restTemplate = restTemplateBuilder.build();
        return this.restTemplate;
    }


    public HttpHeaders addJwtToken(){
        String jwtAccessToken = getJwtAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+jwtAccessToken);
        return headers;
    }


    public String getJwtAccessToken(){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        OAuth2AuthorizedClient oauth2Client = oauth2ClientService.loadAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId(),
            oauthToken.getName());

        String jwtAccessToken = oauth2Client.getAccessToken().getTokenValue();
        return jwtAccessToken;
    }

    public String getIdToken(OidcUser principal){
        OidcIdToken idToken = principal.getIdToken();
        String idTokenValue = idToken.getTokenValue();
        return idTokenValue;
    }


}
