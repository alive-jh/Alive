package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "device_test")
public class DeviceTest implements Serializable {

	@Id
	@GeneratedValue(generator = "deviceTestTableGenerator")
	@GenericGenerator(name = "deviceTestTableGenerator", strategy = "identity")
	@Column(name = "id", length = 11)
	private Integer id;

	@Column(name = "device_no")
	private String deviceNo;
	
	
	@Column(name = "epal_id")
	private String epalId;
	
	
	@Transient
	private Device device;
	

	 /**
     * 设备测试结果
     * 0 待测试
     * 1 不合格
     * 2 已合格
     * 3 已维修
     */
	@Column(name = "status")
	private Integer status = new Integer(0);

	/**
	 * LED
	 */
	@Column(name = "led")
	private Integer led = new Integer(0);

	/**
	 * usb连接
	 */
	@Column(name = "usb_connect")
	private Integer usbConnect = new Integer(0);

	/**
	 * 烧录程序
	 */
	@Column(name = "burning_process")
	private Integer burningProcess = new Integer(0);

	/**
	 * 摇晃
	 */
	@Column(name = "shake")
	private Integer shake = new Integer(0);


	/**
	 * 摄像头
	 */

	@Column(name = "camara")
	private Integer camara = new Integer(0);

	/**
	 * 手势识别
	 */

	@Column(name = "gesture_recognition")
	private Integer gestureRecognition = new Integer(0);

	/**
	 * 显示屏
	 */

	@Column(name = "display")
	private Integer display = new Integer(0);

	/**
	 * 主机HOME键
	 */

	@Column(name = "touch_key")
	private Integer touchKey = new Integer(0);

	/**
	 * 
	 */

	@Column(name = "rfid")
	private Integer rfid = new Integer(0);
	
	/**
	 * 旋转功能
	 */

	@Column(name = "rotation_function")
	private Integer rotationFunction = new Integer(0);

	/**
	 * WIFI
	 */
	@Column(name = "wifi")
	private Integer wifi = new Integer(0);
	
	
	/**
	 * 开机启动
	 */
	@Column(name = "power")
	private Integer power = new Integer(0);
	
	
	/**
	 * 测试程序
	 */
	@Column(name = "test_process")
	private Integer testProcess = new Integer(0);
	
	/**
	 * left_mic
	 */
	@Column(name = "left_mic")
	private Integer leftMic = new Integer(0);

	/**
	 * right_mic
	 */
	@Column(name = "right_mic")
	private Integer rightMic = new Integer(0);
	
	
	/**
	 * 左喇叭
	 */
	@Column(name = "left_horn")
	private Integer leftHorn = new Integer(0);
	
	
	/**
	 * 右喇叭
	 */
	@Column(name = "right_horn")
	private Integer rightHorn = new Integer(0);
	
	/**
	 * 左耳机
	 */
	@Column(name = "left_ear")
	private Integer leftEar = new Integer(0);
	
	/**
	 * 右耳机
	 */
	@Column(name = "right_ear")
	private Integer rightEar = new Integer(0);
	
	/**
	 * 耳机MIC
	 */
	@Column(name = "ear_mic")
	private Integer earMic = new Integer(0);
	
	
	/**
	 * 头顶Touch
	 */
	@Column(name = "top_touch")
	private Integer topTouch = new Integer(0);
	
	/**
	 * 左Touch
	 */
	@Column(name = "left_touch")
	private Integer leftTouch = new Integer(0);
	
	/**
	 * 右Touch
	 */
	@Column(name = "right_touch")
	private Integer rightTouch = new Integer(0);
	
	
	/**
	 * 底座充电
	 */
	@Column(name = "foot_charge")
	private Integer footCharge = new Integer(0);
	
	
	/**
	 * usb充电
	 */
	@Column(name = "usb_charge")
	private Integer usbCharge = new Integer(0);
	
	
	
