package coursera.posa15;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

public class DownloadImageActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

    	Intent i = getIntent();
    	final Uri url = i.getData();
		 
		DownloaderTask downloaderTask = new DownloaderTask();
		downloaderTask.execute(url);

    }

	private class DownloaderTask extends AsyncTask<Uri, Void, Uri> {
		
		@Override
        protected void onPostExecute(Uri path) {
			Intent intent = new Intent();
			intent.putExtra(MainActivity.IMAGE_PATH, path.toString());
			setResult(RESULT_OK, intent);

			DownloadImageActivity.this.finish();
        }

		@Override
		protected Uri doInBackground(Uri ... url) {
			return Utils.downloadImage(DownloadImageActivity.this, url[0]);
		}
	}
}
