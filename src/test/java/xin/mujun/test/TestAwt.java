/**
 * Copyright(C) 2018 Hangzhou Fugle Technology Co., Ltd. All rights reserved.
 *
 */
package xin.mujun.test;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @since 2018年10月13日 下午12:11:11
 * @version $Id$
 * @author MuJun
 *
 */
public class TestAwt {

	public static void main(String[] args) {

		Frame fram = new Frame("test窗口");

		Button button = new Button("click me");
		button.setForeground(Color.blue);

		TextField field = new TextField();

		fram.add(field, BorderLayout.CENTER);
		fram.add(button, BorderLayout.SOUTH);
		fram.setVisible(true);
		fram.pack();

		fram.addWindowListener(new WindowAdapter() {// 使用窗口关闭命令

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

		});

		button.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {// 鼠标放开
				field.setText("放开");
			}

			@Override
			public void mousePressed(MouseEvent e) {// 鼠标按下
				field.setText("按下");
			}

			@Override
			public void mouseExited(MouseEvent e) {// 鼠标移除
				field.setText("移除");
			}

			@Override
			public void mouseEntered(MouseEvent e) {// 鼠标移入

				field.setText("移入");
			}

			@Override
			public void mouseClicked(MouseEvent e) {// 鼠标点击
				// field.setText("点击");
			}
		});

	}

}
