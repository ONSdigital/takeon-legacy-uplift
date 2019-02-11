package uk.gov.ons.collection.controller;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;

import uk.gov.ons.collection.entity.Employee;
import uk.gov.ons.collection.repository.EmployeeRepository;

@Controller
public class MainController {

    @Autowired
    private EmployeeRepository employeeRepository;

    private static final String[] NAMES = new String[] { "Tom", "Jerry", "Donald" };

    @ResponseBody
    @RequestMapping("/")
    public String home() {
        String html = "";
        html += "<ul>";
        html += " <li><a href='/testInsert'>Test Insert</a></li>";
        html += " <li><a href='/showAllEmployee'>Show All Employee</a></li>";
        html += " <li><a href='/showFullNameLikeTom'>Show All 'Tom'</a></li>";
        html += " <li><a href='/deleteAllEmployee'>Delete All Employee</a></li>";
        html += "</ul>";
        return html;
    }

    @GetMapping(value = "findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Employee>> findAll() {
        return ResponseEntity.ok(this.employeeRepository.findAll());
    }

    @ResponseBody
    @RequestMapping("/testInsert")
    public String testInsert() {

        Long empIdMax = this.employeeRepository.getMaxId();

        Employee employee = new Employee();

        int random = new Random().nextInt(3);

        long id = empIdMax + 1;
        String fullName = NAMES[random] + " " + id;

        employee.setId(id);
        employee.setEmpNo("E" + id);
        employee.setFullName(fullName);
        employee.setHireDate(new Date());
        this.employeeRepository.save(employee);

        return "Inserted: " + employee;
    }

    @ResponseBody
    @RequestMapping("/showAllEmployee")
    public String showAllEmployee() {

        Iterable<Employee> employees = this.employeeRepository.findAll();

        String html = "";
        for (Employee emp : employees) {
            html += emp + "<br>";
        }

        return html;
    }

    @ResponseBody
    @RequestMapping("/showFullNameLikeTom")
    public String showFullNameLikeTom() {

        List<Employee> employees = this.employeeRepository.findByFullNameLike("Tom");

        String html = "";
        for (Employee emp : employees) {
            html += emp + "<br>";
        }
        if (html=="")
                throw new IllegalArgumentException();
        return html;
    }

    @ResponseBody
    @RequestMapping("/deleteAllEmployee")
    public String deleteAllEmployee() {

        this.employeeRepository.deleteAll();
        return "Deleted!";
    }

}

