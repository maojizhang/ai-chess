/**
 * Copyright(C) 2018 Hangzhou Fugle Technology Co., Ltd. All rights reserved.
 *
 */
package xin.mujun.model.chess;

import java.util.Map;

import xin.mujun.constast.ChessNameConstant;
import xin.mujun.model.Chess;
import xin.mujun.model.Coord;

/**
 * 兵/卒
 * 
 * @since 2018年11月1日 下午9:47:03
 * @version $Id$
 * @author Administrator
 *
 */
public class ArmsChess extends Chess {

	/* (non-Javadoc)
	 * @see xin.mujun.model.Chess#run(xin.mujun.model.Coord, xin.mujun.model.Chess[][], java.util.Map)
	 */
	@Override
	public boolean run(Coord coord, Chess[][] chessArr, Map<String, Chess> chessMap) {
		int x = coord.getX();
		int y = coord.getY();
		Byte type = coord.getType();
		Byte currType = getType();

		Integer currX = getCurrX();
		Integer currY = getCurrY();
		if (currType.equals(type)) {
			if (x != currX) {
				return false;
			}
			if (ChessNameConstant.RED.equals(currType)) {
				if (currY - y == 1) {
					return true;
				}

			} else {
				if (y - currY == 1) {
					return true;
				}

			}
		} else {
			if (currY == y && abs(x, currX) == 1) {
				return true;
			}

			if (ChessNameConstant.RED.equals(currType)) {
				if (currY - y == 1 && x == currX) {
					return true;
				}
			} else {
				if (y - currY == 1 && x == currX) {
					return true;
				}

			}

		}
		return false;
	}

}
