/**
 * Copyright(C) 2018 Hangzhou Fugle Technology Co., Ltd. All rights reserved.
 *
 */
package xin.mujun.model.chess;

import java.util.Map;

import xin.mujun.model.Chess;
import xin.mujun.model.Coord;

/**
 * 士
 * 
 * @since 2018年11月1日 下午9:44:16
 * @version $Id$
 * @author Administrator
 *
 */
public class SoldierChess extends Chess {
	/* (non-Javadoc)
	 * @see xin.mujun.model.Chess#run(xin.mujun.model.Coord, xin.mujun.model.Chess[][], java.util.Map)
	 */
	@Override
	public boolean run(Coord coord, Chess[][] chessArr, Map<String, Chess> chessMap) {
		int x = coord.getX();
		int y = coord.getY();

		Integer currX = getCurrX();
		Integer currY = getCurrY();
		if (abs(x, currX) == 1 && abs(y, currY) == 1) {
			int startX = getStartX();
			int startY = getStartY();
			int otherX = 8 - startX;
			int newX = (startX + otherX) / 2;
			if (abs(x, newX) <= 1 && abs(y, startY) <= 2) {
				return true;
			}
		}
		return false;
	}

}
