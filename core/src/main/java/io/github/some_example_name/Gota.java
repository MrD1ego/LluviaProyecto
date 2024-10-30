package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Gota {
    protected Rectangle area;
    protected Texture textura; // Añade un atributo para la textura

    public Gota(float x, float y, Texture textura) {
        this.area = new Rectangle(x, y, 64, 64);
        this.textura = textura; // Inicializa la textura
    }

    public Rectangle getArea() {
        return area;
    }

    public abstract void interactuarConTarro(Tarro tarro);

    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, area.x, area.y); // Dibuja la gota
    }
}


