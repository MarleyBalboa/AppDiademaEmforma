package br.com.fatec.projetointegrador;

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

public class GMapAcademiasActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap gMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmap_academias);

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
        LatLng location1 = new LatLng(-23.6894277,-46.5955766); // Localização 1
        LatLng location2 = new LatLng(-23.6844202, -46.6331142); // Localização 2
        LatLng location3 = new LatLng(-23.6884202,-46.6291648); // Localização 3
        LatLng location4 = new LatLng(-23.6928607,-46.6210102); // Localização 4

        // Adicionando os marcadores com título
        Marker marker1 = googleMap.addMarker(new MarkerOptions().position(location1).title("Villa Fitness"));
        Marker marker2 = googleMap.addMarker(new MarkerOptions().position(location2).title("Panabianco Diadema"));
        Marker marker3 = googleMap.addMarker(new MarkerOptions().position(location3).title("Smart Fit Diadema"));
        Marker marker4 = googleMap.addMarker(new MarkerOptions().position(location4).title("Bluefit Diadema"));

        // Movendo a câmera para o centro entre as localizações
        LatLng center = new LatLng(
                (-23.6894277 + -23.6844202 + -23.6884202 + -23.6928607) / 4,
                (-46.5955766 + -46.6331142 + -46.6291648 + -46.6210102) / 4
        );
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 12));  // Ajusta o zoom e move a câmera

        // Exibindo a InfoWindow automaticamente
        marker1.showInfoWindow();  // Mostra a janela de informações para o primeiro marcador
        marker2.showInfoWindow();  // Mostra a janela de informações para o segundo marcador
        marker3.showInfoWindow();  // Mostra a janela de informações para o terceiro marcador
        marker4.showInfoWindow();  // Mostra a janela de informações para o quarto marcador

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
