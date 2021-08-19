package learn.springboot.oauth2webclient;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PublicPagesController {

    @GetMapping("/index")
    public String index(){
        return "index";
    }

}
