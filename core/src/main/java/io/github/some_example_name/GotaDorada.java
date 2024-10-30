package io.github.some_example_name;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;
public class GotaDorada extends Gota {
    private Sound shinySound;

    public GotaDorada(float x, float y, Sound shinySound) {
        super(x, y, new Texture(Gdx.files.internal("dropGolden.png"))); // Pasa la textura
        this.shinySound = shinySound;
    }

    @Override
    public void interactuarConTarro(Tarro tarro) {
        tarro.sumarPuntos(50);
        shinySound.play();
    }
}


