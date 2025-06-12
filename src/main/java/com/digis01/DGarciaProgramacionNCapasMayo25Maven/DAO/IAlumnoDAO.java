package com.digis01.DGarciaProgramacionNCapasMayo25Maven.DAO;

import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.AlumnoDireccion;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Result;

public interface IAlumnoDAO {
    
    Result GetAll();
    
    Result Add(AlumnoDireccion alumnoDireccion);
    
    Result GetDetalleAlumno(int idAlumno);
}
