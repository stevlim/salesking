package com.example.koiware.salesking;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;

public class LogoutStatusView extends AppCompatActivity {

    //private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logout_status_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null){
            mAuth = FirebaseAuth.getInstance();
            Log.i("a user is logged in: ",user.getEmail());
        }
        else{
            Log.i("a user is logged in: ","fail");
        }*/

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
