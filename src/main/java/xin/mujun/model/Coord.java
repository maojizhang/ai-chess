/**
 * Copyright(C) 2018 Hangzhou Fugle Technology Co., Ltd. All rights reserved.
 *
 */
package xin.mujun.model;

import xin.mujun.constast.ChessNameConstant;

/**
 * @since 2018年11月8日 下午10:08:06
 * @version $Id$
 * @author Administrator
 *
 */
public class Coord {

	private int x;

	private int y;

	public Byte getType() {
		if (y <= 4) {
			return ChessNameConstant.BACK;
		}
		return ChessNameConstant.RED;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return x;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Coord) {
			Coord coord = (Coord) obj;
			return this.x == coord.x && this.y == coord.y;
		}
		return false;
	}

}
