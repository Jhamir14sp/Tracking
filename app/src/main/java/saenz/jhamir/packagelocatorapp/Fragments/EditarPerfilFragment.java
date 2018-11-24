package saenz.jhamir.packagelocatorapp.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import saenz.jhamir.packagelocatorapp.LoginActivity;
import saenz.jhamir.packagelocatorapp.R;
import saenz.jhamir.packagelocatorapp.mysql.users;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditarPerfilFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    Button confirma,cancela;
    EditText name,mail,pwd1,pwd2;
    View vista;
    public EditarPerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        users usuario= new users();
        vista=inflater.inflate(R.layout.fragment_editar_perfil, container, false);
        confirma=vista.findViewById(R.id.confirm);
        String nombre,email;
        nombre=LoginActivity.nombres2;
        email=LoginActivity.email2;
        ((EditText)vista.findViewById(R.id.txtNombre)).setText(nombre);
        ((EditText)vista.findViewById(R.id.txtEmail)).setText(email);
        pwd1=vista.findViewById(R.id.txtPassword);
        pwd2=vista.findViewById(R.id.txtPassword2);
        name=vista.findViewById(R.id.txtNombre);
        mail=vista.findViewById(R.id.txtEmail);
        mail.setEnabled(false);
        confirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n,c,p1,p2;
                n=name.getText().toString();
                c=mail.getText().toString();
                p1=pwd1.getText().toString();
                p2=pwd2.getText().toString();
                if(n.length()==0&&p1.length()==0&&p2.length()==0){
                    Toast.makeText(getContext(),"No se realizo ninguna modificación",Toast.LENGTH_SHORT).show();
                }else if(n.length()>0&&p1.length()==0 && p2.length()==0){
                   Toast.makeText(getContext(),"Se actualizo el nombre de usuario correctamente",Toast.LENGTH_SHORT).show();
               }else if(n.length()>1&&p1.length()>1&&p2.length()>1) {
                   if (p1.equals(p2)) {
                       Toast.makeText(getContext(), "Cambios realizados satisfactoriamente", Toast.LENGTH_SHORT).show();
                   }else{
                       Toast.makeText(getContext(), "Contraseña no coinciden", Toast.LENGTH_SHORT).show();
                       }
                   }else
                {
                    Toast.makeText(getContext(), "Contraseña no coinciden", Toast.LENGTH_SHORT).show();
                }
               }

        });
        return vista;
    }



    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"Datos incorretos"+error.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {

    }
}