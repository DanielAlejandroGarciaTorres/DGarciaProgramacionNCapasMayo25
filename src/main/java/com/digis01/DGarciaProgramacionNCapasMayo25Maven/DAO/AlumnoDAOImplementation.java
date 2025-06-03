package com.digis01.DGarciaProgramacionNCapasMayo25Maven.DAO;

import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Alumno;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.AlumnoDireccion;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Colonia;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Municipio;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Direccion;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Result;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Semestre;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository // clase repositorio - clase que maneja conexión a bd
public class AlumnoDAOImplementation implements IAlumnoDAO {

    @Autowired // conexión a base de datos 
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll() {

        Result result = new Result();
        try {
            int procesoCorrecto  = jdbcTemplate.execute("{CALL AlumnoGetAllSP(?)}", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.registerOutParameter(1, Types.REF_CURSOR);

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                result.objects = new ArrayList<>();

                while (resultSet.next()) {
                    AlumnoDireccion alumnoDireccion = new AlumnoDireccion();

                    alumnoDireccion.Alumno = new Alumno();
                    alumnoDireccion.Alumno.setIdAlumno(resultSet.getInt("IdAlumno"));
                    alumnoDireccion.Alumno.setNombre(resultSet.getString("NombreAlumno"));
                    alumnoDireccion.Alumno.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                    alumnoDireccion.Alumno.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                    alumnoDireccion.Alumno.setEmail(resultSet.getString("Email"));
                    alumnoDireccion.Alumno.Semestre = new Semestre();
                    alumnoDireccion.Alumno.Semestre.setIdSemestre(resultSet.getInt("IdSemestre"));
                    alumnoDireccion.Alumno.Semestre.setNombre(resultSet.getString("NombreSemestre"));

                    alumnoDireccion.Direccion = new Direccion();
                    alumnoDireccion.Direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                    alumnoDireccion.Direccion.setCalle(resultSet.getString("Calle"));

                    alumnoDireccion.Direccion.Colonia = new Colonia();
                    alumnoDireccion.Direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));

                    alumnoDireccion.Direccion.Colonia.Municipio = new Municipio();
                    alumnoDireccion.Direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                    result.objects.add(alumnoDireccion);
                }

                return 1; // termine satisfactoriamente
            });

            if (procesoCorrecto == 1) {
                result.correct = true;
            }
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
}
