/**
 * Copyright(C) 2018 Hangzhou Fugle Technology Co., Ltd. All rights reserved.
 *
 */
package xin.mujun.model;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import xin.mujun.NullUtil;
import xin.mujun.constast.ChessNameConstant;

/**
 * @since 2018年10月24日 下午1:17:47
 * @version $Id$
 * @author Administrator
 *
 */
public class Chess implements Cloneable {

	private Image chessImg;

	/**
	 * 
	 */
	public Chess() {

	}

	/**
	 * 是否被选中
	 */
	private boolean isSelect;

	/**
	 * 坐标
	 */
	private Integer currX;
	/**
	 * 坐标
	 */
	private Integer currY;

	/**
	 * 起始坐标
	 */
	private int startX;
	/**
	 * 起始坐标
	 */
	private int startY;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 1.将不能对将 2.胜负已分时不能继续下棋 3.将是否处于危险中
	 */
	/**
	 * 
	 * @param coord
	 *            走的集合
	 * @param chessArr
	 *            棋子坐标集合
	 * @param chessMap
	 *            棋子集合
	 * @param currChess
	 *            当前棋子
	 * @return
	 */
	public boolean isNoCaptureGo(Coord coord, Chess[][] chessArr, Map<String, Chess> chessMap, Chess currChess) {
		boolean bossCollide = isBossCollide(coord, chessArr, chessMap, currChess);
		if (bossCollide) {
			System.out.println("不允许将对将");
			return true;
		}

		Chess newChess = (Chess) currChess.clone();
		int x = coord.getX();
		int y = coord.getY();

		newChess.setCurrX(x);
		newChess.setCurrY(y);
		Chess chess = chessArr[x][y];
		Integer oldX = currChess.getCurrX();
		Integer oldY = currChess.getCurrY();
		chessArr[oldX][oldY] = null;

		updateClass(chessArr, chessMap, newChess);

		Chess bossChess = getBossChess(currChess.getType(), chessMap);
		boolean bossDanger = isBossDanger(bossChess, chessMap, chessArr);
		// 最后修改回来
		updateClass(chessArr, chessMap, currChess);
		chessArr[x][y] = chess;
		if (bossDanger) {
			System.out.println("将处于危险中,name=" + bossChess.getName());
			return true;
		}

		return false;

	}

	private void updateClass(Chess[][] chessArr, Map<String, Chess> chessMap, Chess chess) {
		chessMap.put(chess.getName(), chess);
		chessArr[chess.getCurrX()][chess.getCurrY()] = chess;
	}

