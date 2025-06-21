package com.digis01.DGarciaProgramacionNCapasMayo25Maven.DAO;

import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.AlumnoDireccion;
import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Result;
import java.util.List;

public interface IAlumnoDAO {
    
    Result GetAll();
    
    Result Add(AlumnoDireccion alumnoDireccion);
    
    Result Add(List<AlumnoDireccion> alumnosDireccion);
    
    Result GetDetalleAlumno(int idAlumno);
}
