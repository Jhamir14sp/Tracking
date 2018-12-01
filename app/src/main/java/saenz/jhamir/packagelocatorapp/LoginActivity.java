package saenz.jhamir.packagelocatorapp;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import saenz.jhamir.packagelocatorapp.Objetos.FirebaseReferences;
import saenz.jhamir.packagelocatorapp.Objetos.coche;
import saenz.jhamir.packagelocatorapp.mysql.users;

public class LoginActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    RequestQueue rq;
    JsonRequest jrq;
    Button login,cancel;
    EditText email,pass;
    public static String nombres2="";
    public static String email2="";
    public static String password2="";
    private final int DURACION_SPLASH = 3000;
    public static int veces=0;
    FirebaseAuth.AuthStateListener mAuthListener;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=(Button)findViewById(R.id.login);
        cancel=(Button)findViewById(R.id.cancel);
        email= (EditText)findViewById(R.id.txtEmail);
        pass= (EditText)findViewById(R.id.txtPassword);
        rq= Volley.newRequestQueue(getBaseContext());
        login.setEnabled(true);
        veces=0;
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(veces<4) {
                    iniciarSesion();
                    veces = veces + 1;
                }else{
                    Toast.makeText(getBaseContext(),"Espere un momento",Toast.LENGTH_LONG).show();
                    login.setEnabled(false);

                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getBaseContext(),"Ingresó datos incorrectos intente nuevamente",Toast.LENGTH_LONG).show();
        Toast.makeText(getBaseContext(),""+error,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        users usuario=new users();
        JSONArray jsonArray=response.optJSONArray("datos");
        JSONObject jsonObject=null;
        try{
            jsonObject=jsonArray.getJSONObject(0);
            usuario.setName(jsonObject.optString("name"));
            usuario.setEmail(jsonObject.optString("email"));
            usuario.setType(jsonObject.optString("type"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Toast.makeText(getBaseContext(), "Bienvenido: " + usuario.getName(), Toast.LENGTH_SHORT).show();
        nombres2=usuario.getName();
        email2=usuario.getEmail();

        int t= Integer.parseInt(usuario.getType());
        if(t==2) {
            Intent intent = new Intent(LoginActivity.this, MenuClienteActivity.class);
            intent.putExtra(MenuClienteActivity.nombres,usuario.getEmail());
            startActivity(intent);
        }else{
            Intent intent = new Intent(LoginActivity.this, MenuTrabajadorActivity.class);
            startActivity(intent);
        }
    }
    private void iniciarSesion(){
        String url="https://diars2018upn.000webhostapp.com/login/sesion.php?user="+ email.getText().toString()+"&pwd="+pass.getText().toString();
        jrq=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }
}
