package com.digis01.DGarciaProgramacionNCapasMayo25Maven.DAO;

import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Colonia;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Direccion;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Estado;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Municipio;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Pais;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Result;
import java.sql.ResultSet;
import java.sql.Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionDAOImplementation implements IDireccionDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result DireccionById(int IdDireccion) {
        Result result = new Result();
        try{
            
            result.correct = jdbcTemplate.execute("{CALL DireccionById(?, ?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
            
                callableStatement.setInt(1, IdDireccion);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                
                if(resultSet.next()){
                    Direccion direccion = new Direccion();
 
                    direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                    direccion.setCalle(resultSet.getString("Calle"));
                    direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                    direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
 
                    direccion.Colonia = new Colonia();
                    direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
 
                    direccion.Colonia.Municipio = new Municipio();
                    direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                    
                    direccion.Colonia.Municipio.Estado = new Estado();
                    direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                    
                    direccion.Colonia.Municipio.Estado.Pais = new Pais();
                    direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                    
                    result.object = direccion;
                }
                
                return true;
            });
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
}
