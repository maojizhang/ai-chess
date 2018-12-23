/**
 * Copyright(C) 2018 Hangzhou Fugle Technology Co., Ltd. All rights reserved.
 *
 */
package xin.mujun.service;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import xin.mujun.FilePathUtil;
import xin.mujun.constast.ChessNameConstant;
import xin.mujun.model.Chess;
import xin.mujun.model.chess.ArmsChess;
import xin.mujun.model.chess.BossChess;
import xin.mujun.model.chess.CannonChess;
import xin.mujun.model.chess.CarChess;
import xin.mujun.model.chess.ElephantChess;
import xin.mujun.model.chess.HorseChess;
import xin.mujun.model.chess.SoldierChess;

/**
 * @since 2018年11月4日 下午2:39:51
 * @version $Id$
 * @author Administrator
 *
 */
public class ChessInit implements MouseListener {

	private Map<String, Chess> chessMap = new HashMap<>();

	private Chess[][] chessArr = new Chess[9][10];

	public ChessInit() {
		try {
			init();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		setChessArr();

	}

	private void setChessArr() {

		Set<Entry<String, Chess>> entrySet = chessMap.entrySet();

		for (Entry<String, Chess> entry : entrySet) {
			Chess value = entry.getValue();
			int startX = value.getStartX();
			int startY = value.getStartY();

			chessArr[startX][startY] = value;
		}

	}

	/**
	 * 棋子初始化
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private void init() throws InstantiationException, IllegalAccessException {

		setChess(4, 0, ChessNameConstant.BOSS, 1, BossChess.class);

		setChess(0, 0, ChessNameConstant.CAR, 2, CarChess.class);

		setChess(1, 0, ChessNameConstant.HORSE, 2, HorseChess.class);

		setChess(2, 0, ChessNameConstant.ELEPHANT, 2, ElephantChess.class);

		setChess(3, 0, ChessNameConstant.SOLDIER, 2, SoldierChess.class);

		setChess(1, 2, ChessNameConstant.CANNON, 2, CannonChess.class);

		setChess(0, 3, ChessNameConstant.ARMS, 5, ArmsChess.class);
	}

	private <T extends Chess> void setChess(int startX, int startY, String name, int count, Class<T> CLS)
			throws InstantiationException, IllegalAccessException {
		for (int i = 0; i < count; i++) {
			int x = startX;
			if (count <= 2) {
				if (i == 1) {
					x = 8 - startX;
				}
			} else if (count == 5) {
				x = startX + i * 2;
			}
			setChess(x, startY, name, ChessNameConstant.BACK, CLS, i);
			setChess(x, 9 - startY, name, ChessNameConstant.RED, CLS, i);
		}

	}

	/**
	 * 设置棋子
	 * 
	 * @param startX
	 * @param startY
	 * @param name
	 * @param type
	 * @param CLS
	 * @param i
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private <T extends Chess> void setChess(int startX, int startY, String name, Byte type, Class<T> CLS, int i)
			throws InstantiationException, IllegalAccessException {
		Chess newInstance = CLS.newInstance();
		newInstance.setStartX(startX);
		newInstance.setStartY(startY);
		newInstance.setType(type);
		String filename = getFileName(type, name);
		Image chessImg = Toolkit.getDefaultToolkit().getImage(filename);
		newInstance.setChessImg(chessImg);

		name = getName(name, type, i);
		newInstance.setName(name);
		chessMap.put(name, newInstance);
	}

	/**
	 * 获取文件名称
	 * 
	 * @param type
	 * @param name
	 * @return
	 */
	private String getFileName(Byte type, String name) {
		StringBuilder sb = new StringBuilder(FilePathUtil.getFilePath());
		sb.append("image/chess/");
		if (ChessNameConstant.RED.equals(type)) {
			sb.append('红');
		} else {
			sb.append('黑');
		}
		sb.append(name).append(".jpg");
		return sb.toString();
	}

	/**
	 * 获取组装后的名称
	 * 
	 * @param name
	 * @param type
	 * @param i
	 * @return
	 */
	private String getName(String name, Byte type, int i) {
		StringBuilder sb = new StringBuilder();
		if (ChessNameConstant.RED.equals(type)) {
			sb.append("RED");
		} else {
			sb.append("BACK");
		}
		sb.append('_').append(name).append('_').append(i);
		return sb.toString();
	}

	/**
	 * @return the chessMap
	 */
	public Map<String, Chess> getChessMap() {
		return chessMap;
	}

	/**
	 * @param chessMap
	 *            the chessMap to set
	 */
	public void setChessMap(Map<String, Chess> chessMap) {
		this.chessMap = chessMap;
	}

	/**
	 * @return the chessArr
	 */
	public Chess[][] getChessArr() {
		return chessArr;
	}

	/**
	 * @param chessArr
	 *            the chessArr to set
	 */
	public void setChessArr(Chess[][] chessArr) {
		this.chessArr = chessArr;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
