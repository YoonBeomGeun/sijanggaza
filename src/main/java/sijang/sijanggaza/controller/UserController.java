package sijang.sijanggaza.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sijang.sijanggaza.domain.Order;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.service.OrderService;
import sijang.sijanggaza.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult result) {
        if(result.hasErrors()) {
            return "signup_form";
        }

        if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            result.rejectValue("password2", "passwordInCorrect",
                    "패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        try {
            userService.create(userCreateForm.getUsername(), userCreateForm.getPassword1(),
                    userCreateForm.getEmail(), userCreateForm.getStatus());
            System.out.println();
        } catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            result.reject("signupFailed", "이미 등록된 사용자입니다. 아이디나 이메일을 확인해주세요.");
            return "signup_form";
        } catch(Exception e) {
            e.printStackTrace();
            result.reject("signupFailed", e.getMessage());
            return "signup_form";
        }

        //userService.create(userCreateForm.getUsername(), userCreateForm.getPassword1(), userCreateForm.getEmail(), userCreateForm.getStatus());
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

    @GetMapping("/mypage")
    public String mypage(@RequestParam(value ="page", defaultValue = "0") int page,
                         @RequestParam(value = "kw", defaultValue = "") String kw,
                         Principal principal, Model model) {
        SiteUser user = this.userService.getUser(principal.getName());
        List<Order> orderList = user.getOrderList();
        model.addAttribute("user", user);
        model.addAttribute("orderList", orderList);
        return "user_mypage";
    }

    /**
     * 마이페이지
     * 주문 내역
     */
    /*@GetMapping("/list")
    public String list(@RequestParam(value ="page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw, Model model) {
        Page<Order> paging = this.orderService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "user_mypage";
    }*/
}
