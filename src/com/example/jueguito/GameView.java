package com.example.jueguito;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

@SuppressLint("WrongCall")
public class GameView extends SurfaceView {
	private Bitmap bmp;

	private disparo balas[];
	private minave jugador;
	private naveenemiga enemigo; 
	private Boton arriba;
	private Boton abajo;
	private Boton derecha;
	private Boton izquierda;
	private Boton ataque;
	private GameLoopThread gameLoopThread;
	private SurfaceHolder holder;
	public final int MAXBALAS = 128;

	boolean touch;

	int xtouch;
	int ytouch;

	public GameView(Context context) {
		super(context);
		gameLoopThread = new GameLoopThread(this);

		balas = new disparo[MAXBALAS + 1];
		xtouch = 0;
		ytouch = 0;

		touch = false;

		bmp =crearBitmap(R.drawable.bueno);
		jugador = new minave(100, 100, bmp);
		Bitmap bmp2 = crearBitmap(R.drawable.malo);
		enemigo = new naveenemiga(10,10,1,bmp2);

		Bitmap arp  = crearBitmap(R.drawable.arribapasivo);
		Bitmap ara  = crearBitmap(R.drawable.arribaactivo);

		arriba = new Boton(500,300, ara, arp);

		Bitmap abp  = crearBitmap(R.drawable.abajopasivo);
		Bitmap aba  = crearBitmap(R.drawable.abajoactivo);


		abajo = new Boton(500,360, aba, abp);


		Bitmap dep  = crearBitmap(R.drawable.derechapasivo);
		Bitmap dea  = crearBitmap(R.drawable.derechaactivo);


		derecha = new Boton(560,300, dea, dep);

		Bitmap izp  = crearBitmap(R.drawable.izquierdapasivo);
		Bitmap iza  = crearBitmap(R.drawable.izquierdaactivo);

		izquierda =new  Boton(440,300, iza, izp);

		Bitmap pas  = crearBitmap(R.drawable.botonlibre);
		Bitmap act  = crearBitmap(R.drawable.botonpress);


		ataque = new Boton(495, 450, act, pas);
		Bitmap bmp3 = crearBitmap(R.drawable.bala);

		for(int i = 0; i <= MAXBALAS; i++)
			balas[i] = new disparo(0,0,bmp3);


		holder = getHolder();
		holder.addCallback(new Callback() {

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				gameLoopThread.setRunning(true);
				gameLoopThread.start();
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
			}
		});
	}

	public Bitmap crearBitmap(int id) {
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), id);
		return bmp;
	}
	@Override
	protected void onDraw(Canvas canvas) {

		canvas.drawColor(Color.BLACK);

		jugador.onDraw(canvas);
		muevenave();
		if (enemigo.getEstado() != 0) {
			enemigo.onDraw(canvas) ;
		} 
		mueveBalas(canvas);

		if(touch && arriba.esColision(xtouch, ytouch)) {
			arriba.onDraw(canvas, true);
			jugador.setY(jugador.getY()-10);			
		}
		else arriba.onDraw(canvas, false);

		if(touch && abajo.esColision(xtouch, ytouch)) {
			abajo.onDraw(canvas, true);
			jugador.setY(jugador.getY()+10);			
		}
		else abajo.onDraw(canvas, false);

		if(touch && derecha.esColision(xtouch, ytouch)) {
			derecha.onDraw(canvas, true);
			jugador.setX(jugador.getX() + 10);			
		}
		else derecha.onDraw(canvas, false);

		if(touch && izquierda.esColision(xtouch, ytouch)) {
			izquierda.onDraw(canvas, true);
			jugador.setX(jugador.getX() - 10);			
		}
		else izquierda.onDraw(canvas, false);

		if(touch && ataque.esColision(xtouch, ytouch)) {
			ataque.onDraw(canvas, true);
			creadisparo();			
		}
		else ataque.onDraw(canvas, false);
	}
	public void muevenave() { 
	
		if (enemigo.getEstado() == 1) { 

			enemigo.setX(enemigo.getX()+10); 
			if (enemigo.getX()>600) enemigo.setEstado(2); 
		} 
		
		if (enemigo.getEstado() == 2) { 
			enemigo.setX(enemigo.getX()-10); 
			if (enemigo.getX()<40) enemigo.setEstado(1); 
		} 
	} 
	public void mueveBalas(Canvas canvas) {
		int i; 
		for (i=0 ; i<=MAXBALAS ; i++) { 
			
			if (balas[i].getX() != 0) { 
				balas[i].setY(balas[i].getY() -5); 

				balas[i].onDraw(canvas);
				if(enemigo.esColision(balas[i].getX(), balas[i].getY()))
					enemigo.setEstado(0);
				} 
		} 
	}

	void creadisparo() { 
		int libre=-1; 
		
		for (int i=0 ; i<=MAXBALAS ; i++) { 
			if (balas[i].getX()==0) 
				libre=i; 
		} 
		
		if (libre>=0) { 
			balas[libre].setX(jugador.getX() + 30); 
			balas[libre].setY(jugador.getY()-15); 
		} 


	} 
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		xtouch = (int) event.getX();
		ytouch = (int) event.getY();
		int action = event.getAction();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			Log.e("TouchEven ACTION_DOWN", "Se toco la pantalla ");
			touch = true;
			break;
		case MotionEvent.ACTION_MOVE:
			touch = true;
			Log.e("TouchEven ACTION_MOVE", "Nos desplazamos por la pantalla ");
			break;

		case MotionEvent.ACTION_UP:		
			touch = false;
			Log.e("TouchEven ACTION_UP", "Ya no tocamos la pantalla");
			break;
		case MotionEvent.ACTION_CANCEL:
			touch = false;
			Log.e("TouchEven ACTION_CANCEL", " ");
			break;
		case MotionEvent.ACTION_OUTSIDE:
			Log.e("TouchEven ACTION_OUTSIDE", " ");
			touch = false;
			break;
		default:
		}	
		return true;
	}

}
