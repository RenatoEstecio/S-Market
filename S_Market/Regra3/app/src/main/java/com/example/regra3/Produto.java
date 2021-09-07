package com.example.regra3;

import org.json.JSONObject;

import java.math.BigDecimal;

public class Produto implements Comparable<Produto>{
    public String Nome;
    public double Preço;
    public int Qtde;
    public int Unidade;
    public String TpUnidade;

    public  Produto(String Nome, int Unidade, int Qtde, Double Preço, String TpUnidade){
       this.Preço = Preço;
       this.Qtde = Qtde;
       this.Unidade = Unidade;
       this.Nome = Nome;
       this.TpUnidade = TpUnidade;

    }
    public  Produto(){ }

    double GetPreco()
    {
        return Preço;
    }
    int GetUnidade()
    {
        return Unidade;
    }

    @Override public int compareTo(Produto outro) {

        try {
            int v1 = OrdemUn(this);
            int v2 = OrdemUn(outro);

            if(v1 < v2)
                return -1;
            if(v1 > v2)
                return 1;

            outro  = Normalizar(outro);
            Produto aux = Normalizar(this);

            if (aux.GetPreco() / aux.GetUnidade() > outro.GetPreco() / GetUnidade())
                return -1;
            else
                return 1;

        }
        catch(Exception z){ }
        return 1;
    }

    Produto Normalizar(Produto p)
    {
        switch (p.TpUnidade)
        {
            case "Kg":
            case "Lt":p.Unidade = 1000 * p.Unidade;break;
        }

        return  p;
    }

    int OrdemUn(Produto p)
    {
        switch (p.TpUnidade)
        {
            case "Lt":
            case "ml":return 1;

            case "Kg":
            case "mg":return 2;

            case "Cx":return 3;
            default: return 4;
        }
    }

}