	public Integer getPower() {
		return power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

	public Integer getTestProcess() {
		return testProcess;
	}

	public void setTestProcess(Integer testProcess) {
		this.testProcess = testProcess;
	}

	public Integer getLeftHorn() {
		return leftHorn;
	}

	public void setLeftHorn(Integer leftHorn) {
		this.leftHorn = leftHorn;
	}

	public Integer getRightHorn() {
		return rightHorn;
	}

	public void setRightHorn(Integer rightHorn) {
		this.rightHorn = rightHorn;
	}

	public Integer getLeftEar() {
		return leftEar;
	}

	public void setLeftEar(Integer leftEar) {
		this.leftEar = leftEar;
	}

	public Integer getRightEar() {
		return rightEar;
	}

	public void setRightEar(Integer rightEar) {
		this.rightEar = rightEar;
	}

	public Integer getEarMic() {
		return earMic;
	}

	public void setEarMic(Integer earMic) {
		this.earMic = earMic;
	}

	public Integer getTopTouch() {
		return topTouch;
	}

	public void setTopTouch(Integer topTouch) {
		this.topTouch = topTouch;
	}

	public Integer getLeftTouch() {
		return leftTouch;
	}

	public void setLeftTouch(Integer leftTouch) {
		this.leftTouch = leftTouch;
	}

	public Integer getRightTouch() {
		return rightTouch;
	}

	public void setRightTouch(Integer rightTouch) {
		this.rightTouch = rightTouch;
	}

	public Integer getFootCharge() {
		return footCharge;
	}

	public void setFootCharge(Integer footCharge) {
		this.footCharge = footCharge;
	}

	/**
	 * 修改记录
	 */
	@Column(name = "back_result")
	private String backResult;
	
	/**
	 * 登记人
	 */
	@Column(name = "regist_men")
	private String registMen;
	
	/**
	 * 登记时间
	 */
	@Column(name = "regist_time")
	private Timestamp registTime;
	
	/**
	 * 审核人
	 */
	@Column(name = "review_men")
	private String reviewMen;
	
	/**
	 * 登记时间
	 */
	@Column(name = "review_time")
	private Timestamp reviewTime;
	
	
	
	public String getRegistMen() {
		return registMen;
	}

	public void setRegistMen(String registMen) {
		this.registMen = registMen;
	}

	public Timestamp getRegistTime() {
		return registTime;
	}

	public void setRegistTime(Timestamp registTime) {
		this.registTime = registTime;
	}

	public String getReviewMen() {
		return reviewMen;
	}

	public void setReviewMen(String reviewMen) {
		this.reviewMen = reviewMen;
	}

	public Timestamp getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(Timestamp reviewTime) {
		this.reviewTime = reviewTime;
	}

	public String getEpalId() {
		return epalId;
	}

	public void setEpalId(String epalId) {
		this.epalId = epalId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getLed() {
		return led;
	}

	public void setLed(Integer led) {
		this.led = led;
	}

	public Integer getUsbConnect() {
		return usbConnect;
	}

	public void setUsbConnect(Integer usbConnect) {
		this.usbConnect = usbConnect;
	}

	public Integer getBurningProcess() {
		return burningProcess;
	}

	public void setBurningProcess(Integer burningProcess) {
		this.burningProcess = burningProcess;
	}

	public Integer getLeftMic() {
		return leftMic;
	}

	public void setLeftMic(Integer leftMic) {
		this.leftMic = leftMic;
	}

	public Integer getRightMic() {
		return rightMic;
	}

	public void setRightMic(Integer rightMic) {
		this.rightMic = rightMic;
	}

	public Integer getCamara() {
		return camara;
	}

	public void setCamara(Integer camara) {
		this.camara = camara;
	}

	public Integer getGestureRecognition() {
		return gestureRecognition;
	}

	public void setGestureRecognition(Integer gestureRecognition) {
		this.gestureRecognition = gestureRecognition;
	}

	public Integer getDisplay() {
		return display;
	}

	public void setDisplay(Integer display) {
		this.display = display;
	}

	public Integer getTouchKey() {
		return touchKey;
	}

	public void setTouchKey(Integer touchKey) {
		this.touchKey = touchKey;
	}

	public Integer getRfid() {
		return rfid;
	}

	public void setRfid(Integer rfid) {
		this.rfid = rfid;
	}

	public Integer getRotationFunction() {
		return rotationFunction;
	}

	public void setRotationFunction(Integer rotationFunction) {
		this.rotationFunction = rotationFunction;
	}

	public Integer getWifi() {
		return wifi;
	}

	public void setWifi(Integer wifi) {
		this.wifi = wifi;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public String getBackResult() {
		return backResult;
	}

	public void setBackResult(String backResult) {
		this.backResult = backResult;
	}

	public Integer getUsbCharge() {
		return usbCharge;
	}

	public void setUsbCharge(Integer usbCharge) {
		this.usbCharge = usbCharge;
	}

	public Integer getShake() {
		return shake;
	}

	public void setShake(Integer shake) {
		this.shake = shake;
	}

}
