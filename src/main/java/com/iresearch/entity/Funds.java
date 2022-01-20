package com.iresearch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@ApiModel(value = "Funds对象", description = "")
public class Funds implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private Integer projectId;

    private String funds;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    
    public Integer getId() {
        return id;
    }

      public void setId(Integer id) {
          this.id = id;
      }
    
    public Integer getProjectId() {
        return projectId;
    }

      public void setProjectId(Integer projectId) {
          this.projectId = projectId;
      }
    
    public String getFunds() {
        return funds;
    }

      public void setFunds(String funds) {
          this.funds = funds;
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
        return "Funds{" +
              "id=" + id +
                  ", projectId=" + projectId +
                  ", funds=" + funds +
                  ", createTime=" + createTime +
                  ", updateTime=" + updateTime +
              "}";
    }
}
