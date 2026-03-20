package com.project.domain;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "Individuo")

public class Individuo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")

    @NotEmpty
    private Integer idIndividuo;
    @NotEmpty
    private String nombre;
    @NotEmpty
    private String apellido;
    @NotEmpty
    private int edad;
    @NotEmpty
    private String correo;
    @NotEmpty
    private String telefono;

}
