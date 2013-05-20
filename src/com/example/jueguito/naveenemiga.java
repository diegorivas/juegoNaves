package com.example.jueguito;

import android.graphics.Bitmap;


public class naveenemiga extends Sprite {
	private int estado;
	
	public naveenemiga(int x, int y, int estado, Bitmap bitmap) {
		
		super(x, y, bitmap);		
		this.estado = estado;
	}
	
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public int getEstado() {
		return estado;
	}

}
