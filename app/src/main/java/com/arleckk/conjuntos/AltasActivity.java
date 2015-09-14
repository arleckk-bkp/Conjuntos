package com.arleckk.conjuntos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AltasActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUniverso,editTextConjuntoA, editTextConjuntoB;
    private Button btnAceptar;
    private ArrayList conjuntoUniverso, conjuntoA, conjuntoB;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altas);
        editTextUniverso = (EditText) findViewById(R.id.edit_text_universo);
        editTextConjuntoA = (EditText) findViewById(R.id.edit_text_conjunto_a);
        editTextConjuntoB = (EditText) findViewById(R.id.edit_text_conjunto_b);
        btnAceptar = (Button) findViewById(R.id.btn_aceptar);
        btnAceptar.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_altas, menu);
        return true;
    }

    public boolean validar(String exp, String universo,String conjA, String conjB){
        Pattern pattern = Pattern.compile(exp);
        Matcher matcher = pattern.matcher(universo);
        String datos ="";
        conjuntoUniverso = new ArrayList();
        conjuntoA = new ArrayList();
        conjuntoB = new ArrayList();
        if(universo.length()>0) {
            while (matcher.find()) {
                conjuntoUniverso.add(matcher.group());
            }
            HashSet hs = new HashSet();
            hs.addAll(conjuntoUniverso);
            conjuntoUniverso.clear();
            conjuntoUniverso.addAll(hs);
            //Conjunto A
            matcher = pattern.matcher(conjA);
            if(conjA.length()>0){
                while(matcher.find()){
                    conjuntoA.add(matcher.group());
                }
                if(comprobarEnUniverso(conjuntoUniverso,conjuntoA)) {
                    Log.d("datos", "Conjunto A: " + conjuntoA.toString());
                    //Conjunto B
                    matcher = pattern.matcher(conjB);
                    if(conjB.length()>0){
                        while(matcher.find()){
                            conjuntoB.add(matcher.group());
                        }
                        if(comprobarEnUniverso(conjuntoUniverso,conjuntoB)) {
                            Log.d("datos", "Conjunto B: " + conjuntoB.toString());
                            hs.clear();
                            hs.addAll(conjuntoA);
                            conjuntoA.clear();
                            conjuntoA.addAll(hs);
                            hs.clear();
                            hs.addAll(conjuntoB);
                            conjuntoB.clear();
                            conjuntoB.addAll(hs);
                            return true;
                        }
                        else {
                            conjuntoUniverso.clear();
                            conjuntoA.clear();
                            conjuntoB.clear();
                            Toast toast = new Toast(this);
                            toast.makeText(this,"uno o mas elementos del conjunto B no existe en universo",Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    }
                }
                else {
                    conjuntoUniverso.clear();
                    conjuntoA.clear();
                    conjuntoB.clear();
                    Toast toast = new Toast(this);
                    toast.makeText(this,"uno o mas elementos del conjunto A no existe en universo",Toast.LENGTH_SHORT).show();
                }
            }
        }
        return false;
    }

    public boolean comprobarEnUniverso (ArrayList universo, ArrayList conjunto) {
        int contiene = 1;
        for(int i=0; i<conjunto.size();i++){
            if(!universo.contains(conjunto.get(i))){
                contiene = 0;
            }
        }
        if(contiene == 1)
            return true;
        else
            return false;
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
        int id = v.getId();
        switch (id){
            case R.id.btn_aceptar:
                if(editTextUniverso.getText().toString().length()>0
                        && editTextConjuntoA.getText().toString().length()>0
                        && editTextConjuntoB.getText().toString().length()>0) {
                    if (validar("[A-z0-9ñÑ]{1,}",
                            editTextUniverso.getText().toString(),
                            editTextConjuntoA.getText().toString(),
                            editTextConjuntoB.getText().toString())) {
                        Log.d("datos", "universo: " + conjuntoUniverso);
                        Log.d("datos", "conjunto A: " + conjuntoA);
                        Log.d("datos", "conjunto B: " + conjuntoB);
                        Intent data = new Intent();
                        data.putStringArrayListExtra("universo", conjuntoUniverso);
                        data.putStringArrayListExtra("a", conjuntoA);
                        data.putStringArrayListExtra("b", conjuntoB);
                        setResult(1, data);
                        finish();
                    }
                } else {
                    Toast.makeText(this,"Uno o mas elementos esta vacio",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
