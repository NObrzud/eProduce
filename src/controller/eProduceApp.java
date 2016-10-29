package controller;

import javax.swing.SwingUtilities;

import view.StartView;

public class eProduceApp {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				StartView start = new StartView();
				start.frame.setVisible(true);
			}
		});
	}
}
