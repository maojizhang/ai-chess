/**
 * Copyright(C) 2018 Hangzhou Fugle Technology Co., Ltd. All rights reserved.
 *
 */
package xin.mujun.model.chess;

import java.util.Map;

import xin.mujun.model.Chess;
import xin.mujun.model.Coord;

/**
 * 象
 * 
 * @since 2018年11月1日 下午9:45:40
 * @version $Id$
 * @author Administrator
 *
 */
public class ElephantChess extends Chess {

	/* (non-Javadoc)
	 * @see xin.mujun.model.Chess#run(xin.mujun.model.Coord, xin.mujun.model.Chess[][], java.util.Map)
	 */
	@Override
	public boolean run(Coord coord, Chess[][] chessArr, Map<String, Chess> chessMap) {
		int x = coord.getX();
		int y = coord.getY();

		Byte type = this.getType();

		if (!coord.getType().equals(type)) {
			return false;
		}
		Integer currX = this.getCurrX();
		Integer currY = this.getCurrY();

		if (abs(x, currX) == 2 && abs(y, currY) == 2) {
			x = (x + currX) / 2;
			y = (y + currY) / 2;
			Chess chess = chessArr[x][y];
			if (null == chess) {// 拌象眼
				return true;
			}
			return false;
		}
		return false;

	}

}
