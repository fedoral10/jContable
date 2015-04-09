/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.contable.cuentas;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Jorge Potosme
 */
public class Cuenta {

    private double saldo = 0;
    private String nombre;
    private Naturaleza naturalezaCuenta;

    public Cuenta getPadre() {
        return padre;
    }

    public void setPadre(Cuenta padre) {
        this.padre = padre;
    }
    private Cuenta padre;
    public Cuenta(String nombre, Naturaleza tipo) {
        this.nombre = nombre;
        this.naturalezaCuenta = tipo;
    }

    public enum Naturaleza {
        ACREEDORA, DEUDORA
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Naturaleza getNaturaleza() {
        return naturalezaCuenta;
    }

    public void setNaturaleza(Naturaleza naturaleza) {
        this.naturalezaCuenta = naturaleza;
    }
    
    public String getJerarquia(){
        Cuenta padreLocal = this.padre;
        String salida = "";
        List<String> lista = new ArrayList<>();
        while(padreLocal != null)
        {
            lista.add(padreLocal.getNombre());
            padreLocal = padreLocal.getPadre();
        }
        ListIterator iter = lista.listIterator(lista.size());
        while(iter.hasPrevious())
        {
            salida =salida + iter.previous()+ "--";
        }
        return salida+this.nombre;
    }
}
