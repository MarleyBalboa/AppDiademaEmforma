package br.com.fatec.projetointegrador;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

public class TelaAgendarActivity extends AppCompatActivity {

    private Button btnAgendar;  // Adiciona uma variável para o botão

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_agendar); // Certifique-se de que está usando o layout correto

        // Inicializando o botão btnAgendar
        btnAgendar = findViewById(R.id.btnAgendar);

        // Configurando o clique no botão "Agendar"
        btnAgendar.setOnClickListener(v -> {
            // Ao clicar no botão, redireciona para TelaConsultaActivity
            startActivity(new Intent(TelaAgendarActivity.this, TelaConsultaActivity.class));
        });

        AppCompatEditText data_aula = findViewById(R.id.data_aula);
        data_aula.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();

                // Verifica se a data está com o formato correto e adiciona a barra (/) automaticamente
                if (text.length() == 2 || text.length() == 5) {
                    text += "/";  // Adiciona a barra (/) automaticamente
                    data_aula.setText(text);
                    data_aula.setSelection(text.length());  // Posiciona o cursor no final
                }

                // Limita o ano para 2 dígitos
                if (text.length() > 7) {
                    // Se o ano for digitado com 4 dígitos, corta para 2 dígitos
                    String correctedText = text.substring(0, 7);
                    data_aula.setText(correctedText);
                    data_aula.setSelection(correctedText.length());  // Posiciona o cursor no final
                }

                // Valida a data
                if (text.length() == 10) { // Quando o formato completo de data for preenchido
                    String[] parts = text.split("/");

                    // Extrai o dia, mês e ano
                    int day = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    int year = Integer.parseInt("20" + parts[2]);  // Adiciona o prefixo '20' para formar o ano completo

                    // Valida o ano (a partir de 2024)
                    if (year < 2024) {
                        data_aula.setError("O ano deve ser a partir de 2024.");
                        return;
                    }

                    // Valida o mês (entre 1 e 12)
                    if (month < 1 || month > 12) {
                        data_aula.setError("Mês inválido. Deve ser entre 01 e 12.");
                        return;
                    }

                    // Valida o dia dependendo do mês e ano (ano bissexto também)
                    if (!isValidDay(day, month, year)) {
                        data_aula.setError("Dia inválido para o mês e ano informados.");
                    }
                }
            }

            // Função para verificar se o dia é válido para o mês e ano
            private boolean isValidDay(int day, int month, int year) {
                // Array com o número de dias de cada mês (considerando o ano bissexto)
                int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

                // Se for fevereiro e o ano for bissexto, ajusta o número de dias
                if (month == 2 && isLeapYear(year)) {
                    daysInMonth[1] = 29;
                }

                // Verifica se o dia é válido para o mês
                return day >= 1 && day <= daysInMonth[month - 1];
            }

            // Função para verificar se o ano é bissexto
            private boolean isLeapYear(int year) {
                return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
            }
        });
    }
}
