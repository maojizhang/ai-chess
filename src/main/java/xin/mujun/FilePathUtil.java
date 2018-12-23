/**
 * Copyright(C) 2018 Hangzhou Fugle Technology Co., Ltd. All rights reserved.
 *
 */
package xin.mujun;

import java.io.File;

/**
 * @since 2018年11月4日 下午2:09:42
 * @version $Id$
 * @author Administrator
 *
 */
public class FilePathUtil {

	private final static String IMAGE_PATH = "/src/main/resources/xin/mujun/resources/";

	/**
	 * 获取图片路径
	 * 
	 * @param path
	 * @return
	 */
	public static String getFilePath(String path) {
		String projectPath = System.getProperty("user.dir");
		if (null == projectPath || path == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(projectPath).append(IMAGE_PATH);
		sb.append(path);
		path = sb.toString();
		File file = new File(path);
		boolean exists = file.exists();
		if (exists) {
			return sb.toString();
		}
		return null;
	}

	public static String getFilePath() {
		String projectPath = System.getProperty("user.dir");
		StringBuilder sb = new StringBuilder();
		sb.append(projectPath).append(IMAGE_PATH);
		return sb.toString();
	}

	private FilePathUtil() {

	}

}
