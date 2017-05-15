package io.battleball.core.rooms;

public class MapModel {
	public int height = 14;
	public int width = 9;
	public int doorX = 4;
	public int doorY = 0;

	public int[][] getLayer() {
		int[][] matrix = new int[width][height];
		for (int i = 1; i < width; i++)
        {
            for (int j = 1; j < height; j++)
            {
                matrix[i][j] = 1;
            }
        }
        matrix[doorX][doorY] = 1;
        return matrix;
	}
}
