package learn.springboot.oauth2webclient;

import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users/")
public class UserPagesController {

    @Autowired
    private  CouponResource couponResource;

    @GetMapping("/index")
    public String index(Model model,@AuthenticationPrincipal OidcUser principal){
        OidcIdToken oidcIdToken = principal.getIdToken();
        String fullName = oidcIdToken.getFullName();
        String email = oidcIdToken.getEmail();
        Instant issuedAt = oidcIdToken.getIssuedAt();
        Instant expiresAt = oidcIdToken.getExpiresAt();
        model.addAttribute("fullName",fullName);
        model.addAttribute("email",email);
        model.addAttribute("issuedAt",issuedAt);
        model.addAttribute("expiresAt",expiresAt);
        if(couponResource.getConfig().isConnectToCouponResource()){
            model.addAttribute("couponStatus",couponResource.getCouponResourceStatus());
        }
        return "users/index";
    }







}
