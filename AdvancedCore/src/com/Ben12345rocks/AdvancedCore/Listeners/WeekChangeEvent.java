package com.Ben12345rocks.AdvancedCore.Listeners;

import java.util.Date;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

// TODO: Auto-generated Javadoc
/**
 * The Class PlayerRewardEvent.
 */
public class WeekChangeEvent extends Event {

	/** The Constant handlers. */
	private static final HandlerList handlers = new HandlerList();

	/**
	 * Gets the handler list.
	 *
	 * @return the handler list
	 */
	public static HandlerList getHandlerList() {
		return handlers;
	}

	/**
	 * Instantiates a new player reward event.
	 *
	 * @param reward
	 *            the reward
	 * @param player
	 *            the player
	 */
	public WeekChangeEvent() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bukkit.event.Event#getHandlers()
	 */
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

}
