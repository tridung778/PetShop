package com.example.ASM.controllers;

import com.example.ASM.configs.SecurityConfig;
import com.example.ASM.dto.Github;
import com.example.ASM.dto.Google;
import com.example.ASM.enums.Role;
import com.example.ASM.models.User;
import com.example.ASM.services.CartService;
import com.example.ASM.services.UserService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;


@Controller
public class AuthController {

    public final static String path = "C:\\Java 5\\ASM\\src\\main\\resources\\static\\images\\";

    @Autowired
    private final UserService userService;

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    private CartService cartService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("userInfo", "");
        return "login";
    }

    @RequestMapping("/user-page")
    public String showUserPage(Model model, Authentication authentication) {
        if (authentication != null) {
            model.addAttribute("userInfo", userService.findByUsername(authentication.getName()));
        }
        assert authentication != null;
        model.addAttribute("animalIndex", cartService.findAllByUser(userService.findByUsername(authentication.getName())).size());
        model.addAttribute("router", "user-page.jsp");
        return "index";
    }

    @RequestMapping("/update-user")
    public String saveUser(Model model, @ModelAttribute("userInfo") User newUser, @RequestParam("photo-file") MultipartFile photoFile) throws IOException {
        User user = userService.findUserById(newUser.getId());
        String fileName = null;
        if (!photoFile.isEmpty()) {
            fileName = photoFile.getOriginalFilename();
            String filePath = path + File.separator + fileName;

            File parentDir = new File(path);
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }
            photoFile.transferTo(new File(filePath));
        }
        user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        user.setName(newUser.getName());
        user.setPhone(newUser.getPhone());
        user.setAddress(newUser.getAddress());
        user.setRole(newUser.getRole());
        user.setPassword(newUser.getPassword());
        if (newUser.getPhoto() != null) {
            user.setPhoto("/images/" + user.getPhoto());
        } else {
            user.setPhoto(fileName);
        }
        userService.register(user);
        model.addAttribute("router", "user-page.jsp");
        return "index";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("user") User newUser, HttpSession session, Model model) throws MessagingException {
        // Tạo người dùng mới nhưng chưa lưu vào cơ sở dữ liệu
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(newUser.getUsername());
        user.setPassword(SecurityConfig.encode(newUser.getPassword()));
        user.setEmail(newUser.getEmail());
        user.setName(newUser.getName());
        user.setPhone(null);
        user.setAddress(null);
        user.setRole(Role.USER);
        user.setPhoto(null);

        // Kiểm tra trùng lặp
        if (userService.isDuplicateUser(user)) {
            model.addAttribute("error", "Tên người dùng, email hoặc số điện thoại đã tồn tại.");
            return "error";
        }

        // Lưu người dùng vào session
        session.setAttribute("pendingUser", user);

        // Gửi email xác thực
        sendVerificationEmail(user);

        model.addAttribute("message", "Vui lòng kiểm tra email để xác thực tài khoản.");
        return "redirect:/register";
    }

    private void sendVerificationEmail(User user) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setTo(user.getEmail());
        helper.setSubject("Xác nhận email");
        String htmlContent = "<html><body>"
                + "<p>Mail của bạn đã được gửi đi. Vui lòng nhấp vào liên kết sau để xác thực tài khoản:</p>"
                + "<a href='http://localhost:8080/auth-mail?id=" + user.getId() + "'>Đây là liên kết</a>"
                + "</body></html>";
        helper.setText(htmlContent, true);
        mailSender.send(message);
    }

    @GetMapping("/auth-mail")
    public String authMail(@RequestParam("id") UUID id, HttpSession session, Model model) {
        // Lấy người dùng từ session
        User user = (User) session.getAttribute("pendingUser");
        if (user != null && user.getId().equals(id)) {
            userService.register(user); // Lưu người dùng vào cơ sở dữ liệu
            session.removeAttribute("pendingUser"); // Xóa người dùng khỏi session
            model.addAttribute("message", "Xác thực email thành công! Bạn có thể đăng nhập.");
            return "redirect:/login";
        }
        model.addAttribute("error", "Liên kết xác thực không hợp lệ hoặc đã hết hạn.");
        return "error"; // Hiển thị trang lỗi
    }

    @GetMapping("/secured")
    public String securedGithub(Authentication authentication) {
        Github github = new Github();
        User user = new User();
        Google google = new Google();
        if (authentication != null && authentication.getPrincipal() instanceof DefaultOAuth2User oauth2User) {
            Map<String, Object> attributes = oauth2User.getAttributes();
            if (attributes.toString().contains("github")) {
                github.setUsername(attributes.get("id").toString());
                github.setName((String) (attributes.get("name")));
                github.setAvatarUrl((String) attributes.get("avatar_url"));
                User userOptional = userService.findByUsername(github.getUsername());
                if (userOptional == null) {
                    user.setId(UUID.randomUUID());
                    user.setUsername(github.getUsername());
                    user.setPassword(SecurityConfig.encode(String.valueOf(UUID.randomUUID())));
                    user.setEmail(null);
                    user.setName(github.getName());
                    user.setPhone(null);
                    user.setAddress(null);
                    user.setRole(Role.USER);
                    user.setPhoto(github.getAvatarUrl());
                    userService.register(user);
                }
            } else if (attributes.toString().contains("google")) {
                google.setEmail((String) attributes.get("email"));
                google.setName((String) attributes.get("name"));
                google.setPicture((String) attributes.get("picture"));
                google.setUsername((String) attributes.get("sub"));
                User userOptional = userService.findByUsername(google.getUsername());
                if (userOptional == null) {
                    user.setId(UUID.randomUUID());
                    user.setUsername(google.getUsername());
                    user.setPassword(SecurityConfig.encode(String.valueOf(UUID.randomUUID())));
                    user.setEmail(google.getEmail());
                    user.setName(google.getName());
                    user.setPhone(null);
                    user.setAddress(null);
                    user.setRole(Role.USER);
                    user.setPhoto(google.getPicture());
                    userService.register(user);
                }
            }
        }
        return "redirect:/";
    }

}