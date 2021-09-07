package com.example.regra3;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


public class Arquivo {

    public List<Produto> lista;
    final String ARQUIVO = "Lista.txt";
    Context context;

    public Arquivo(Context context)
    {
        this.context = context;
    }

    public String Salvar(Produto p)
    {
        try {

            if(p.Nome.length() == 0 || p.Unidade == 0 || p.Preço == 0 || p.Qtde == 0)
                return "Por favor, Preencha Todos Os Campos";

            if(!(p.Unidade > 0 && p.Preço > 0 && p.Qtde > 0))
                return "Por favor, Utilize Apenas Valores Positivos";

            p.Nome += " ("+p.Unidade+p.TpUnidade+")";

            for(int i=0;i< lista.size();i++)
                if(lista.get(i).Nome.trim().equals(p.Nome.trim()))
                    return "Este Produto Já Existe";

            lista.add(p);
            Collections.sort(lista);
            String resp = ToJson(p);
            FileOutputStream out = context.openFileOutput(ARQUIVO, Context.MODE_APPEND);
            out.write((resp+"\n").getBytes());
            out.flush();
            out.close();
            return "Adicionado Com Sucesso";

        } catch (Exception e) {
            Log.e("myTag", e.toString());
            return "Erro ao Adicionar Produto";
        }
    }

    public String Deletar(Produto p){
        try {

            for(int i=0;i< lista.size();i++)
                if(lista.get(i).Nome == p.Nome) {
                    lista.remove(i);
                    i = lista.size();
                }

            GetFile().delete();

            for(int i=0;i< lista.size();i++)
                Salvar(lista.get(i));

            return "Removido Com Sucesso";
        } catch (Exception e) {
            Log.e("myTag", e.toString());
            return "Erro ao Remover Produto";
        }
    }

    public String Carregar() throws FileNotFoundException, IOException{
        try {
            InitLista();
            List<String> linList = Files.readAllLines(GetFile().toPath());

            for(int i=0;i<linList.size();i++) {
                Produto p = new Gson().fromJson(linList.get(i), Produto.class);
                lista.add(p);
            }

            Collections.sort(lista);
        }
        catch (Exception e) {
            Log.e("myTag", "Tag->"+ e.toString());
            DeletarArquivo();
        }
        Log.e("myTag", "Tag-> Iniciado...");
        return "";
    }

    void LimparLista()
    {
        DeletarArquivo();
        InitLista();
    }

    void InitLista()
    {
        lista = new ArrayList<Produto>();
    }

    public ArrayList Listar()
    {
        ArrayList strList = new ArrayList<>();
        double total = 0;
        for(Produto item : lista){
            double v = item.Preço * item.Qtde;
            strList.add(item.Nome + " "+item.Qtde+" Un. R$ "+ FormatDouble(v));
            total += v;
        }
        strList.add("Total: R$ "+ FormatDouble(total));
        return strList;
    }

    String FormatDouble(double f)
    {
        return String.format("%.2f", f);
    }

    void DeletarArquivo()
    {
        try{ GetFile().delete(); }
        catch(Exception z) {   Log.e("myTag", "Tag-> Não Deletado.");}
    }

    File GetFile()
    {
        File file = context.getFilesDir();
        File textfile = new File(file + "/"+ARQUIVO);
        return textfile;
    }
    public String ToJson(Produto p) {
        return new Gson().toJson(p);
    }
}





