
package com.digis01.DGarciaProgramacionNCapasMayo25Maven.JPA;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Colonia {
 
    @Id
    @Column(name="idcolonia")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdColonia;
    @Column(name="nombre")
    private String Nombre;
    @Column(name="codigopostal")
    private String CodigoPostal;
    @ManyToOne
    @JoinColumn(name="idmunicipio")
    public Municipio Municipio; 

    public int getIdColonia() {
        return IdColonia;
    }

    public void setIdColonia(int IdColonia) {
        this.IdColonia = IdColonia;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getCodigoPostal() {
        return CodigoPostal;
    }

    public void setCodigoPostal(String CodigoPostal) {
        this.CodigoPostal = CodigoPostal;
    }

    public Municipio getMunicipio() {
        return Municipio;
    }

    public void setMunicipio(Municipio Municipio) {
        this.Municipio = Municipio;
    }
    
    
    
    
}
