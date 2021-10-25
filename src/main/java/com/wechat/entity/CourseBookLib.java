package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "course_booklib")
public class CourseBookLib {
	
	@Id
	@GeneratedValue(generator ="courseBookLibGenerator")       
    @GenericGenerator(name ="courseBookLibGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	@Column(name = "productid")
	private Integer productId;			//商品ID
	
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	@Column(name = "bookname")
	private String bookName;			//书籍名称
	
	@Column(name = "bookcover")
	private String bookCover;			//书籍封面
	
	@Column(name = "bookisbn")			//书籍ISBN
	private String bookISBN;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBookCover() {
		return bookCover;
	}

	public void setBookCover(String bookCover) {
		this.bookCover = bookCover;
	}

	public String getBookISBN() {
		return bookISBN;
	}

	public void setBookISBN(String bookISBN) {
		this.bookISBN = bookISBN;
	}

	

}
