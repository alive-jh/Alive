package com.wechat.entity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Anchor entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "anchor", catalog = "wechat")
public class Anchor implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String logo;
	private String summary;

	// Constructors

	/** default constructor */
	public Anchor() {
	}

	/** full constructor */
	public Anchor(String name, String logo, String summary) {
		this.name = name;
		this.logo = logo;
		this.summary = summary;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "logo")
	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Column(name = "summary")
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

}