/**
 * Copyright(C) 2018 Hangzhou Fugle Technology Co., Ltd. All rights reserved.
 *
 */
package xin.mujun.model.chess;

import java.util.Map;

import xin.mujun.model.Chess;
import xin.mujun.model.Coord;

/**
 * 帅/将
 * 
 * @since 2018年11月1日 下午9:29:00
 * @version $Id$
 * @author Administrator
 *
 */
public class BossChess extends Chess {

	/* (non-Javadoc)
	 * @see xin.mujun.model.Chess#run(xin.mujun.model.Coord)
	 */
	@Override
	public boolean run(Coord coord, Chess[][] chessArr, Map<String, Chess> chessMap) {
		int x = coord.getX();
		int y = coord.getY();

		int startX = this.getStartX();
		int startY = this.getStartY();

		if (abs(startX, x) <= 1 && abs(startY, y) <= 2) {
			Integer currX = this.getCurrX();
			Integer currY = this.getCurrY();

			if (abs(currX, x) == 1 && y - currY == 0) {
				return true;
			} else if (abs(currY, y) == 1 && x - currX == 0) {
				return true;
			}
		}
		return false;
	}

}
