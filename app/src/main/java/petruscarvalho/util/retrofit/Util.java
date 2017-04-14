package petruscarvalho.util.retrofit;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by petruscarvalho on 14/04/17.
 */

public class Util {

    public static ProgressDialog retornaProgressDialog(Activity activity){
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setTitle("Loading");

        return progressDialog;
    }
}
