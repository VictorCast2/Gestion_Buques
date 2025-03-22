package com.example.buques.document.Buque;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dimension {

    private double peso;
    private double largo;
    private double ancho;
}
