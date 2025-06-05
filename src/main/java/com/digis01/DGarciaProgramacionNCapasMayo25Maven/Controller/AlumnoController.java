package com.digis01.DGarciaProgramacionNCapasMayo25Maven.Controller;

import com.digis01.DGarciaProgramacionNCapasMayo25Maven.DAO.AlumnoDAOImplementation;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.AlumnoDireccion;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
    
    @GetMapping("form") // este prepara la vista de formualrio
    public String Accion(Model model){
        model.addAttribute("alumnoDireccion", new AlumnoDireccion());
        return "AlumnoForm";
    }  
    @PostMapping("form") // este recupera los datos del formulario
    public String Accion(@ModelAttribute AlumnoDireccion alumnoDireccion){
        
        Result result = alumnoDAOImplementation.Add(alumnoDireccion);
        return "algo"; // redireccionen a la vista de GetAll
    }
    
    /*
    GET - Lectura, en vista es para preparación de datos o de la vista
    POST - Escritura, recuperar datos de una vista 
    */
    
}
