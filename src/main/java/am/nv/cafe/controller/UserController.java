package am.nv.cafe.controller;

import am.nv.cafe.controller.data.Response;
import am.nv.cafe.dataaccess.model.User;
import am.nv.cafe.dataaccess.model.lcp.UserProfile;
import am.nv.cafe.exception.DuplicateEntryException;
import am.nv.cafe.exception.InternalErrorException;
import am.nv.cafe.service.impl.UserServiceImpl;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping("/home")
    public String home(@AuthenticationPrincipal User user, HttpSession session) {
        if (user == null) {
            return "redirect:login";
        }
        if (user.getProfile() == UserProfile.MANAGER) {
            return "manager";
        }
        return "waiter";
    }

    @PostMapping(path = "/user/add")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseBody
    public Response<User> createUser(User waiter) {
        Response<User> response = new Response<>();

        Map<String, String> fieldError = validate(waiter);
        if (!fieldError.isEmpty()) {
            response.setStatus(HttpStatus.BAD_REQUEST)
                    .setFieldErrors(fieldError);
            return response;
        }

        waiter.setProfile(UserProfile.WAITER);
        //Setting temp password, that user should update on login
        waiter.setPassword("user");

        try {
            User userAdded = userService.add(waiter);
            response.setData(userAdded)
                    .setStatus(HttpStatus.OK);
        } catch (DuplicateEntryException e) {
            response.setMessage(getMessage("msg.email.used"));
        } catch (InternalErrorException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            LOGGER.error(String.format(getMessage("msg.user.failed.to.add"), waiter));
        }
        return response;
    }
}
