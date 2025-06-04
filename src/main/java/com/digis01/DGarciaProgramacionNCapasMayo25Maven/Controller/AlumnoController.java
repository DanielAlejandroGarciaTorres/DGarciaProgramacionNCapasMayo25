package com.digis01.DGarciaProgramacionNCapasMayo25Maven.Controller;

import com.digis01.DGarciaProgramacionNCapasMayo25Maven.DAO.AlumnoDAOImplementation;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/alumno")
public class AlumnoController {
    
    @Autowired
    private AlumnoDAOImplementation alumnoDAOImplementation;
    
    @GetMapping
    public String Index(Model model){
        
        Result result = alumnoDAOImplementation.GetAll();
        if (result.correct) {
            model.addAttribute("alumnosDireccion", result.objects);
        }
        return "AlumnoIndex";
    }
}
