/**
 * Copyright(C) 2018 Hangzhou Fugle Technology Co., Ltd. All rights reserved.
 *
 */
package xin.mujun.model.chess;

import java.util.Map;

import xin.mujun.model.Chess;
import xin.mujun.model.Coord;

/**
 * 马
 * 
 * @since 2018年11月1日 下午9:41:55
 * @version $Id$
 * @author Administrator
 *
 */
public class HorseChess extends Chess {

	/* (non-Javadoc)
	 * @see xin.mujun.model.Chess#run(xin.mujun.model.Coord, xin.mujun.model.Chess[][], java.util.Map)
	 */
	@Override
	public boolean run(Coord coord, Chess[][] chessArr, Map<String, Chess> chessMap) {
		int x = coord.getX();
		int y = coord.getY();
		Integer currX = getCurrX();
		Integer currY = getCurrY();

		if (abs(x, currX) == 2 && abs(y, currY) == 1) {
			int newX = (x + currX) / 2;
			Chess chess = chessArr[newX][currY];
			if (null == chess) {// 拌马蹄
				return true;
			}

		} else if (abs(x, currX) == 1 && abs(y, currY) == 2) {
			int newY = (y + currY) / 2;
			Chess chess = chessArr[currX][newY];
			if (null == chess) {// 拌马蹄
				return true;
			}
		}
		return false;
	}

}
