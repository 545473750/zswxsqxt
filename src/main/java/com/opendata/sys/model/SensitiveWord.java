package com.opendata.sys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 敏感词
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_sensitiveword")
public class SensitiveWord {
	@Length(max=32)
	private java.lang.String id;
    /**
     * 敏感词名称       db_column: name 
     */ 	
	@Length(max=1000)
	private java.lang.String name;
    /**
     * 敏感词级别       db_column: code 
     */ 	
	@Length(max=100)
	private java.lang.String level;
	
	@Id @GeneratedValue(generator="custom-id")
	@GenericGenerator(name="custom-id", strategy = "uuid.hex")
	@Column(name = "id",  nullable = false, insertable = true, updatable = true, length = 32)
	public java.lang.String getId() {
		return this.id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	
	@Column(name = "name", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
	public java.lang.String getName() {
		return this.name;
	}
	public void setName(java.lang.String value) {
		this.name = value;
	}
	@Column(name = "level", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
	public java.lang.String getLevel() {
		return level;
	}
	public void setLevel(java.lang.String level) {
		this.level = level;
	}
	
}
