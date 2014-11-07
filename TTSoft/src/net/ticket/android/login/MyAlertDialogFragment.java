package net.ticket.android.login;



import net.ticket.android.bluebamboo.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;



public  class MyAlertDialogFragment extends DialogFragment {
	public static final String TITLE = "title";
    public static final String MESSAGE = "message";

    public static MyAlertDialogFragment newInstance(String title, String message) {
        MyAlertDialogFragment frag = new MyAlertDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MyAlertDialogFragment.MESSAGE, message);     // Require ArrayList
        bundle.putString(MyAlertDialogFragment.TITLE, title);

        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	Bundle bundle = getArguments();
        String title = bundle.getString(TITLE);
        String message = bundle.getString(MESSAGE);
        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                /*.setPositiveButton(R.string.signin,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            ((LoginActivity)getActivity()).doPositiveClick();
                        }
                    }
               )*/
                .setNegativeButton(R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            ((LoginActivity)getActivity()).doNegativeClick();
                        }
                    }
                )
                .create();
    }
}
