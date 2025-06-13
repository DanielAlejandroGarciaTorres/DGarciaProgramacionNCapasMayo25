package com.digis01.DGarciaProgramacionNCapasMayo25Maven.Controller;

import com.digis01.DGarciaProgramacionNCapasMayo25Maven.DAO.AlumnoDAOImplementation;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.DAO.DireccionDAOImplementation;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.DAO.EstadoDAOImplementation;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.DAO.PaisDAOImplementation;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Alumno;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.AlumnoDireccion;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Direccion;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Result;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/alumno")
public class AlumnoController {

    @Autowired
    private AlumnoDAOImplementation alumnoDAOImplementation;

    @Autowired
    private PaisDAOImplementation paisDAOImplementation;

    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation;

    @Autowired
    private DireccionDAOImplementation direccionDAOImplementation;

    @GetMapping
    public String Index(Model model) {

        Result result = alumnoDAOImplementation.GetAll();
        if (result.correct) {
            model.addAttribute("alumnosDireccion", result.objects);
        }
        return "AlumnoIndex";
    }

    @GetMapping("form/{idAlumno}") // este prepara la vista de formualrio
    public String Accion(Model model, @PathVariable int idAlumno) {

//        Result resultPaises = paisDAOImplementation.GetAll();
        if (idAlumno < 1) {
            model.addAttribute("paises", paisDAOImplementation.GetAll().objects);
//            model.addAttribute("paises", resultPaises.objects);
            AlumnoDireccion alumnoDireccion = new AlumnoDireccion();
            alumnoDireccion.Alumno = new Alumno();
            alumnoDireccion.Direccion = new Direccion();
            model.addAttribute("alumnoDireccion", alumnoDireccion);
            return "AlumnoForm";
        } else {
            model.addAttribute("alumnoDireccion", alumnoDAOImplementation.GetDetalleAlumno(idAlumno).object);
            return "AlumnoDetail";
        }

//      
    }

    @GetMapping("/formeditable")
    public String AccionEditable(@RequestParam int IdAlumno, @RequestParam(required = false) Integer IdDireccion, Model model) {

        if (IdDireccion == null) { // editarAlumno
            AlumnoDireccion alumnoDireccion = new AlumnoDireccion();
            //alumnoDireccion.Alumno = alumnoDAOImplementation.GetById(IdAlumno);
            alumnoDireccion.Alumno = new Alumno();
            alumnoDireccion.Alumno.setIdAlumno(IdAlumno);
            alumnoDireccion.Alumno.setNombre("Ramón");
            alumnoDireccion.Direccion = new Direccion();
            alumnoDireccion.Direccion.setIdDireccion(-1);
            model.addAttribute("alumnoDireccion", alumnoDireccion);
        } else if (IdDireccion == 0) { // Agregar direccion
            AlumnoDireccion alumnoDireccion = new AlumnoDireccion();
            alumnoDireccion.Alumno = new Alumno();
            alumnoDireccion.Alumno.setIdAlumno(IdAlumno); // identifico a quien voy a darle nueva direccion
            alumnoDireccion.Direccion = new Direccion();
            model.addAttribute("alumnoDireccion", alumnoDireccion);
            model.addAttribute("paises", paisDAOImplementation.GetAll().objects);
            // roles
        } else { // editar direccion
            AlumnoDireccion alumnoDireccion = new AlumnoDireccion();
            alumnoDireccion.Alumno = new Alumno();
            alumnoDireccion.Alumno.setIdAlumno(IdAlumno);
            alumnoDireccion.Direccion = new Direccion(); // recuperar direccion usuario por id direccion
            alumnoDireccion.Direccion = (Direccion) direccionDAOImplementation.DireccionById(IdDireccion).object;

            model.addAttribute("paises", paisDAOImplementation.GetAll().objects);
            model.addAttribute("estados", estadoDAOImplementation.GetEstadosByPais(alumnoDireccion.Direccion.Colonia.Municipio.Estado.Pais.getIdPais()).objects);
//            model.addAttribute("estado", estadoDAOImplementation.GetEstadosByPais(IdPais));

            model.addAttribute("alumnoDireccion", alumnoDireccion);
        }

        return "AlumnoForm";
    }

    @PostMapping("form") // este recupera los datos del formulario
    public String Accion(@Valid @ModelAttribute AlumnoDireccion alumnoDireccion,
            BindingResult bindingResult,
            @RequestParam MultipartFile imagenFile,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("alumnoDireccion", alumnoDireccion);
            return "AlumnoForm";
        }
        try {
            if (!imagenFile.isEmpty()) {
                byte[] bytes = imagenFile.getBytes();
                String imgBase64 = Base64.getEncoder().encodeToString(bytes);
                alumnoDireccion.Alumno.setImagen(imgBase64);
            }
        } catch (Exception ex){
            System.out.println(ex.getLocalizedMessage());
        }

        /*Si id alumno == 0 y Id Direccion == 0  agregar usuario*/
 /*Si id alumno == n y ID Direccion == -1 edita usaurio*/
 /*Si id alumno == n y ID Direccion == m edita direccion*/
 /*Si id alumno == n y ID Direccion == 0 agrega direccion*/
        Result result = alumnoDAOImplementation.Add(alumnoDireccion);
        return "algo"; // redireccionen a la vista de GetAll
    }

    @GetMapping("/GetEstadosByPais/{idPais}")
    @ResponseBody // retorno de dato estructurado (objeto en JSON/XML)
    public Result GetEstadosByPais(@PathVariable("idPais") int IdPais) {
        return estadoDAOImplementation.GetEstadosByPais(IdPais);
    }
    /*
    GET - Lectura, en vista es para preparación de datos o de la vista
    POST - Escritura, recuperar datos de una vista 
     */

}
