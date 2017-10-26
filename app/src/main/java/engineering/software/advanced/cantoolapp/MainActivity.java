package engineering.software.advanced.cantoolapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;

import engineering.software.advanced.cantoolapp.connector.BuletoothConnector;
import engineering.software.advanced.cantoolapp.webinterfaces.TestInterface;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //    webview instance on main_layout
    private WebView webview = null;

    // html files
    private static final HashMap<String, String> urls = new HashMap<>();

    // init hashmap on start
    static {
        urls.put("bluetooth", "file:///android_asset/html/bluetooth.html");
        urls.put("detail", "file:///android_asset/html/detail.html");
        urls.put("messages", "file:///android_asset/html/messages.html");
        urls.put("command", "file:///android_asset/html/command.html");
        urls.put("meter", "file:///android_asset/html/meter.html");
        urls.put("send", "file:///android_asset/html/send.html");
        urls.put("database","file:///android_asset/html/database.html");
    }

    private SharedPreferences sharedPreferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init drawer compontent
        __initDrawer();

        //init sharedpreference which stores can settings
        sharedPreferences = this.getSharedPreferences("engineering.software.advanced.cantoolapp.can_setting", Context.MODE_PRIVATE);

        //init webview and config it
        webview = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webview.addJavascriptInterface(new TestInterface(this, new BuletoothConnector(), sharedPreferences), "Android");

        //this is necessary or app will crash when you click a button
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String newUrl = request.getUrl().toString();
                String oldUrl = webview.getUrl().toString();
                if (!newUrl.equals(oldUrl) && !newUrl.equals(oldUrl + "?")) {
                    webview.loadUrl(request.getUrl().toString());
                }
                Log.i("webviwe", "attempting to load Url: " + request.getUrl());
                return true;
            }
        });

        webview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            if(webview.canGoBack()) {
                                webview.goBack();
                                return true;
                            }
                            break;
                    }
                }
                return false;
            }
        });
        webview.loadUrl(urls.get("bluetooth"));

        File s= getExternalFilesDir("");
        //wtrie dbc file
        writeDBC();


    }

    private void writeDBC() {


        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try{

            AssetManager am = getResources().getAssets();
            is = am.open("canmsg-sample.dbc");
            isr = new InputStreamReader(is,"GBK");
            br = new BufferedReader(isr);

            osw = new OutputStreamWriter(new FileOutputStream(new File("/storage/emulated/0/Android/data/engineering.software.advanced.cantoolapp/files/canmsg-sample.dbc")),"GBK");
            bw = new BufferedWriter(osw);
            String s = "";
            while ((s = br.readLine()) != null) {
                bw.write(s);
                bw.write('\n');
            }
            bw.flush();

            /*byte[] buffer = new byte[is.available()];
            int byteCount = 0;
            while((byteCount=is.read(buffer))!=-1) {//循环从输入流读取 buffer字节
                Log.e("tag",String.valueOf(byteCount));
                fout.write(buffer, 0, byteCount);//将读取的输入流写入到输出流
            }*/
        }

        catch(Exception e){
            e.printStackTrace();
        }finally {

            try {
                br.close();
                isr.close();
                is.close();
                bw.close();
                osw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    //删除右上角
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // webview is not init
        if(webview == null) {
            return false;
        }

        if (id == R.id.nav_module1) {
            webview.loadUrl(urls.get("bluetooth"));
        } else if (id == R.id.nav_module2) {
            webview.loadUrl(urls.get("messages"));
        } else if (id == R.id.nav_module3) {
            webview.loadUrl(urls.get("meter"));
        } else if (id == R.id.module4) {
            webview.loadUrl(urls.get("command"));
        } else if (id == R.id.module5) {
            webview.loadUrl(urls.get("send"));
        } else if (id == R.id.nav_send) {
            webview.loadUrl(urls.get("database"));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void __initDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                //List<String> list = data.getStringArrayListExtra(Constant.RESULT_INFO);//Constant.RESULT_INFO == "paths"
                List<String> list = data.getStringArrayListExtra("paths");
                Toast.makeText(getApplicationContext(), "选中了" + list.size() + "个文件", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
