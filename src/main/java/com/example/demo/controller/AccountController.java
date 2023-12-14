package com.example.demo.controller;

import com.example.demo.model.Salesperson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private SalespersonService service;

    public AccountController(SalespersonService service) {
        this.service = service;
    }

    @GetMapping("")
    public String accountPage(Model model){
        List<Salesperson> saleList = service.getAllSalesPerson();
        model.addAttribute("salespersons",saleList);

        return "Account/account_manager";
    }


    @GetMapping("/add")
    public String showAddSalespersonForm(Model model) {
        model.addAttribute("newSalesperson", new Salesperson());
        return "Account/add_salesperson";
    }

    @PostMapping("/add")
    public String addSalesperson(@ModelAttribute("newSalesperson") Salesperson salesperson) {
        service.save(salesperson);
        return "redirect:/account";
    }

    @GetMapping("/detail/{id}")
    public String salespersonDetail(@PathVariable int id, Model model) {
        Optional<Salesperson> salesperson = service.getSalespersonById(id);
        model.addAttribute("salesperson", salesperson.orElse(null));
        return "Account/salesperson_detail";
    }

    @GetMapping("/changeStatus/{id}")
    public String showChangeStatusForm(@PathVariable int id, Model model) {
        Optional<Salesperson> salesperson = service.getSalespersonById(id);
        model.addAttribute("salesperson", salesperson.orElse(null));
        return "Account/change_status_form";
    }

    @PostMapping("/changeStatus/{id}")
    public String changeStatus(@PathVariable int id, @RequestParam("confirm") boolean confirm, Model model) {
        Optional<Salesperson> optionalSalesperson = service.getSalespersonById(id);

        if (optionalSalesperson.isPresent()) {
            Salesperson salesperson = optionalSalesperson.get();

            if (confirm) {
                salesperson.setStatus(!salesperson.isStatus());
                service.updateSalesperson(salesperson);
                model.addAttribute("confirmationMessage", "Status changed successfully.");
            } else {
                model.addAttribute("confirmationMessage", "Status change canceled.");
            }
        }

        return "redirect:/account";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteConfirmationForm(@PathVariable int id, Model model) {
        Optional<Salesperson> salesperson = service.getSalespersonById(id);
        model.addAttribute("salesperson", salesperson.orElse(null));
        return "Account/confirm_delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteSalesperson(@PathVariable int id) {
        service.deleteSalespersonById(id);
        return "redirect:/account";
    }
}
