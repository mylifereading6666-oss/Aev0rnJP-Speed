package jp.aev0rnjp.speed;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final String HOME_URL =
            "https://mylifereading6666-oss.github.io/Aev0rnJP-Speed/";
    private WebView webView;
    private LinearLayout loadingView;
    private ProgressBar progressBar;
    private boolean pageFailed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.rgb(8, 11, 18));

        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setMax(100);
        root.addView(progressBar, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, dp(3)));

        webView = new WebView(this);
        webView.setBackgroundColor(Color.rgb(8, 11, 18));
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                progressBar.setVisibility(newProgress >= 100 ? View.GONE : View.VISIBLE);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request,
                                        WebResourceError error) {
                if (request.isForMainFrame()) showErrorPage();
            }

            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description,
                                        String failingUrl) {
                if (failingUrl != null && failingUrl.equals(view.getUrl())) showErrorPage();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
            }
        });

        root.addView(webView, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));
        setContentView(root);

        if (savedInstanceState != null && webView.restoreState(savedInstanceState) != null) {
            return;
        }
        loadHome();
    }

    private void loadHome() {
        pageFailed = false;
        progressBar.setVisibility(View.VISIBLE);
        webView.loadUrl(HOME_URL);
    }

    private void showErrorPage() {
        if (pageFailed) return;
        pageFailed = true;
        String html = "<!doctype html><html lang='ja'><meta charset='utf-8'>"
                + "<meta name='viewport' content='width=device-width,initial-scale=1'>"
                + "<body style='margin:0;background:#080b12;color:#f5f7ff;font-family:sans-serif;"
                + "display:flex;min-height:100vh;align-items:center;justify-content:center;text-align:center'>"
                + "<div style='padding:28px'><h2>Aev0rnJP SPEED</h2>"
                + "<p>ページを読み込めませんでした。</p>"
                + "<p style='color:#96a2b8'>インターネット接続を確認して、もう一度お試しください。</p>"
                + "<button onclick='location.href="" + HOME_URL + ""' "
                + "style='padding:14px 22px;border:0;border-radius:24px;background:#6ee7ff;font-weight:bold'>再読み込み</button>"
                + "</div></body></html>";
        webView.loadDataWithBaseURL(HOME_URL, html, "text/html", "UTF-8", null);
        progressBar.setVisibility(View.GONE);
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (webView != null) webView.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
