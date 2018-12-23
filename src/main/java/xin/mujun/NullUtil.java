/**
 * Copyright(C) 2018 Hangzhou Fugle Technology Co., Ltd. All rights reserved.
 *
 */
package xin.mujun;

import java.util.Collection;
import java.util.Map;

/**
 * @since 2018年11月4日 下午2:13:53
 * @version $Id$
 * @author Administrator
 *
 */
public class NullUtil {

	@SuppressWarnings("rawtypes")
	public static boolean isNull(Object obj) {
		if (null == obj) {
			return true;
		} else if (obj instanceof String) {
			String str = (String) obj;
			return isNull(str);
		} else if (obj instanceof Collection) {

			return ((Collection) obj).isEmpty();

		} else if (obj instanceof Map) {
			return ((Map) obj).isEmpty();
		}
		return false;
	}

	public static boolean isNull(String str) {
		if (null == str || "".equals(str) || "null".equals(str) || "NULL".equals(str) || "Null".equals(str)) {
			return true;
		}
		return false;
	}

	private NullUtil() {

	}

}
