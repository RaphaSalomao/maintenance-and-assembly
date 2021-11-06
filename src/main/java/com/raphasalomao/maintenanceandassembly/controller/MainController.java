package com.raphasalomao.maintenanceandassembly.controller;

import com.raphasalomao.maintenanceandassembly.model.*;
import com.raphasalomao.maintenanceandassembly.model.enumerated.Role;
import com.raphasalomao.maintenanceandassembly.model.enumerated.Status;
import com.raphasalomao.maintenanceandassembly.service.MaintenanceRequestService;
import com.raphasalomao.maintenanceandassembly.service.PartService;
import com.raphasalomao.maintenanceandassembly.service.UpgradeRequestService;
import com.raphasalomao.maintenanceandassembly.service.UserService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
@AllArgsConstructor
@Slf4j
public class MainController {

    private final UserService userService;
    private final MaintenanceRequestService maintenanceRequestService;
    private final UpgradeRequestService upgradeRequestService;
    private final PartService partService;


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("role", userService.retrievePrincipalRole());
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("role", userService.retrievePrincipalRole());
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            log.warn("Authentication: {}", authentication.getAuthorities());
            if (userService.retrievePrincipal().getRole().equals(Role.ADMIN)) {
                log.warn("User logged as admin");
                model.addAttribute("user", new CreateUserRequest());
                return "register";
            } else {
                log.warn("User logged as NOT admin");
                model.addAttribute("user", new CreateUserRequest());
                return "redirect:/";
            }
        } catch (Exception e) {
            log.warn("User NOT logged");
            model.addAttribute("user", new CreateUserRequest());
            return "register";
        }
    }

    @PostMapping("/register")
    public String register(CreateUserRequest request) {
        userService.createUser(request);
        return "redirect:/logout";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("role", userService.retrievePrincipalRole());
        return "contact";
    }

    @GetMapping("/maintenance")
    public String maintenance(Model model) {
        model.addAttribute("role", userService.retrievePrincipalRole());
        model.addAttribute("maintenanceInfo", new MaintenanceInfo());
        return "maintenance-quiz";
    }

    @PostMapping("/maintenance")
    public String maintenance(MaintenanceInfo request) {
        try {
            maintenanceRequestService.create(request);
        } catch (Exception e) {
            return "error";
        }
        return "contact";
    }

    @GetMapping("/upgrade")
    public String upgrade(Model model) {
        model.addAttribute("role", userService.retrievePrincipalRole());
        model.addAttribute("upgradeInfo", new UpgradeInfo());
        return "upgrade-quiz";
    }

    @PostMapping("/upgrade")
    public String upgrade(UpgradeInfo request) {
        try {
            upgradeRequestService.create(request);
        } catch (Exception e) {
            return "error";
        }
        return "contact";
    }

    @GetMapping("/maintenance-requests")
    public String listMaintenanceRequests(Model model) {
        model.addAttribute("role", userService.retrievePrincipalRole());
        model.addAttribute("requests", maintenanceRequestService.findPenndingRequests());
        return "maintenance-requests";
    }

    @GetMapping("/upgrade-requests")
    public String listUpgradeRequests(Model model) {
        model.addAttribute("role", userService.retrievePrincipalRole());
        model.addAttribute("requests", upgradeRequestService.findPenndingRequests());
        return "upgrade-requests";
    }

    @GetMapping("/parts/{id}")
    public String maintenanceParts(Model model, @PathVariable UUID id) {
        model.addAttribute("role", userService.retrievePrincipalRole());
        model.addAttribute("part",
                PartRequest.builder()
                        .id(id)
                        .build());
        return "parts";
    }

    @PostMapping("/parts")
    public String createParts(PartRequest part) {
        log.warn("Part: {}", part);
        partService.createPart(part);
        return part.getType().equals("UPGRADE") ? "redirect:/upgrade-requests" : "redirect:/maintenance-requests";
    }

    @GetMapping("/my-maintenances")
    public String myMaintenances(Model model) {
        model.addAttribute("role", userService.retrievePrincipalRole());
        try {
            model.addAttribute("status", Status.WAITING_FOR_APPROVAL);
            model.addAttribute("maintenances", maintenanceRequestService.findPrincipalRequests());
        } catch (Exception e) {
        }
        return "my-maintenances";
    }

    @GetMapping("/my-upgrades")
    public String myRequests(Model model) {
        model.addAttribute("role", userService.retrievePrincipalRole());
        try {
            model.addAttribute("upgrades", upgradeRequestService.findPrincipalRequests());
        } catch (Exception e) {
        }
        return "my-upgrades";
    }

    @GetMapping("/approve/{type}/{id}")
    public String approve(@PathVariable String type, @PathVariable UUID id) {
        try {
            if (type.equals("maintenance")) {
                maintenanceRequestService.approve(id);
            } else {
                upgradeRequestService.approve(id);
            }
        } catch (Exception e) {
        }
        return type.equals("maintenance") ? "redirect:/my-maintenances" : "redirect:/my-upgrades";
    }

    @GetMapping("/cancel/{type}/{id}")
    public String cancel(@PathVariable String type, @PathVariable UUID id) {
        try {
            if (type.equals("maintenance")) {
                maintenanceRequestService.cancel(id);
            } else {
                upgradeRequestService.cancel(id);
            }
        } catch (Exception e) {
        }
        return type.equals("maintenance") ? "redirect:/my-maintenances" : "redirect:/my-upgrades";
    }
}
