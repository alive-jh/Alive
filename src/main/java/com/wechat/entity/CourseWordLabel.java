package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "course_word_label")
public class CourseWordLabel {
	@Id
	@GeneratedValue(generator ="coursewordlabeltableGenerator")       
    @GenericGenerator(name ="coursewordlabeltableGenerator", strategy ="identity")
	
    @Column(name = "id",length = 32)
	private Integer id;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "label_en")
	private String labelEN ;						//标签英文名称
	
	public String getLabelEN() {
		return labelEN;
	}

	public void setLabelEN(String labelEN) {
		this.labelEN = labelEN;
	}
	
	
	@Column(name = "label_cn")
	private String labelCN;							//标签中文名称
	
	
	public String getLabelCN() {
		return labelCN;
	}

	public void setLabelCN(String labelCN) {
		this.labelCN = labelCN;
	}
	
	
	@Column(name = "image")
	private String image;							//图片
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	
	@Column(name = "group_name")
	private String group;						//标签分组
	
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
		
	@Column(name = "status")
	private Integer status;						//标签状态
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	
	@Column(name = "sound")
	private String sound;						//音效
	
	public void setSound(String sound) {
		// TODO Auto-generated method stub
		this.sound = sound;
		
	}
	
	public String getSound() {
		// TODO Auto-generated method stub
		return sound;
		
	}
}
