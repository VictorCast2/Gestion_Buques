package com.App.Gestion_Buques.Empresa.Entity;

import com.App.Gestion_Buques.AgenteNav.Entity.AgenteNavEntity;
import jakarta.persistence.*;
import java.util.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "empresas")
public class EmpresaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "Name")
    private String name;

    @Column(name = "NIT", nullable = false, unique = true)
    private String NIT;

    @Column(name = "Pais")
    private String pais;

    @Column(name = "Ciudad")
    private String ciudad;

    @Column(name = "Direccion")
    private String direccion;

    @Column(name = "Telefono")
    private String telefono;

    @Column(name = "Correo")
    private String correo;

    @Column(name = "Cantidad_Buques")
    private int cantidad_Buques;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private Set<AgenteNavEntity> agentesNavieros = new HashSet<>();

    /**
     * Método que permite agregar un Agente Naviero (Usuario).
     * @param agenteNaviero
     */
    public void addAgenteNaviero(AgenteNavEntity agenteNaviero) {
        if (agenteNaviero != null) {
            agentesNavieros.add(agenteNaviero);
            agenteNaviero.setEmpresa(this);
        }
    }

    /**
     * Método que permite eliminar un Agente Naviero (Usuario).
     * @param agenteNaviero
     */
    public void deleteAgenteNaviero(AgenteNavEntity agenteNaviero) {
        if (agenteNaviero != null) {
            agentesNavieros.remove(agenteNaviero);
            agenteNaviero.setEmpresa(null);
        }
    }

}