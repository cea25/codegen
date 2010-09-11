/*
 * Copyright (c) 2010 Mysema Ltd.
 * All rights reserved.
 *
 */
package com.mysema.codegen;

import static org.junit.Assert.*;

import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.mysema.codegen.model.ClassType;
import com.mysema.codegen.model.Type;
import com.mysema.codegen.model.TypeCategory;
import com.mysema.codegen.model.Types;


public class ComplexEvaluationTest {
    
    private EvaluatorFactory factory = new EvaluatorFactory((URLClassLoader) getClass().getClassLoader());
    
    @Test
    @SuppressWarnings("unchecked")
    public void Complex(){
        ClassType resultType = new ClassType(TypeCategory.LIST,List.class, Types.STRING);
        StringBuilder source = new StringBuilder();
        source.append("java.util.List<String> rv = new java.util.ArrayList<String>();\n");
        source.append("for (String a : a_){\n");
        source.append("    for (String b : b_){\n");
        source.append("        if (a.equals(b)){\n");
        source.append("            rv.add(a);\n");
        source.append("        }\n");
        source.append("    }\n");
        source.append("}\n");
        source.append("return rv;");
        
        Evaluator<List> evaluator = factory.createEvaluator(
            source.toString(), 
            resultType, 
            new String[]{"a_","b_"}, 
            new Type[]{resultType, resultType},
            new Class[]{List.class,List.class},
            Collections.<String,Object>emptyMap());
        
        List<String> a_ = Arrays.asList("1","2","3","4");
        List<String> b_ = Arrays.asList("2","4","6","8");
        
        assertEquals(Arrays.asList("2","4"), evaluator.evaluate(a_, b_));
    }

}
