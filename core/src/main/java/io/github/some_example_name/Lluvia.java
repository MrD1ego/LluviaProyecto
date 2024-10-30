package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Lluvia {
    private Array<Gota> gotas;
    private Texture gotaBuena;
    private Texture gotaMala;
    private Texture gotaDorada;
    private Sound dropSound;
    private Sound hurtSound;
    private Sound shinySound;
    private Music rainMusic;
    private long lastDropTime;

    public Lluvia(Texture gotaBuena, Texture gotaMala, Texture gotaDorada, Sound shinySound, Sound dropSound, Sound hurtSound, Music rainMusic) {
        this.gotaBuena = gotaBuena;
        this.gotaMala = gotaMala;
        this.gotaDorada = gotaDorada;
        this.shinySound = shinySound;
        this.dropSound = dropSound;
        this.hurtSound = hurtSound;
        this.rainMusic = rainMusic;
        gotas = new Array<>();
        lastDropTime = 0;

        rainMusic.setLooping(true);
        rainMusic.play();
    }

    public void crear() {
        crearGotaDeLluvia();
    }

    private void crearGotaDeLluvia() {
        int randomTipo = MathUtils.random(1, 10);
        Gota nuevaGota;

        if (randomTipo < 3) {
            nuevaGota = new GotaMala(MathUtils.random(0, Gdx.graphics.getWidth()), Gdx.graphics.getHeight(), hurtSound);
        } else if (randomTipo == 10) {
            nuevaGota = new GotaDorada(MathUtils.random(0, Gdx.graphics.getWidth()), Gdx.graphics.getHeight(), shinySound);
        } else {
            nuevaGota = new GotaBuena(MathUtils.random(0, Gdx.graphics.getWidth()), Gdx.graphics.getHeight(), dropSound);
        }

        gotas.add(nuevaGota);
        lastDropTime = TimeUtils.nanoTime();
    }

    public void actualizarMovimiento(Tarro tarro) {
        // generar gotas de lluvia
        if (TimeUtils.nanoTime() - lastDropTime > 100000000) crearGotaDeLluvia();

        // revisar si las gotas cayeron al suelo o chocaron con el tarro
        for (int i = 0; i < gotas.size; i++) {
            Gota gota = gotas.get(i);
            gota.getArea().y -= 300 * Gdx.graphics.getDeltaTime();
            // cae al suelo y se elimina
            if (gota.getArea().y + 64 < 0) {
                gotas.removeIndex(i);
            }
            if (gota.getArea().overlaps(tarro.getArea())) { // la gota choca con el tarro
                gota.interactuarConTarro(tarro);
                gotas.removeIndex(i);
            }
        }
    }

    public void actualizarDibujoLluvia(SpriteBatch batch) {
        for (Gota gota : gotas) {
            gota.dibujar(batch);
        }
    }

    public void destruir() {
        rainMusic.stop();
        rainMusic.dispose();
    }
}

