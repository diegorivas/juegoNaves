package com.example.jueguito;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Boton {
	Bitmap activo;
	Bitmap pasivo;
	int x;
	int y;
	int ancho;
	int alto;
    Boton(int x, int y, Bitmap activo , Bitmap pasivo) {
    	this.x = x;
    	this.y = y;
    	this.activo = activo;
    	this.pasivo = pasivo;
    	ancho = activo.getWidth();
    	alto = activo.getHeight();
    }
    public boolean esColision(int x2, int y2) {
        return x2 > x && x2 < x + ancho && y2 > y && y2 < y + alto;
  }
    
    public void onDraw(Canvas canvas, boolean touch) {
 	   Rect imagen = new Rect(0,0,ancho, alto);
       Rect posicion = new Rect(x, y, x + ancho, y + alto);
       if(touch)
          canvas.drawBitmap(activo, imagen, posicion, null);
       else canvas.drawBitmap(pasivo, imagen, posicion, null);
    }
}

