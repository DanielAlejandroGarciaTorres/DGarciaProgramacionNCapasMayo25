package com.digis01.DGarciaProgramacionNCapasMayo25Maven.DAO;

import com.digis01.DGarciaProgramacionNCapasMayo25Maven.JPA.Alumno;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.JPA.Direccion;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.JPA.Semestre;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.AlumnoDireccion;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AlumnoJPADAOImplementation implements IAlumnoJPADAO {

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

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result GetAll() {
        Result result = new Result();
        result.objects = new ArrayList<>();

        try {
            TypedQuery<Alumno> alumnosQuery = entityManger.createQuery("FROM Alumno ORDER BY IdAlumno ASC", Alumno.class);
            List<Alumno> alumnos = alumnosQuery.getResultList();

            for (Alumno alumnoJPA : alumnos) {
                AlumnoDireccion alumnoDireccion = new AlumnoDireccion();
                alumnoDireccion.Alumno = new com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Alumno();
                alumnoDireccion.Alumno.setIdAlumno(alumnoJPA.getIdAlumno());
                alumnoDireccion.Alumno.setNombre(alumnoJPA.getNombre());
                alumnoDireccion.Alumno.setApellidoPaterno(alumnoJPA.getApellidoPaterno());
                alumnoDireccion.Alumno.setApellidoMaterno(alumnoJPA.getApellidoMaterno());

                TypedQuery<Direccion> direccionesQuery = entityManger.createQuery("FROM Direccion WHERE Alumno.IdAlumno = :idalumno", Direccion.class);
                direccionesQuery.setParameter("idalumno", alumnoJPA.getIdAlumno());
                List<Direccion> direccionesJPA = direccionesQuery.getResultList();

                if (direccionesJPA.size() != 0) {

                    alumnoDireccion.Direcciones = new ArrayList<>();

                    for (Direccion direccionJPA : direccionesJPA) {
                        com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Direccion direccion = new com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Direccion();
                        direccion.setCalle(direccionJPA.getCalle());
                        direccion.setNumeroExterior(direccionJPA.getNumeroExterior());
                        direccion.setNumeroInterior(direccionJPA.getNumeroInterior());

                        alumnoDireccion.Direcciones.add(direccion);
                    }
                }

                result.objects.add(alumnoDireccion);

            }
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Transactional
    @Override
    public Result DeleteDireccionByIdDireccion(int idDireccion) {

        Result result = new Result();

        try {
            // FROM Direccion WHERE IdDireccion = 1, DireccionJPA.CLASS
            Direccion direccionJPA = entityManger.find(Direccion.class, idDireccion);
            entityManger.remove(direccionJPA);
            
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

}
