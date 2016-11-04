package controller;

import javax.swing.SwingUtilities;

import view.MainPageView;
import view.StartView;

public class eProduceApp {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				MainPageView start = new MainPageView(null);
				start.frame.setVisible(true);
			}
		});
	}
}
