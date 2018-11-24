package saenz.jhamir.packagelocatorapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import saenz.jhamir.packagelocatorapp.LoginActivity;
import saenz.jhamir.packagelocatorapp.R;
import saenz.jhamir.packagelocatorapp.mysql.users;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClienteFragment extends Fragment {
    private ListView listView;
    View vista;
    Button agregar;
    public ClienteFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista=inflater.inflate(R.layout.fragment_cliente, container, false);
       agregar=vista.findViewById(R.id.btn);
        listView=vista.findViewById(R.id.lista);
        List<String> names= new ArrayList<String>();

        ArrayAdapter<String> adapter=new ArrayAdapter<String >(getContext(),android.R.layout.simple_list_item_1,names);
        listView.setAdapter(adapter);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> names= new ArrayList<String>();
                names.add("Envio en curso\nPelota adidas\nAv. Snt. Luisa 548\nJhon Torres");
                ArrayAdapter<String> adapter=new ArrayAdapter<String >(getContext(),android.R.layout.simple_list_item_1,names);
                listView.setAdapter(adapter);
            }
        });
        return vista;
    }
}
