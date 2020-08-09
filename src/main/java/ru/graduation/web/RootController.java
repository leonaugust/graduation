package ru.graduation.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {
    @Autowired
    private UserController userController;

    @Autowired
    private RestaurantController restaurantController;

    @Autowired
    private MealController mealController;

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userController.getAll());
        return "users";
    }

    @GetMapping("/restaurants")
    public String getRestaurants(Model model) {
        model.addAttribute("restaurants", restaurantController.getAll());
        return "restaurants";
    }

    @GetMapping("/meals")
    public String getMeals(Model model) {
        model.addAttribute("meals", mealController.getAll());
        return "meals";
    }

//    @PostMapping("/users")
//    public String setUser(HttpServletRequest request) {
//        int userId = Integer.parseInt(request.getParameter("userId"));
//        SecurityUtil.setAuthUserId(userId);
//        return "redirect:meals";
//    }
}
