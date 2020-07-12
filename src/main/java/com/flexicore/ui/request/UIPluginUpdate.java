package com.flexicore.ui.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flexicore.request.BaseclassCreate;
import com.flexicore.ui.model.UIPlugin;

public class UIPluginUpdate extends UIPluginCreate {

	private String id;
	@JsonIgnore
	private UIPlugin uIPlugin;

	public String getId() {
		return id;
	}

	public UIPluginUpdate setId(String id) {
		this.id = id;
		return this;
	}

	@JsonIgnore
	public UIPlugin getUIPlugin() {
		return uIPlugin;
	}

	public UIPluginUpdate setUIPlugin(UIPlugin uIPlugin) {
		this.uIPlugin = uIPlugin;
		return this;
	}
}
