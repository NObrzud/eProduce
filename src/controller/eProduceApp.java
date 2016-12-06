package controller;

import javax.swing.SwingUtilities;

import model.User;
import view.StartView;
//simple main method to run the app
public class eProduceApp {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				new eProduceDatabase(); //create a static db
				StartView start = new StartView(new User());
				start.frame.setVisible(true);
			}
		});
	}
}