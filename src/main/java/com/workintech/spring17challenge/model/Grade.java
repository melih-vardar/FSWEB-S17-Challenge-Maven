package com.workintech.spring17challenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Grade {

    private int coefficient;
    private String note;

}
