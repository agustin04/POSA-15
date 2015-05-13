package coursera.posa15;


import coursera.posa15.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {


    private static final int DOWNLOAD_IMAGE_REQUEST = 1;
    private static final int FILTER_IMAGE_REQUEST = 2;
    public static final String IMAGE_PATH = "path";

    private EditText mUrlEditText;

    private Uri mDefaultUrl =
        Uri.parse("http://www.zelda.com/universe/_img/gallery/minish_wp4_800.jpg");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

		setContentView(R.layout.main_activity);

		mUrlEditText = (EditText) findViewById(R.id.url);

    }

    public void downloadImage(View view) {
        try {
            // Hide the keyboard.
            hideKeyboard(this,
                         mUrlEditText.getWindowToken());

            Uri url = getUrl();
            if (url == null) return;

        	Intent i = makeDownloadImageIntent(url);
            startActivityForResult(i, DOWNLOAD_IMAGE_REQUEST);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        if (resultCode == RESULT_OK) {
        	String path = data.getStringExtra(IMAGE_PATH);

            if (requestCode == DOWNLOAD_IMAGE_REQUEST) {
            	try {
	            	Intent i = makeFilterIntent(path);
	            	startActivityForResult(i, FILTER_IMAGE_REQUEST);
            	} catch (Exception e) {
            		Toast.makeText(this,
            				"Invalid image format",
            					Toast.LENGTH_SHORT).show();
            		
            	}
            }else if(requestCode == FILTER_IMAGE_REQUEST){
            	Intent i = makeGalleryIntent(path);

            	startActivity(i);
            }
        }
        else{
    		Toast.makeText(this,
                   "An unexpected error have occurred",
                   	Toast.LENGTH_SHORT).show();
        }
    }    

    private Intent makeGalleryIntent(String pathToImageFile) {

    	Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + pathToImageFile), "image/*");
    			 
        return i;

    }
    
    private Intent makeFilterIntent(String imagePath) {

    	Intent i = new Intent(this, ImageFilterActivity.class);
        i.setData(Uri.parse(imagePath));
    			 
        return i;

    }

    private Intent makeDownloadImageIntent(Uri url) {

    	Intent i = new Intent();
    	i.setAction(Intent.ACTION_WEB_SEARCH);
    	i.setData(url);
        i.putExtra(IMAGE_PATH, url.toString());

        return i;
    }

    protected Uri getUrl() {
        Uri url = null;

        url = Uri.parse(mUrlEditText.getText().toString());

        String uri = url.toString();
        if (uri == null || uri.equals(""))
            url = mDefaultUrl;

        if (URLUtil.isValidUrl(url.toString()))
            return url;
        else {
            Toast.makeText(this,
                           "Invalid URL",
                           Toast.LENGTH_SHORT).show();
            return null;
        } 
    }


    public void hideKeyboard(Activity activity,
                             IBinder windowToken) {
        InputMethodManager mgr =
            (InputMethodManager) activity.getSystemService
            (Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken,
                                    0);
    }
}
