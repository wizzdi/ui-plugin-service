package com.flexicore.ui.data;

import com.flexicore.annotations.plugins.PluginInfo;
import com.flexicore.interfaces.AbstractRepositoryPlugin;
import com.flexicore.model.QueryInformationHolder;
import com.flexicore.security.SecurityContext;
import com.flexicore.ui.model.UIPlugin;
import com.flexicore.ui.model.UIPlugin_;
import com.flexicore.ui.request.UIPluginFilter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.pf4j.Extension;
import org.springframework.stereotype.Component;

@PluginInfo(version = 1)
@Extension
@Component
public class UIPluginRepository extends AbstractRepositoryPlugin {

	public List<UIPlugin> listAllUIPlugins(UIPluginFilter uiPluginFilter,
			SecurityContext securityContext) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<UIPlugin> q = cb.createQuery(UIPlugin.class);
		Root<UIPlugin> r = q.from(UIPlugin.class);
		List<Predicate> preds = new ArrayList<>();
		addUIPluginPredicates(preds, cb, r, uiPluginFilter);
		QueryInformationHolder<UIPlugin> queryInformationHolder = new QueryInformationHolder<>(
				uiPluginFilter, UIPlugin.class, securityContext);
		return getAllFiltered(queryInformationHolder, preds, cb, q, r);
	}

	private void addUIPluginPredicates(List<Predicate> preds,
			CriteriaBuilder cb, Root<UIPlugin> r, UIPluginFilter uiPluginFilter) {
		if (uiPluginFilter.getAssociationReferences() != null
				&& !uiPluginFilter.getAssociationReferences().isEmpty()) {
			preds.add(r.get(UIPlugin_.associationReference).in(
					uiPluginFilter.getAssociationReferences()));
		}
	}

	public long countAllUIPlugins(UIPluginFilter uiPluginFilter,
			SecurityContext securityContext) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> q = cb.createQuery(Long.class);
		Root<UIPlugin> r = q.from(UIPlugin.class);
		List<Predicate> preds = new ArrayList<>();
		addUIPluginPredicates(preds, cb, r, uiPluginFilter);
		QueryInformationHolder<UIPlugin> queryInformationHolder = new QueryInformationHolder<>(
				uiPluginFilter, UIPlugin.class, securityContext);
		return countAllFiltered(queryInformationHolder, preds, cb, q, r);
	}
}
