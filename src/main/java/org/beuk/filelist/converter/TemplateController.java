package org.beuk.filelist.converter;

import java.io.*;
import java.util.*;

import freemarker.core.*;
import freemarker.ext.beans.*;
import freemarker.template.*;

public class TemplateController {

	Configuration cfg;

	public TemplateController() throws IOException {
		cfg = new Configuration(Configuration.VERSION_2_3_24);
		cfg.setClassForTemplateLoading(this.getClass(), "/templates");
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);
		final BeansWrapper beansWrapper = (BeansWrapper) ObjectWrapper.BEANS_WRAPPER;
		beansWrapper.setExposeFields(true);
		cfg.setObjectWrapper(beansWrapper);
	}

	public String processTemplate(final Object dataObject, final String templateName) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {

		return processTemplate(dataObject, templateName, Locale.getDefault());
	}

	public String processTemplate(final Object dataObject, String templateName, final Locale locale) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {

		Template template;
		if (!templateName.endsWith(".ftlh"))
			templateName += ".ftlh";

		template = cfg.getTemplate(templateName, locale);

		try (StringWriter sIO = new StringWriter()) {
			template.process(dataObject, sIO);
			return sIO.toString();
		}
	}

}
