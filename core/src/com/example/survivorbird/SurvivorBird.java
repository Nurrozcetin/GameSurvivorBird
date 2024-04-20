package com.example.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

import sun.font.GlyphRenderData;

public class SurvivorBird extends ApplicationAdapter {
	SpriteBatch batch; //objeler
	Texture background;
	Texture bird;
	Texture enemy1;
	Texture enemy2;
	Texture enemy3;
	float birdX = 0;
	float birdY = 0;
	int gameState = 0;
	float birdVelocity = 0;
	float enemyVelocity = 4;
	Random random;
	int numOfEnemies = 4;
	int score = 0;
	int scoreEnemy = 0;
	float [] enemyX = new float[numOfEnemies];
	float [] enemyOffset1 = new float[numOfEnemies];
	float [] enemyOffset2 = new float[numOfEnemies];
	float [] enemyOffset3 = new float[numOfEnemies];

	float distance = 0;
	Circle birdCircle;
	Circle[] enemyCircles1;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;
	BitmapFont fontScore;
	BitmapFont fontGameOver;
	//ShapeRenderer shapeRenderer;
	@Override
	public void create () { //oyun acildiginda
		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture("bird.png");
		enemy1 = new Texture("enemy.png");
		enemy2 = new Texture("enemy.png");
		enemy3 = new Texture("enemy.png");

		birdX = Gdx.graphics.getWidth()/6;
		birdY = Gdx.graphics.getHeight()/3;

		birdCircle = new Circle();
		enemyCircles1 = new Circle[numOfEnemies];
		enemyCircles2 = new Circle[numOfEnemies];
		enemyCircles3 = new Circle[numOfEnemies];

		distance = Gdx.graphics.getWidth()/2;
		random = new Random();
		//shapeRenderer = new ShapeRenderer();

		fontScore = new BitmapFont();
		fontScore.setColor(Color.WHITE);
		fontScore.getData().setScale(8);

		fontGameOver = new BitmapFont();
		fontGameOver.setColor(Color.BLACK);
		fontGameOver.getData().setScale(10);
		for(int i=0; i<numOfEnemies; i++){
			enemyX[i] = Gdx.graphics.getWidth() - enemy1.getWidth()/2 + i * distance;

			enemyOffset1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

			enemyCircles1[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();

		}
	}

	@Override
	public void render () { //oyun devam ettigi surece devamli cagrilan surekli olmasini istedigimz seyler
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if(gameState == 1){
			if(enemyX[scoreEnemy] <  Gdx.graphics.getWidth() - enemy1.getWidth()/2 ) {
				score++;
				if(scoreEnemy < (numOfEnemies -1)){
					scoreEnemy++;
				}else{
					scoreEnemy = 0;
				}
			}
			if(Gdx.input.justTouched()){
				birdVelocity =-20;
			}

			for(int i=0; i<numOfEnemies; i++){
				if(enemyX[i] < Gdx.graphics.getWidth()/15){
					enemyX[i] = enemyX[i] + numOfEnemies * distance;

					enemyOffset1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

				}else{
					enemyX[i] = enemyX[i] - enemyVelocity;
				}

				batch.draw(enemy1, enemyX[i], Gdx.graphics.getHeight()/2 + enemyOffset1[i], Gdx.graphics.getWidth()/15, Gdx.graphics.getHeight()/10);
				batch.draw(enemy2, enemyX[i], Gdx.graphics.getHeight()/2 + enemyOffset2[i], Gdx.graphics.getWidth()/15, Gdx.graphics.getHeight()/10);
				batch.draw(enemy3, enemyX[i], Gdx.graphics.getHeight()/2 + enemyOffset3[i], Gdx.graphics.getWidth()/15 , Gdx.graphics.getHeight()/10);

				enemyCircles1[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth()/30 , enemyOffset1[i]  + Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth()/ 30);
				enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth()/30 , enemyOffset2[i]  + Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth()/ 30);
				enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth()/30 , enemyOffset3[i]  + Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth()/ 30);
			}

			if(birdY > 0){
				birdVelocity ++ ;
				birdY = birdY - birdVelocity;
			}else{
				gameState = 2;
			}

		}else if(gameState == 0){
			if(Gdx.input.justTouched()){
				gameState = 1;
			}

		} else if (gameState == 2) {
			fontGameOver.draw(batch, "Game Over! Tap To Try Again!", Gdx.graphics.getWidth()/6, Gdx.graphics.getHeight()/2 );

			if(Gdx.input.justTouched()){
				gameState = 1;

				birdY = Gdx.graphics.getHeight()/3;

				for(int i=0; i<numOfEnemies; i++){
					enemyX[i] = Gdx.graphics.getWidth() - enemy1.getWidth()/2 + i * distance;

					enemyOffset1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

					enemyCircles1[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3[i] = new Circle();

				}

				birdVelocity = 0;
				score = 0;
				scoreEnemy = 0;
			}

		}

		batch.draw(bird, birdX, birdY,Gdx.graphics.getWidth()/15, Gdx.graphics.getHeight()/10);
		fontScore.draw(batch, String.valueOf(score), 100,200);
		batch.end();

		birdCircle.set(birdX + Gdx.graphics.getWidth()/30, birdY + Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/30);

		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);

		for(int i = 0; i < numOfEnemies; i++){
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth()/30 , Gdx.graphics.getHeight()/2 + enemyOffset1[i], Gdx.graphics.getWidth()/30);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth()/30 , Gdx.graphics.getHeight()/2 + enemyOffset2[i], Gdx.graphics.getWidth()/30);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth()/30 , Gdx.graphics.getHeight()/2 + enemyOffset3[i] , Gdx.graphics.getWidth()/30);
			if(Intersector.overlaps(birdCircle, enemyCircles1[i]) || Intersector.overlaps(birdCircle, enemyCircles2[i]) || Intersector.overlaps(birdCircle, enemyCircles3[i])) {
				gameState = 2;
			}
		}
		//shapeRenderer.end();
	}
	
	@Override
	public void dispose () {

	}
}
