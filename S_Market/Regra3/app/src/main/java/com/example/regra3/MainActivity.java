package com.example.regra3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    Arquivo arq;
    String[] Unidades = { "ml", "mg", "Kg", "Lt","Cx"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Spinner s = (Spinner) findViewById(R.id.spUnidade);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, Unidades);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s.setAdapter(adapter);

            init();

        } catch (IOException e) {
            Log.d("myTag", e.getMessage());
            e.printStackTrace();
        }
    }

    private void init() throws IOException {

        arq = new Arquivo(this.getBaseContext());
        arq.Carregar();
    }

    public void voltar(View view)
    {
        finish();
        Intent homepage = new Intent(this,Activity_Listar.class);
        startActivity(homepage);
    }

    public void onClick(View view) {
        String Item = "Nome";
        String Msg = "";

        try {

            String Nome = GetString(R.id.txtNome);
            Item = "Valor";
            double Preço =  GetDouble(R.id.txtValor);
            Item = "Quantidade";
            int Qtde =  GetInt(R.id.txtQtde);
            Item = "Campo Unidade";
            int Unidade =  GetInt(R.id.txtunidade);
            Item = "Tipo Unidade";
            String Tipo =  GetStringSp(R.id.spUnidade);
            Item = "";

            Msg = arq.Salvar(new Produto(Nome,Unidade,Qtde,Preço,Tipo));
        }
        catch (Exception z) {
            if(Item.length()>0)
                Msg = Item+" Inválido.";
            Log.d("myTag", "erro "+z.getMessage());
        }
        Snackbar sb = Snackbar.make(view, Msg, 3000);
        sb.show();
    }

    String GetString(int id)
    {
        TextView tv = findViewById(id);
        return tv.getText().toString().trim();
    }

    String GetStringSp(int id)
    {
        Spinner mySpinner = (Spinner) findViewById(id);
        return mySpinner.getSelectedItem().toString().trim();
    }

    double GetDouble(int id)
    {
        return Double.parseDouble(GetString(id).replace(",","."));
    }

    int GetInt(int id)
    {
        return Integer.parseInt(GetString(id));
    }


}