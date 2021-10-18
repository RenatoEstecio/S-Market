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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    Arquivo arq;
    String[] Unidades = { "ml", "mg", "Kg", "Lt","Cx"};
    boolean Adicionar = true;
    String Item_mod = "";

    /* Eventos */

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


    public void onClick(View view) {

        if(Adicionar == false)
            arq.Deletar(arq.GetProduto(Item_mod));

        String Item = "Nome";
        String Msg = "";

        try {

            String Nome = GetString(R.id.txtNome);
            Item = "Valor";
            double Preço =  GetDouble(R.id.txtValor);
            Item = "Quantidade";
            int Qtde =  GetInt(R.id.txtQtde);
            Item = "Campo Unidade";
            double Unidade =  GetDouble(R.id.txtunidade);
            Item = "Tipo Unidade";
            String Tipo =  GetStringSp(R.id.spUnidade);
            Item = "";

            Msg = arq.Salvar(new Produto(Nome,Unidade,Qtde,Preço,Tipo));

            if(Adicionar)
                Msg = "Alterado Com Sucesso";
            else
                GoActMain();

            LimparCampos();
        }
        catch (Exception z) {
            if(Item.length()>0)
                Msg = Item+" Inválido.";
            Log.d("myTag", "erro "+z.getMessage());
        }
        Snackbar sb = Snackbar.make(view, Msg, 3000);
        sb.show();
    }

    public void voltar(View view)
    {
        if(Adicionar == false)
            arq.Deletar(arq.GetProduto(Item_mod));

        GoActMain();
    }

    /* Outros Metodos */

    void GoActMain()
    {
        finish();
        Intent listar = new Intent(this,Activity_Listar.class);
        startActivity(listar);
    }

    private void init() throws IOException {

        arq = new Arquivo(this.getBaseContext());
        arq.Carregar();
        Bundle bundle = getIntent().getExtras();

        try {
            Item_mod = bundle.getString("Item");

            if (Item_mod != null) {
                Adicionar = false;
                Button btt = (Button) findViewById(R.id.bttGravar);
                btt.setText("Alterar/Voltar");

                Button bttVoltar = (Button) findViewById(R.id.bttVoltar);
                bttVoltar.setText("Apagar");

                IniciaCampos(arq.GetProduto(Item_mod));
            }
        }catch (Exception z) { }
    }

    void LimparCampos()
    {
        SetString(R.id.txtQtde,"");
        SetString(R.id.txtValor,"");
        SetString(R.id.txtunidade,"");
        SetString(R.id.txtNome,"");
        Spinner mySpinner = (Spinner) findViewById(R.id.spUnidade);
        mySpinner.setSelection(0);
    }

    void IniciaCampos(Produto p)
    {
        SetString(R.id.txtQtde,p.Qtde+"");
        SetString(R.id.txtNome,p.Nome.split("[ (]")[0]);
        SetString(R.id.txtunidade,p.Unidade+"");
        SetString(R.id.txtValor,p.Preço+"");
        SetStringSp(R.id.spUnidade,p.TpUnidade);
    }

    /* Metodos Auxiliares */

    String GetString(int id)
    {
        TextView tv = findViewById(id);
        return tv.getText().toString().trim();
    }

    void SetString(int id,String value)
    {
        TextView tv = findViewById(id);
        tv.setText(value);
    }

    void SetStringSp(int id,String value)
    {
        Spinner mySpinner = (Spinner) findViewById(id);
        int t = mySpinner.getAdapter().getCount();

        try{
            for(int i=0;i<t;i++)
            {
                String un = (String)mySpinner.getItemAtPosition(i);
                if(un.equals(value))
                    mySpinner.setSelection(i);
            }
        }
        catch (Exception z) { }
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