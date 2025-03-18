package com.example.buques.AgenteNaviero.entity;

import com.example.buques.Empresa.entity.Empresa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Agentes_Navieros")
public class AgenteNaviero implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String cedula;

    @Column(length = 150, nullable = false)
    private String nombre;

    @Column(length = 150, nullable = false)
    private String apellido;

    @Column(length = 150, nullable = false, unique = true)
    private String telefono;

    @Column(length = 250, nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String password;

    // Cardinalidad para la tabla Empresas
    @ManyToOne
    @JoinColumn(name = "empresa_id", referencedColumnName = "id",
                foreignKey = @ForeignKey(name = "FK_agente-empresa"))
    private Empresa empresa;
}
