package saenz.jhamir.packagelocatorapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import saenz.jhamir.packagelocatorapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrabajadorFragment extends Fragment {


    public TrabajadorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trabajador, container, false);
    }

}
