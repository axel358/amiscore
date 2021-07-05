package cu.daxyel.amiscore;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
/*import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import cu.daxyel.amiscore.MainActivity;*/
import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.Manifest;
import android.content.pm.PackageManager;
import android.content.Intent;

public class ScanQRActivity extends AppCompatActivity
{
    //private CodeScanner mCodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT > 22)
        {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
            {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 58);
            }
        }

        setContentView(R.layout.activity_scan_qr);
        /*CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
                @Override
                public void onDecoded(@NonNull final Result result)
                {
                    runOnUiThread(new Runnable() {
                            @Override
                            public void run()
                            {
                                setResult(Utils.SCAN_REQUEST_CODE, new Intent().putExtra("result", result.getText()));
                                finish();
                            }
                        });
                }
            });
        scannerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    mCodeScanner.startPreview();
                }
            });       
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause()
    {
        mCodeScanner.releaseResources();
        super.onPause();
    }*/
}}
