package br.com.fatec.projetointegrador.Usuario;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.contrib.PickerActions.setTime;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.widget.DatePicker;
import android.widget.TimePicker;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import java.util.Calendar;

import br.com.fatec.projetointegrador.Autenticacao.TelaLoginActivity;
import br.com.fatec.projetointegrador.R;
import br.com.fatec.projetointegrador.TelaAgendarActivity;

@RunWith(AndroidJUnit4.class)
public class TelaAgendarActivityTest {

    @Before
    public void setUp() {
        ActivityScenario.launch(TelaLoginActivity.class);
        // Esse usuário precisa existir no banco de dados para não dar erro
        onView(withId(R.id.editTextUsername)).perform(typeText("maria@teste.com"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextEmail)).perform(typeText("senha123"), closeSoftKeyboard());
        onView(withId(R.id.btnEntrar)).perform(click());
    }

    @Test
    public void testAgendamentoSimples() throws InterruptedException {
        Thread.sleep(2000);

        Intent intent = new Intent(androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getTargetContext(), TelaAgendarActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getTargetContext().startActivity(intent);

        Thread.sleep(1000);

        onView(withId(R.id.spinner_descricao)).perform(typeText("Agendamento de teste"), closeSoftKeyboard());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        onView(withId(R.id.data_aula)).perform(click());
        onView(isAssignableFrom(DatePicker.class)).perform(setDate(year, month, day));
        onView(withText("OK")).perform(click());


        Thread.sleep(1000);

        onView(withId(R.id.horario_aula)).perform(click());
        onView(isAssignableFrom(TimePicker.class)).perform(setTime(12, 30));
        onView(withText("OK")).perform(click());

        Thread.sleep(1000);

        onView(withId(R.id.spinner_local)).perform(click());
        onData(anything()).atPosition(0).perform(click());

        onView(withId(R.id.spinner_tipoAgen)).perform(click());
        onData(anything()).atPosition(0).perform(click());

        onView(withId(R.id.spinner_profissional)).perform(click());
        onData(anything()).atPosition(0).perform(click());

        onView(withId(R.id.btnAgendar)).perform(click());
    }
}

