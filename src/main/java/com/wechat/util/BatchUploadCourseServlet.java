package com.wechat.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.Iterator;
import java.util.List;

/**
 * 课程文件批量上传
 */
public class BatchUploadCourseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// 上传文件的保存路径
	protected String configPath = "/data/file/course";

	protected String dirTemp = "/data/file/temp/";

	protected String dirName = "file";

	static final String JDBC_DRIVER = PropertyUtil
			.getDataBaseProperty("driverClass");
	static final String DB_URL = PropertyUtil.getDataBaseProperty("jdbcUrl");

	static final String USER = PropertyUtil.getDataBaseProperty("jdbcName");
	static final String PASS = PropertyUtil.getDataBaseProperty("password");

	public BatchUploadCourseServlet() {
		//System.out.println("init");
	}

	public boolean batchInsertCourse(JSONArray mainCourses) {
		Connection conn = null;
		PreparedStatement pst = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			// 插入主课程SQL
			StringBuffer insertMainCourseSql = new StringBuffer(
					"insert into mallproduct (name,createdate,content,accountid,logo1,status,price,mp3type,cat_id) values (?,?,?,?,?,?,?,?,?)");
			pst = conn.prepareStatement(insertMainCourseSql.toString());
			JSONObject mainCourse = mainCourses.getJSONObject(0);
			pst.setString(1, mainCourse.get("name").toString());
			pst.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			pst.setString(3, mainCourse.get("content").toString());
			pst.setInt(4, 0);
			pst.setString(5, mainCourse.get("logo").toString());
			pst.setInt(6, 1);
			pst.setString(7, "0-0");
			pst.setInt(8, 2);
			pst.setInt(9, 0);
			int count = pst.executeUpdate();
			if (count > 0) {// 主课程记录保存成功
				ResultSet res = pst.getGeneratedKeys();
				if (res.next()) {
					int productId = res.getInt(1);
					//System.out.println("生成productId:" + productId);
					// 插入子课程SQL
					StringBuffer insertChildCourseSql = new StringBuffer(
							"insert into course (name,url,productid,total_class) values (?,?,?,?)");
					JSONArray childCourses = mainCourse
							.getJSONArray("subLessonList");
					for (int i = 0; i < childCourses.size(); i++) {
						JSONObject childCourse = childCourses.getJSONObject(i);
						// 子课时JSONArray
						JSONArray periodCourses = childCourse
								.getJSONArray("missionList");
						pst = conn.prepareStatement(insertChildCourseSql
								.toString());
						pst.setString(1, childCourse.getString("name"));
						pst.setString(2, childCourse.getString("url"));
						pst.setInt(3, productId);
						pst.setInt(4, periodCourses.size());
						int childCourseCount = pst.executeUpdate();
						if (childCourseCount > 0) {// 子课程记录保存成功
							ResultSet childCourseRes = pst.getGeneratedKeys();
							if (childCourseRes.next()) {
								int courseId = childCourseRes.getInt(1);
								//System.out.println("生成courseId:" + courseId);
								// 插入子课时SQL
								StringBuffer insertPeriodCourseSql = new StringBuffer(
										"insert into course_period (courseperiod_name,course_id,missionCmdList) values (?,?,?)");
								for (int j = 0; j < periodCourses.size(); j++) {
									JSONObject periodCourse = periodCourses
											.getJSONObject(j);
									pst = conn
											.prepareStatement(insertPeriodCourseSql
													.toString());
									pst.setString(1, periodCourse.getString("name"));
									pst.setInt(2, courseId);
									pst.setString(
											3,
											periodCourse.getJSONArray(
													"missionCmdList")
													.toString());
									pst.executeUpdate();
								}
							}
						}
					}

				}
			}

			pst.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pst != null)
					pst.close();
			} catch (SQLException se1) {
				se1.printStackTrace();
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se2) {
				se2.printStackTrace();
			}
		}
		return false;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		// 文件保存目录路径
		String savePath = configPath;
		// 临时文件目录
		String tempPath = dirTemp;

		// 创建文件夹
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		// 创建临时文件夹
		File dirTempFile = new File(tempPath);
		if (!dirTempFile.exists()) {
			dirTempFile.mkdirs();
		}

		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(20 * 1024 * 1024); // 设定使用内存超过5M时，将产生临时文件并存储于临时目录中。
		factory.setRepository(new File(tempPath)); // 设定存储临时文件的目录。

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");

		try {
			List<?> items = upload.parseRequest(request);
			Iterator<?> itr = items.iterator();

			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				String fileName = item.getName();
				if (!item.isFormField()) {
					try {
						File uploadedFile = new File(savePath,
								fileName.replaceAll(" ", ""));
						OutputStream os = new FileOutputStream(uploadedFile);
						InputStream is = item.getInputStream();
						byte buf[] = new byte[1024];// 可以修改 1024 以提高读取速度
						int length = 0;
						StringBuffer stringBuffer = new StringBuffer();
						while ((length = is.read(buf)) > 0) {
							os.write(buf, 0, length);
							stringBuffer.append(new String(buf, Charset
									.forName("UTF-8")));
						}
						// 读取课程文件信息添加到对应表

						JSONArray  mainCourses = JSONArray
								.fromObject(stringBuffer.toString());
						// 批量上传课程信息
						batchInsertCourse(mainCourses);
						// 关闭流
						os.flush();
						os.close();
						is.close();
						//System.out.println("上传成功！路径：" + savePath + "/"+ fileName);
								
						out.write("{\"Success\":1,\"ErrorMessage\":\"上传成功\"}");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

}
