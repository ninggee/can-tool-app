package engineering.software.advanced.cantoolapp;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Set;

import engineering.software.advanced.cantoolapp.connector.BuletoothConnector;
import engineering.software.advanced.cantoolapp.converter.database.DataBase;
import engineering.software.advanced.cantoolapp.converter.database.Impl.DataBaseImpl;
import engineering.software.advanced.cantoolapp.converter.entity.CanSignal;
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init drawer compontent
        __initDrawer();

        //init webview and config it
        webview = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.addJavascriptInterface(new TestInterface(this, new BuletoothConnector()), "Android");

        //this is necessary or app will crash when you click a button
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.i("webviwe", "attempting to load Url: " + request.getUrl());
                return true;
            }
        });
        webview.loadUrl(urls.get("bluetooth"));


        //wtrie dbc file
        writeDBC();
        //test can delete
        DataBase db = DataBaseImpl.getInstance();
        Set<CanSignal> set = db.searchSignalUseMessage(db.searchMessageUseId((long)856));
        for(CanSignal cs : set){
            Log.e("ttt",cs.toString());
        }


    }

    private void writeDBC() {

        File f= new File("/data/data/engineering.software.advanced.cantoolapp/files/canmsg-sample.dbc");
        if(f.exists()){
            return;
        }

        try{

            AssetManager am = getResources().getAssets();
            InputStream is = am.open("canmsg-sample.dbc");

            FileOutputStream fout =openFileOutput("canmsg-sample.dbc", MODE_PRIVATE);

            byte[] buffer = new byte[is.available()];
            int byteCount = 0;
            while((byteCount=is.read(buffer))!=-1) {//循环从输入流读取 buffer字节
                fout.write(buffer, 0, byteCount);//将读取的输入流写入到输出流
            }
            fout.flush();
            is.close();
            fout.close();
        }

        catch(Exception e){
            e.printStackTrace();
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
    }

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
            webview.loadUrl(urls.get("detail"));
        } else if (id == R.id.module4) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void __initDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
}
