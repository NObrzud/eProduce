package controller;

import javax.swing.SwingUtilities;

import view.MainPageView;

public class eProduceApp {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				MainPageView start = new MainPageView();
				start.frame.setVisible(true);
			}
		});
	}
}
