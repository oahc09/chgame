
package com.oahcfly.chgame.test.box2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.oahcfly.chgame.core.CHGame;
import com.oahcfly.chgame.core.CHScreen;

public class Box2dScreen extends CHScreen {

    Image starImage, lineImage;

    /** our box2D world **/
    protected World world;

    /** ground body to connect the mouse joint to **/
    protected Body groundBody;

    Body body;

    @Override
    public void initScreen() {
        starImage = CHGame.getInstance().getImage("screen/big_star.png");
        addActor(starImage);

        lineImage = CHGame.getInstance().getImage("screen/en_panel_piece.png");
        addActor(lineImage);

        // create the world
        world = new World(new Vector2(0, -9.8f), true);

        // we also need an invisible zero size ground body
        // to which we can connect the mouse joint
        BodyDef bodyDef = new BodyDef();
        groundBody = world.createBody(bodyDef);
        renderer = new Box2DDebugRenderer();

        //        EdgeShape shape = new EdgeShape();
        //        shape.set(new Vector2(200, 200), new Vector2(200, 400));
        // 
        //        CircleShape circleShape = new CircleShape();
        //        circleShape.setRadius(50);
        //        circleShape.setPosition(new Vector2(100, 200));
        //
        //        FixtureDef fd = new FixtureDef();
        //        fd.shape = circleShape;
        //        groundBody.createFixture(fd);
        //        circleShape.dispose();

        EdgeShape edge = new EdgeShape();
        FixtureDef boxShapeDef = new FixtureDef();
        boxShapeDef.shape = edge;
        edge.set(new Vector2(0f, 0f), new Vector2(400f, 0f));
        groundBody.createFixture(boxShapeDef);
        edge.set(new Vector2(0f, 0f), new Vector2(0f, 400f));
        groundBody.createFixture(boxShapeDef);
        edge.set(new Vector2(400f, 0f), new Vector2(400f, 400f));
        groundBody.createFixture(boxShapeDef);
        edge.set(new Vector2(400f, 400f), new Vector2(0f, 400f));
        groundBody.createFixture(boxShapeDef);

        // Create ball body and shape
        BodyDef ballBodyDef = new BodyDef();
        ballBodyDef.type = BodyType.DynamicBody;
        ballBodyDef.position.set(50f, 200f);
        
        body = world.createBody(ballBodyDef);
        body.setUserData("ball");

        CircleShape circle = new CircleShape();
        circle.setRadius(5f);
        FixtureDef ballShapeDef = new FixtureDef();
        ballShapeDef.shape = circle;
        // 密度
        ballShapeDef.density = 0f;
        ballShapeDef.friction = 0f;
        ballShapeDef.restitution = 1.0f;
        body.createFixture(ballShapeDef);
 
        body.setLinearVelocity(0, 0);
        //        调整球的摩擦力和恢复力。
        //
        //        把回复力restitution设置为1，这意味着，我们的球在碰撞的时候，将会是完全弹性碰撞。
        //
        //        把摩擦力friction设置为0，这防止球在碰撞的时候，由于摩擦损失能量，导致来回碰撞的过程中会有一点点偏差。

    }

    @Override
    public void endScreen() {
        // TODO Auto-generated method stub

    }

    @Override
    public void clickBackKey() {
        // TODO Auto-generated method stub

    }

    /** the renderer **/
    protected Box2DDebugRenderer renderer;

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        super.render(delta);
        //6和2分别为速度、位置模拟的迭代计数
        world.step(Gdx.app.getGraphics().getDeltaTime(), 6, 2);

        starImage.setPosition(body.getPosition().x, body.getPosition().y,Align.center);
 
        renderer.render(world, getStage().getCamera().combined);
    }

}
