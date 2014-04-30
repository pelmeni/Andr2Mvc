package com.example.andr2mvc.andr2mvc;

import java.util.ArrayList;

/**
 * Created by PichuginSI on 29.04.2014.
 */
public class DbImageProvider {
    static ArrayList<dbimage> list=new ArrayList<dbimage>();

    public static void Add(dbimage i){

        if(GetById(i.id)==null)
                list.add(i);
    }
    public static dbimage GetById(int id){
        for(dbimage i:list){
            if(i.id==id)
                return i;
        }
        return null;
    }
    public static ArrayList<dbimage> GetList(){
        return list;
    }

    public static int Count(){
        return list.size();
    }

}
