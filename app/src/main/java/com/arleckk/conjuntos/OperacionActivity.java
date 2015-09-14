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
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperacionActivity extends AppCompatActivity implements View.OnClickListener{

    private ArrayList conjuntoUniverso, conjuntoA, conjuntoB, resultado;
    private Button btnRealizar;
    private EditText editTextOperacion;
    private String operacion;
    private TextView textViewResultado, textViewResultadoOperacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operacion);
        Intent intent = getIntent();
        conjuntoUniverso = intent.getStringArrayListExtra("universo");
        conjuntoA = intent.getStringArrayListExtra("a");
        conjuntoB = intent.getStringArrayListExtra("b");
        resultado = new ArrayList();
        editTextOperacion = (EditText) findViewById(R.id.edit_text_operacion);
        btnRealizar = (Button) findViewById(R.id.btn_realizar);
        btnRealizar.setOnClickListener(this);
        textViewResultado = (TextView) findViewById(R.id.text_resultado);
        textViewResultadoOperacion = (TextView) findViewById(R.id.text_resultado_operacion);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_operacion, menu);
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
        int id = v.getId();
        switch(id){
            case R.id.btn_realizar:
                operacion = editTextOperacion.getText().toString();
                if(checkOperation(operacion, "[A|B][I|U|\\-|C|P|X][A|B|]*\\s*")){
                    if(operacion.contains("U")){
                        union();
                    } else if(operacion.contains("I")){
                        interseccion();
                        Log.d("operacion", "interseccion");
                    } else if(operacion.contains("-")){
                        diferencia();
                        Log.d("operacion", "diferencia");
                    } else if(operacion.contains("C")){
                        complemento();
                        Log.d("operacion", "complemento");
                    } else if(operacion.contains("P")){
                        if(operacion.contains("A"))
                        potencia(conjuntoA);
                        else if(operacion.contains("B"))
                            potencia(conjuntoB);
                        Log.d("operacion", "potencia");
                    } else if(operacion.contains("X")){
                        if(operacion.startsWith("A")){
                            producto(conjuntoA,conjuntoB);
                            Log.d("operacion", "producto");
                        } else if(operacion.startsWith("B")){
                            producto(conjuntoB,conjuntoA);
                            Log.d("operacion", "producto");
                        }
                    }
                } else {
                    Toast.makeText(this, "La operacion no se pudo realizar",Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    public void union(){
        resultado.clear();
        resultado.addAll(conjuntoA);
        resultado.addAll(conjuntoB);
        HashSet hs = new HashSet();
        hs.addAll(resultado);
        resultado.clear();
        resultado.addAll(hs);
        textViewResultadoOperacion.setText("");
        textViewResultado.setText(R.string.resultado);
        textViewResultadoOperacion.setText("[AUB] = "+resultado.toString());
        Log.d("operacion", "Resultado: " + resultado.toString());
    }

    public void interseccion(){
        resultado.clear();
        if(operacion.equals("AIB")||operacion.equals("AIB ")){
            for(int i=0; i<conjuntoA.size();i++){
                if(conjuntoB.contains(conjuntoA.get(i))){
                    resultado.add(conjuntoA.get(i));
                }
            }
            textViewResultado.setText(R.string.resultado);
            textViewResultadoOperacion.setText("[AIB] = "+resultado.toString());
            Log.d("operacion", "Resultado [AIB]= " + resultado.toString());
        }// AIB
        else if(operacion.equals("BIA")||operacion.equals("BIA ")){
            for(int i=0; i<conjuntoB.size();i++){
                if(conjuntoA.contains(conjuntoB.get(i))){
                    resultado.add(conjuntoB.get(i));
                }
            }
            textViewResultado.setText(R.string.resultado);
            textViewResultadoOperacion.setText("[BIA] = "+resultado.toString());
            Log.d("operacion", "Resultado [BIA]= " + resultado.toString());
        }// BIA
    }

    public void diferencia(){
        resultado.clear();
        if(operacion.equals("A-B")||operacion.equals("A-B ")){
            for(int i=0; i<conjuntoA.size();i++){
                if(!(conjuntoB.contains(conjuntoA.get(i)))){
                    resultado.add(conjuntoA.get(i));
                }
            }
            textViewResultado.setText(R.string.resultado);
            textViewResultadoOperacion.setText("[A-B] = "+resultado.toString());
            Log.d("operacion", "Resultado [A-B]= " + resultado.toString());
        }// A-B
        else if(operacion.equals("B-A")||operacion.equals("B-A ")){
            for(int i=0; i<conjuntoB.size();i++){
                if(!(conjuntoA.contains(conjuntoB.get(i)))){
                    resultado.add(conjuntoB.get(i));
                }
            }
            textViewResultado.setText(R.string.resultado);
            textViewResultadoOperacion.setText("[B-A] = "+resultado.toString());
            Log.d("operacion", "Resultado [B-A]= " + resultado.toString());
        }// B-A
    }

    public void complemento(){
        resultado.clear();
        if(operacion.equals("AC")||operacion.equals("AC ")){
            for(int i=0; i<conjuntoUniverso.size();i++){
                if(!(conjuntoA.contains(conjuntoUniverso.get(i)))){
                    resultado.add(conjuntoUniverso.get(i));
                }
            }
            textViewResultado.setText(R.string.resultado);
            textViewResultadoOperacion.setText("[AC] = "+resultado.toString());
            Log.d("operacion", "Resultado [AC]= " + resultado.toString());
        }// AC
        else if(operacion.equals("BC")||operacion.equals("BC ")){
            for(int i=0; i<conjuntoUniverso.size();i++){
                if(!(conjuntoB.contains(conjuntoUniverso.get(i)))){
                    resultado.add(conjuntoUniverso.get(i));
                }
            }
            textViewResultado.setText(R.string.resultado);
            textViewResultadoOperacion.setText("[BC] = "+resultado.toString());
            Log.d("operacion", "Resultado [BC]= " + resultado.toString());
        }// BC
    }

    public void potencia(ArrayList conjunto){
        resultado.clear();
        Set s = new HashSet();
        s.addAll(conjunto);
        s = powerset(s);
        HashSet hs = new HashSet();
        hs.addAll(s);
        resultado.addAll(hs);
        textViewResultado.setText(R.string.resultado);
        textViewResultadoOperacion.setText(operacion+" = " + resultado);
        Log.d("operacion", "Potencia = " + resultado);
    }

    public void producto(ArrayList conjuntoA, ArrayList conjuntoB){
        resultado.clear();
        List temp;
        int i=0;
        int j=0;
        while(i<conjuntoA.size()){
            while(j<conjuntoB.size()){
                temp = new ArrayList();
                temp.add(conjuntoA.get(i).toString());
                temp.add(conjuntoB.get(j).toString());
                Log.d("operacion", "temp: " + temp.toString());
                j++;
                resultado.add(temp);
            }
            j=0;
            i++;
        }
        textViewResultado.setText(R.string.resultado);
        textViewResultadoOperacion.setText(operacion+" = " + resultado);
        Log.d("operacion", "Potencia = " + resultado);
    }

    protected static <T> Set<T> copyWithout(Set<T> s, T e) {
        Set<T> result = new HashSet<T>(s);
        result.remove(e);
        return result;
    }

    protected static <T> Set<T> copyWith(Set<T> s, T e) {
        Set<T> result = new HashSet<T>(s);
        result.add(e);
        return result;
    }

    public static <T> Set<Set<T>> powerset(Set<T> s) {
        Set<Set<T>> result = new HashSet<Set<T>>();
        if(s.isEmpty()) {
            Set<T> empty = Collections.emptySet();
            result.add(empty);
        } else {
            for (T e : s) {
                Set<T> t = copyWithout(s, e);
                Set<Set<T>> ps = powerset(t);
                result.addAll(ps);
                for (Set<T> ts : ps) {
                    result.add(copyWith(ts, e));
                }
            }
        }
        return result;
    }

    public boolean checkOperation(String text, String exp){
        Pattern pattern = Pattern.compile(exp);
        Matcher matcher = pattern.matcher(text);
        if(matcher.matches())
            return true;
        else
            return false;
    }
}
