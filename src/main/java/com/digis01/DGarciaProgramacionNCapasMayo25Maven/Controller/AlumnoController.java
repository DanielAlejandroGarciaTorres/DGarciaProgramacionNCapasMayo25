package com.digis01.DGarciaProgramacionNCapasMayo25Maven.Controller;

import com.digis01.DGarciaProgramacionNCapasMayo25Maven.DAO.AlumnoDAOImplementation;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.DAO.AlumnoJPADAOImplementation;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.DAO.DireccionDAOImplementation;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.DAO.EstadoDAOImplementation;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.DAO.PaisDAOImplementation;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Alumno;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.AlumnoDireccion;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Direccion;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Result;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.ResultValidarDatos;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
    private AlumnoJPADAOImplementation alumnoJPADAOImplementation;

    @Autowired
    private PaisDAOImplementation paisDAOImplementation;

    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation;

    @Autowired
    private DireccionDAOImplementation direccionDAOImplementation;

    @GetMapping
    public String Index(Model model) {

        Result result = alumnoJPADAOImplementation.GetAll();
        model.addAttribute("alumnoBusqueda", new Alumno());
        if (result.correct) {
            model.addAttribute("alumnosDireccion", result.objects);
        }
        return "AlumnoIndex";
    }

    @PostMapping
    public String index(@ModelAttribute Alumno alumnoBusqueda, Model model) {

        model.addAttribute("alumnoBusqueda", alumnoBusqueda);
        model.addAttribute("alumnosDireccion", alumnoDAOImplementation.GetAll().objects);
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
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        /*Si id alumno == 0 y Id Direccion == 0  agregar usuario*/
 /*Si id alumno == n y ID Direccion == -1 edita usaurio*/
 /*Si id alumno == n y ID Direccion == m edita direccion*/
 /*Si id alumno == n y ID Direccion == 0 agrega direccion*/
 
 
 
//        Result result = alumnoDAOImplementation.Add(alumnoDireccion);
        Result result = alumnoJPADAOImplementation.Add(alumnoDireccion);
        
        return "algo"; // redireccionen a la vista de GetAll
    }

    @GetMapping("cargamasiva")
    public String CargaMasiva() {
        return "CargaMasiva";
    }

    @PostMapping("cargamasiva")
    public String CargaMasiva(@RequestParam MultipartFile archivo, Model model, HttpSession session) throws IOException {
        // archivodato.txt
        // si aplico split ["archivosato","txt"]
        if (archivo != null && !archivo.isEmpty()) {
            String fileExtention = archivo.getOriginalFilename().split("\\.")[1];

            String root = System.getProperty("user.dir");
            String path = "src/main/resources/archivos";
            String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String absolutePath = root + "/" + path + "/" + fecha + archivo.getOriginalFilename();

            archivo.transferTo(new File(absolutePath));

            List<AlumnoDireccion> alumnosDireccion = new ArrayList<>();

            if (fileExtention.equals("txt")) {
                alumnosDireccion = LecturaArchivoTXT(archivo);
            } else { //"xlsx"
                alumnosDireccion = LecturaArchivoExcel(new File(absolutePath));
            }

            //metodo para validar datos
            List<ResultValidarDatos> listaErrores = ValidarDatos(alumnosDireccion);

            if (listaErrores.isEmpty()) {
                session.setAttribute("path", absolutePath);
                model.addAttribute("listaErrores", listaErrores);
                model.addAttribute("archivoCorrecto", true);
            } else {
                model.addAttribute("listaErrores", listaErrores);
                model.addAttribute("archivoCorrecto", false);
            }
        }

         return "CargaMasiva";
    }

    @GetMapping("/cargamasiva/procesar")
    public String ProcesarCargaMasiva(HttpSession session) {
        
        String ruta =  session.getAttribute("path").toString();
        session.removeAttribute("path");
        
        // validar un arhchivo 
        
        // leer txt o excel
        
        // iterar e insertar los datos
        
        return "";
    }

    public List<AlumnoDireccion> LecturaArchivoTXT(MultipartFile archivo) {

        List<AlumnoDireccion> alumnosDireccion = new ArrayList<>();

        try (InputStream inputStream = archivo.getInputStream(); 
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));) {

            bufferedReader.readLine();
            String linea = "";
            while ((linea = bufferedReader.readLine()) != null) {
                String[] datos = linea.split("\\|");

                AlumnoDireccion alumnoDireccion = new AlumnoDireccion();
                alumnoDireccion.Alumno = new Alumno();
                alumnoDireccion.Alumno.setNombre(datos[0]);
                alumnoDireccion.Alumno.setApellidoPaterno(datos[1]);

                alumnosDireccion.add(alumnoDireccion);
            }

        } catch (Exception ex) {
            alumnosDireccion = null;
        }
        return alumnosDireccion;
    }

    public List<AlumnoDireccion> LecturaArchivoExcel(File archivo) {

        List<AlumnoDireccion> alumnosDireccion = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(archivo);) {
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                AlumnoDireccion alumnoDireccion = new AlumnoDireccion();
                alumnoDireccion.Alumno = new Alumno();
                alumnoDireccion.Alumno.setNombre(row.getCell(0) != null ? row.getCell(0).toString() : "");
                alumnoDireccion.Alumno.setApellidoPaterno(row.getCell(1) != null ? row.getCell(1).toString() : "");

                alumnosDireccion.add(alumnoDireccion);
            }
        } catch (Exception ex) {
            System.out.println("Errore en apartura de archivo");
        }

        return alumnosDireccion;
    }

    private List<ResultValidarDatos> ValidarDatos(List<AlumnoDireccion> alumnos) {

        List<ResultValidarDatos> listaErrores = new ArrayList<>();

        int fila = 1;

        if (alumnos == null) {
            listaErrores.add(new ResultValidarDatos(0, "Lista inexistente", "Lista inexistente"));
        } else if (alumnos.isEmpty()) {
            listaErrores.add(new ResultValidarDatos(0, "Lista vacia", "Lista vacia"));
        } else {

            for (AlumnoDireccion alumnodireccion : alumnos) {

                if (alumnodireccion.Alumno.getNombre() == null || alumnodireccion.Alumno.getNombre().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, alumnodireccion.Alumno.getNombre(), "Campo obligatorio"));
                }
//                if (alumnodireccion.Alumno.getApellidoMaterno() == null || alumnodireccion.Alumno.getApellidoMaterno().equals("")) {
//                    listaErrores.add(new ResultValidarDatos(fila, alumnodireccion.Alumno.getApellidoMaterno(), "Campo obligatorio"));
//                }
                fila++;

            }

        }

        return listaErrores;

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
