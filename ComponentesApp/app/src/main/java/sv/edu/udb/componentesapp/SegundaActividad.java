package sv.edu.udb.componentesapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SegundaActividad extends AppCompatActivity {
    private TextView tvNombre;
    private TextView tvEdad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda_actividad);

        tvNombre=(TextView) findViewById(R.id.textViewNombre);
        tvEdad=(TextView) findViewById(R.id.textViewEdad);

        Bundle bundle= getIntent().getExtras();
        String nombre=bundle.getString("txtNombre");
        String edad=bundle.getString("txtEdad");
        tvNombre.setText(nombre);
        tvEdad.setText(edad);
    }
    public void finalizar(View v){
        finish();
    }
}