	/**
	 * 将/帅是否处于危险中
	 * 
	 * @param bossChess
	 * @param chessMap
	 * @param chessArr
	 * @return
	 */
	public boolean isBossDanger(Chess bossChess, Map<String, Chess> chessMap, Chess[][] chessArr) {
		Coord coord = new Coord();
		coord.setX(bossChess.getCurrX());
		coord.setY(bossChess.getCurrY());
		Byte bossType = bossChess.getType();
		for (Entry<String, Chess> chessEntry : chessMap.entrySet()) {
			Chess chess = chessEntry.getValue();
			Byte chessType = chess.getType();
			if (bossType.equals(chessType)) {
				continue;
			}
			boolean captureGo = chess.captureGo(coord, chessArr, chessMap);
			if (captureGo) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected Object clone() {
		Chess chess = null;
		try {
			chess = (Chess) super.clone();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chess;
	}

	/**
	 * 获取自己方boss
	 * 
	 * @param currType
	 * @return
	 */
	public static Chess getBossChess(Byte currType, Map<String, Chess> chessMasp) {
		if (ChessNameConstant.BACK.equals(currType)) {
			return chessMasp.get(ChessNameConstant.BACK_BOSS);

		} else {
			return chessMasp.get(ChessNameConstant.RED_BOSS);
		}
	}

	/**
	 * 是否将对将,将对将不能走
	 * 
	 * @return
	 */
	private boolean isBossCollide(Coord coord, Chess[][] chessArr, Map<String, Chess> chessMap, Chess currChess) {
		int x = coord.getX();
		Chess backBoss = chessMap.get(ChessNameConstant.BACK_BOSS);
		Chess redBoss = chessMap.get(ChessNameConstant.RED_BOSS);
		Integer backBossCurrX = backBoss.getCurrX();
		Integer redBossCurrX = redBoss.getCurrX();

		Integer currX = currChess.getCurrX();
		if (backBossCurrX == redBossCurrX && backBossCurrX == currX && x != backBossCurrX) {
			Chess[] chesses = chessArr[backBossCurrX];
			Integer backBossCurrY = backBoss.getCurrY();
			Integer redBossCurrY = redBoss.getCurrY();
			List<Chess> list = list(chesses, backBossCurrY, redBossCurrY, false);
			return list.size() == 1;
		}
		return false;
	}

	/**
	 * 求绝对值
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public int abs(int a, int b) {
		return Math.abs(a - b);
	}

	/**
	 * 是否可以吃棋
	 * 
	 * @param coord
	 * @param chessArr
	 * @param chessMap
	 * @return
	 */
	public boolean captureGo(Coord coord, Chess[][] chessArr, Map<String, Chess> chessMap) {
		return this.run(coord, chessArr, chessMap);

	}

	private List<Chess> list(Chess[] chessX, int moveA, int currA, boolean lastHashChess) {
		List<Chess> chessList = new ArrayList<>();
		int min = moveA < currA ? moveA : currA;
		int max = moveA < currA ? currA : moveA;
		for (int i = min + 1; i < max; i++) {
			Chess chess = chessX[i];
			if (null != chess) {
				chessList.add(chess);
			}
		}
		if (lastHashChess) {
			Chess chess = chessX[moveA];
			if (null != chess) {
				chessList.add(chess);
			}
		}
		return chessList;
	}

	/**
	 * 没有棋子
	 * 
	 * @param chessX
	 * @param a
	 * @param b
	 * @param lastHashChess
	 *            最后一个是否有棋子
	 * @return
	 */
	public boolean isNoChess(Chess[] chessX, int moveA, int currA, boolean lastHashChess) {
		List<Chess> list = list(chessX, moveA, currA, lastHashChess);
		return NullUtil.isNull(list);
	}

	/**
	 * 获取y坐标的所有棋子
	 * 
	 * @param chessX
	 * @param y
	 * @return
	 */
	public Chess[] getChesss(Chess[][] chessX, int y) {
		Chess[] chesss = new Chess[9];

		for (int i = 0; i < chessX.length; i++) {
			Chess[] chesses = chessX[i];
			chesss[i] = chesses[y];
		}
		return chesss;

	}

	/**
	 * @return the isSelect
	 */
	public boolean isSelect() {
		return isSelect;
	}

	/**
	 * @param isSelect
	 *            the isSelect to set
	 */
	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}

	/**
	 * @param chessImg
	 *            the chessImg to set
	 */
	public void setChessImg(Image chessImg) {
		this.chessImg = chessImg;
	}

	/**
	 * @return the chessImg
	 */
	public Image getChessImg() {
		return chessImg;
	}

	/**
	 * @param chessImg
	 *            the chessImg to set
	 */
	public void chessImg(Image chessImg) {
		this.chessImg = chessImg;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 属性 1=红方;2=黑方
	 */
	private Byte type;

	/**
	 * @return the type
	 */
	public Byte getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Byte type) {
		this.type = type;
	}

	/**
	 * 走法
	 */
	public boolean run(Coord coord, Chess[][] chessArr, Map<String, Chess> chessMap) {

		return true;
	};

	/**
	 * @return the currX
	 */
	public Integer getCurrX() {
		if (null == currX) {
			return startX;
		}
		return currX;
	}

	/**
	 * @param currX
	 *            the currX to set
	 */
	public void setCurrX(Integer currX) {
		this.currX = currX;
	}

	/**
	 * @param currY
	 *            the currY to set
	 */
	public void setCurrY(Integer currY) {
		this.currY = currY;
	}

	/**
	 * @return the currY
	 */
	public Integer getCurrY() {
		if (null == currY) {
			return startY;
		}
		return currY;
	}

	/**
	 * @return the startX
	 */
	public int getStartX() {
		return startX;
	}

	/**
	 * @param startX
	 *            the startX to set
	 */
	public void setStartX(int startX) {
		this.startX = startX;
	}

	/**
	 * @return the startY
	 */
	public int getStartY() {
		return startY;
	}

	/**
	 * @param startY
	 *            the startY to set
	 */
	public void setStartY(int startY) {
		this.startY = startY;
	}

}
