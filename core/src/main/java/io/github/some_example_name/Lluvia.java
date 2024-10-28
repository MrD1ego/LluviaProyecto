package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Lluvia {
	private Array<Rectangle> rainDropsPos;
	private Array<Integer> rainDropsType;
    private long lastDropTime;
    private Texture gotaBuena;
    private Texture gotaMala;

    private Texture gotaDorada;

    private Sound shinySound;
    private Sound dropSound;
    private Music rainMusic;

	public Lluvia(Texture gotaBuena, Texture gotaMala, Texture gotaDorada, Sound shinySound , Sound ss, Music mm) {
		rainMusic = mm;
		dropSound = ss;
        this.shinySound = shinySound;
		this.gotaBuena = gotaBuena;
		this.gotaMala = gotaMala;
        this.gotaDorada = gotaDorada;
	}

	public void crear() {
		rainDropsPos = new Array<Rectangle>();
		rainDropsType = new Array<Integer>();
		crearGotaDeLluvia();
	      // start the playback of the background music immediately
	      rainMusic.setLooping(true);
	      rainMusic.play();
	}

	private void crearGotaDeLluvia() {
	      Rectangle raindrop = new Rectangle();
	      raindrop.x = MathUtils.random(0, 800-64);
	      raindrop.y = 480;
	      raindrop.width = 64;
	      raindrop.height = 64;
	      rainDropsPos.add(raindrop);
	      // ver el tipo de gota

        int randomTipo = MathUtils.random(1, 10);
        if (randomTipo < 3) {
            rainDropsType.add(1); // gota mala
        } else if (randomTipo == 10) {
            rainDropsType.add(3); // gota dorada
        } else {
            rainDropsType.add(2); // gota buena
        }


	      lastDropTime = TimeUtils.nanoTime();
	   }

   public void actualizarMovimiento(Tarro tarro) {
	   // generar gotas de lluvia
	   if(TimeUtils.nanoTime() - lastDropTime > 100000000) crearGotaDeLluvia();


	   // revisar si las gotas cayeron al suelo o chocaron con el tarro
	   for (int i=0; i < rainDropsPos.size; i++ ) {
		  Rectangle raindrop = rainDropsPos.get(i);
	      raindrop.y -= 300 * Gdx.graphics.getDeltaTime();
	      //cae al suelo y se elimina
	      if(raindrop.y + 64 < 0) {
	    	  rainDropsPos.removeIndex(i);
	    	  rainDropsType.removeIndex(i);
	      }
	      if(raindrop.overlaps(tarro.getArea())) { //la gota choca con el tarro
	    	if(rainDropsType.get(i)==1) { // gota dañina
	    	  tarro.dañar();
	    	  rainDropsPos.removeIndex(i);
	          rainDropsType.removeIndex(i);
	      	}else if (rainDropsType.get(i) == 2) { // gota a recolectar
	    	  tarro.sumarPuntos(10);
	          dropSound.play();
	          rainDropsPos.removeIndex(i);
	          rainDropsType.removeIndex(i);
	      	}else if (rainDropsType.get(i) == 3){
              tarro.sumarPuntos(50);
              shinySound.play();
              rainDropsPos.removeIndex(i);
              rainDropsType.removeIndex(i);
            }
	      }
	   }
   }

   public void actualizarDibujoLluvia(SpriteBatch batch) {

	  for (int i=0; i < rainDropsPos.size; i++ ) {
		  Rectangle raindrop = rainDropsPos.get(i);
		  if(rainDropsType.get(i)==1) // gota dañina
	         batch.draw(gotaMala, raindrop.x, raindrop.y);
		  else if(rainDropsType.get(i)==2)
			 batch.draw(gotaBuena, raindrop.x, raindrop.y);
          else if (rainDropsType.get(i)==3)
              batch.draw(gotaDorada, raindrop.x, raindrop.y);
	   }
   }
   public void destruir() {
	      dropSound.dispose();
	      rainMusic.dispose();
   }

}