package saenz.jhamir.packagelocatorapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import saenz.jhamir.packagelocatorapp.Fragments.AcercaFragment;
import saenz.jhamir.packagelocatorapp.Fragments.ClienteFragment;
import saenz.jhamir.packagelocatorapp.Fragments.EditarPerfilFragment;
import saenz.jhamir.packagelocatorapp.Fragments.RutaFragment;
import saenz.jhamir.packagelocatorapp.Fragments.TrabajadorFragment;

public class MenuTrabajadorActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    TextView usernombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_trabajador);
        setToolbar();

        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout2);
        String nombre;
        nombre=LoginActivity.nombres2;
        View header = ((NavigationView)findViewById(R.id.navview2)).getHeaderView(0);
        navigationView=(NavigationView)findViewById(R.id.navview2);
        ((TextView) header.findViewById(R.id.username123)).setText(nombre);
        setFragmentByDefault();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override

            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                boolean fragmentTransaction=false;
                Fragment fragment=null;

                switch (menuItem.getItemId()){
                    case R.id.menu_rutas:
                        fragment=new RutaFragment();
                        fragmentTransaction=true;
                        break;
                    case R.id.menu_trabajador:
                        fragment=new TrabajadorFragment();
                        fragmentTransaction=true;
                        break;
                    case R.id.menu_editar:
                        fragment=new EditarPerfilFragment();
                        fragmentTransaction=true;
                        break;

                    case R.id.menu_acerca:
                        fragment=new AcercaFragment();
                        fragmentTransaction=true;
                        break;
                    case R.id.menu_logout:
                        //fragment=new LogoutFragment();

                        /*dialogo para q cierre sesion*/
                        AlertDialog.Builder builder= new AlertDialog.Builder(MenuTrabajadorActivity.this);
                        builder.setIcon(R.drawable.logout).setTitle("Advertencia").setMessage("¿Desea cerrar sesión?").
                                setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        Intent intent = new Intent(MenuTrabajadorActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alertDialog=builder.create();
                        alertDialog.show();
                        break;
                }
                if(fragmentTransaction){
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,fragment).commit();
                    menuItem.setChecked(true);
                    getSupportActionBar().setTitle(menuItem.getTitle());
                    drawerLayout.closeDrawers();
                }
                return true;
            }
        });
    }
    private void setToolbar(){
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private  void setFragmentByDefault(){
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new TrabajadorFragment()).commit();
    }
    private void changeFragments(Fragment fragment, MenuItem menuItem){


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}
