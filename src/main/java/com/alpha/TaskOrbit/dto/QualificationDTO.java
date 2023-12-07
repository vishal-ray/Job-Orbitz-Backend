package com.alpha.TaskOrbit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Year;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QualificationDTO {
    private int qualificationId;
    private Year sessionFrom;
    private Year sessionTo;
}
