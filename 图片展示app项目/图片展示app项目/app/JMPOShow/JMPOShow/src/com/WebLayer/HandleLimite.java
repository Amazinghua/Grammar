package com.WebLayer;

import com.BusinessLayer.CheckLoadImg;
import com.jmposhow.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class HandleLimite extends Activity {

	private ImageView logo;
	private Button openlimite;
	private Button closelimite;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// »•µÙ±ÍÃ‚¿∏
		setContentView(R.layout.handle_limite);
		setupview();
	}

	public void setupview() {
		logo = (ImageView) this.findViewById(R.id.logo);
		logo.setImageResource(R.drawable.logobehind);
		openlimite = (Button) this.findViewById(R.id.openset);
		closelimite = (Button) this.findViewById(R.id.closeset);
		openlimite.setVisibility(View.GONE);
		closelimite.setVisibility(View.GONE);
		CheckLoadImg cli = new CheckLoadImg(HandleLimite.this);
		boolean value = cli.checkalwayrun();
		if (value) {
			openlimite.setVisibility(View.VISIBLE);
		} else {
			closelimite.setVisibility(View.VISIBLE);
		}
		openlimite.setOnClickListener(new openlimited());
		closelimite.setOnClickListener(new closelimited());

	}

	public class openlimited implements OnClickListener {
		@Override
		public void onClick(View v) {
			CheckLoadImg cli = new CheckLoadImg(HandleLimite.this);
			cli.changewayrun();
			openlimite.setVisibility(View.GONE);
			closelimite.setVisibility(View.VISIBLE);
		};
	}

	public class closelimited implements OnClickListener {
		@Override
		public void onClick(View v) {
			CheckLoadImg cli = new CheckLoadImg(HandleLimite.this);
			cli.changewayrun();
			openlimite.setVisibility(View.VISIBLE);
			closelimite.setVisibility(View.GONE);
		};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.handle_limite, menu);
		return true;
	}

}
