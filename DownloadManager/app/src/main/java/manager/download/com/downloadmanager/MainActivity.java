package manager.download.com.downloadmanager;

import android.app.DownloadManager;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DownloadManager downloadManager;
    String extention;
    String urltoDowload;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAndRequestPermissions();
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        urltoDowload = "https://firebasestorage.googleapis.com/v0/b/blood-37d51.appspot.com/o/Images%2Fcropped-195524258.jpg?alt=media&token=4804eb30-01b3-40ea-9e87-1c68071e3cdb";
        downloadFileNow("Image", urltoDowload, "appTest");

    }

    private boolean checkAndRequestPermissions() {

        int storage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();


        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    public void downloadFileNow(String fileType, String d, String fileName) {
        Uri uri = Uri.parse(d);
        DownloadManager.Request request = new DownloadManager.Request(uri);
//                set the notification
        request.setDescription("Downloading " + fileName).setTitle("Demo App");


        extention = String.valueOf(d);
        extention = extention.substring(extention.lastIndexOf(".")).substring(0, 4);


        request.setDestinationInExternalPublicDir("/bbbbbb/Files", fileName + extention);


//                make file visible by and manageable by system's download app
        request.setVisibleInDownloadsUi(true);

//                select which network, etc
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                | DownloadManager.Request.NETWORK_MOBILE);
        //after download notification will show complete
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);

//                queue the download
        downloadManager.enqueue(request);

    }
}
