/**
 * Copyright(C) 2018 Hangzhou Fugle Technology Co., Ltd. All rights reserved.
 *
 */
package xin.mujun.service.impl;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import xin.mujun.model.ChessBoard;

/**
 * @since 2018年11月1日 下午10:07:22
 * @version $Id$
 * @author Administrator
 *
 */
public class MainChess {

	public static void main(String[] args) {
		ChessBoard testBoard = new ChessBoard();
		testBoard.setVisible(true);
		testBoard.setSize(1000, 1000);
		testBoard.setResizable(false);
		testBoard.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

	}

}
