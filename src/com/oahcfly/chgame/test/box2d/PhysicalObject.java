
package com.oahcfly.chgame.test.box2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * 
 * <pre>
 * 注意初始化方法里。我们有一个非常重要的变量 offset.
 * 它保存了物理body的中心和精灵的位置之间的偏移度，这个偏移度在物理body是不规则图形的时候尤其中哦纲要，
 * 在圆形和矩形中，偏移量为0.这个偏移量是以MassData来进行计算的。
 * 
 * RADIO是为了协调精灵和box2d设置的同步比率，如这里的RADIO=32意思就是1m代表32px。
 * 像素转换成单位：米的值，因为BOX2D使用的计量单位是米。
 * 同样的，位置和角度的设定依然是根据物理body的，在计算位置的时候，别忘了加上它的偏移量。这样，我们就可以用像素px来同步所有的物理操作了。
 * date: 2014-11-24
 * </pre>
 * @author caohao
 */
public class PhysicalObject {
    /**
    * the world body , call it directly . its not null 
    */
    public Body body;

    /**
    * the object such as a sprite, or its a AnimationSprite 
    */
    public Actor object;

    /**
    * This is a very important synchronous variables.
    * Used to box2d and animation will sync up. At the same time, 
    * can draw box2d debugging interface.
    */
    public static final float RADIO = 32.00000000f;

    /**
    * The default restitution of the rigid body
    * @see com.badlogic.gdx.physics.box2d.FixtureDef#restitution
    */
    public static final float DEFAULT_restitution = 0.3f;

    /**
    * The default angularDamping of the rigid body
    * @see com.badlogic.gdx.physics.box2d.BodyDef#angularDamping
    */
    public static final float DEFAULT_angularDamping = (float)(Math.PI / 2);

    /**
    * The default linerDamping of the rigid body
    * @see com.badlogic.gdx.physics.box2d.BodyDef#linearDamping
    */
    public static final float DEFAULT_linearDamping = 0.3f;

    /**
    * The default Friction of the rigid body
    * @see com.badlogic.gdx.physics.box2d.FixtureDef#friction
    */
    public static final float DEFAULT_friction = 0.4f;

    /**
    * The default density of the rigid body 
    * @see com.badlogic.gdx.physics.box2d.FixtureDef#density
    */
    public static final float DEFAULT_density = 1f;

    /**
    * if the physical object is recycled
    */
    private boolean visiable = false;

    /**
    * scale 
    */
    protected float scale = 1.0f;

    protected Vector2 offset = new Vector2();

    protected void init() {
        if (null == this.body || null == this.object) {
            Gdx.app.error("C2d", "the body and the sprite must be not null");
            System.exit(0);
        }
        this.body.setSleepingAllowed(true);

        this.body.setAwake(false);
        this.body.setActive(false);

        float shapeHalfWidth = this.object.getWidth() / 2;
        float shapeHalfHeight = this.object.getHeight() / 2;
        this.scale = this.object.getScaleX();

        final MassData massData = this.body.getMassData();
        this.object
                .setOrigin(-massData.center.x * RADIO + shapeHalfWidth, -massData.center.y * RADIO + shapeHalfHeight);
        offset.set(-massData.center.x * RADIO + shapeHalfWidth, -massData.center.y * RADIO + shapeHalfHeight);

        /* move it to the sprite's positon */
        //fix bug: instead of use the (-640,-640) out screen vector . we 
        //use the sprite's position so the when the init method called again and again,
        //its no effect to the whole pysicalObject
        float factor = 1 / PhysicalObject.RADIO;
        this.body.setTransform(new Vector2(this.object.getX() * factor, factor * this.object.getY()).add(offset),
                object.getRotation() * MathUtils.degreesToRadians);
    }

    private void syncObjectAndBody() {
        final Vector2 position = body.getPosition();
        this.object.setPosition(position.x * PhysicalObject.RADIO - offset.x, position.y * PhysicalObject.RADIO
                - offset.y);
        this.object.setRotation(MathUtils.radiansToDegrees * this.body.getAngle());
    }

    /** get the the box shape due to the Sprite object */
    public static Vector2 getBoxShapeVector(Sprite object) {
        float factor = 1 / PhysicalObject.RADIO / 2;
        return new Vector2(factor * object.getWidth() * object.getScaleX(), factor * object.getHeight()
                * object.getScaleY());
    }

    /** get the radius of the circle shape due to the Sprite object */
    public static float getCircleShapeRadius(Sprite object) {
        return object.getWidth() * object.getScaleX() / PhysicalObject.RADIO / 2;
    }
}
