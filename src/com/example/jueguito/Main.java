package com.example.jueguito;

import android.os.Bundle;
import android.app.Activity;

public class Main extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new GameView(this));
	}


}
