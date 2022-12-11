package com.example.roger_300341127.controller;

import com.example.roger_300341127.entities.Salesman;
import com.example.roger_300341127.repositories.SalesmanRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
public class SalesmanController {

    @Autowired
    private SalesmanRepository sRepo;

    //Method to list salesman data
    @GetMapping({"/showSalesman", "/", "/list"})
    public ModelAndView showSalesman() {
        ModelAndView mav = new ModelAndView("list_data");
        List<Salesman> salesmanList = sRepo.findAll();
        mav.addObject("salesmanList", salesmanList);
        return mav;
    }

    @GetMapping("/addSalesmanForm")
    public ModelAndView addSalesmanForm() {
        ModelAndView mav = new ModelAndView("add_salesman_data");
        Salesman salesman = new Salesman();
        mav.addObject("salesman", salesman);
        return mav;
    }


    //Method that handles the data inserted in the form
    @PostMapping("/saveSalesman")
    public String saveSalesman(@ModelAttribute Salesman salesman) {
        sRepo.save(salesman);
        return "redirect:/list";
    }

    //Method that handles delete from the list.
    @GetMapping("/showDeleteForm")
    public String showDeleteForm(@RequestParam Long id) {
        sRepo.deleteById(id);
        return "redirect:/list";
    }

    //Method that handle update from the list
    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long id) {
        ModelAndView mav = new ModelAndView("add_salesman_data");
        Salesman salesman = sRepo.findById(id).get();
        mav.addObject("salesman", salesman);
        return mav;
    }


}
