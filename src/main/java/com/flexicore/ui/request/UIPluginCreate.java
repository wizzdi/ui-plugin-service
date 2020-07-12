package com.flexicore.ui.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flexicore.model.FileResource;
import com.flexicore.request.BaseclassCreate;

public class UIPluginCreate extends BaseclassCreate {

	private String contentId;
	@JsonIgnore
	private FileResource content;

	private String version;
	private String associationReference;

	public String getContentId() {
		return contentId;
	}

	public <T extends UIPluginCreate> T setContentId(String contentId) {
		this.contentId = contentId;
		return (T) this;
	}

	@JsonIgnore
	public FileResource getContent() {
		return content;
	}

	public <T extends UIPluginCreate> T setContent(FileResource content) {
		this.content = content;
		return (T) this;
	}

	public String getVersion() {
		return version;
	}

	public <T extends UIPluginCreate> T setVersion(String version) {
		this.version = version;
		return (T) this;
	}

	public String getAssociationReference() {
		return associationReference;
	}

	public <T extends UIPluginCreate> T setAssociationReference(
			String associationReference) {
		this.associationReference = associationReference;
		return (T) this;
	}
}
