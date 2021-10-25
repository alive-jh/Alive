package com.wechat.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.jfinal.captcha.Captcha;
import com.jfinal.captcha.CaptchaManager;
import com.jfinal.captcha.ICaptchaCache;
import com.jfinal.core.Controller;

public class GraphicsUtils {

	protected static String captchaName = "_jfinal_captcha";
	protected static final Random random = new Random(System.nanoTime());
	
	// 榛樿鐨勯獙璇佺爜澶у皬
	protected static final int WIDTH = 108, HEIGHT = 40;
	// 楠岃瘉鐮侀殢鏈哄瓧绗︽暟缁�
	protected static final char[] charArray = "3456789ABCDEFGHJKMNPQRSTUVWXY".toCharArray();
	// 楠岃瘉鐮佸瓧浣�
	protected static final Font[] RANDOM_FONT = new Font[] {
		new Font("nyala", Font.BOLD, 38),
		new Font("Arial", Font.BOLD, 32),
		new Font("Bell MT", Font.BOLD, 32),
		new Font("Credit valley", Font.BOLD, 34),
		new Font("Impact", Font.BOLD, 32),
		new Font(Font.MONOSPACED, Font.BOLD, 40)
	};
	
	
	
	protected String getRandomString() {
		char[] randomChars = new char[4];
		for (int i=0; i<randomChars.length; i++) {
			randomChars[i] = charArray[random.nextInt(charArray.length)];
		}
		return String.valueOf(randomChars);
	}
	
	public static void drawGraphic(String randomString, BufferedImage image){
		// 鑾峰彇鍥惧舰涓婁笅鏂�
		Graphics2D g = image.createGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		// 鍥惧舰鎶楅敮榻�
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// 瀛椾綋鎶楅敮榻�
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		// 璁惧畾鑳屾櫙鑹�
		g.setColor(getRandColor(210, 250));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		//缁樺埗灏忓瓧绗﹁儗鏅�
		Color color = null;
		for(int i = 0; i < 20; i++){
			color = getRandColor(120, 200);
			g.setColor(color);
			String rand = String.valueOf(charArray[random.nextInt(charArray.length)]);
			g.drawString(rand, random.nextInt(WIDTH), random.nextInt(HEIGHT));
			color = null;
		}
		
		//璁惧畾瀛椾綋
		g.setFont(RANDOM_FONT[random.nextInt(RANDOM_FONT.length)]);
		// 缁樺埗楠岃瘉鐮�
		for (int i = 0; i < randomString.length(); i++){
			//鏃嬭浆搴︽暟 鏈�濂藉皬浜�45搴�
			int degree = random.nextInt(28);
			if (i % 2 == 0) {
				degree = degree * (-1);
			}
			//瀹氫箟鍧愭爣
			int x = 22 * i, y = 21;
			//鏃嬭浆鍖哄煙
			g.rotate(Math.toRadians(degree), x, y);
			//璁惧畾瀛椾綋棰滆壊
			color = getRandColor(20, 130);
			g.setColor(color);
			//灏嗚璇佺爜鏄剧ず鍒板浘璞′腑
			g.drawString(String.valueOf(randomString.charAt(i)), x + 8, y + 10);
			//鏃嬭浆涔嬪悗锛屽繀椤绘棆杞洖鏉�
			g.rotate(-Math.toRadians(degree), x, y);
		}
		//鍥剧墖涓棿鏇茬嚎锛屼娇鐢ㄤ笂闈㈢紦瀛樼殑color
		g.setColor(color);
		//width鏄嚎瀹�,float鍨�
		BasicStroke bs = new BasicStroke(3);
		g.setStroke(bs);
		//鐢诲嚭鏇茬嚎
		QuadCurve2D.Double curve = new QuadCurve2D.Double(0d, random.nextInt(HEIGHT - 8) + 4, WIDTH / 2, HEIGHT / 2, WIDTH, random.nextInt(HEIGHT - 8) + 4);
		g.draw(curve);
		// 閿�姣佸浘鍍�
		g.dispose();
	}
	
	/*
	 * 缁欏畾鑼冨洿鑾峰緱闅忔満棰滆壊
	 */
	protected static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	
	/**
	 * 鏍￠獙鐢ㄦ埛杈撳叆鐨勯獙璇佺爜鏄惁姝ｇ‘
	 * @param controller 鎺у埗鍣�
	 * @param userInputString 鐢ㄦ埛杈撳叆鐨勫瓧绗︿覆
	 * @return 楠岃瘉閫氳繃杩斿洖 true, 鍚﹀垯杩斿洖 false
	 */
	public static boolean validate(Controller controller, String userInputString) {
		String captchaKey = controller.getCookie(captchaName);
		if (validate(captchaKey, userInputString)) {
			controller.removeCookie(captchaName);
			return true;
		}
		return false;
	}
	
	/**
	 * 鏍￠獙鐢ㄦ埛杈撳叆鐨勯獙璇佺爜鏄惁姝ｇ‘
	 * @param captchaKey 楠岃瘉鐮� key锛屽湪涓嶆敮鎸� cookie 鐨勬儏鍐典笅鍙�氳繃浼犲弬缁欐湇鍔＄
	 * @param userInputString 鐢ㄦ埛杈撳叆鐨勫瓧绗︿覆
	 * @return 楠岃瘉閫氳繃杩斿洖 true, 鍚﹀垯杩斿洖 false
	 */
	public static boolean validate(String captchaKey, String userInputString) {
		ICaptchaCache captchaCache = CaptchaManager.me().getCaptchaCache();
		Captcha captcha = captchaCache.get(captchaKey);
		if (captcha != null && captcha.notExpired() && captcha.getValue().equalsIgnoreCase(userInputString)) {
			captchaCache.remove(captcha.getKey());
			return true;
		}
		return false;
	}
}
