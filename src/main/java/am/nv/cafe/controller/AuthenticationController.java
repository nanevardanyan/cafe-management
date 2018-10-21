package am.nv.cafe.controller;

import am.nv.cafe.dataaccess.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthenticationController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(Model model,
                               @AuthenticationPrincipal User user,
                               @RequestParam(required = false) String error,
                               @RequestHeader(value = "referer", required = false, defaultValue = "/home") final String referer) {

        // goes to previous or home page in case of authenticated user
        if (user != null) {
            return "redirect:" + referer;
        }

        model.addAttribute("error", error);
        return "login";
    }
}
