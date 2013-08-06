package com.punchline.javalib.entities.tiles;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.punchline.javalib.entities.EntityWorld;
import com.punchline.javalib.entities.components.Component;
import com.punchline.javalib.entities.components.ComponentManager;
import com.punchline.javalib.entities.components.physical.Transform;
import com.punchline.javalib.entities.components.render.Renderable;
import com.punchline.javalib.utils.Convert;
import com.punchline.javalib.utils.Display;

/**
 * Component wrapper for a map made in Tiled. When added to an Entity, this will automatically add bodies to the
 * Box2D physics world, as defined by the Tiled map's "physics" object layer and the given materials XML file.
 * 
 * Map objects that contain the property "Entity" with a template tag will be used to create an Entity. The args provided
 * to the template will be as follows:
 * 
 * args[0] = The body associated with the entity
 * args[1] = The properties of the MapObject.
 * 
 * @author Natman64
 * @created Aug 1, 2013
 */
public class TileMap implements Component, Renderable, Transform {

	//region Fields/Initialization
	
	private static TmxMapLoader loader = new TmxMapLoader();
	
	private boolean init = false;
	
	private TiledMap map;
	private MapBodyManager bodyManager;
	private OrthogonalTiledMapRenderer renderer;
	
	private boolean changed;
	private Map<String, MapObject> disabledObjects = new HashMap<String, MapObject>();
	
	/**
	 * Constructs a TileMap component.
	 * @param world The EntityWorld containing this map.
	 * @param mapFilename The file path of the map's .tmx file.
	 * @param materialsFilename The file path of the XML file containing the materials info for the map.
	 */
	public TileMap(EntityWorld world, String mapFilename, String materialsFilename) {
		map = loader.load(mapFilename);
		
		bodyManager = new MapBodyManager(world, Convert.getMeterPixelRatio(), materialsFilename, 0);
	}
	
	//endregion
	
	//region TiledMap Accessors/Mutators
	
	/**
	 * @return Whether the objects in this TileMap have been changed since the last {@link #refresh()} call.
	 */
	public boolean isChanged() {
		return changed;
	}
	
	/**
	 * @return The TiledMap.
	 */
	public TiledMap getMap() {
		return map;
	}
	
	/**
	 * Toggles one of the map's objects.
	 * @param name The name of the object.
	 */
	public void toggleObject(String name) {
		MapLayer layer = map.getLayers().get("physics"); //get the physics layer.
		
		if (disabledObjects.containsKey(name)) { //if disabled
			layer.getObjects().add(disabledObjects.get(name)); //return it to the layer
			disabledObjects.remove(name); //remove it from the disabled map
		} else { //if enabled
			MapObject object = layer.getObjects().get(name); //get the desired object
			if (object == null) return; //null check
			disabledObjects.put(name, object); //add to disabled list
			layer.getObjects().remove(object); //remove from the layer
		}
		
		changed = true; //commit the changes to the map
	}
	
	/**
	 * Disables one of the map's objects.
	 * @param name The name of the object.
	 */
	public void disableObject(String name) {
		MapLayer layer = map.getLayers().get("physics"); //get the physics layer.
		
		if (!disabledObjects.containsKey(name)) { //if enabled
			MapObject object = layer.getObjects().get(name); //get the desired object
			if (object == null) return; //null check
			disabledObjects.put(name, object); //add to disabled list
			layer.getObjects().remove(object); //remove from the layer
		}
		
		changed = true; //commit the changes to the map
	}
	
	/**
	 * Enables one of the map's objects.
	 * @param name The name of the object.
	 */
	public void enableObject(String name) {
		MapLayer layer = map.getLayers().get("physics"); //get the physics layer.
		
		if (disabledObjects.containsKey(name)) { //if disabled
			layer.getObjects().add(disabledObjects.get(name)); //return it to the layer
			disabledObjects.remove(name); //remove it from the disabled map
		}
		
		changed = true; //commit the changes to the map
	}
	
	/**
	 * Sets a cell of a tile layer to the given cell.
	 * @param layerName The name of the layer to edit.
	 * @param x The x coordinate of the tile to edit.
	 * @param y The y coordinate of the tile to edit.
	 * @param value The cell to set the tile to.
	 */
	public void setTile(String layerName, int x, int y, Cell value) {
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(layerName); //get the desired layer
		
		if (layer == null) return; //null check
		
		layer.setCell(x, y, value); //set the desired cell
	}
	
	/**
	 * Gets a cell of a tile layer.
	 * @param layerName The name of the layer to search.
	 * @param x The x coordinate of the tile to return.
	 * @param y The y coordinate of the tile to return.
	 * @return The desired tile.
	 */
	public Cell getTile(String layerName, int x, int y) {
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(layerName); //get the desired layer
		
		if (layer == null) return null; //null check
		
		return layer.getCell(x, y); //return the desired cell
	}
	
	//endregion
	
	//region Transform Implementation
	
	@Override
	public Vector2 getPosition() {
		return Vector2.Zero.cpy();
	}

	@Override
	public float getRotation() {
		return 0f;
	}

	@Override
	public Vector2 getOrigin() {
		return Vector2.Zero.cpy();
	}

	@Override
	public float getWidth() {
		return 0f;
	}

	@Override
	public float getHeight() {
		return 0f;
	}

	//endregion
	
	//region Renderable Implementation
	
	@Override
	public void setPosition(Vector2 position) { }

	@Override
	public void setRotation(float degrees) { }

	@Override
	public void setScale(float scaleX, float scaleY) { }

	@Override
	public void setOrigin(Vector2 origin) { }

	@Override
	public void draw(SpriteBatch spriteBatch, float deltaSeconds) {
		if (!init) {
			renderer = new OrthogonalTiledMapRenderer(map, spriteBatch);
			init = true;
		}
		
		spriteBatch.end();
		renderer.setView(spriteBatch.getProjectionMatrix().cpy(), 0, 0, Display.getRealWidth(), Display.getRealHeight());
		renderer.render();
		spriteBatch.begin();
	}

	//endregion
	
	//region Events
	
	@Override
	public void onAdd(ComponentManager container) {
		bodyManager.createPhysics(map);
	}

	@Override
	public void onRemove(ComponentManager container) {
		bodyManager.destroyPhysics();
		map.dispose();
		renderer.dispose();
	}

	/**
	 * Re-creates the Box2D bodies representing the map's object layer. 
	 * Called by the EntityWorld containing this TileMap.
	 */
	public void refresh() {
		bodyManager.destroyPhysics();
		bodyManager.createPhysics(map);
		
		changed = false;
	}
	
	//endregion
	
}