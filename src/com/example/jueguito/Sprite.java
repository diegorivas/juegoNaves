package com.example.jueguito;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {	

	private Bitmap bmp;
	private int x = 0;
	private int y = 0;
	
	private int width;
	private int height;

	public Sprite(int x, int y, Bitmap bmp) {
		this.width = bmp.getWidth();
		this.height = bmp.getHeight();

		this.bmp = bmp;

		this.x = x;
		this.y = y;

	}



	public void onDraw(Canvas canvas) {
		Rect imagen = new Rect(0, 0, width, height);
		Rect posicion = new Rect(x, y, x + width, y + height);
		canvas.drawBitmap(bmp, imagen, posicion, null);
	}



	public boolean esColision(int x2, int y2) {
		return x2 > x && x2 < x + width && y2 > y && y2 < y + height;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getX() {
		return x;
	}

	public void setY(int y) {
		this.y = y;
	}
	public int getY() {
		return y;
	}
}