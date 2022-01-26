package com.iresearch.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * CollegeVO
 * @author lengqie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollegeVO implements Serializable {
    private Integer id;
    private String name;
}
