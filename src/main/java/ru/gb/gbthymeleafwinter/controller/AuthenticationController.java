package ru.gb.gbthymeleafwinter.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.gbthymeleafwinter.entity.security.AccountUser;
import ru.gb.gbthymeleafwinter.service.AccountUserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@RequestMapping("/authentication")
@Slf4j
public class AuthenticationController {

    private final AccountUserService accountUserService;

    @GetMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest) {
        try {
            httpServletRequest.logout();
        } catch (ServletException e) {
            log.error(e.getMessage());
        }
        return "redirect:/product/all";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("accountUser", new AccountUser());
        return "authentication/login";
    }

    @PostMapping("/login")
    public String login(Model model, HttpServletRequest httpServletRequest, AccountUser user) {
        try {
            httpServletRequest.login(user.getUsername(), user.getPassword());
            return "redirect:/product/all";
        } catch (ServletException e) {
            log.error(e.getMessage());
            model.addAttribute("accountUser", user);
            model.addAttribute("error", e.getMessage());
            return "authentication/login";
        }
    }

    @GetMapping("/registration")
    public String registrationForm(Model model, @RequestParam(name = "id", required = false) Long id) {
        AccountUser user;
        if (id != null) {
            user = accountUserService.findById(id);
        } else {
            user = new AccountUser();
        }
        model.addAttribute("accountUser", user);
        return "authentication/registration";
    }

    @PostMapping("/registration")
    public String registration(Model model, AccountUser user) {
        try {
            accountUserService.save(user);
            return "redirect:/product/all";
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            model.addAttribute("accountUser", user);
            model.addAttribute("error", e.getMessage());
            return "authentication/registration";
        }
    }
}
