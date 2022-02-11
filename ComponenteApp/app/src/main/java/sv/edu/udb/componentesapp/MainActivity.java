package sv.edu.udb.componentesapp;

import static android.Manifest.permission.READ_CONTACTS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView lblPermiso;
    private TextView lblContacto;
    private final int MY_PERMISSONS= 100;
    private final int OPEN_CONTACT= 200;
    private final String str_permitido="PERMITIDO";
    private final String str_denegado="DENEGADO";
    private String estado;

    public boolean verificaPermiso(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;
        if (checkSelfPermission(READ_CONTACTS)== getPackageManager().PERMISSION_GRANTED) return true;

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        estado = "Estado del permiso:";
        lblPermiso=(TextView) findViewById(R.id.lblPermiso);
        lblContacto=(TextView) findViewById(R.id.lblContacto);

        if (verificaPermiso())
            lblPermiso.setText(estado+ " "+ str_permitido);
        else
            lblPermiso.setText(estado+ " "+ str_denegado);
    }
    @TargetApi(Build.VERSION_CODES.M)
    public void on_Click(View view){
        if (verificaPermiso()){
            lblPermiso.setText(estado+" "+str_permitido);
            Intent intent= new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent,OPEN_CONTACT);
        }
        else{
            requestPermissions(new String[]{READ_CONTACTS},MY_PERMISSONS);
        }
    }

    @SuppressLint("Range")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if (resultCode== Activity.RESULT_OK){
            switch (requestCode) {
                case OPEN_CONTACT:
                    Uri contactUri = data.getData();
                    Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);

                    String nombre = "\n" + "Contacto Seleccionado: " + "\n";

                    if (cursor.moveToFirst()) {
                        nombre = nombre + cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    }
                    lblContacto.setText(nombre);
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionResults(int requeastCode, @NonNull String[] permission, @NonNull int[] grantResults){
       super.onRequestPermissionsResult(requeastCode,permission,grantResults);
       if (requeastCode==MY_PERMISSONS){
           lblPermiso.setText(estado+" "+str_permitido);
           Intent intent= new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
           startActivityForResult(intent, OPEN_CONTACT);
       }
       else{
           lblPermiso.setText(estado+" "+str_denegado);
       }
    }
}