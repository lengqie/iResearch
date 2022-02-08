package com.iresearch.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * SubjectVO
 * @author lengqie
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectVO implements Serializable {
    private Integer id;
    private String name;
    private Integer collegeId;
}
