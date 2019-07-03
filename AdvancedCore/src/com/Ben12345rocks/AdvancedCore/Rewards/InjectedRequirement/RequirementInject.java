package com.Ben12345rocks.AdvancedCore.Rewards.InjectedRequirement;

import org.bukkit.configuration.ConfigurationSection;

import com.Ben12345rocks.AdvancedCore.Rewards.Inject;
import com.Ben12345rocks.AdvancedCore.Rewards.Reward;
import com.Ben12345rocks.AdvancedCore.Rewards.RewardOptions;
import com.Ben12345rocks.AdvancedCore.UserManager.User;
import com.Ben12345rocks.AdvancedCore.Util.EditGUI.EditGUIButton;

import lombok.Getter;

public abstract class RequirementInject extends Inject {

	@Getter
	private boolean allowReattempt = false;

	@Getter
	private boolean alwaysForce = false;

	public RequirementInject(String path) {
		super(path);
	}

	public RequirementInject addEditButton(EditGUIButton button) {
		getEditButtons().add(button);
		return this;
	}

	public void validate(Reward reward, ConfigurationSection data) {
		validate.onValidate(reward, this, data);
	}

	public RequirementInject allowReattempt() {
		this.allowReattempt = true;
		return this;
	}

	public RequirementInject alwaysForce() {
		this.alwaysForce = true;
		return this;
	}

	public boolean isEditable() {
		return !getEditButtons().isEmpty();
	}

	@Getter
	private RequirementInjectValidator validate;

	public RequirementInject validator(RequirementInjectValidator validate) {
		this.validate = validate;
		return this;
	}

	public abstract boolean onRequirementRequest(Reward reward, User user, ConfigurationSection data,
			RewardOptions rewardOptions);

	public RequirementInject priority(int priority) {
		setPriority(priority);
		return this;
	}
}
