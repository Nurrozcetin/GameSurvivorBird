package com.example.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class SurvivorBird extends ApplicationAdapter {
	SpriteBatch batch; //objeler
	Texture background;
	Texture bird;
	float birdX = 0;
	float birdY = 0;
	
	@Override
	public void create () { //oyun acildiginda
		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture("bird.png");

		birdX = Gdx.graphics.getWidth()/6;
		birdY = Gdx.graphics.getHeight()/3;
	}

	@Override
	public void render () { //oyun devam ettigi surece devamli cagrilan surekli olmasini istedigimz seyler
		batch.begin();

		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(bird, birdX, birdY,Gdx.graphics.getWidth()/15, Gdx.graphics.getHeight()/10);

		batch.end();
	}
	
	@Override
	public void dispose () {

	}
}
