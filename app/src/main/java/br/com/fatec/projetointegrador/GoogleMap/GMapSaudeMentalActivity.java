package br.com.fatec.projetointegrador.GoogleMap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.fatec.projetointegrador.R;

public class GMapSaudeMentalActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap gMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmap_saude_mental);

        // Verificar e solicitar permissões de localização
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Solicitar permissão
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        // Carregar o mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.id_map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Definindo as localizações dos marcadores
        LatLng location1 = new LatLng(-23.6858899,-46.6195748); // Localização 1
        LatLng location2 = new LatLng(-23.6842693,-46.6221611); // Localização 2
        LatLng location3 = new LatLng(-23.6890472,-46.626975); // Localização 3
        LatLng location4 = new LatLng(-23.690211, -46.623151); // Localização 4


        // Adicionando os marcadores com título
        Marker marker1 = googleMap.addMarker(new MarkerOptions().position(location1).title("CEPAP - Psicólogos | Psiquiatra Diadema"));
        Marker marker2 = googleMap.addMarker(new MarkerOptions().position(location2).title("OPUS Clínica de Psicologia e Neuropsicologia"));
        Marker marker3 = googleMap.addMarker(new MarkerOptions().position(location3).title("Psicóloga Elizete Ferreira"));
        Marker marker4 = googleMap.addMarker(new MarkerOptions().position(location4).title("Consultório de Psicologia Ativaamente"));

        // Movendo a câmera para o centro entre as localizações
        LatLng center = new LatLng(
                (-23.6858899 + -23.6842693 + -23.6890472 + -23.690211) / 4,
                (-46.6195748 + -46.6221611 + -46.626975 + -46.623151) / 4
        );
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 13));  // Ajusta o zoom e move a câmera

        // Exibindo a InfoWindow automaticamente
        marker1.showInfoWindow();  // Mostra a janela de informações para o primeiro marcador
        marker2.showInfoWindow();  // Mostra a janela de informações para o segundo marcador
        marker3.showInfoWindow();  // Mostra a janela de informações para o terceiro marcador
        marker4.showInfoWindow();  // Mostra a janela de informações para o terceiro marcador


        // Configura um listener de clique para os marcadores
        googleMap.setOnMarkerClickListener(marker -> {
            // Exibe a janela de informações quando um marcador é clicado
            marker.showInfoWindow();
            return true;  // Retorna true para indicar que o clique foi tratado
        });
    }





    // Processar a resposta da solicitação de permissões
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Se a permissão foi concedida
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissão concedida, habilitar localização
                if (gMap != null) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    gMap.setMyLocationEnabled(true);  // Habilita a localização do usuário
                }
            } else {
                // Permissão negada, você pode exibir uma mensagem ou tomar outra ação
            }
        }
    }
}
