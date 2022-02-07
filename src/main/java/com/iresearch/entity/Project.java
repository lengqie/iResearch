package com.iresearch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author lengqie
 * @since 2022-01-20
 */
@ApiModel(value = "Project对象", description = "")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String name;

    private Integer createId;

    private Integer collegeId;

    private Integer subjectId;

    private String inCharge;

    private Integer projectType;

    // @TableLogic
    private Integer projectStatus;

    private Integer fileId;

    private String projectPurpose;

    private String economicAnalysis;

    private String existingConditions;

    private String expectedResult;

    private String viableAnalysis;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    
    public Integer getId() {
        return id;
    }

      public void setId(Integer id) {
          this.id = id;
      }
    
    public String getName() {
        return name;
    }

      public void setName(String name) {
          this.name = name;
      }
    
    public Integer getCreateId() {
        return createId;
    }

      public void setCreateId(Integer createId) {
          this.createId = createId;
      }
    
    public Integer getCollegeId() {
        return collegeId;
    }

      public void setCollegeId(Integer collegeId) {
          this.collegeId = collegeId;
      }
    
    public Integer getSubjectId() {
        return subjectId;
    }

      public void setSubjectId(Integer subjectId) {
          this.subjectId = subjectId;
      }
    
    public String getInCharge() {
        return inCharge;
    }

      public void setInCharge(String inCharge) {
          this.inCharge = inCharge;
      }
    
    public Integer getProjectType() {
        return projectType;
    }

      public void setProjectType(Integer projectType) {
          this.projectType = projectType;
      }
    
    public Integer getProjectStatus() {
        return projectStatus;
    }

      public void setProjectStatus(Integer projectStatus) {
          this.projectStatus = projectStatus;
      }
    
    public Integer getFileId() {
        return fileId;
    }

      public void setFileId(Integer fileId) {
          this.fileId = fileId;
      }
    
    public String getProjectPurpose() {
        return projectPurpose;
    }

      public void setProjectPurpose(String projectPurpose) {
          this.projectPurpose = projectPurpose;
      }
    
    public String getEconomicAnalysis() {
        return economicAnalysis;
    }

      public void setEconomicAnalysis(String economicAnalysis) {
          this.economicAnalysis = economicAnalysis;
      }
    
    public String getExistingConditions() {
        return existingConditions;
    }

      public void setExistingConditions(String existingConditions) {
          this.existingConditions = existingConditions;
      }
    
    public String getExpectedResult() {
        return expectedResult;
    }

      public void setExpectedResult(String expectedResult) {
          this.expectedResult = expectedResult;
      }
    
    public String getViableAnalysis() {
        return viableAnalysis;
    }

      public void setViableAnalysis(String viableAnalysis) {
          this.viableAnalysis = viableAnalysis;
      }
    
    public LocalDateTime getCreateTime() {
        return createTime;
    }

      public void setCreateTime(LocalDateTime createTime) {
          this.createTime = createTime;
      }
    
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

      public void setUpdateTime(LocalDateTime updateTime) {
          this.updateTime = updateTime;
      }

    @Override
    public String toString() {
        return "Project{" +
              "id=" + id +
                  ", name=" + name +
                  ", createId=" + createId +
                  ", collegeId=" + collegeId +
                  ", subjectId=" + subjectId +
                  ", inCharge=" + inCharge +
                  ", projectType=" + projectType +
                  ", projectStatus=" + projectStatus +
                  ", fileId=" + fileId +
                  ", projectPurpose=" + projectPurpose +
                  ", economicAnalysis=" + economicAnalysis +
                  ", existingConditions=" + existingConditions +
                  ", expectedResult=" + expectedResult +
                  ", viableAnalysis=" + viableAnalysis +
                  ", createTime=" + createTime +
                  ", updateTime=" + updateTime +
              "}";
    }
}
