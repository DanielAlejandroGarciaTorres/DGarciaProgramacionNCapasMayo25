package com.digis01.DGarciaProgramacionNCapasMayo25Maven.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/demo")
public class DemoController {
    
    
    @GetMapping("saludo/{persona}")
    public String Hello(Model model, @PathVariable String persona){
        model.addAttribute("persona", persona);
        return "HolaMundo";
    }
    
}
