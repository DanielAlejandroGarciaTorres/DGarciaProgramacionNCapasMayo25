package com.digis01.DGarciaProgramacionNCapasMayo25Maven.Controller;

import com.digis01.DGarciaProgramacionNCapasMayo25Maven.DAO.AlumnoDAOImplementation;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.DAO.EstadoDAOImplementation;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.DAO.PaisDAOImplementation;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.AlumnoDireccion;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Result;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/alumno")
public class AlumnoController {
    
    @Autowired
    private AlumnoDAOImplementation alumnoDAOImplementation;
    
    @Autowired
    private PaisDAOImplementation paisDAOImplementation;
    
    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation;
    
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
        
        Result resultPaises = paisDAOImplementation.GetAll();
        
//        model.addAttribute("paises", paisDAOImplementation.GetAll().objects);
        model.addAttribute("paises", resultPaises.objects);
        model.addAttribute("alumnoDireccion", new AlumnoDireccion());
        return "AlumnoForm";
    }  
    @PostMapping("form") // este recupera los datos del formulario
    public String Accion(@Valid @ModelAttribute AlumnoDireccion alumnoDireccion,
                            BindingResult bindingResult,
                            Model model){
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("alumnoDireccion", alumnoDireccion);
            return "AlumnoForm";
        }
        
        Result result = alumnoDAOImplementation.Add(alumnoDireccion);
        return "algo"; // redireccionen a la vista de GetAll
    }
    
    @GetMapping("/GetEstadosByPais/{idPais}")
    @ResponseBody // retorno de dato estructurado (objeto en JSON/XML)
    public Result GetEstadosByPais(@PathVariable("idPais") int IdPais){
        return estadoDAOImplementation.GetEstadosByPais(IdPais);
    }
    /*
    GET - Lectura, en vista es para preparación de datos o de la vista
    POST - Escritura, recuperar datos de una vista 
    */
    
}
