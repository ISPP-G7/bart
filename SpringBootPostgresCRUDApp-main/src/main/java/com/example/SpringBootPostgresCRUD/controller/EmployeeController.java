package com.example.SpringBootPostgresCRUD.controller;

import com.example.SpringBootPostgresCRUD.entity.Employee;
import com.example.SpringBootPostgresCRUD.repo.EmployeeRepository;
import com.example.SpringBootPostgresCRUD.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {

    @Autowired
    EmployeeService empService;

    @GetMapping({"/", "/viewEmployees"})
    public String viewEmployees(@ModelAttribute("message") String message, Model model) {
        List<Employee> empList = empService.getAllEmployees();

        model.addAttribute("empList", empList);
        model.addAttribute("message", message);

        return "ViewEmployee";
    }

    @GetMapping("/addEmployee")
    public String newEmployee(@ModelAttribute("message") String message, Model model) {
        Employee emp = new Employee();
        model.addAttribute("emp", emp);
        model.addAttribute("message", message);

        return "AddEmployee";
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(Employee emp, RedirectAttributes redirectAttributes) {
        if (empService.saveOrUpdateEmployee(emp)) {
            redirectAttributes.addFlashAttribute("message", "Save Success");
            return "redirect:/viewEmployees";
        }

        redirectAttributes.addFlashAttribute("message", "Save Failure");
        return "redirect:/addEmployee";
    }

    @GetMapping("/editEmployee/{id}")
    public String editEmployee(@PathVariable Long id, @ModelAttribute("message") String message, Model model) {
        Employee emp = empService.getEmployeeById(id);
        model.addAttribute("emp", emp);
        model.addAttribute("message", message);

        return "EditEmployee";
    }

    @PostMapping("/editSaveEmployee")
    public String editSaveEmployee(@ModelAttribute("emp") Employee emp, RedirectAttributes redirectAttributes) {
        if (empService.saveOrUpdateEmployee(emp)) {
            redirectAttributes.addFlashAttribute("message", "Edit Success");
            return "redirect:/viewEmployees";
        }

        redirectAttributes.addFlashAttribute("message", "Edit Failure");
        return "redirect:/editEmployee/" + emp.getId();
    }

    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (empService.deleteEmployee(id)) {
            redirectAttributes.addFlashAttribute("message", "Delete Success");
            return "redirect:/viewEmployees";
        }

        redirectAttributes.addFlashAttribute("message", "Delete Failure");
        return "redirect:/viewEmployees";
    }

}
