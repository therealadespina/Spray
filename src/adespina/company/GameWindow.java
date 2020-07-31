package adespina.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GameWindow extends JFrame {

    private static GameWindow game_window;
    private static long last_frame_time;
    private static Image background;
    private static Image game_over;
	private static Image school;
	private static float school_left = 200;
	private static float school_top = -400;
	private static float school_v = 200;
	private static int score;


    public static void main(String[] args) throws IOException {
    	background = ImageIO.read(GameWindow.class.getResourceAsStream("background.jpg"));
		game_over = ImageIO.read(GameWindow.class.getResourceAsStream("game_over.png"));
		school = ImageIO.read(GameWindow.class.getResourceAsStream("school.png"));
        game_window = new GameWindow();
	    game_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    game_window.setLocation(200, 100);
	    game_window.setSize(906, 478);
	    game_window.setResizable(false);
	    last_frame_time = System.nanoTime();
	    GameField game_field = new GameField();
	    game_field.addMouseListener(new MouseAdapter () {
	    	@Override
			public void mousePressed(MouseEvent e) {
	    		int x = e.getX();
	    		int y = e.getY();
	    		float school_right = school_left + school.getWidth(null);
	    		float school_bottom = school_top + school.getHeight(null);
	    		boolean is_school = x >= school_left && x <= school_right && y >= school_top && y <= school_bottom;
	    		if (is_school) {
	    			school_top = -100;
	    			school_left = (int) (Math.random() * (game_field.getWidth() - school.getWidth(null)));
	    			school_v = school_v + 20;
	    			score ++;
	    			game_window.setTitle("Score: " + score);
				}
			}
	    });
	    game_window.add(game_field);
	    game_window.setVisible(true);
    }
    private static void onRepaint(Graphics g) {
    	long current_time = System.nanoTime();
    	float delta_time = (current_time - last_frame_time) * 0.000000001f;
    	last_frame_time = current_time;

    	school_top = school_top + school_v * delta_time;
		g.drawImage(background, 0,0, null);
		g.drawImage(school, (int) school_left, (int) school_top, null);
		if (school_top > game_window.getHeight())
			g.drawImage(game_over, 0,0, null);

	}
	private static class GameField extends  JPanel {
    	@Override
		protected  void paintComponent(Graphics g) {
    		super.paintComponent(g);
    		onRepaint(g);
    		repaint();
		}

	}
}
