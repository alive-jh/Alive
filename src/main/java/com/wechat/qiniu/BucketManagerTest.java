package com.wechat.qiniu;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;

public class BucketManagerTest {

	
	//获取文件列表
	public static void main(String args[]) {
		// 设置需要操作的账号的AK和SK
		String ACCESS_KEY = "vIkgOuBrwb1ySeLKY3NqhrfiZoY7J_I1baWxeMrX";
		String SECRET_KEY = "38Z2HlWeq-wy6huqDrnMy_3qBNLP_tB9yZvIUkoM";
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		// 实例化一个BucketManager对象
		BucketManager bucketManager = new BucketManager(auth,
				new Configuration(Zone.zone0()));
		// 要测试的空间和key，并且这个key在你空间中存在
		String bucket = "word";
		// 要列举文件的空间名
		try {
			// 调用listFiles方法列举指定空间的指定文件
			// 参数一：bucket 空间名
			// 参数二：prefix 文件名前缀
			// 参数三：marker 上一次获取文件列表时返回的marker
			// 参数四：limit 每次迭代的长度限制，最大1000，推荐值100
			// 参数五：delimiter 指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
			FileListing fileListing = bucketManager.listFiles(bucket, null,
					null, 10, null);
			FileInfo[] items = fileListing.items;
			for (FileInfo fileInfo : items) {
				//System.out.println(fileInfo.key);
			}
		} catch (QiniuException e) {
			// 捕获异常信息
			Response r = e.response;
			//System.out.println(r.toString());
		}
	}
}
