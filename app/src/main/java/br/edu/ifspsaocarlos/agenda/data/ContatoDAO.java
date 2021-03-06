package br.edu.ifspsaocarlos.agenda.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import br.edu.ifspsaocarlos.agenda.model.Contato;

import java.sql.SQLInput;
import java.util.ArrayList;
import java.util.List;


public class ContatoDAO {
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public ContatoDAO(Context context) {
        this.dbHelper=new SQLiteHelper(context);
    }

    public  List<Contato> buscaTodosContatos(boolean favorite)
    {
        database=dbHelper.getReadableDatabase();
        List<Contato> contatos = new ArrayList<>();

        Cursor cursor;

        String[] cols=new String[] {
            SQLiteHelper.KEY_ID,
            SQLiteHelper.KEY_NAME,
            SQLiteHelper.KEY_FONE,
            SQLiteHelper.KEY_EMAIL,
            SQLiteHelper.KEY_FAVORITE,
            SQLiteHelper.KEY_FONE_SECONDARY,
            SQLiteHelper.KEY_BIRTHDATE
        };

        String where = "";
        if (favorite) {
            where += SQLiteHelper.KEY_FAVORITE +" = 1";
        }

        cursor = database.query(SQLiteHelper.DATABASE_TABLE, cols, where , null,
                null, null, SQLiteHelper.KEY_NAME);


        while (cursor.moveToNext())
        {
            Contato contato = new Contato();
            contato.setId(cursor.getInt(0));
            contato.setNome(cursor.getString(1));
            contato.setFone(cursor.getString(2));
            contato.setEmail(cursor.getString(3));
            contato.setFavorite(Integer.parseInt(cursor.getString(4)));
            contato.setFoneSecondary(cursor.getString(5));
            contato.setBirthdate(cursor.getString(6));
            contatos.add(contato);
        }
        cursor.close();


        database.close();
        return contatos;
    }

    public  List<Contato> buscaContato(String nome, boolean favorite)
    {
        database=dbHelper.getReadableDatabase();
        List<Contato> contatos = new ArrayList<>();

        Cursor cursor;

        String[] cols=new String[] {
                SQLiteHelper.KEY_ID,
                SQLiteHelper.KEY_NAME,
                SQLiteHelper.KEY_FONE,
                SQLiteHelper.KEY_EMAIL,
                SQLiteHelper.KEY_FAVORITE,
                SQLiteHelper.KEY_FONE_SECONDARY,
                SQLiteHelper.KEY_BIRTHDATE
        };
        String where=SQLiteHelper.KEY_NAME + " like ? OR " + SQLiteHelper.KEY_EMAIL + " like ?";
        if (favorite) {
            where += " AND "+ SQLiteHelper.KEY_FAVORITE +" = 1";
        }
        String[] argWhere=new String[]{nome + "%", "%" + nome + "%"};


        cursor = database.query(SQLiteHelper.DATABASE_TABLE, cols, where , argWhere,
                null, null, SQLiteHelper.KEY_NAME);


        while (cursor.moveToNext())
        {
            Contato contato = new Contato();
            contato.setId(cursor.getInt(0));
            contato.setNome(cursor.getString(1));
            contato.setFone(cursor.getString(2));
            contato.setEmail(cursor.getString(3));
            contato.setFavorite(Integer.parseInt(cursor.getString(4)));
            contato.setFoneSecondary(cursor.getString(5));
            contato.setBirthdate(cursor.getString(6));
            contatos.add(contato);

        }
        cursor.close();

        database.close();
        return contatos;
    }

    public void salvaContato(Contato c) {
        database=dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.KEY_NAME, c.getNome());
        values.put(SQLiteHelper.KEY_FONE, c.getFone());
        values.put(SQLiteHelper.KEY_EMAIL, c.getEmail());
        values.put(SQLiteHelper.KEY_FAVORITE, c.getFavorite());
        values.put(SQLiteHelper.KEY_FONE_SECONDARY, c.getFoneSecondary());
        values.put(SQLiteHelper.KEY_BIRTHDATE, c.getBirthdate());

       if (c.getId()>0)
          database.update(SQLiteHelper.DATABASE_TABLE, values, SQLiteHelper.KEY_ID + "="
                + c.getId(), null);
        else
           database.insert(SQLiteHelper.DATABASE_TABLE, null, values);

        database.close();
    }



    public void apagaContato(Contato c)
    {
        database=dbHelper.getWritableDatabase();
        database.delete(SQLiteHelper.DATABASE_TABLE, SQLiteHelper.KEY_ID + "="
                + c.getId(), null);
        database.close();
    }
}
