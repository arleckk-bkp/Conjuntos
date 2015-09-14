package com.arleckk.conjuntos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnAltas, btnOperacion, btnSalir;
    private ArrayList conjuntoUniverso, conjuntoA, conjuntoB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAltas = (Button) findViewById(R.id.btn_alta);
        btnOperacion = (Button) findViewById( R.id.btn_operacion);
        btnSalir = (Button) findViewById(R.id.btn_salir);
        btnAltas.setOnClickListener(this);
        btnOperacion.setOnClickListener(this);
        btnSalir.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id;
        id = v.getId();
        switch (id){
            case R.id.btn_alta:
                Intent intentLanzar = new Intent(this,AltasActivity.class);
                this.onPause();
                startActivityForResult(intentLanzar, 1);
                break;

            case R.id.btn_operacion:
                intentLanzar = new Intent(this,OperacionActivity.class);
                this.onPause();
                intentLanzar.putStringArrayListExtra("universo", conjuntoUniverso);
                intentLanzar.putStringArrayListExtra("a",conjuntoA);
                intentLanzar.putStringArrayListExtra("b",conjuntoB);
                startActivity(intentLanzar);
                break;

            case R.id.btn_salir:
                this.finish();
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1){
            if(data!=null) {
                conjuntoUniverso = data.getStringArrayListExtra("universo");
                conjuntoA = data.getStringArrayListExtra("a");
                conjuntoB = data.getStringArrayListExtra("b");
                Log.d("principal", "universo: " + conjuntoUniverso);
                Log.d("principal", "conjunto A: " + conjuntoA);
                Log.d("principal", "conjunto B: " + conjuntoB);
                Toast.makeText(this,"La acción se realizo correctamente",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
