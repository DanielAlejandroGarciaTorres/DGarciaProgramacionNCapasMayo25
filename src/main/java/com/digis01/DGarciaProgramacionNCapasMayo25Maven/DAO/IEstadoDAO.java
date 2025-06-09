
package com.digis01.DGarciaProgramacionNCapasMayo25Maven.DAO;

import com.digis01.DGarciaProgramacionNCapasMayo25Maven.ML.Result;


public interface IEstadoDAO {
    
    Result GetEstadosByPais(int IdPais);
}
