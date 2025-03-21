package com.example.buques.Empresa.entity;

import com.example.buques.AgenteNaviero.entity.AgenteNaviero;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Empresas")
public class Empresa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nit;

    @Column(length = 250, nullable = false)
    private String nombre;

    @Column(length = 75, nullable = false)
    private String pais;

    @Column(length = 150, nullable = false)
    private String ciudad;

    @Column(length = 250, nullable = false)
    private String direccion;

    @Column(length = 250, nullable = false, unique = true)
    private String correo;

    @Column(name = "cantidad_buques")
    private int cantidadBuques;

    // Cardinalidad para la tabla AgenteNaviero
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private Set<AgenteNaviero> agenteNavieros = new HashSet<>();

    // Relacion Bidireccional

    //Metodo para adicionar un Agente Naviero
    public void addAgenteNaviero(AgenteNaviero agenteNaviero) {
        if (agenteNaviero != null) {
            agenteNavieros.add(agenteNaviero);
            agenteNaviero.setEmpresa(this);
        }
    }

    //Metodo para eliminar un Agente Naviero
    public void deleteAgenteNaviero(AgenteNaviero agenteNaviero) {
        if (agenteNaviero != null) {
            agenteNavieros.remove(agenteNaviero);
            agenteNaviero.setEmpresa(null);
        }
    }
}
