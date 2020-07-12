package com.flexicore.ui.service;

import com.flexicore.annotations.plugins.PluginInfo;
import com.flexicore.data.jsoncontainers.PaginationResponse;
import com.flexicore.interfaces.ServicePlugin;
import com.flexicore.model.Baseclass;
import com.flexicore.model.FileResource;
import com.flexicore.security.SecurityContext;
import com.flexicore.service.BaseclassNewService;
import com.flexicore.ui.data.UIPluginRepository;
import com.flexicore.ui.model.UIPlugin;
import com.flexicore.ui.request.UIPluginCreate;
import com.flexicore.ui.request.UIPluginFilter;
import com.flexicore.ui.request.UIPluginUpdate;

import javax.ws.rs.BadRequestException;
import java.util.List;
import java.util.logging.Logger;
import org.pf4j.Extension;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@PluginInfo(version = 1)
@Extension
@Component
public class UIPluginService implements ServicePlugin {

	@Autowired
	private Logger logger;

	@PluginInfo(version = 1)
	@Autowired
	private UIPluginRepository uIPluginRepository;

	@Autowired
	private BaseclassNewService baseclassNewService;

	public UIPlugin UIPluginUpdate(UIPluginUpdate uiPluginUpdate,
			SecurityContext securityContext) {
		if (UIPluginUpdateNoMerge(uiPluginUpdate, uiPluginUpdate.getUIPlugin())) {
			uIPluginRepository.merge(uiPluginUpdate.getUIPlugin());
		}
		return uiPluginUpdate.getUIPlugin();
	}

	public boolean UIPluginUpdateNoMerge(UIPluginCreate uiPluginCreate,
			UIPlugin uIPlugin) {
		boolean update = baseclassNewService.updateBaseclassNoMerge(
				uiPluginCreate, uIPlugin);
		if (uiPluginCreate.getContent() != null
				&& (uIPlugin.getContent() == null || !uiPluginCreate
						.getContent().getId()
						.equals(uIPlugin.getContent().getId()))) {
			uIPlugin.setContent(uiPluginCreate.getContent());
			update = true;
		}

		if (uiPluginCreate.getAssociationReference() != null
				&& !uiPluginCreate.getAssociationReference().equals(
						uIPlugin.getAssociationReference())) {
			uIPlugin.setAssociationReference(uiPluginCreate
					.getAssociationReference());
			update = true;
		}

		if (uiPluginCreate.getVersion() != null
				&& !uiPluginCreate.getVersion().equals(uIPlugin.getVersion())) {
			uIPlugin.setVersion(uiPluginCreate.getVersion());
			update = true;
		}
		return update;
	}

	public <T extends Baseclass> T getByIdOrNull(String id, Class<T> c,
			List<String> batchString, SecurityContext securityContext) {
		return uIPluginRepository.getByIdOrNull(id, c, batchString,
				securityContext);
	}

	public PaginationResponse<UIPlugin> getAllUIPlugins(
			UIPluginFilter uiPluginFilter, SecurityContext securityContext) {
		List<UIPlugin> list = listAllUIPlugins(uiPluginFilter, securityContext);
		long count = uIPluginRepository.countAllUIPlugins(uiPluginFilter,
				securityContext);
		return new PaginationResponse<>(list, uiPluginFilter, count);
	}

	public List<UIPlugin> listAllUIPlugins(UIPluginFilter uiPluginFilter,
			SecurityContext securityContext) {
		return uIPluginRepository.listAllUIPlugins(uiPluginFilter,
				securityContext);
	}

	public UIPlugin UIPluginCreate(UIPluginCreate uiPluginCreate,
			SecurityContext securityContext) {
		UIPlugin uIPlugin = UIPluginCreateNoMerge(uiPluginCreate,
				securityContext);
		uIPluginRepository.merge(uIPlugin);
		return uIPlugin;

	}

	private UIPlugin UIPluginCreateNoMerge(UIPluginCreate uiPluginCreate,
			SecurityContext securityContext) {
		UIPlugin uIPlugin = new UIPlugin(uiPluginCreate.getName(),
				securityContext);
		UIPluginUpdateNoMerge(uiPluginCreate, uIPlugin);
		return uIPlugin;
	}

	public void validate(UIPluginCreate uiPluginCreate,
			SecurityContext securityContext) {
		baseclassNewService.validate(uiPluginCreate, securityContext);
		String contentId = uiPluginCreate.getContentId();
		FileResource content = contentId != null ? getByIdOrNull(contentId,
				FileResource.class, null, securityContext) : null;
		if (contentId != null && content == null) {
			throw new BadRequestException("No FileResource with id "
					+ contentId);
		}
		uiPluginCreate.setContent(content);
	}
}
