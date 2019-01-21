package com.example.koiware.salesking;

        import android.support.v7.app.ActionBar;
        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.MenuItem;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

public class MenuNoticeView01 extends AppCompatActivity {
    private ActionBar mActionbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_notice_view01);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //mActionbar.hide();
/*
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
