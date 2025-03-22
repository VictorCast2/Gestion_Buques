package com.example.buques.docs.Buque;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dimension {

    private double peso;
    private double largo;
    private double ancho;
}
