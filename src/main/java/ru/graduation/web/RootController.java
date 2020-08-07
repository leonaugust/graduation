package ru.graduation.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {
//    @Autowired
//    private UserController userController;

    @GetMapping("/")
    public String root() {
        return "index";
    }

//    @GetMapping("/users")
//    public String getUsers(Model model) {
//        model.addAttribute("users", userController.getAll());
//        return "users";
//    }

//    @PostMapping("/users")
//    public String setUser(HttpServletRequest request) {
//        int userId = Integer.parseInt(request.getParameter("userId"));
//        SecurityUtil.setAuthUserId(userId);
//        return "redirect:meals";
//    }
}
