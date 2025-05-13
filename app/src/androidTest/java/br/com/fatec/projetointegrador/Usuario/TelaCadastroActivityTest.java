package br.com.fatec.projetointegrador.Usuario;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.fatec.projetointegrador.Autenticacao.TelaCadastroActivity;
import br.com.fatec.projetointegrador.R;

@RunWith(AndroidJUnit4.class)
public class TelaCadastroActivityTest {

    @Before
    public void setUp() {
        Intents.init();
        ActivityScenario.launch(TelaCadastroActivity.class);
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testCamposObrigatoriosVazios() {
        onView(withId(R.id.btnRegistrar)).perform(click());
        // Neste caso, você verifica manualmente se o Toast aparece (instrumented tests não capturam Toasts por padrão)
        // Para isso, seria necessário usar uma lib como ToastMatcher ou verificar via log
    }

    @Test
    public void testSenhaDiferenteDaConfirmacao() {
        onView(withId(R.id.editTextTextUsername)).perform(typeText("Gabriel"));
        onView(withId(R.id.editTextTextEmail)).perform(typeText("senha123"));
        onView(withId(R.id.editTextTextPassword)).perform(typeText("senha123"));
        onView(withId(R.id.editTextTextConfirmPassword)).perform(typeText("senha321"));
        onView(withId(R.id.btnRegistrar)).perform(click());
        // Toast de senhas diferentes deve aparecer (mesmo ponto do anterior: precisa capturar via ToastMatcher)
    }

    @Test
    public void testEmailInvalido() {
        onView(withId(R.id.editTextTextUsername)).perform(typeText("Gabriel"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextEmail)).perform(typeText("gabrielsemarroba"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextEmail)).perform(typeText("senha123"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword)).perform(typeText("senha123"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextConfirmPassword)).perform(typeText("senha123"), closeSoftKeyboard());

        onView(withId(R.id.btnRegistrar)).perform(click());

        // Continua na tela ou exibe Toast de erro
        intended(hasComponent(TelaCadastroActivity.class.getName()));
    }

    @Test
    public void testCadastroComSucesso() {
        onView(withId(R.id.editTextTextUsername)).perform(typeText("Maria da Silva"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextEmail)).perform(typeText("maria@teste.com"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword)).perform(typeText("senha123"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextConfirmPassword)).perform(typeText("senha123"), closeSoftKeyboard());

        // Supondo que o botão "Registrar" envia os dados para API e redireciona para a Tela de Login
        onView(withId(R.id.btnRegistrar)).perform(click());

        //Espresso.onIdle();

        // Verifica se a tela seguinte foi chamada (ex: TelaCadastroActivity)
        intended(hasComponent("br.com.fatec.projetointegrador.Autenticacao.TelaCadastroActivity"));
    }


}