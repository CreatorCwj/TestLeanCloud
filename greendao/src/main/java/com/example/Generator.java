package com.example;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class Generator {

    public static void main(String[] args) throws IOException {
        Schema schema = new Schema(1, "com.dao.generate");
        addCity(schema);
        try {
            new DaoGenerator().generateAll(schema, "app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addCity(Schema schema) {
        Entity note = schema.addEntity("City");

        note.addIdProperty();
        note.addIntProperty("cityId").notNull();
        note.addStringProperty("name").notNull();
        note.addStringProperty("pinyin").notNull();
        note.addStringProperty("shortPinyin").notNull();
        note.addLongProperty("lastUseTime");
    }
}
