package com.lostcode.javalib.entities.components.generic;

import com.lostcode.javalib.entities.Entity;
import com.lostcode.javalib.entities.EntityWorld;
import com.lostcode.javalib.entities.components.ComponentManager;
import com.lostcode.javalib.entities.components.abstracted.Stat;
import com.lostcode.javalib.entities.events.EventHandler;

/**
 * A component describing an Entity's health. When health becomes 0, the Entity
 * will be deleted.
 * 
 * @author MadcowD
 * @created Jul 23, 2013
 */
public class Health extends Stat {

	private Entity owner;
	private EntityWorld world;

	/** Whether this health component should delete its owner when it dies. */
	public boolean deleteOnDeath = true;

	/**
	 * Whether a health bar should be rendered above this component's owner. The
	 * health bar will only render if the EntityWorld contains a
	 * HealthRenderSystem.
	 */
	public boolean render = false;

	/** Invoked when the Entity that owns this component dies. */
	public final EventHandler onDeath = new EventHandler();

	/** Invoked when the Entity that owns this component takes damage. */
	public final EventHandler onDamage = new EventHandler();

	/** Invoked when the Entity that owns this component is healed. */
	public final EventHandler onHeal = new EventHandler();

	/**
	 * Constructs a Health component.
	 * 
	 * @param owner
	 *            The Entity that owns this component.
	 * @param world
	 *            The EntityWorld that owns Entity owner.
	 * @param max
	 *            The max amount of health.
	 */
	public Health(Entity owner, EntityWorld world, float max) {
		super(max);

		this.owner = owner;
		this.world = world;
	}

	@Override
	public void drain(double amount) {
		if (isEmpty())
			return; // Already dead, don't drain.

		super.drain(amount);

		if (isEmpty()) { // Dead

			if (onDeath != null)
				onDeath.invoke(owner, world);

			if (deleteOnDeath)
				owner.delete();

			return;
		}

		if (amount > 0) { // Damaged

			if (onDamage != null)
				onDamage.invoke(owner, world);

		}
	}

	@Override
	public void fill(double amount) {
		super.fill(amount);

		if (amount > 0) { // Healed

			if (onHeal != null)
				onHeal.invoke(owner, world);

		}
	}

	@Override
	public void onAdd(ComponentManager container) {
	}

	@Override
	public void onRemove(ComponentManager container) {
	}

}
