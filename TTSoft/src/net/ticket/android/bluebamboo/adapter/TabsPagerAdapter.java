package net.ticket.android.bluebamboo.adapter;

import net.ticket.android.bluebamboo.DepensesFragment;
import net.ticket.android.bluebamboo.RapportFragment;
import net.ticket.android.bluebamboo.TicketsFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			
			return new TicketsFragment();
		case 1:
			
			return new DepensesFragment();	
		case 2:
			return new RapportFragment();
			
			
			
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
