package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "course_word")
public class CourseWord {

	@Id
	@GeneratedValue(generator ="courseGenerator")       
    @GenericGenerator(name ="courseGenerator", strategy ="identity")
    @Column(name = "id",length = 32)
	private Integer id;
	
	@Column(name="word_txt")
	private String wordTxt;	//单词名
	
	@Column(name="word_audio")
	private String wordAudio;	//读音文件地址
	
	@Column(name="word_pic")
	private String wordPic;	//单词卡片地址
	
	@Column(name="audioexplain")
	private String audioexplain; //发音解释地址
	
	@Column(name="txtexplain")
	private String txtexplain;	//单词解释
	
	@Column(name="status")
	private Integer status;	//状态
	
	@Column(name="createtime")
	private Timestamp createtime;	//创建时间
	

	public String getWordTxt() {
		return wordTxt;
	}

	public void setWordTxt(String wordTxt) {
		this.wordTxt = wordTxt;
	}

	public String getWordAudio() {
		return wordAudio;
	}

	public void setWordAudio(String wordAudio) {
		this.wordAudio = wordAudio;
	}

	public String getWordPic() {
		return wordPic;
	}

	public void setWordPic(String wordPic) {
		this.wordPic = wordPic;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAudioexplain() {
		return audioexplain;
	}

	public void setAudioexplain(String audioexplain) {
		this.audioexplain = audioexplain;
	}

	public String getTxtexplain() {
		return txtexplain;
	}

	public void setTxtexplain(String txtexplain) {
		this.txtexplain = txtexplain;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
	
}
