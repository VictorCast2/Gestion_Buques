package com.App.Gestion_Buques.Empresa.Entity;

import com.App.Gestion_Buques.Usuario.Entity.UsuarioEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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
    private Set<UsuarioEntity> agentesNavieros = new HashSet<>();

    // Metodo para Adicionar un Agente Naviero (Usuario)
    public void addAgenteNaviero(UsuarioEntity agenteNaviero) {
        if (agenteNaviero != null) {
            agentesNavieros.add(agenteNaviero);
            agenteNaviero.setEmpresa(this);
        }
    }

    // Metodo para eliminar un Agente Naviero (Usuario)
    public void deleteAgenteNaviero(UsuarioEntity agenteNaviero) {
        if (agenteNaviero != null) {
            agentesNavieros.remove(agenteNaviero);
            agenteNaviero.setEmpresa(null);
        }
    }

}