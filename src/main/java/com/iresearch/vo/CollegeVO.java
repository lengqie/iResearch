package com.iresearch.vo;

import com.iresearch.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

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
    private List<SubjectVO> subjects;
}
