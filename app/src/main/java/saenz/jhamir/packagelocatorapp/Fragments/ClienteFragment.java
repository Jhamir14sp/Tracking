package saenz.jhamir.packagelocatorapp.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import saenz.jhamir.packagelocatorapp.LoginActivity;
import saenz.jhamir.packagelocatorapp.R;

import saenz.jhamir.packagelocatorapp.mysql.addpackage;
public class ClienteFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    private ListView listView;
    public static String codRastreo="";
    View vista;
    Button agregar;
    RequestQueue rq;
    JsonRequest jrq;
    RequestQueue rq2;
    JsonRequest jrq2;
    ListView listaResultado;
    private  ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    boolean fragmentTransaction=false;
    public static  String rastreo="";
    public static  String lat="";
    public static  String lng="";
    public static  String worker="";
    public static  String latW="";
    public static  String lngW="";

    Fragment fragment=null;
    public ClienteFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista=inflater.inflate(R.layout.fragment_cliente, container, false);
       agregar=vista.findViewById(R.id.btn);
        listaResultado = (ListView)vista.findViewById(R.id.lista);
        List<String>names=new ArrayList<String>();

        rq= Volley.newRequestQueue(getContext());
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1=new AlertDialog.Builder(getContext());
                View view=getLayoutInflater().inflate(R.layout.addcod,null);
                final EditText codigo= (EditText)view.findViewById(R.id.codigo);
                Button btnAdd= (Button)view.findViewById(R.id.addcodigo);
                builder1.setView(view);
                final AlertDialog dialog = builder1.create();
                dialog.show();
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!codigo.getText().toString().isEmpty()){
                            codRastreo=codigo.getText().toString();
                            cargarDatos();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        listaResultado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment newFragment = new LocalizarFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return vista;
    }

    public void cargarDatos(){
        String url ="https://diars2018upn.000webhostapp.com/login/consulta.php?cod="+codRastreo;
        jrq=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }


    @Override
    public void onResponse(JSONObject response) {
        addpackage newpackage=new addpackage();
        JSONArray jsonArray=response.optJSONArray("datos");
        JSONObject jsonObject=null;
        try{
            jsonObject=jsonArray.getJSONObject(0);
            newpackage.setCliente(jsonObject.optString("cliente"));
            newpackage.setAddress(jsonObject.optString("address"));
            newpackage.setCodRastreo(jsonObject.optString("codRastreo"));
            newpackage.setProduct(jsonObject.optString("product"));
            newpackage.setLat(jsonObject.optString("lat"));
            newpackage.setLng(jsonObject.optString("lng"));
            newpackage.setState(jsonObject.optString("state"));
            newpackage.setTrabajador(jsonObject.optString("Trabajador"));
            newpackage.setLatW(jsonObject.optString("4"));
            newpackage.setLngW(jsonObject.optString("5"));
            newpackage.setMailW(jsonObject.optString("7"));

            latW=newpackage.getLat();
            lngW=newpackage.getLng();

            lat=newpackage.getLngW();
            lng=newpackage.getLatW();
            rastreo=newpackage.getCodRastreo();
            worker=newpackage.getTrabajador();
        } catch (Exception e){
            e.printStackTrace();
        }


        Toast.makeText(getContext(), "cliente:"+lat+" "+lng, Toast.LENGTH_LONG).show();
        Toast.makeText(getContext(), "trabajador", Toast.LENGTH_SHORT).show();

        Toast.makeText(getContext(), latW+" "+lngW, Toast.LENGTH_LONG).show();
        Toast.makeText(getContext(), "Código agregado exitosamente", Toast.LENGTH_SHORT).show();
        String[]items={newpackage.getCliente(),newpackage.getMail()};
        List<String>names=new ArrayList<String >();
        names.add("Cliente: "+newpackage.getCliente()+"\n"+
                "Paquete: "+newpackage.getProduct()+"\n"+
                "Dirección: "+newpackage.getAddress()+"\n"+
                "Responsable:"+newpackage.getTrabajador());
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,names);
        listaResultado.setAdapter(adapter);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"Ingresó codigo invalido",Toast.LENGTH_LONG).show();

    }

}














