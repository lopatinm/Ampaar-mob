package com.lopatinm.ampaar.ui.barter;

public class Barter {
    Integer id;
    Integer product_id;

    String comment;
    String name;

    Integer count;

    public Barter(Integer id, Integer product_id, String comment, Integer count, String name){

        this.product_id=product_id;
        this.name=name;
        this.count=count;
        this.comment=comment;
        this.id=id;
    }
}
