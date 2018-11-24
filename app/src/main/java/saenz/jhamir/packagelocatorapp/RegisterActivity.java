package saenz.jhamir.packagelocatorapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{
    EditText name,email,pwd1,pwd2;
    Button register,cancel;
    ProgressDialog progreso;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=(EditText)findViewById(R.id.txtNombre);
        email=(EditText)findViewById(R.id.txtEmail);
        pwd1=(EditText)findViewById(R.id.txtPassword);
        pwd2=(EditText)findViewById(R.id.txtPassword2);
        register=(Button)findViewById(R.id.registar);
        cancel=(Button)findViewById(R.id.cancel);

        request = Volley.newRequestQueue(getBaseContext());

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom,mail,pass1,pass2;
                nom=name.getText().toString();
                mail=email.getText().toString();
                pass1=pwd1.getText().toString();
                pass2=pwd2.getText().toString();
                if(pass1.equals(pass2)){
                    //Toast.makeText(getBaseContext(),nom+" "+mail+" "+pass1+" "+pass2,Toast.LENGTH_LONG).show();
                    registarUsuario();
                }else{
                    Toast.makeText(getBaseContext(),"Contraseñas diferentes",Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getBaseContext(),nom+" "+mail+" "+pass1+" "+pass2,Toast.LENGTH_LONG).show();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getBaseContext(),"Datos incorretos"+error.toString(),Toast.LENGTH_LONG).show();
       // progreso.hide();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getBaseContext(),"Registro exitoso",Toast.LENGTH_LONG).show();
        name.setText("");
        email.setText("");
        pwd1.setText("");
        pwd2.setText("");
       //progreso.hide();
    }
    private void registarUsuario(){
       /* progreso= new ProgressDialog(getBaseContext());
        progreso.setMessage("Cargando");
        progreso.show();*/
        String url="https://diars2018upn.000webhostapp.com/login/Register.php?name="+ name.getText().toString()+"&email="+email.getText().toString()+"&pwd="+pwd1.getText().toString();

        url=url.replace(" ","%20");
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);

        request.add(jsonObjectRequest);
    }
}
