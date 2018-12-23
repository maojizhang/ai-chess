/**
 * Copyright(C) 2018 Hangzhou Fugle Technology Co., Ltd. All rights reserved.
 *
 */
package xin.mujun.model.chess;

import java.util.Map;

import xin.mujun.model.Chess;
import xin.mujun.model.Coord;

/**
 * 車
 * 
 * @since 2018年11月1日 下午9:28:24
 * @version $Id$
 * @author Administrator
 *
 */
public class CarChess extends Chess {

	/* (non-Javadoc)
	 * @see xin.mujun.model.Chess#run(xin.mujun.model.Coord, xin.mujun.model.Chess[][], java.util.Map)
	 */
	@Override
	public boolean run(Coord coord, Chess[][] chessArr, Map<String, Chess> chessMap) {
		int x = coord.getX();
		int y = coord.getY();

		Integer currX = getCurrX();
		Integer currY = getCurrY();

		if (currX == x) {
			Chess[] chessX = chessArr[x];
			return isNoChess(chessX, y, currY, false);

		} else if (currY == y) {
			Chess[] chesss = getChesss(chessArr, y);
			return isNoChess(chesss, x, currX, false);
		}
		return false;
	}

}
