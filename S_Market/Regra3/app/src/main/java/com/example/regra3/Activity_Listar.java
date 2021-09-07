package com.example.regra3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class Activity_Listar extends AppCompatActivity {

    Arquivo arq;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        try {
            init();

          /*  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, ITENS);
            AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autoC);

            textView.setAdapter(adapter);*/
        } catch (IOException e) {
            e.printStackTrace();
        }

        CarregarLista();
    }

    private void init() throws IOException {

        arq = new Arquivo(this.getBaseContext());
        arq.Carregar();
    }

    public void CarregarLista()
    {
        ArrayList<String> listItems = arq.Listar();

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);

        ListView lv = (ListView) findViewById(R.id.listaView);
        lv.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    public void Limpar(View view)
    {
        try {
            arq.LimparLista();
            CarregarLista();
        } catch (Exception e) {
            Log.d("myTag", e.getMessage());
            e.printStackTrace();
        }
    }

    public void Adicionar(View view)
    {
        finish();
        Intent homepage = new Intent(this,MainActivity.class);
        startActivity(homepage);
    }
}