import java.awt.Color;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GameOfLife {

	@SuppressWarnings("unchecked")
	public GameOfLife() {
		box = new BlockingQueue[N][N];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				box[i][j] = new LinkedBlockingQueue<>(100);
			}
		}
		this.grid = new int[N][N];

	}

	private class msg {
		boolean status;
		int index;
	}

	class Node extends Thread {
		public Node(int i, int j) {
			this.i = i;
			this.j = j;

			if (i == 0 && j == 0)
				status = true;

			neighbours = new msg[2][8];
			num = new int[2];
			m = new msg();
		}

		@Override
		public void run() {
			num[0] = 0;
			num[1] = 0;

			for (int k = 1; k <= numGenerations; k++) {
				m = new msg();

				m.status = status;
				m.index = k;

				for (int p = xStart(i); p <= xEnd(i); p++) {
					for (int q = yStart(j); q <= yEnd(j); q++) {
						if (p != i || q != j) {
							try {
								box[p][q].put(m);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}

				while (num[k % 2] < numOfNeighbors(i, j)) {
					try {
						m = box[i][j].take();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					neighbours[m.index % 2][num[m.index % 2]] = m;
					num[m.index % 2] = num[m.index % 2] + 1;
				}

				num[k % 2] = 0;
				calculateState(k);

				try {
					sleep(100);
				} catch (InterruptedException e) {
				}
			}

			super.run();
		}

		private void calculateState(int k) {
			int num = 0;
			for (int index = 0; index < numOfNeighbors(i, j); index++) {
				if (neighbours[k % 2][index].status)
					num++;
			}

			if (status && (num < 2 || num > 3))
				status = false;
			else if (!status && num == 3)
				status = true;

			grid[i][j] = !status ? 0 : k;
		}

		private int numOfNeighbors(int i, int j) {
			return (xEnd(i) - xStart(i) + 1) * (yEnd(j) - yStart(j) + 1) - 1;
		}

		private int yEnd(int j) {
			return j == N - 1 ? j : j + 1;
		}

		private int yStart(int j) {
			return j == 0 ? j : j - 1;
		}

		private int xEnd(int i) {
			return i == N - 1 ? i : i + 1;
		}

		private int xStart(int i) {
			return i == 0 ? i : i - 1;
		}

		private final int i, j;

		private boolean status = new Random().nextBoolean();
		private msg[][] neighbours;
		private int[] num;
		private msg m;
	}

	static final int numGenerations = 10000;
	static final int N = 100;

	private final BlockingQueue<msg>[][] box;
	final int[][] grid;

}
