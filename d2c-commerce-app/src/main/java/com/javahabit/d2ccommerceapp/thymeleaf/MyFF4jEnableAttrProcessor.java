package com.javahabit.d2ccommerceapp.thymeleaf;

import org.ff4j.FF4j;
import org.ff4j.core.FlippingExecutionContext;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.standard.processor.AbstractStandardConditionalVisibilityTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;


public class MyFF4jEnableAttrProcessor extends AbstractStandardConditionalVisibilityTagProcessor {
    private FF4j ff4j;
    private FlippingExecutionContext fex;

    protected MyFF4jEnableAttrProcessor(final String dialectPrefix, FF4j ff4j, FlippingExecutionContext fex)
    {
        super(TemplateMode.HTML, dialectPrefix, "enable", 11);
        this.ff4j = ff4j;
        this.fex = fex;
    }

    @Override
    protected boolean isVisible(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName, String attributeValue)
    {
        final String feature = attributeValue;
        if (feature == null || feature.trim().equals(""))
        {
            return false;
        }
        return ff4j.check(feature, fex);
    }

    private String getClientIp()
    {
        return "0.0.0.0";
    }
}
