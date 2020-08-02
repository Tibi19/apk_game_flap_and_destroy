package com.bat.fnd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class EnemyManager {

	private static Texture texture;
	private static TextureRegion[][] tmp;
	public static Animation[] animations, animationsFlipped;
	public static FixtureDef[] fixtureDefs, fixtureDefsFlipped;
	public static float[] widths, heights;
	private static final float[] ratios = {18, 15, 14, 14, 8, 6, 7, 2};
	public static final float[] forces = {5, 5, 5, 10, 17, 20, 100, 25};
	public static final int[] values = {1, 1, 3, 4, 5, 6, 7, 20};
	public static final int[] frSpeeds = {11, 11, 11, 13, 13, 16, 14, 13};
	public static float[] bodyPos, bodyHeight;
	public static final int FBALL = 0, THUG = 1, SGULL = 2, TOUC = 3, VULT = 4, UFO = 5, PIG = 6, DRAG = 7;
	
	public static void initialize(){
		animations = new Animation[8];
		animationsFlipped = new Animation[8];
		fixtureDefs = new FixtureDef[8];
		fixtureDefsFlipped = new FixtureDef[8];
		widths = new float[8];
		heights = new float[8];
		bodyPos = new float[8];
		bodyHeight = new float[8];
		
		createFootballer();
		createThug();
		createPescarus();
		createToucan();
		createVulture();
		createUfo();
		createPig();
		createDragon();
	}
	
	/*public static void createTexture(int index){
		switch(index){
		case FOOTBALLER:
			createFootballer();
			break;
		case THUG:
			createThug();
			break;
		case PESCARUS:
			createPescarus();
			break;
		case VULTURE:
			createVulture();
			break;
		case TOUCAN:
			createToucan();
			break;
		case UFO:
			createUfo();
			break;
		case PIG:
			createPig();
			break;
		case DRAGON:
			createDragon();
			break;
		}
	}*/

		private static void createDragon() {
			texture = new Texture(Gdx.files.internal("dragon.png"));
	    	
			tmp = TextureRegion.split(texture, 512, 725);
	    	TextureRegion[] aux = new TextureRegion[3];
	    	aux[0] = tmp[0][0];
	    	aux[1] = tmp[0][1];
	    	aux[2] = tmp[0][2];
	    	
	    	tmp = TextureRegion.split(texture, 512, 725);
	    	TextureRegion[] aux2 = new TextureRegion[3];
	    	aux2[0] = tmp[0][0];
	    	aux2[0].flip(true, false);
	    	aux2[1] = tmp[0][1];
	    	aux2[1].flip(true, false);
	    	aux2[2] = tmp[0][2];
	    	aux2[2].flip(true, false);
	    	
	    	animations[DRAG] = new Animation(1f, aux);
	    	animationsFlipped[DRAG] = new Animation(1f, aux2);
	    	
	    	float width = widths[DRAG] = Util.generateWidth(ratios[DRAG]);
	    	float height = heights[DRAG] = Util.generateHeight(width, aux[0].getRegionWidth(), aux[0].getRegionHeight());
	    	/*float[] vertices = {
	    			width*0.0035f, height*0.4899f,
	    			width*0.046f, height*0.4675f,
	    			width*0.1451f, height*0.4949f,
	    			width*0.338f, height*0.4549f,
	    			width*0.5858f, height*0.47f,
	    			width*0.9912f, height*0.5762f,
	    			width*0.4354f, height*0.5275f,
	    			width*0.1221f, height*0.5412f
	    	};
	    	float[] verticesFlipped = {
	    			width*0.9964f, height*0.4899f,
	    			width*0.9539f, height*0.4675f,
	    			width*0.8548f, height*0.4949f,
	    			width*0.6619f, height*0.4549f,
	    			width*0.4141f, height*0.47f,
	    			width*0.0087f, height*0.5762f,
	    			width*0.5645f, height*0.5275f,
	    			width*0.8778f, height*0.5412f
	    	};*/
	    	float[] vertices = {
	    			width*0.0141f, height*0.4837f,
	    			width*0.5876f, height*0.4737f,
	    			width*0.9912f, height*0.5787f,
	    			width*0.1398f, height*0.5287f,
	    			width*0.1150f, height*0.5399f
	    	};
	    	float[] verticesFlipped = {
	    			width*0.9858f, height*0.4837f,
	    			width*0.4123f, height*0.4737f,
	    			width*0.0087f, height*0.5787f,
	    			width*0.8601f, height*0.5287f,
	    			width*0.8849f, height*0.5399f
	    	};
	    	bodyPos[DRAG] = height * 0.2875f;
	    	bodyHeight[DRAG] = height * 0.7324f;
	    	fixtureDefs[DRAG] = new FixtureDef();
	    	fixtureDefsFlipped[DRAG] = new FixtureDef();
	    	PolygonShape shape = new PolygonShape();
	    	PolygonShape shapeFlipped = new PolygonShape();
	    	shape.set(vertices);
	    	fixtureDefs[DRAG].shape = shape;
	    	shapeFlipped.set(verticesFlipped);
	    	fixtureDefsFlipped[DRAG].shape = shapeFlipped;
		}

		private static void createPig() {
			texture = new Texture(Gdx.files.internal("pig.png"));
	    	
			tmp = TextureRegion.split(texture, 256, 218);
	    	TextureRegion[] aux = new TextureRegion[3];
	    	aux[0] = tmp[0][0];
	    	aux[1] = tmp[0][1];
	    	aux[2] = tmp[0][2];
	    	
	    	tmp = TextureRegion.split(texture, 256, 218);
	    	TextureRegion[] aux2 = new TextureRegion[3];
	    	aux2[0] = tmp[0][0];
	    	aux2[0].flip(true, false);
	    	aux2[1] = tmp[0][1];
	    	aux2[1].flip(true, false);
	    	aux2[2] = tmp[0][2];
	    	aux2[2].flip(true, false);
	    	
	    	animations[PIG] = new Animation(1f, aux);
	    	animationsFlipped[PIG] = new Animation(1f, aux2);
	    	
	    	float width = widths[PIG] = Util.generateWidth(ratios[PIG]);
	    	float height = heights[PIG] = Util.generateHeight(width, aux[0].getRegionWidth(), aux[0].getRegionHeight());
	    	float[] vertices = {
	    			width*0.0425f, height*0.325f,
	    			width*0.1862f, height*0.06f,
	    			width*0.4289f, height*0.1799f,
	    			width*0.9761f, height*0.0175f,
	    			width*0.8313f, height*0.4687f,
	    			width*0.2363f, height*0.5787f,
	    			width*0.1128f, height*0.5125f,
	    			width*0.0159f, height*0.4025f
	    	};
	    	float[] verticesFlipped = {
	    			width*0.9574f, height*0.325f,
	    			width*0.8137f, height*0.06f,
	    			width*0.571f, height*0.1799f,
	    			width*0.0238f, height*0.0175f,
	    			width*0.1686f, height*0.4687f,
	    			width*0.7636f, height*0.5787f,
	    			width*0.8871f, height*0.5125f,
	    			width*0.984f, height*0.4025f
	    	};
	    	bodyPos[PIG] = height * 0.0175f;
	    	bodyHeight[PIG] = height * 0.5787f;
	    	fixtureDefs[PIG] = new FixtureDef();
	    	fixtureDefsFlipped[PIG] = new FixtureDef();
	    	PolygonShape shape = new PolygonShape();
	    	PolygonShape shapeFlipped = new PolygonShape();
	    	shape.set(vertices);
	    	fixtureDefs[PIG].shape = shape;
	    	shapeFlipped.set(verticesFlipped);
	    	fixtureDefsFlipped[PIG].shape = shapeFlipped;
		}

		private static void createUfo() {
			texture = new Texture(Gdx.files.internal("ufo.png"));
	    	
			tmp = TextureRegion.split(texture, 197, 74);
	    	TextureRegion[] aux = new TextureRegion[2];
	    	aux[0] = tmp[0][0];
	    	aux[1] = tmp[0][1];
	    	
	    	animations[UFO] = animationsFlipped[UFO] = new Animation(1f, aux);
	    	
	    	float width = widths[UFO] = Util.generateWidth(ratios[UFO]);
	    	float height = heights[UFO] = Util.generateHeight(width, aux[0].getRegionWidth(), aux[0].getRegionHeight());
	    	float[] vertices = {
	    			width*0.014f, height*0.2725f,
	    			width*0.508f, height*0.03f,
	    			width*0.986f, height*0.31f,
	    			width*0.6836f, height*0.4875f,
	    			width*0.5427f, height*0.9325f,
	    			width*0.4488f, height*0.9375f,
	    			width*0.3061f, height*0.4849f,
	    			width*0.0178f, height*0.3624f
	    	};
	    	float[] verticesFlipped = {
	    			width*0.9859f, height*0.2725f,
	    			width*0.4919f, height*0.03f,
	    			width*0.0139f, height*0.31f,
	    			width*0.3163f, height*0.4875f,
	    			width*0.4572f, height*0.9325f,
	    			width*0.5511f, height*0.9375f,
	    			width*0.6938f, height*0.4849f,
	    			width*0.9821f, height*0.3624f
	    	};
	    	bodyPos[UFO] = height * 0.03f;
	    	bodyHeight[UFO] = height * 0.9375f;
	    	fixtureDefs[UFO] = new FixtureDef();
	    	fixtureDefsFlipped[UFO] = new FixtureDef();
	    	PolygonShape shape = new PolygonShape();
	    	PolygonShape shapeFlipped = new PolygonShape();
	    	shape.set(vertices);
	    	fixtureDefs[UFO].shape = shape;
	    	shapeFlipped.set(verticesFlipped);
	    	fixtureDefsFlipped[UFO].shape = shapeFlipped;
		}

		private static void createVulture() {
			texture = new Texture(Gdx.files.internal("vulture.png"));
	    	
			tmp = TextureRegion.split(texture, 158, 170);
	    	TextureRegion[] aux = new TextureRegion[3];
	    	aux[0] = tmp[0][0];
	    	aux[1] = tmp[0][1];
	    	aux[2] = tmp[0][2];
	    	
	    	tmp = TextureRegion.split(texture, 158, 170);
	    	TextureRegion[] aux2 = new TextureRegion[3];
	    	aux2[0] = tmp[0][0];
	    	aux2[0].flip(true, false);
	    	aux2[1] = tmp[0][1];
	    	aux2[1].flip(true, false);
	    	aux2[2] = tmp[0][2];
	    	aux2[2].flip(true, false);
	    	
	    	animations[VULT] = new Animation(1f, aux);
	    	animationsFlipped[VULT] = new Animation(1f, aux2);
	    	
	    	float width = widths[VULT] = Util.generateWidth(ratios[VULT]);
	    	float height = heights[VULT] = Util.generateHeight(width, aux[0].getRegionWidth(), aux[0].getRegionHeight());
	    	float[] vertices = {
	    			width*0.0053f, height*0.3887f,
	    			width*0.3954f, height*0.2875f,
	    			width*0.4532f, height*0.3175f,
	    			width*0.8634f, height*0.3175f,
	    			width*0.5191f, height*0.5037f,
	    			width*0.3174f, height*0.495f,
	    			width*0.2219f, height*0.425f,
	    			width*0.1183f, height*0.435f
	    	};
	    	float[] verticesFlipped = {
	    			width*0.9946f, height*0.3887f,
	    			width*0.6045f, height*0.2875f,
	    			width*0.5467f, height*0.3175f,
	    			width*0.1365f, height*0.3175f,
	    			width*0.4808f, height*0.5037f,
	    			width*0.6825f, height*0.495f,
	    			width*0.778f, height*0.425f,
	    			width*0.8816f, height*0.435f
	    	};
	    	bodyPos[VULT] = height * 0.2875f;
	    	bodyHeight[VULT] = height * 0.5037f;
	    	fixtureDefs[VULT] = new FixtureDef();
	    	fixtureDefsFlipped[VULT] = new FixtureDef();
	    	PolygonShape shape = new PolygonShape();
	    	PolygonShape shapeFlipped = new PolygonShape();
	    	shape.set(vertices);
	    	fixtureDefs[VULT].shape = shape;
	    	shapeFlipped.set(verticesFlipped);
	    	fixtureDefsFlipped[VULT].shape = shapeFlipped;
		}

		private static void createToucan() {
			texture = new Texture(Gdx.files.internal("toucan.png"));
	    	
			tmp = TextureRegion.split(texture, 128, 116);
	    	TextureRegion[] aux = new TextureRegion[2];
	    	aux[0] = tmp[0][0];
	    	aux[1] = tmp[0][1];
	    	
	    	tmp = TextureRegion.split(texture, 128, 116);
	    	TextureRegion[] aux2 = new TextureRegion[2];
	    	aux2[0] = tmp[0][0];
	    	aux2[0].flip(true, false);
	    	aux2[1] = tmp[0][1];
	    	aux2[1].flip(true, false);
	    	
	    	animations[TOUC] = new Animation(1f, aux);
	    	animationsFlipped[TOUC] = new Animation(1f, aux2);
	    	
	    	float width = widths[TOUC] = Util.generateWidth(ratios[TOUC]);
	    	float height = heights[TOUC] = Util.generateHeight(width, aux[0].getRegionWidth(), aux[0].getRegionHeight());
	    	float[] vertices = {
	    			width*0.009f, height*0.4687f,
	    			width*0.2288f, height*0.5324f,
	    			width*0.4621f, height*0.3287f,
	    			width*0.7216f, height*0.2875f,
	    			width*0.8881f, height*0.4712f,
	    			width*0.3919f, height*0.6262f,
	    			width*0.3251f, height*0.7324f,
	    			width*0.0521f, height*0.5837f
	    	};
	    	float[] verticesFlipped = {
	    			width*0.9909f, height*0.4687f,
	    			width*0.7711f, height*0.5324f,
	    			width*0.5378f, height*0.3287f,
	    			width*0.2783f, height*0.2875f,
	    			width*0.1118f, height*0.4712f,
	    			width*0.608f, height*0.6262f,
	    			width*0.6748f, height*0.7324f,
	    			width*0.9478f, height*0.5837f
	    	};
	    	bodyPos[TOUC] = height * 0.2875f;
	    	bodyHeight[TOUC] = height * 0.7324f;
	    	fixtureDefs[TOUC] = new FixtureDef();
	    	fixtureDefsFlipped[TOUC] = new FixtureDef();
	    	PolygonShape shape = new PolygonShape();
	    	PolygonShape shapeFlipped = new PolygonShape();
	    	shape.set(vertices);
	    	fixtureDefs[TOUC].shape = shape;
	    	shapeFlipped.set(verticesFlipped);
	    	fixtureDefsFlipped[TOUC].shape = shapeFlipped;
		}

		private static void createPescarus() {
			texture = new Texture(Gdx.files.internal("pescarus.png"));
	    	
			tmp = TextureRegion.split(texture, 170, 167);
	    	TextureRegion[] aux = new TextureRegion[3];
	    	aux[0] = tmp[0][0];
	    	aux[1] = tmp[0][1];
	    	aux[2] = tmp[0][2];
	    	
	    	tmp = TextureRegion.split(texture, 170, 167);
	    	TextureRegion[] aux2 = new TextureRegion[3];
	    	aux2[0] = tmp[0][0];
	    	aux2[0].flip(true, false);
	    	aux2[1] = tmp[0][1];
	    	aux2[1].flip(true, false);
	    	aux2[2] = tmp[0][2];
	    	aux2[2].flip(true, false);
	    	
	    	animations[SGULL] = new Animation(1f, aux);
	    	animationsFlipped[SGULL] = new Animation(1f, aux2);
	    	
	    	float width = widths[SGULL] = Util.generateWidth(ratios[SGULL]);
	    	float height = heights[SGULL] = Util.generateHeight(width, aux[0].getRegionWidth(), aux[0].getRegionHeight());
	    	float[] vertices = {
	    			width*0.0085f, height*0.7999f,
	    			width*0.1841f, height*0.7475f,
	    			width*0.0884f, height*0.4712f,
	    			width*0.3818f, height*0.2262f,
	    			width*0.3536f, height*0.0175f,
	    			width*0.7281f, height*0.3112f,
	    			width*0.3659f, height*0.6762f,
	    			width*0.3646f, height*0.9774f
	    	};
	    	float[] verticesFlipped = {
	    			width*0.9914f, height*0.7999f,
	    			width*0.8158f, height*0.7475f,
	    			width*0.9115f, height*0.4712f,
	    			width*0.6181f, height*0.2262f,
	    			width*0.6463f, height*0.0175f,
	    			width*0.2718f, height*0.31125f,
	    			width*0.634f, height*0.6762f,
	    			width*0.6353f, height*0.9774f
	    	};
	    	bodyPos[SGULL] = height * 0.0175f;
	    	bodyHeight[SGULL] = height * 0.9774f;
	    	fixtureDefs[SGULL] = new FixtureDef();
	    	fixtureDefsFlipped[SGULL] = new FixtureDef();
	    	PolygonShape shape = new PolygonShape();
	    	PolygonShape shapeFlipped = new PolygonShape();
	    	shape.set(vertices);
	    	fixtureDefs[SGULL].shape = shape;
	    	shapeFlipped.set(verticesFlipped);
	    	fixtureDefsFlipped[SGULL].shape = shapeFlipped;
		}
		
		private static void createThug() {
			texture = new Texture(Gdx.files.internal("thug.png"));
	    	
			tmp = TextureRegion.split(texture, 128, 175);
	    	TextureRegion[] aux = new TextureRegion[3];
	    	aux[0] = tmp[0][0];
	    	aux[1] = tmp[0][1];
	    	aux[2] = tmp[0][2];
	    	
	    	tmp = TextureRegion.split(texture, 128, 175);
	    	TextureRegion[] aux2 = new TextureRegion[3];
	    	aux2[0] = tmp[0][0];
	    	aux2[0].flip(true, false);
	    	aux2[1] = tmp[0][1];
	    	aux2[1].flip(true, false);
	    	aux2[2] = tmp[0][2];
	    	aux2[2].flip(true, false);
	    	
	    	animations[THUG] = new Animation(1f, aux);
	    	animationsFlipped[THUG] = new Animation(1f, aux2);
	    	
	    	float width = widths[THUG] = Util.generateWidth(ratios[THUG]);
	    	float height = heights[THUG] = Util.generateHeight(width, aux[0].getRegionWidth(), aux[0].getRegionHeight());
	    	float[] vertices = {
	    			width*0.0034f, height*0.5062f,
	    			width*0.1948f, height*0.4425f,
	    			width*0.2802f, height*0.3012f,
	    			width*0.5742f, height*0.26f,
	    			width*0.7792f, height*0.3637f,
	    			width*0.5075f, height*0.5375f,
	    			width*0.2905f, height*0.5475f,
	    			width*0.152f, height*0.61f
	    	};
	    	float[] verticesFlipped = {
	    			width*0.9965f, height*0.5062f,
	    			width*0.8051f, height*0.4425f,
	    			width*0.7197f, height*0.3012f,
	    			width*0.4257f, height*0.26f,
	    			width*0.2207f, height*0.3637f,
	    			width*0.4924f, height*0.5375f,
	    			width*0.7094f, height*0.5475f,
	    			width*0.8479f, height*0.61f
	    	};
	    	bodyPos[THUG] = height * 0.26f;
	    	bodyHeight[THUG] = height * 0.61f;
	    	fixtureDefs[THUG] = new FixtureDef();
	    	fixtureDefsFlipped[THUG] = new FixtureDef();
	    	PolygonShape shape = new PolygonShape();
	    	PolygonShape shapeFlipped = new PolygonShape();
	    	shape.set(vertices);
	    	fixtureDefs[THUG].shape = shape;
	    	shapeFlipped.set(verticesFlipped);
	    	fixtureDefsFlipped[THUG].shape = shapeFlipped;
		}

		private static void createFootballer() {
			texture = new Texture(Gdx.files.internal("footballer.png"));
	    	
			tmp = TextureRegion.split(texture, 128, 161);
	    	TextureRegion[] aux = new TextureRegion[3];
	    	aux[0] = tmp[0][0];
	    	aux[1] = tmp[0][1];
	    	aux[2] = tmp[0][2];
	    	
	    	tmp = TextureRegion.split(texture, 128, 161);
	    	TextureRegion[] aux2 = new TextureRegion[3];
	    	aux2[0] = tmp[0][0];
	    	aux2[0].flip(true, false);
	    	aux2[1] = tmp[0][1];
	    	aux2[1].flip(true, false);
	    	aux2[2] = tmp[0][2];
	    	aux2[2].flip(true, false);
	    	
	    	animations[FBALL] = new Animation(1f, aux);
	    	animationsFlipped[FBALL] = new Animation(1f, aux2);
	    	
	    	float width = widths[FBALL] = Util.generateWidth(ratios[FBALL]);
	    	float height = heights[FBALL] = Util.generateHeight(width, aux[0].getRegionWidth(), aux[0].getRegionHeight());
	    	float[] vertices = {
	    			width*0.0235f, height*0.5012f,
	    			width*0.3238f, height*0.3212f,
	    			width*0.6619f, height*0.3537f,
	    			width*0.9103f, height*0.41f,
	    			width*0.1304f, height*0.6525f
	    	};
	    	float[] verticesFlipped = {
	    			width*0.97641f, height*0.5012f,
	    			width*0.6761f, height*0.3212f,
	    			width*0.338f, height*0.3537f,
	    			width*0.0896f, height*0.41f,
	    			width*0.8695f, height*0.6525f
	    	};
	    	bodyPos[FBALL] = height * 0.0175f;
	    	bodyHeight[FBALL] = height * 0.9774f;
	    	fixtureDefs[FBALL] = new FixtureDef();
	    	fixtureDefsFlipped[FBALL] = new FixtureDef();
	    	PolygonShape shape = new PolygonShape();
	    	PolygonShape shapeFlipped = new PolygonShape();
	    	shape.set(vertices);
	    	fixtureDefs[FBALL].shape = shape;
	    	shapeFlipped.set(verticesFlipped);
	    	fixtureDefsFlipped[FBALL].shape = shapeFlipped;
	    	
		}
	
		public static void dispose() {
			for(int i = 0; i <= DRAG; i++)
			{
				animations[i].getKeyFrame(0).getTexture().dispose();
				animationsFlipped[i].getKeyFrame(0).getTexture().dispose();
			}
			texture = null;
			tmp = null;
			animations = null;
			animationsFlipped = null;
			fixtureDefs = null;
			fixtureDefsFlipped = null;
		}
}
