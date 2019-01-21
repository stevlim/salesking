package com.example.koiware.salesking;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityGroup;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.LocalActivityManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity  extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        /*android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorLightSkyBlue)));*/



        ////구글
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null){
            mAuth = FirebaseAuth.getInstance();

            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.nav_login).setTitle(user.getEmail());
            menu.findItem(R.id.nav_login_btn).setTitle("로그아웃");

            Log.i("a user is logged in: ",user.getEmail());
        }
        else{
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.nav_login).setTitle("로그인되지 않았습니다.");
            menu.findItem(R.id.nav_login_btn).setTitle("로그인");
        }


        ////상단 메뉴 설정
        TabHost tabHost1 = (TabHost) findViewById(R.id.tabHost1) ;
        tabHost1.setup() ;

        /*toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        LocalActivityManager mLocalActivityManager = new   LocalActivityManager(this, false);
        mLocalActivityManager.dispatchCreate(savedInstanceState);
        tabHost1.setup(mLocalActivityManager);


        // 첫 번째 Tab. (탭 표시 텍스트:"TAB 1"), (페이지 뷰:"content1")
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1") ;
        ts1.setContent(R.id.content1) ;
        //ts1.setContent(new Intent(this, TabView11.class)) ;
        ts1.setIndicator("할인 앱") ;
        tabHost1.addTab(ts1)  ;


        // 두 번째 Tab. (탭 표시 텍스트:"TAB 2"), (페이지 뷰:"content2")
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2") ;
        ts2.setContent(R.id.content2) ;
        //ts2.setContent(new Intent(this, TabView12.class)) ;
        ts2.setIndicator("프로모션") ;
        tabHost1.addTab(ts2) ;

        // 세 번째 Tab. (탭 표시 텍스트:"TAB 3"), (페이지 뷰:"content3")
        TabHost.TabSpec ts3 = tabHost1.newTabSpec("Tab Spec 3") ;
        ts3.setContent(R.id.content3) ;
        ts3.setIndicator("이벤트") ;
        tabHost1.addTab(ts3) ;

        // 네 번째 Tab. (탭 표시 텍스트:"TAB 4"), (페이지 뷰:"content4")
        TabHost.TabSpec ts4 = tabHost1.newTabSpec("Tab Spec 4") ;
        //ts4.setContent(R.id.content4) ;


        //로그인 유무에 따라 좋아요 화면 보여줌
        if(user!=null){
            ts4.setContent(new Intent(this, TabView41.class)) ;
        }
        else{
            ts4.setContent(new Intent(this, LogoutStatusNoBarView.class)) ;
        }

        ts4.setIndicator("좋아요") ;
        tabHost1.addTab(ts4) ;



        //첫번째 서브 탭 ****************
        /*TabHost tabHost11 = (TabHost) findViewById(R.id.tabHost11) ;
        tabHost11.setup() ;*/

        TabHost tabHost11 = (TabHost) findViewById(R.id.tabHost11) ;
        tabHost11.setup() ;

        /*toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        LocalActivityManager mLocalActivityManager11 = new   LocalActivityManager(this, false);
        mLocalActivityManager11.dispatchCreate(savedInstanceState);
        tabHost11.setup(mLocalActivityManager11);



        //tabHost11.addTab(tabHost1.newTabSpec("Tab Spec 11").setIndicator("인기순위").setContent(new Intent(this, TabView11.class)));

        TabHost.TabSpec ts11 = tabHost1.newTabSpec("Tab Spec 11") ;

        ts11.setIndicator("인기순위") ;
        //ts11.setIndicator("인기순위", getResources().getDrawable(R.drawable.ic_tab_popular)) ;


        //ts11.setContent(R.id.content11) ;
        ts11.setContent(new Intent(this, TabView11.class)) ;
        tabHost11.addTab(ts11) ;

        TabHost.TabSpec ts12 = tabHost1.newTabSpec("Tab Spec 12") ;
        //ts12.setContent(R.id.content12) ;
        ts12.setContent(new Intent(this, TabView12.class)) ;

        ts12.setIndicator("카테고리") ;
        //ts12.setIndicator("카테고리", getResources().getDrawable(R.drawable.ic_tab_category)) ;


        tabHost11.addTab(ts12) ;

        TabHost.TabSpec ts13 = tabHost1.newTabSpec("Tab Spec 13") ;
        //ts13.setContent(R.id.content13) ;
        ts13.setContent(new Intent(this, TabView13.class)) ;
        ts13.setIndicator("추천") ;
        tabHost11.addTab(ts13) ;


        //두번째 서브 탭 ************************
        //TabHost tabHost21 = (TabHost) findViewById(R.id.tabHost21) ;
        //tabHost21.setup() ;

        TabHost tabHost21 = (TabHost) findViewById(R.id.tabHost21) ;
        tabHost21.setup() ;

        /*toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        LocalActivityManager mLocalActivityManager21 = new   LocalActivityManager(this, false);
        mLocalActivityManager21.dispatchCreate(savedInstanceState);
        tabHost21.setup(mLocalActivityManager21);

        TabHost.TabSpec ts21 = tabHost1.newTabSpec("Tab Spec 21") ;
        //ts21.setContent(R.id.content21) ;
        ts21.setContent(new Intent(this, TabView21.class)) ;
        ts21.setIndicator("인기순위") ;
        tabHost21.addTab(ts21) ;

        TabHost.TabSpec ts22 = tabHost1.newTabSpec("Tab Spec 22") ;
        //ts22.setContent(R.id.content22) ;
        ts22.setContent(new Intent(this, TabView22.class)) ;
        ts22.setIndicator("카테고리") ;
        tabHost21.addTab(ts22) ;

        TabHost.TabSpec ts23 = tabHost1.newTabSpec("Tab Spec 23") ;
        //ts23.setContent(R.id.content23) ;
        ts23.setContent(new Intent(this, TabView23.class)) ;
        ts23.setIndicator("추천") ;
        tabHost21.addTab(ts23) ;


        //세번째 서브 탭 ************************
        /*TabHost tabHost31 = (TabHost) findViewById(R.id.tabHost31) ;
        tabHost31.setup() ;*/
        TabHost tabHost31 = (TabHost) findViewById(R.id.tabHost31) ;
        tabHost31.setup() ;

        /*toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        LocalActivityManager mLocalActivityManager31 = new   LocalActivityManager(this, false);
        mLocalActivityManager31.dispatchCreate(savedInstanceState);
        tabHost31.setup(mLocalActivityManager31);

        TabHost.TabSpec ts31 = tabHost1.newTabSpec("Tab Spec 31") ;
        //ts31.setContent(R.id.content31) ;
        ts31.setContent(new Intent(this, TabView31.class)) ;
        ts31.setIndicator("인기순위") ;
        tabHost31.addTab(ts31) ;

        TabHost.TabSpec ts32 = tabHost1.newTabSpec("Tab Spec 32") ;
        //ts32.setContent(R.id.content32) ;
        ts32.setContent(new Intent(this, TabView32.class)) ;
        ts32.setIndicator("카테고리") ;
        tabHost31.addTab(ts32) ;

        TabHost.TabSpec ts33 = tabHost1.newTabSpec("Tab Spec 33") ;
        //ts33.setContent(R.id.content33) ;
        ts33.setContent(new Intent(this, TabView33.class)) ;
        ts33.setIndicator("추천") ;
        tabHost31.addTab(ts33) ;


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

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorLightSkyBlue));

        ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(Color.WHITE);

        ImageView searchClose = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        searchClose.setColorFilter(Color.argb(255, 255, 255, 255));

        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Sales King 검색");

        //searchView.getQuery();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Log.i("aaaaaaaaaaaabbb", query);
                Intent intent = new Intent(getApplicationContext(), SearchListView.class);

                intent.putExtra("searchText", query);

                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        /*SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if(null != searchManager) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            Log.i("aaa", "aaaaa");
        }
        searchView.setIconifiedByDefault(true);*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_notice) {    //알림
            // Handle the camera action
            //FirebaseAuth.getInstance().signOut();
            /*FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

            if(user!=null){

                Log.i("a user is logged in: ",user.getEmail());
            }
            else{
                Log.i("Username", "there is no user");
            }*/
            //mAuth.signOut();
            Intent MenuNoticeView = new Intent(getApplicationContext(), MenuNoticeView01.class);
            startActivity(MenuNoticeView);
        } else if (id == R.id.nav_review) { //리뷰 남기기
            Intent MenuReviewView = new Intent(getApplicationContext(), MenuReviewView02.class);
            startActivity(MenuReviewView);
        } else if (id == R.id.nav_share) {  //공유
            Intent MenuShareView = new Intent(getApplicationContext(), MenuShareView03.class);
            startActivity(MenuShareView);
        } else if (id == R.id.nav_setting) {    //설정
            Intent MenuSettingView = new Intent(getApplicationContext(), MenuSettingView04.class);
            startActivity(MenuSettingView);
        } else if (id == R.id.nav_manage) { //내 앱 관리

            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

            if(user!=null){
                //mAuth = FirebaseAuth.getInstance();
                Log.i("go manage page login:", "ok");
                Intent MenuManageView = new Intent(getApplicationContext(), MenuManageView05.class);
                startActivity(MenuManageView);
            }
            else{
                Log.i("go manage page login: ","fail");
                Intent MenuManageView = new Intent(getApplicationContext(), LogoutStatusView.class);
                startActivity(MenuManageView);
            }


        } else if (id == R.id.nav_login_btn) {  //로그인, 로그아웃
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

            if(user!=null) { //로그인 상태, 로그아웃 해야하는 경우
                FirebaseAuth.getInstance().signOut();

                /*Menu menu = navigationView.getMenu();
                menu.findItem(R.id.nav_login_btn).setTitle("로그아웃");*/

                //Intent intent = new Intent(getApplicationContext(), Activity.class);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(getApplicationContext(), LoginDialogView.class);
                startActivity(intent);
                //startActivityForResult(intent, 3000);
            }




        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);



        return true;
    }








    ////구글 계정 로그인 관련 부분
    private boolean isSignedIn() {
        return GoogleSignIn.getLastSignedInAccount(this) != null;
    }



}
