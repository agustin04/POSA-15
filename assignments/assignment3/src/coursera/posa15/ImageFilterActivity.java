package coursera.posa15;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

public class ImageFilterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);

    	Intent i = getIntent();
    	final Uri url = i.getData();

		FilterTask downloaderTask = new FilterTask();
		downloaderTask.execute(url);
		
    }

private class FilterTask extends AsyncTask<Uri, Void, Uri> {
		
		@Override
        protected void onPostExecute(Uri path) {
			Intent intent = new Intent();
			intent.putExtra(MainActivity.IMAGE_PATH, path.toString());
			setResult(RESULT_OK, intent);

			ImageFilterActivity.this.finish();
        }

		@Override
		protected Uri doInBackground(Uri ... url) {
			return Utils.grayScaleFilter(ImageFilterActivity.this, url[0]);
		}
	}
}
