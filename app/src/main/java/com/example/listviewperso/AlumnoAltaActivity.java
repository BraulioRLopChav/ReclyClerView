package com.example.listviewperso;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listviewperso.Model.AlumnosDb;

public class AlumnoAltaActivity extends AppCompatActivity {
    private Button btnGuardar, btnRegresar;
    private Alumno alumno;
    private EditText txtNombre, txtMatricula, txtGrado;
    private ImageView imgAlumno;
    private TextView lblImagen;
    private int posicion;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_alta);
        btnGuardar = (Button) findViewById(R.id.btnSalir);
        btnRegresar = (Button) findViewById(R.id.btnRegresar);
        txtMatricula = (EditText) findViewById(R.id.txtMatricula);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtGrado = (EditText) findViewById(R.id.txtGrado);
        imgAlumno = (ImageView) findViewById(R.id.imgAlumno);

        Bundle bundle = getIntent().getExtras();
        alumno = (Alumno) bundle.getSerializable("alumno");
        posicion = bundle.getInt("posicion", posicion);

        if (posicion >= 0) {
            txtMatricula.setText(alumno.getMatricula());
            txtNombre.setText(alumno.getNombre());
            txtGrado.setText(alumno.getCarrera());
            imgAlumno.setImageResource(alumno.getImg());
        }

        btnGuardar.setOnClickListener(v -> {
            if (alumno == null) {


                if (validar()) {
                    alumno = new Alumno();
                    alumno.setCarrera(txtGrado.getText().toString());
                    alumno.setMatricula(txtMatricula.getText().toString());
                    alumno.setNombre(txtNombre.getText().toString());

                    Aplicacion.alumnosDb.insertAlumno(alumno);
                    Aplicacion.alumnos.add(this.alumno);
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Falto Capturar datos", Toast.LENGTH_SHORT).show();
                    txtMatricula.requestFocus();
                }

            }
            if (posicion >= 0) {
                alumno.setMatricula(txtMatricula.getText().toString());
                alumno.setNombre(txtNombre.getText().toString());
                alumno.setCarrera(txtGrado.getText().toString());

                Aplicacion.alumnosDb.updateAlumno(alumno);
                Aplicacion.alumnos.get(posicion).setMatricula(txtMatricula.getText().toString());
                Aplicacion.alumnos.get(posicion).setNombre(txtNombre.getText().toString());
                Aplicacion.alumnos.get(posicion).setCarrera(txtGrado.getText().toString());


                Toast.makeText(getApplicationContext(), "Se modifico con exito ", Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK);
                finish();
            }
        });

        btnRegresar.setOnClickListener(v -> {
            finish();
        });

    }

    private boolean validar(){
        boolean exito = true;
        Log.d("nombre", "validar: " + txtNombre.getText());
        if (txtNombre.getText().toString().equals("")) exito = false;
        if (txtGrado.getText().toString().equals("")) exito = false;
        if (txtMatricula.getText().toString().equals("")) exito = false;

        return exito;
    }
}
