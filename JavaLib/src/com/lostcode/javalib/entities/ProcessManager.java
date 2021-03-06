package com.lostcode.javalib.entities;

import java.util.Iterator;

import com.badlogic.gdx.utils.Array;
import com.lostcode.javalib.entities.processes.Process;
import com.lostcode.javalib.entities.processes.ProcessState;

/**
 * Manages all processes in a given EntityWorld.
 * 
 * @author MadcowD
 * @created Sep 28, 2013
 */
public class ProcessManager {

	// region Fields

	private Array<Process> attachedProcesses = new Array<Process>();

	// endregion

	// region Process Management

	/**
	 * Attaches a process to the process manager and STARTS it.
	 * 
	 * @param process
	 */
	public void attach(Process process) {
		attachedProcesses.add(process);
		process.start();
	}

	/**
	 * Aborts all of the processes in the Process manager.
	 */
	public void abortAll() {
		for (Process p : attachedProcesses) {
			p.end(ProcessState.ABORTED);
		}
	}

	/**
	 * Ends all processes of the given type.
	 * 
	 * @param type
	 * @param endState
	 *            The ProcessState to end with.
	 */
	public void endAll(Class<? extends Process> type, ProcessState endState) {
		for (int i = 0; i < attachedProcesses.size; i++) {
			Process p = attachedProcesses.get(i);
			
			if (type.isInstance(p)) {
				p.end(endState);
			}
		}
	}

	// endregion

	// region Processing

	/**
	 * Processes all processes.
	 * 
	 * @param deltaTime
	 * @param world
	 */
	public void process(EntityWorld world, float deltaTime) {
		for (Iterator<Process> i = attachedProcesses.iterator(); i.hasNext();) {
			// Get the process
			Process p = i.next();

			// Handle states
			if (p.getState() == ProcessState.RUNNING) {
				p.update(world, deltaTime); // IF IT'S RUNNING, UPDATE
			} else { // IF IT IS DEAD
				if (p.getState() == ProcessState.SUCCEEDED) {
					for (Process child : p.getChildren()) {
						this.attach(child); // If the process was successful,
											// attach its children
					}
				}

				p.onEnd(world, p.getState());

				// REMOVE FROM PROCESS MANAGER

				i.remove();
			}
		}
	}

	// endregion

}
