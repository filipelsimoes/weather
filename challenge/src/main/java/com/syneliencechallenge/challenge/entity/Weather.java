package com.syneliencechallenge.challenge.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class Weather {

    private String name;
    private String description;
    private BigDecimal temperature;
    private BigDecimal temperatureMin;
    private BigDecimal temperatureMax;
    private BigDecimal pressure;
    private BigDecimal humidity;
}
