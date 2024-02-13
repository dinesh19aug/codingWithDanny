package com.javahabit.d2ccommerceapp.thymeleaf;

import org.ff4j.FF4j;
import org.ff4j.core.FlippingExecutionContext;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;


public class MyFF4JDialect extends AbstractProcessorDialect {
    private FF4j ff4j;
    private FlippingExecutionContext fex;

    public MyFF4JDialect()
    {
        super("FF4JDialect", "ff4j", 10);

    }

    public void setFF4J(FF4j ff4j)
    {
        this.ff4j = ff4j;
    }
    public void setFex(FlippingExecutionContext fex) {
        this.fex = fex;
    }
    //
    // The processors.
    //
    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix)
    {
        final Set<IProcessor> processors = new HashSet<IProcessor>();
        processors.add(new MyFF4jEnableAttrProcessor(dialectPrefix, ff4j,fex));
        processors.add(new MyFF4jDisableAttrProcessor(dialectPrefix, ff4j));
        return processors;

    }
}
