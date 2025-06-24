package com.digis01.DGarciaProgramacionNCapasMayo25Maven.DAO;

import com.digis01.DGarciaProgramacionNCapasMayo25Maven.JPA.Alumno;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.JPA.Semestre;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.AlumnoDireccion;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AlumnoJPADAOImplementation  implements IAlumnoJPADAO{
    
    @Autowired
    private EntityManager entityManger;

    @Transactional
    @Override
    public Result Add(AlumnoDireccion alumnoDireccion) {
        Result result = new Result();
        
        try {
            
            Alumno alumnoJPA = new Alumno();
            
            alumnoJPA.setNombre(alumnoDireccion.Alumno.getNombre());
            alumnoJPA.setApellidoPaterno(alumnoDireccion.Alumno.getApellidoPaterno());
            alumnoJPA.setApellidoMaterno(alumnoDireccion.Alumno.getApellidoMaterno());
            alumnoJPA.setEmail(alumnoDireccion.Alumno.getEmail());
            
            alumnoJPA.Semestre = new Semestre();
            alumnoJPA.Semestre.setIdSemestre(alumnoDireccion.Alumno.Semestre.getIdSemestre());
            
            entityManger.persist(alumnoJPA);
            
            
            /*persistir direccion*/
            //direccionJPA = new DIreccionJPA
            //direccionjpa.alumno = alumnoJPA
            
            result.correct = true;
            
        } catch (Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
    
    
}
