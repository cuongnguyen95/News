package cuongd13.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import cuongd13.news.Async.AsyncTaskGetJson;
import cuongd13.news.Model.User;

public class LoginActivity extends Activity {
    ArrayList<User> listUser;
    private String dulieu;
    ListView lv;
    EditText edtUser, edtPassword;
    Button btnLogin;
    User UserLogin;
    String a, b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("hello","helllo") ;
        Log.d("hello","branch02") ;
        Log.d("cuong" , "cuong");
        String b ;

        setContentView(R.layout.activity_login);
        listUser = new ArrayList<>();

        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        AsyncTaskGetJson getJsonLogin = new AsyncTaskGetJson();
        getJsonLogin.execute("" +
                "");
        try {
            dulieu = getJsonLogin.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        CustomUser(dulieu);

    }

    private void CustomUser(String str) {
        try {
            JSONArray jsonArray = new JSONArray(str);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                User user = new User();
                user.setUser(jsonObject.getString("username"));
                user.setPass(jsonObject.getString("password"));
                listUser.add(user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void Login(View v) {
        a = edtUser.getText().toString();
        b = edtPassword.getText().toString();
        boolean value = TestLogin(a,b);
        if(value){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else
            Toast.makeText(this, "Mat khau hoac tai khoan sai", Toast.LENGTH_SHORT).show();


    }
    private boolean TestLogin(String user, String password){
        for (int j = 0; j < listUser.size(); j++) {
            if (user.equals(listUser.get(j).getUser().toString())  && password.equals(listUser.get(j).getPass().toString())) {
                return true;
            }
        }
        return false;
    }
}
