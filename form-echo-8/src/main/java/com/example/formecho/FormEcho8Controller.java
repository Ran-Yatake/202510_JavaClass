package com.example.formecho;
import org.springframework.boot.SpringApplication;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;
@Controller
public class FormEcho8Controller {
    @GetMapping("/")
    public String showForm() {
        // templates/form.html を表示
        return "form";
    }

@PostMapping(value = "/echo")
public String echo(@RequestParam(name = "text", defaultValue = "") String text,
		 Model model) {
    String upper = text.toUpperCase();      
    String reversed = new StringBuilder(text).reverse().toString(); 
    model.addAttribute("text",text );
    model.addAttribute("upper",upper);
    model.addAttribute("reversed",reversed);
    return"result";
}
}