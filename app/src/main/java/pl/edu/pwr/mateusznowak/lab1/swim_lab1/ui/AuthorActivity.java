package pl.edu.pwr.mateusznowak.lab1.swim_lab1.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.OnClick;
import pl.edu.pwr.mateusznowak.lab1.swim_lab1.R;

public class AuthorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);
        setTitle(R.string.author_name);
    }

    @OnClick(R.id.fab_GitHub)
    public void onGitHubFabClick(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.github.com/nowakprojects"));
        startActivity(browserIntent);
    }

}
