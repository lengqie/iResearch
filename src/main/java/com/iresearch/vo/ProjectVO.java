package com.iresearch.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.iresearch.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Project显示层对象
 * @author lengqie
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectVO implements Serializable  {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String collegeName;

    private String subjectName;

    private String inCharge;

    private String projectTypeName;

    private String fileName;

    private String projectPurpose;

    private String economicAnalysis;

    private String existingConditions;

    private String expectedResult;

    private String viableAnalysis;
}
