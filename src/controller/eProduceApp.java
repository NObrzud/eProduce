package controller;

import javax.swing.SwingUtilities;

import view.MainPageView;
import view.StartView;

public class eProduceApp {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				eProduceDatabase db = new eProduceDatabase(); //instantiate the db once. Statically call it the rest of the time.
				StartView start = new StartView(null);
				start.frame.setVisible(true);
			}
		});
	}
}