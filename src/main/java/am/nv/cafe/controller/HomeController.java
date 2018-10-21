package am.nv.cafe.controller;

import am.nv.cafe.dataaccess.model.User;
import am.nv.cafe.dataaccess.model.lcp.UserProfile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/")
    String home(@AuthenticationPrincipal User user) {
        if (user == null) {
            return "index";
        }
        if (user.getProfile() == UserProfile.MANAGER) {
            return "manager";
        }
        return "waiter";
    }
}
