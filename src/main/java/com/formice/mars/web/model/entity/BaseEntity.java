package com.formice.mars.web.model.entity;

import com.formice.mars.web.common.Constant;
import com.formice.mars.web.common.SessionBag;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = 3745200467736051071L;

	private Date createTime;

	private Long createBy;

	private Date updateTime;

	private Long updateBy;

	private Integer isDeleted;
	
	public static final Integer DELETE_YES = 1;
	public static final Integer DELETE_NO = 0;
	
	
	public static final Long CREATE_BY_SYS = -88L;//操作人是系统
	public static final Long CREATE_BY_PAY_NOFITY = -99L;//操作人是支付notify
	public static final Long CREATE_BY_QZ = -77L;//定时器任务更新


	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getCreateBy() {
		return SessionBag.get(Constant.CURRENT_USER_ID,Long.class);
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

}
