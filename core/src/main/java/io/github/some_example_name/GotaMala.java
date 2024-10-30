package io.github.some_example_name;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;
public class GotaMala extends Gota {
    private Sound hurtSound;

    public GotaMala(float x, float y, Sound hurtSound) {
        super(x, y, new Texture(Gdx.files.internal("dropBad.png"))); // Pasa la textura
        this.hurtSound = hurtSound;
    }

    @Override
    public void interactuarConTarro(Tarro tarro) {
        tarro.dañar();
        hurtSound.play();
    }
}


