package pl.edu.pwr.mateusznowak.lab1.swim_lab1.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.OnClick;
import pl.edu.pwr.mateusznowak.lab1.swim_lab1.R;

public class AuthorActivity extends AppCompatActivity {

    private FloatingActionButton gitHubFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);
        setTitle(R.string.author_name);

        gitHubFab= (FloatingActionButton) findViewById(R.id.fab_GitHub);
        
        gitHubFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGitHubFabClick();
            }
        });
    }

    private void onGitHubFabClick(){
        Log.d("Button","Clicked");
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.github.com/nowakprojects"));
        startActivity(browserIntent);
    }

}
