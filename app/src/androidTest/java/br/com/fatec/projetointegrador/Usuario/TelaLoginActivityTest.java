package br.com.fatec.projetointegrador.Usuario;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.fatec.projetointegrador.Autenticacao.TelaLoginActivity;
import br.com.fatec.projetointegrador.Home;
import br.com.fatec.projetointegrador.R;

@RunWith(AndroidJUnit4.class)
public class TelaLoginActivityTest {

    @Before
    public void setUp() {
        Intents.init();
        ActivityScenario.launch(TelaLoginActivity.class);
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testLoginComSucesso() {
        onView(withId(R.id.editTextUsername)).perform(typeText("joao.silva@teste.com"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextEmail)).perform(typeText("batatinha123"), closeSoftKeyboard());
        onView(withId(R.id.btnEntrar)).perform(click());

        intended(hasComponent(Home.class.getName()));
    }

    @Test
    public void testLoginEmailIncorreto() {
        onView(withId(R.id.editTextUsername)).perform(typeText("emailinvalido@errado.com"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextEmail)).perform(typeText("senha123"), closeSoftKeyboard());
        onView(withId(R.id.btnEntrar)).perform(click());
    }

    @Test
    public void testLoginSenhaIncorreta() {
        onView(withId(R.id.editTextUsername)).perform(typeText("maria@teste.com"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextEmail)).perform(typeText("senhaalternativa"), closeSoftKeyboard());
        onView(withId(R.id.btnEntrar)).perform(click());
    }

    @Test
    public void testUsuarioNaoExiste() {
        onView(withId(R.id.editTextUsername)).perform(typeText("naoexiste@teste.com"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextEmail)).perform(typeText("algumasenha"), closeSoftKeyboard());
        onView(withId(R.id.btnEntrar)).perform(click());
    }
}
