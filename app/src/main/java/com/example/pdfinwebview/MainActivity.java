package com.example.pdfinwebview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int i = 0;

    private WebView pdfView;
    private ImageView imageViewLeft, imageViewRight;
    private ProgressBar progress;
    private String removePdfTopIcon = "javascript:(function() {" + "document.querySelector('[role=\"toolbar\"]').remove();})()";

    private String[] strArray = {
            "http://www.pdf995.com/samples/pdf.pdf",
            "https://www.antennahouse.com/XSLsample/pdf/sample-link_1.pdf",
            "http://www.africau.edu/images/default/sample.pdf",
            "http://www.lequydonhanoi.edu.vn/upload_images/S%C3%A1ch%20ngo%E1%BA%A1i%20ng%E1%BB%AF/Rich%20Dad%20Poor%20Dad.pdf"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pdfView = findViewById(R.id.pdfView);
        progress = findViewById(R.id.progress);
        imageViewLeft = findViewById(R.id.iv_previous);
        imageViewRight = findViewById(R.id.iv_next);

        showPdfFile(strArray[i]);

        imageViewLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i != 0) {
                    showPdfFile(strArray[i--]);
                } else {
                    Toast.makeText(getApplicationContext(), "You're on first PDF", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageViewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i != strArray.length) {
                    showPdfFile(strArray[i++]);
                } else {
                    Toast.makeText(getApplicationContext(), "Going to first PDF", Toast.LENGTH_SHORT).show();
                    showPdfFile(strArray[0]);
                }
            }
        });

    }

    private void showPdfFile(final String imageString) {
        showProgress();
        pdfView.invalidate();
        pdfView.getSettings().setJavaScriptEnabled(true);
        pdfView.getSettings().setSupportZoom(true);
        pdfView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + imageString);
        pdfView.setWebViewClient(new WebViewClient() {
            boolean checkOnPageStartedCalled = false;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                checkOnPageStartedCalled = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (checkOnPageStartedCalled) {
                    pdfView.loadUrl(removePdfTopIcon);
                    hideProgress();
                } else {
                    showPdfFile(imageString);
                }
            }
        });
    }

    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

}
