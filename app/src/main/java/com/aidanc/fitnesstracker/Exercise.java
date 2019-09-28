package com.aidanc.fitnesstracker;

import java.io.Serializable;

public class Exercise implements Serializable {
    public int id;
    public String name;
    public int weight;
    public int sets;
    public int workout_id;
}
