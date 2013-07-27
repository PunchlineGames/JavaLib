package com.punchline.javalib.entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.utils.Pool;

/**
 * Contains and organizes game {@link Entity Entities}, sorting them according to tag, group, and type for easy access.
 * @author Nathaniel
 *
 */
public class EntityManager extends Pool<Entity>{
	
	/**
	 * Contains all entities.
	 */
	private List<Entity> entities = new ArrayList<Entity>();
	
	
	
	/**
	 * Contains all entities that have been added in this iteration of the game loop.
	 */
	private List<Entity> newEntities = new ArrayList<Entity>();
	
	/**
	 * Contains all entities that have been changed in this iteration of the game loop.
	 */
	private List<Entity> changedEntities = new ArrayList<Entity>();
	
	/**
	 * Contains all entities that have been removed in this iteration of the game loop.
	 */
	private List<Entity> removedEntities = new ArrayList<Entity>();
	
	int free = 0;
	
	
	/**
	 * Checks for {@link Entity Entities} that have been marked for removal, and adds them to the removal list.
	 */
	public void process() {
		
		//Clears the information/post-processing lists.
		newEntities.clear();
		changedEntities.clear();
		for(Entity e : removedEntities){
			this.free(e); //Frees the entity from the entity pool. See pooling.
		}
		
		removedEntities.clear();
		
		
		//Processes Entities that may be deleted.
		for (int i = entities.size() - 1; i >= 0; i--) {
			Entity e = entities.get(i);
			
			if (e.isDeleted()) {
				if(e.OnDelete != null)
					e.OnDelete.invoke(e);
				remove(e); //This will add e to the removal list
			}
			
			if(e.wasChanged()){
				changedEntities.add(e);
			}
		}
	}
	
	/**
	 * Adds an entity to the manager.
	 * @param e The entity to be added.
	 */
	public void add(Entity e) {
		
		//Add to entity list
		entities.add(e);
		
		//Mark for pre-processing
		newEntities.add(e);	
	}
	
	/**
	 * Removes an entity from the manager.
	 * @param e The entity to be removed.
	 */
	public void remove(Entity e) {
		
		//Remove from entity list
		entities.remove(e);
		
		//Mark for post-removal processing
		removedEntities.add(e);
	}
	
	/**
	 * Adds a new Entity to the entity pool for re-use,etc.
	 */
	@Override
	protected Entity newObject() {
		return new Entity();
	}
	
	
	
	//ACCESSORS
	
	
	
	
	
	/**
	 * @return The manager's entity list.
	 */
	public List<Entity> getEntities() {
		return entities;
	}
	
	/**
	 * @return Entities that have been added in this iteration of the game loop.
	 */
	public List<Entity> getNewEntities() {
		return newEntities;
	}
	
	/**
	 * @return Entities that have been changed in this iteration of the game loop.
	 */
	public List<Entity> getChangedEntities() {
		return changedEntities;
	}
	
	/**
	 * @return Entities that have been deleted in this iteration of the game loop.
	 */
	public List<Entity> getRemovedEntities() {
		return removedEntities;
	}

	/**
	 * Tries to get an entity based on its tag and/or its group and/or its type.
	 * @param tag The tag of the entity. "" for not inclusive search.
	 * @param group The group of the entity. "" for not inclusive search.
	 * @param type The type of the entity. "" for not inclusive search
	 * @return Null if not found and the entity if found.
	 */
	public Entity tryGetEntity(String tag, String group, String type) {
		int inclusion = 3;
		
		//Include only the search results that matter
		if(tag.isEmpty())
			inclusion--;
		if(group.isEmpty())
			inclusion--;
		if(type.isEmpty())
			inclusion--;
		if(inclusion == 0)
			return null;
		
		for(Entity e : entities)
		{
			int searchScore = 0;
			
			if(!tag.isEmpty() && e.getTag().equals(tag))
				searchScore++;
			if(!group.isEmpty() && e.getGroup().equals(group))
				searchScore++;
			if(!type.isEmpty() && e.getType().equals(type))
				searchScore++;
			
			//If the entity matches all of the requirements
			if(searchScore == inclusion){
				return e;
			}
		}
		
		
		return null;
	}
}
