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
            int procesoCorrecto = jdbcTemplate.execute("{CALL AlumnoGetAllSP(?)}", (CallableStatementCallback<Integer>) callableStatement -> {

                int idAlumnoPrevio = 0; // sirve para guardar el id previo por si existe ya un usuario en la lista

                callableStatement.registerOutParameter(1, Types.REF_CURSOR);

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                result.objects = new ArrayList<>();

                while (resultSet.next()) {

                    idAlumnoPrevio = resultSet.getInt("IdAlumno");

                    if (!result.objects.isEmpty() && idAlumnoPrevio == ((AlumnoDireccion) (result.objects.get(result.objects.size() - 1))).Alumno.getIdAlumno()) {
                        /*aqui solo agrego direccion*/
                        Direccion direccion = new Direccion();

                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));

                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));

                        ((AlumnoDireccion) (result.objects.get(result.objects.size() - 1))).Direcciones.add(direccion);

                    } else {
                        /*aqui agrego alumno y direccion*/
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
                        alumnoDireccion.Alumno.setImagen(resultSet.getString("Imagen"));
                        
                        alumnoDireccion.Direcciones = new ArrayList<>();

                        Direccion direccion = new Direccion();

                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));

                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));

                        alumnoDireccion.Direcciones.add(direccion);

                        result.objects.add(alumnoDireccion);
                    }

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

    @Override
    public Result Add(AlumnoDireccion alumnoDireccion) {

        Result result = new Result();

        try {
            int rowAffected = jdbcTemplate.execute("{CALL AlumnoAddSP(?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.setString(1, alumnoDireccion.Alumno.getNombre());
                callableStatement.setString(2, alumnoDireccion.Alumno.getApellidoPaterno());
                callableStatement.setString(3, alumnoDireccion.Alumno.getApellidoMaterno());
                callableStatement.setString(4, alumnoDireccion.Alumno.getEmail());
                callableStatement.setInt(5, alumnoDireccion.Alumno.Semestre.getIdSemestre());
                callableStatement.setString(6, alumnoDireccion.Alumno.getImagen());
                callableStatement.setString(7, alumnoDireccion.Direccion.getCalle());
                callableStatement.setString(8, alumnoDireccion.Direccion.getNumeroInterior());
                callableStatement.setString(9, alumnoDireccion.Direccion.getNumeroExterior());
                callableStatement.setInt(10, alumnoDireccion.Direccion.Colonia.getIdColonia());

                return callableStatement.executeUpdate();
            });

            if (rowAffected > 0) {
                result.correct = true;
            }
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        
        return result;
    }
    
    @Override
    public Result GetDetalleAlumno(int idAlumno) {
        Result result = new Result();
 
        try {
 
            result.correct = jdbcTemplate.execute("{CALL AlumnoGetDetalle(?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
 
                callableStatement.setInt(1, idAlumno);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.registerOutParameter(3, Types.REF_CURSOR);
 
                callableStatement.execute();
 
                ResultSet resultSetUsuario = (ResultSet) callableStatement.getObject(2);
                ResultSet resultSetDirecciones = (ResultSet) callableStatement.getObject(3);
 
                AlumnoDireccion alumnoDireccion = new AlumnoDireccion();
 
                if (resultSetUsuario.next()) {
                    alumnoDireccion.Alumno = new Alumno();
                    alumnoDireccion.Alumno.setIdAlumno(resultSetUsuario.getInt("IdAlumno"));
                    alumnoDireccion.Alumno.setNombre(resultSetUsuario.getString("Nombre"));
                    alumnoDireccion.Alumno.setApellidoPaterno(resultSetUsuario.getString("ApellidoPaterno"));
                    alumnoDireccion.Alumno.setApellidoMaterno(resultSetUsuario.getString("ApellidoMaterno"));
                    alumnoDireccion.Alumno.setEmail(resultSetUsuario.getString("Email"));
                    alumnoDireccion.Alumno.Semestre = new Semestre();
                }
 
                alumnoDireccion.Direcciones = new ArrayList<>();
 
                while (resultSetDirecciones.next()) {
                    Direccion direccion = new Direccion();
 
                    direccion.setIdDireccion(resultSetDirecciones.getInt("IdDireccion"));
                    direccion.setCalle(resultSetDirecciones.getString("Calle"));
                    direccion.setNumeroInterior(resultSetDirecciones.getString("NumeroInterior"));
                    direccion.setNumeroExterior(resultSetDirecciones.getString("NumeroExterior"));
 
                    direccion.Colonia = new Colonia();
                    direccion.Colonia.setIdColonia(resultSetDirecciones.getInt("IdColonia"));
 
                    direccion.Colonia.Municipio = new Municipio();
                    direccion.Colonia.Municipio.setIdMunicipio(resultSetDirecciones.getInt("IdMunicipio"));
 
                    alumnoDireccion.Direcciones.add(direccion);
 
                }
 
                result.object = alumnoDireccion;
 
                return true;
            });
 
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
 
        return result;
    }
}
