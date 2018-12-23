/**
 * Copyright(C) 2018 Hangzhou Fugle Technology Co., Ltd. All rights reserved.
 *
 */
package xin.mujun.model;

import java.awt.Container;

import javax.swing.JFrame;

import xin.mujun.service.ChessInit;

/**
 * @since 2018年10月13日 下午12:43:01
 * @version $Id$
 * @author MuJun
 *
 */
public class ChessBoard extends JFrame {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private DrawChessBoard board;

	public ChessBoard() {

		ChessInit init = new ChessInit();
		board = new DrawChessBoard(init);
		setTitle("中国象棋");
		Container contentPane = getContentPane();
		contentPane.add(board);
	}

}
