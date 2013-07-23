/**
 * 
 */
package com.punchline.javalib.entities.components.physical;

import com.badlogic.gdx.math.Vector2;
import com.punchline.javalib.entities.ComponentManager;

/**
 * @author William
 * @created Jul 23, 2013
 * The particle class.
 */
public class Particle implements Velocity, Transform {
	//FIELDS
	/**
	 * The position of the particle.
	 */
	private Vector2 position;
	
	/**
	 * The rotation of the particle.
	 */
	private float rotation;
	
	/**
	 * The origin of the particle.
	 */
	private Vector2 origin;
	
	
	/**
	 * The linear velocity of a particle.
	 */
	private Vector2 linearVelocity;
	
	/**
	 * The angular velocity of a particle.
	 */
	private float angularVelocity;
	
	
	//CONSTRUCTOR
	/**
	 * Initializes a particle with a given transform.
	 * @param position The position of the particle.
	 * @param rotation The rotation of the particle.
	 * @param origin The origin of the particle.
	 */
	public Particle(Vector2 position, float rotation, Vector2 origin){
		linearVelocity = Vector2.Zero;
		angularVelocity = 0f;
		
		this.origin = origin;
		this.rotation = rotation;
		this.position = position;
	}
	
	/**
	 * Initializes a particle with a given transform (without an origin)
	 * @param position The position of the particle.
	 * @param rotation The rotation of the particle.
	 */
	public Particle(Vector2 position, float rotation){
		this(position, rotation, Vector2.Zero);
	}

	
	
	
	//METHODS
	/** 
	 * {@inheritDoc}
	 * @see com.punchline.javalib.entities.components.physical.Transform#getPosition()
	 */
	@Override
	public Vector2 getPosition() {
		return position;
	}

	/** 
	 * {@inheritDoc}
	 * @see com.punchline.javalib.entities.components.physical.Transform#setPosition(com.badlogic.gdx.math.Vector2)
	 */
	@Override
	public Vector2 setPosition(Vector2 position) {
		this.position = position;
		return getPosition();
	}

	/** 
	 * {@inheritDoc}
	 * @see com.punchline.javalib.entities.components.physical.Transform#getRotation()
	 */
	@Override
	public float getRotation() {
		return rotation;
	}

	/** 
	 * {@inheritDoc}
	 * @see com.punchline.javalib.entities.components.physical.Transform#setRotation(float)
	 */
	@Override
	public float setRotation(float rotation) {
		this.rotation = rotation;
		return getRotation();
	}

	/** 
	 * {@inheritDoc}
	 * @see com.punchline.javalib.entities.components.physical.Transform#getOrigin()
	 */
	@Override
	public Vector2 getOrigin() {
		return origin;
	}
	
	/**
	 * Adds the Transform and Velocity interfaces to the entity.
	 */
	@Override
	public void onAdd(ComponentManager container) {
		container.addComponent(Transform.class, this);
		container.addComponent(Velocity.class, this);
	}

	/** 
	 * {@inheritDoc}
	 * @see com.punchline.javalib.entities.Component#onRemove(com.punchline.javalib.entities.ComponentManager)
	 */
	@Override
	public void onRemove(ComponentManager container) {
	}

}
