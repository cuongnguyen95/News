package cuongd13.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailNewActivity extends AppCompatActivity {
    TextView edtTitle ,edtNgaytao ,edtContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_new);

        edtTitle = (TextView)findViewById(R.id.edtTitle);
        edtNgaytao = (TextView)findViewById(R.id.edtNgaytao);
        edtContent =(TextView)findViewById(R.id.edtContent);


        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("dulieu");
        edtTitle.setText(bundle.getString("title"));
        edtNgaytao.setText(bundle.getString("date"));
        edtContent.setText(bundle.getString("content"));

    }
}
