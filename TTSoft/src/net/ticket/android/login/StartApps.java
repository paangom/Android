package net.ticket.android.login;



import net.ticket.android.bluebamboo.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class StartApps extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(StartApps.this, LoginActivity.class);
				startActivity(intent);
				StartApps.this.finish();
			}
		}, 4000);
	}
	

}
