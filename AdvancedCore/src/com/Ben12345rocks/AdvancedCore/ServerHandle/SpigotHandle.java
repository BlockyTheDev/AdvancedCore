package com.Ben12345rocks.AdvancedCore.ServerHandle;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.BaseComponent;

public class SpigotHandle implements IServerHandle {

	@Override
	public void sendMessage(Player player, BaseComponent component) {
		player.spigot().sendMessage(component);

	}

	@Override
	public void sendMessage(Player player, BaseComponent... components) {
		player.spigot().sendMessage(components);

	}

}
