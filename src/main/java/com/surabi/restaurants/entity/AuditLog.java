package com.surabi.restaurants.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Entity
public class AuditLog {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Column
	private String discription;

	@Column
	private String user_created;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public String getUser_created() {
		return user_created;
	}

	public void setUser_created(String user_created) {
		this.user_created = user_created;
	}

	public AuditLog(Integer id, Date createDate, String discription, String user_created) {
		this.id = id;
		this.createDate = createDate;
		this.discription = discription;
		this.user_created = user_created;
	}

	public AuditLog(Date createDate, String discription, String user_created) {
		this.createDate = createDate;
		this.discription = discription;
		this.user_created = user_created;
	}
}
