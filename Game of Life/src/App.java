import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Transient;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class App extends JPanel {

	private int[][] grid;

	public App(int[][] grid) {
		this.grid = grid;
	}

	@Override
	@Transient
	public Dimension getPreferredSize() {
		return new Dimension(grid.length * 8, grid[0].length * 8);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Color gColor = g.getColor();

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				Color color;
				float k = (float) grid[i][j] / GameOfLife.numGenerations;
				if (k == 0)
					color = Color.WHITE;
				else
					color = new Color(k, 0, 1 - k);
				g.setColor(color);
				g.fillRect(j * 8, i * 8, 8, 8);
			}
		}

		g.setColor(gColor);
	}

	public static void main(String[] args) {
		GameOfLife g = new GameOfLife();
		for (int i = 0; i < GameOfLife.N; i++) {
			for (int j = 0; j < GameOfLife.N; j++) {
				g.new Node(i, j).start();
			}
		}

		App c = new App(g.grid);

		JFrame frame = new JFrame();
		frame.getContentPane().add(c);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
		new Timer(1, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				c.repaint();
			}

		}).start();

	}
}