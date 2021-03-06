package br.edu.ifspsaocarlos.agenda.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import br.edu.ifspsaocarlos.agenda.data.ContatoDAO;
import br.edu.ifspsaocarlos.agenda.model.Contato;
import br.edu.ifspsaocarlos.agenda.R;


public class DetalheActivity extends AppCompatActivity implements View.OnClickListener {
    private Contato c;
    private ContatoDAO cDAO;

    private EditText birthdateText;
    private final int DATEPICKER_ID = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().hasExtra("contato"))
        {
            this.c = (Contato) getIntent().getSerializableExtra("contato");
            EditText nameText = (EditText)findViewById(R.id.editTextNome);
            nameText.setText(c.getNome());
            EditText foneText = (EditText)findViewById(R.id.editTextFone);
            foneText.setText(c.getFone());
            EditText foneSecondaryText = (EditText)findViewById(R.id.editTextFoneSecondary);
            foneSecondaryText.setText(c.getFoneSecondary());
            EditText emailText = (EditText)findViewById(R.id.editTextEmail);
            birthdateText = (EditText)findViewById(R.id.editTextBirthdate);
            birthdateText.setText(c.getBirthdate());
            birthdateText.setOnClickListener(this);
            emailText.setText(c.getEmail());
            int pos =c.getNome().indexOf(" ");
            if (pos==-1)
                pos=c.getNome().length();
            setTitle(c.getNome().substring(0,pos));
        }
        cDAO = new ContatoDAO(this);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        Calendar calendario = Calendar.getInstance();

        String data = birthdateText.getText().toString();
        String array[] = new String[3];
        array = data.split("/");

        int ano = Integer.valueOf(array[2]);
        int mes = Integer.valueOf(array[1])-1;
        int dia = Integer.valueOf(array[0]);

        switch (id) {
            case DATEPICKER_ID:
                return new DatePickerDialog(this, mDateSetListener, ano, mes,
                        dia);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            String data = String.valueOf(dayOfMonth) + "/"
                    + String.valueOf(monthOfYear+1) + "/" + String.valueOf(year);
            birthdateText.setText(data);
        }
    };

    @Override
    public void onClick(View v) {
        if (v == birthdateText) {
            showDialog(DATEPICKER_ID);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalhe, menu);
        if (!getIntent().hasExtra("contato"))
        {
            MenuItem item = menu.findItem(R.id.delContato);
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.salvarContato:
                salvar();
                return true;
            case R.id.delContato:
                apagar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void apagar()
    {
        cDAO.apagaContato(c);
        Intent resultIntent = new Intent();
        setResult(3,resultIntent);
        finish();
    }

    private void salvar()
    {
        String name = ((EditText) findViewById(R.id.editTextNome)).getText().toString();
        String fone = ((EditText) findViewById(R.id.editTextFone)).getText().toString();
        String foneSecondary = ((EditText) findViewById(R.id.editTextFoneSecondary)).getText().toString();
        String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();
        String birthdate = ((EditText) findViewById(R.id.editTextBirthdate)).getText().toString();


        if (c==null)
            c = new Contato();

        c.setNome(name);
        c.setFone(fone);
        c.setFoneSecondary(foneSecondary);
        c.setEmail(email);
        c.setBirthdate(birthdate);

        cDAO.salvaContato(c);
        Intent resultIntent = new Intent();
        setResult(RESULT_OK,resultIntent);
        finish();
    }
}

