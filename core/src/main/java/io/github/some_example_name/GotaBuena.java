package io.github.some_example_name;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;
public class GotaBuena extends Gota {
    private Sound dropSound;

    public GotaBuena(float x, float y, Sound dropSound) {
        super(x, y, new Texture(Gdx.files.internal("drop.png"))); // Pasa la textura
        this.dropSound = dropSound;
    }

    @Override
    public void interactuarConTarro(Tarro tarro) {
        tarro.sumarPuntos(10);
        dropSound.play();
    }
}


