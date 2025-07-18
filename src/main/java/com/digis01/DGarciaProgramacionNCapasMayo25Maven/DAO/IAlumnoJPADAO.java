package com.digis01.DGarciaProgramacionNCapasMayo25Maven.DAO;

import com.digis01.DGarciaProgramacionNCapasMayo25Maven.JPA.AlumnoDireccion;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Result;

public interface IAlumnoJPADAO {
    
    Result Add(AlumnoDireccion alumnoDireccion);
    
    Result GetAll();
    
    Result DeleteDireccionByIdDireccion(int idDireccion);
}
