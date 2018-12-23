/**
 * Copyright(C) 2018 Hangzhou Fugle Technology Co., Ltd. All rights reserved.
 *
 */
package xin.mujun.model.chess;

import java.util.Map;

import xin.mujun.model.Chess;
import xin.mujun.model.Coord;

/**
 * 炮
 * 
 * @since 2018年11月1日 下午9:43:04
 * @version $Id$
 * @author Administrator
 *
 */
public class CannonChess extends Chess {
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
			return isNoChess(chessX, y, currY, true);

		} else if (currY == y) {
			Chess[] chesss = getChesss(chessArr, y);
			return isNoChess(chesss, x, currX, true);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see xin.mujun.model.Chess#captureGo(xin.mujun.model.Coord, xin.mujun.model.Chess[][], java.util.Map)
	 */
	@Override
	public boolean captureGo(Coord coord, Chess[][] chessArr, Map<String, Chess> chessMap) {
		int x = coord.getX();
		int y = coord.getY();

		Integer currX = getCurrX();
		Integer currY = getCurrY();

		if (currX == x) {
			Chess[] chessX = chessArr[x];
			return isHashOneChess(chessX, y, currY);

		} else if (currY == y) {
			Chess[] chesss = getChesss(chessArr, y);
			return isHashOneChess(chesss, x, currX);
		}
		return false;
	}

	/**
	 * 是否只有一个棋子
	 * 
	 * @param chessX
	 * @param moveA
	 * @param currA
	 * @return
	 */
	private boolean isHashOneChess(Chess[] chessX, int moveA, int currA) {

		int min = moveA < currA ? moveA : currA;
		int max = moveA < currA ? currA : moveA;

		int count = 0;
		for (int i = min + 1; i < max; i++) {
			Chess chess = chessX[i];
			if (null != chess) {
				count++;
			}
		}
		if (count != 1) {
			return false;
		}
		return true;
	}

}
