package vandy.mooc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

/**
 * An Activity that downloads an image, stores it in a local file on
 * the local device, and returns a Uri to the image file.
 */
public class DownloadImageActivity extends Activity {
    /**
     * Debugging tag used by the Android logger.
     */
    private final String TAG = getClass().getSimpleName();
	private Context mContext;

    /**
     * Hook method called when a new instance of Activity is created.
     * One time initialization code goes here, e.g., UI layout and
     * some class scope variable initialization.
     *
     * @param savedInstanceState object that contains saved state information.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Always call super class for necessary
        // initialization/implementation.
        // @@ TODO -- you fill in here.
    	super.onCreate(savedInstanceState);

        // Get the URL associated with the Intent data.
        // @@ TODO -- you fill in here.
    	Intent i = getIntent();
    	final Uri url = i.getData();
    	
        // Download the image in the background, create an Intent that
        // contains the path to the image file, and set this as the
        // result of the Activity.

        // @@ TODO -- you fill in here using the Android "HaMeR"
        // concurrency framework.  Note that the finish() method
        // should be called in the UI thread, whereas the other
        // methods should be called in the background thread.  See
        // http://stackoverflow.com/questions/20412871/is-it-safe-to-finish-an-android-activity-from-a-background-thread
        // for more discussion about this topic.

		mContext = this; 

		Thread t = new Thread(new Runnable() {
		    public void run() {
		    	try {
		    		Uri path = DownloadUtils.downloadImage(mContext, url);

					// Call the intent to pass the obtained path
					Intent intent = new Intent();
					intent.putExtra("path", path);
					setResult(RESULT_OK, intent);
				    		
					// Finish the activity in UIThread
					runOnUiThread(new Runnable() {
		                @Override
		                public void run() {
		                	finish();
		                }
		            });

		    	} catch (Exception e) {
		    		e.printStackTrace();
		    	}
		    }
		});

		t.start();
		
		//Using Async Task wasn't allow for this assignment 
		//DownloaderTask downloaderTask = new DownloaderTask();
		//downloaderTask.execute(url);

    }

//	private class DownloaderTask extends AsyncTask<Uri, Void, Uri> {
//		
//		// Executed after we downloaded the image
//		@Override
//        protected void onPostExecute(Uri path) {
//			// Call the intent to pass the obtained path
//			Intent intent = new Intent();
//			intent.putExtra("path", path);
//			setResult(RESULT_OK, intent);
//
//			// To finish the activity. It's
//			// not needed anymore
//			DownloadImageActivity.this.finish();
//        }
//
//		// Executing in background thread
//		@Override
//		protected Uri doInBackground(Uri ... url) {
//			return DownloadUtils.downloadImage(mContext, url[0]);
//		}
//	}
}
