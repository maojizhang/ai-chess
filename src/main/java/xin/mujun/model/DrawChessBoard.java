/**
 * Copyright(C) 2018 Hangzhou Fugle Technology Co., Ltd. All rights reserved.
 *
 */
package xin.mujun.model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;

import xin.mujun.FilePathUtil;
import xin.mujun.NullUtil;
import xin.mujun.constast.ChessNameConstant;
import xin.mujun.service.ChessInit;

/**
 * @since 2018年10月13日 下午12:46:29
 * @version $Id$
 * @author MuJun
 *
 */
public class DrawChessBoard extends JPanel implements MouseListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Image boardImg;

	private static final String CHESS_BOARD_PATH = FilePathUtil.getFilePath("image/chessboard.png");

	private ChessInit chess;

	/**
	 * 起始x
	 */
	private int boardX;
	/**
	 * 起始y
	 */
	private int boardY;

	/**
	 * 偏移量
	 */
	private int offX = 80;

	/**
	 * 被选中的棋子
	 */
	private String selectedChessName;

	/**
	 * 当前谁现行
	 */
	private Byte currType = 2;

	/**
	 * @param boardImg
	 */
	public DrawChessBoard(ChessInit chess) {
		boardImg = Toolkit.getDefaultToolkit().getImage(CHESS_BOARD_PATH);
		if (null == boardImg) {
			System.out.println("棋盘背景图片文件不存在");
		}
		addMouseListener(this);
		this.chess = chess;
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		// 背景模版图片和宽
		int imageWidth = boardImg.getWidth(this);
		int imageHeight = boardImg.getHeight(this);

		int width = getWidth();
		int height = getHeight();

		boardX = (width - imageWidth) / 2;
		boardY = (height - imageHeight) / 2;

		g.drawImage(boardImg, boardX, boardY, null);

		int XOffset = 80;
		int maxSize = 30;
		drawLine(boardX, boardY, imageWidth, imageHeight, XOffset, maxSize, g);
		drawChess(boardX, boardY, XOffset, maxSize, g);
	}

	/**
	 * 画棋子
	 * 
	 * @param x
	 * @param y
	 * @param XOffset
	 * @param maxSize
	 * @param g
	 */
	private void drawChess(int x, int y, int XOffset, int maxSize, Graphics g) {

		Map<String, Chess> chessMap = getChessMap();
		for (Entry<String, Chess> chessEntry : chessMap.entrySet()) {
			Chess chess = chessEntry.getValue();
			int x1 = x + chess.getCurrX() * XOffset;
			int y1 = y + chess.getCurrY() * XOffset;
			Image chessImg = chess.getChessImg();
			g.drawImage(chessImg, x1, y1, null);
		}

	}

	/**
	 * 画棋盘线条
	 * 
	 * @param x
	 * @param y
	 * @param imageWidth
	 * @param imageHeight
	 * @param XOffset
	 * @param maxSize
	 * @param g
	 */
	private void drawLine(int x, int y, int imageWidth, int imageHeight, int XOffset, int maxSize, Graphics g) {
		// // 横线
		for (int i = 0; i < 5; i++) {
			g.drawLine(x + maxSize, y + (i * XOffset) + maxSize, x + imageWidth - maxSize, y + (i * XOffset) + maxSize);
		}

		// 竖线
		for (int i = 0; i < 9; i++) {
			g.drawLine(x + (i * XOffset) + maxSize, y + maxSize, x + (i * XOffset) + maxSize, y + 4 * XOffset + maxSize);
		}
		// 斜线
		g.drawLine(x + maxSize + XOffset * 3, y + maxSize, x + maxSize + 5 * XOffset, y + maxSize + 2 * XOffset);

		g.drawLine(x + maxSize + XOffset * 3, y + maxSize + 2 * XOffset, x + maxSize + 5 * XOffset, y + maxSize);

		////////////////////////////// 下半部////////////////////////////////

		for (int i = 0; i < 5; i++) {
			g.drawLine(x + maxSize, y + imageHeight - (i * XOffset) - maxSize, x + imageWidth - maxSize, y + imageHeight - (i * XOffset) - maxSize);
		}

		// 竖线
		for (int i = 0; i < 9; i++) {
			g.drawLine(x + (i * XOffset) + maxSize, y + imageHeight - maxSize, x + (i * XOffset) + maxSize, y + imageHeight - 4 * XOffset - maxSize);
		}
		// // 斜线
		g.drawLine(x + maxSize + 3 * XOffset, y + imageHeight - maxSize, x + maxSize + 5 * XOffset, y + imageHeight - maxSize - 2 * XOffset);
		//
		g.drawLine(x + maxSize + 3 * XOffset, y + imageHeight - maxSize - 2 * XOffset, x + maxSize + 5 * XOffset, y + imageHeight - maxSize);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

		int clickX = e.getX();
		int clickY = e.getY();

		Chess chess = getChess(clickX, clickY);

		if (null == chess) {
			Coord coord = getCoord(clickX, clickY);
			if (coord == null || NullUtil.isNull(this.selectedChessName)) {
				return;
			}

			Map<String, Chess> chessMap = getChessMap();
			Chess selectChess = chessMap.get(selectedChessName);
			Chess[][] chessArr = getChessArr();

			boolean run = selectChess.run(coord, chessArr, chessMap);

			if (run) {
				boolean noCaptureGo = selectChess.isNoCaptureGo(coord, chessArr, chessMap, selectChess);
				if (noCaptureGo) {
					return;
				}
				chessArr[selectChess.getCurrX()][selectChess.getCurrY()] = null;
				int x = coord.getX();
				int y = coord.getY();
				selectChess.setCurrX(x);
				selectChess.setCurrY(y);

				chessArr[x][y] = selectChess;
				this.selectedChessName = null;
				exchange();
			}

		} else {
			Byte type = chess.getType();
			if (currType.equals(type)) {
				selectedChessName = chess.getName();
			} else {
				// 是否可走,可走吃掉,不可走,return
				Map<String, Chess> chessMap = getChessMap();
				Chess selectChess = chessMap.get(selectedChessName);
				if (null == selectChess) {
					return;
				}
				Coord coord = getCoord(chess);
				Chess[][] chessArr = getChessArr();
				boolean captureGo = selectChess.captureGo(coord, chessArr, chessMap);
				if (captureGo) {
					boolean noCaptureGo = selectChess.isNoCaptureGo(coord, chessArr, chessMap, selectChess);
					if (noCaptureGo) {
						return;
					}

					Integer currX = selectChess.getCurrX();
					Integer currY = selectChess.getCurrY();
					chessArr[currX][currY] = null;
					int x = coord.getX();
					int y = coord.getY();
					selectChess.setCurrX(x);
					selectChess.setCurrY(y);
					chessMap.remove(chess.getName());
					chessArr[x][y] = selectChess;
					this.selectedChessName = null;
					exchange();
				}
			}
		}
		repaint();
	}

	/**
	 * 获取坐标值
	 * 
	 * @param chess
	 * @return
	 */
	private Coord getCoord(Chess chess) {
		if (null == chess) {
			return null;
		}
		Coord coord = new Coord();
		coord.setX(chess.getCurrX());
		coord.setY(chess.getCurrY());
		return coord;
	}

	/**
	 * 换边
	 */
	private void exchange() {
		if (ChessNameConstant.BACK.equals(currType)) {
			currType = ChessNameConstant.RED;
			System.out.println("轮到红方下棋");
		} else {
			System.out.println("轮到黑方下棋");
			currType = ChessNameConstant.BACK;
		}

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {

	}

	private Map<String, Chess> getChessMap() {

		return chess.getChessMap();
	}

	/**
	 * 获取点击的坐标
	 * 
	 * @param clickX
	 * @param clickY
	 * @return
	 */
	private Coord getCoord(int clickX, int clickY) {
		int currX1 = (clickX - boardX - 30) / offX;
		int currY1 = (clickY - boardY - 30) / offX;

		int currX = (clickX - boardX - 30) % offX;
		int currY = (clickY - boardY - 30) % offX;
		int x = currX1;
		int y = currY1;
		if (15 < currX && currX < 65) {
			x = -1;
		} else if (currX >= 65) {
			x = currX1 + 1;
		}

		if (15 < currY && currY < 65) {
			y = -1;
		} else if (currY >= 65) {
			y = currY1 + 1;
		}
		if (!isValid(x, y)) {
			return null;
		}
		Coord coord = new Coord();
		coord.setX(x);
		coord.setY(y);

		return coord;
	}

	/**
	 * 获取点击位置坐标值
	 * 
	 * @param clickX
	 * @param clickY
	 * @return
	 */
	private Chess getChess(int clickX, int clickY) {
		System.out.println("点击值:x=" + clickX + "y=" + clickY);
		int currX1 = (clickX - boardX - 30) / offX;
		int currY1 = (clickY - boardY - 30) / offX;

		int currX = (clickX - boardX - 30) % offX;
		int currY = (clickY - boardY - 30) % offX;

		System.out.println("余值:x=" + currX + "y=" + currY);
		int x = currX1;
		int y = currY1;
		if (30 < currX && currX < 50) {
			x = -1;
		} else if (currX >= 50) {
			x = currX1 + 1;
		}

		if (30 < currY && currY < 50) {
			y = -1;
		} else if (currY >= 50) {
			y = currY1 + 1;
		}

		System.out.println("计算值:x=" + x + "y=" + y);
		if (!isValid(x, y)) {
			return null;
		}
		Chess[][] chessArr = getChessArr();
		return chessArr[x][y];
	}

	/**
	 * 坐标是否有效
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isValid(int x, int y) {
		if (x == -1 || y == -1) {
			return false;
		}
		if (x >= 9 || y >= 10) {
			return false;
		}
		return true;
	}

	private Chess[][] getChessArr() {
		return chess.getChessArr();
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
