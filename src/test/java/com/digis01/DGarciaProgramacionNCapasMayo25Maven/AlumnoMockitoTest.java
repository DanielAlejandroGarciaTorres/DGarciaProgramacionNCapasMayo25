package com.digis01.DGarciaProgramacionNCapasMayo25Maven;

import com.digis01.DGarciaProgramacionNCapasMayo25Maven.DAO.AlumnoDAOImplementation;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.DAO.AlumnoJPADAOImplementation;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.JPA.Alumno;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.JPA.AlumnoDireccion;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.JPA.Semestre;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Result;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AlumnoMockitoTest {
    
//    @Mock
//    private EntityManager entityManager;
//    
//    @InjectMocks
//    private AlumnoJPADAOImplementation alumnoJPADAOImplementation;
//    
//    @Test
//    public void AddAlumno() {
//        
//        AlumnoDireccion alumnoDireccion = new  com.digis01.DGarciaProgramacionNCapasMayo25Maven.JPA.AlumnoDireccion();
//        alumnoDireccion.Alumno = new com.digis01.DGarciaProgramacionNCapasMayo25Maven.JPA.Alumno(); 
//        alumnoDireccion.Alumno.setNombre("Kevin");
//        alumnoDireccion.Alumno.setApellidoPaterno("Medrano");
//        alumnoDireccion.Alumno.Semestre = new Semestre();
//        alumnoDireccion.Alumno.Semestre.setIdSemestre(1);
//        //Yo le tengo que decir como comportarse
//        //Mockito when solo en metodos que devuelven algo
//        //Mockito.when(typedQuery.getResultList).thenReturn(ListaFalsa);
//        
//        Result result = alumnoJPADAOImplementation.Add(alumnoDireccion);
//        
//        //Assertions
//        
//        Mockito.verify(entityManager,Mockito.times(1)).persist(Mockito.any(com.digis01.DGarciaProgramacionNCapasMayo25Maven.JPA.Alumno.class));
//    }
//    
}
