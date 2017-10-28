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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import engineering.software.advanced.cantoolapp.connector.BuletoothConnector;
import engineering.software.advanced.cantoolapp.converter.database.Impl.DatabaseImpl;
import engineering.software.advanced.cantoolapp.converter.entity.CanSignal;
import engineering.software.advanced.cantoolapp.utils.Database;
import engineering.software.advanced.cantoolapp.utils.DatabaseItem;
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

        //init webview related things
        __initWebview();


        //wtrie dbc file
        String rootPath= getExternalFilesDir("").getAbsolutePath();

        writeDBC(rootPath);

        //Test dbc
        /*engineering.software.advanced.cantoolapp.converter.database.Database db = new DatabaseImpl();
        Set<CanSignal> list = db.searchSignalUseMessage(db.searchMessageUseId((long)856));
        System.out.println(list.size());
        for(CanSignal cs : list)
            Log.e("tag",cs.toString());*/

    }

    private void writeDBC(String rootPath) {

        //判断是否已经存在这个文件
//        File f = new File("/storage/emulated/0/Android/data/engineering.software.advanced.cantoolapp/files/canmsg-sample.dbc");
        File f = new File(rootPath,"canmsg-sample.dbc");
        if(f.exists())
            return;

        //不存在开始写入
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

            String path = rootPath + "/canmsg-sample.dbc";

            osw = new OutputStreamWriter(new FileOutputStream(new File(rootPath,"canmsg-sample.dbc")),"GBK");
            bw = new BufferedWriter(osw);
            String s = "";
            while ((s = br.readLine()) != null) {
                bw.write(s);
                bw.write('\n');
            }
            bw.flush();


            SharedPreferences sharedPreferences = this.getSharedPreferences(Database.DATABASE_FILE_NAME, Context.MODE_PRIVATE);
            Database database = new Database(sharedPreferences);

            String db_name = "默认数据库";

            String date = new SimpleDateFormat("yyyy-MM-dd HH").format(new Date());


            DatabaseItem databaseItem = new DatabaseItem(db_name, date,  true, path);
            database.add(databaseItem);
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
            webview.loadUrl(urls.get("send"));
        } else if (id == R.id.module4) {
            webview.loadUrl(urls.get("command"));
        //} else if (id == R.id.module5) {
//            webview.loadUrl(urls.get("send"));
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

//    init webview related things
    private void __initWebview() {
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
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) { //select file
                //List<String> list = data.getStringArrayListExtra(Constant.RESULT_INFO);//Constant.RESULT_INFO == "paths"

                SharedPreferences sharedPreferences = this.getSharedPreferences(Database.DATABASE_FILE_NAME, Context.MODE_PRIVATE);
                Database database = new Database(sharedPreferences);

                List<String> list = data.getStringArrayListExtra("paths");
                String path = list.get(0);
                String[] temp = path.split("/");
                String db_name = temp[temp.length - 1].substring(0, temp[temp.length -1].indexOf(".dbc"));

                DatabaseItem item = database.getOne(db_name);

                while(item != null) {
                    db_name = db_name + "(1)";
                    item = database.getOne(db_name);
                }

                String date = new SimpleDateFormat("yyyy-MM-dd HH").format(new Date());


                DatabaseItem databaseItem = new DatabaseItem(db_name, date,  database.getDefault() == null, path);
                database.add(databaseItem);

                Toast.makeText(getApplicationContext(), "添加数据库：" + db_name + "成功", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
