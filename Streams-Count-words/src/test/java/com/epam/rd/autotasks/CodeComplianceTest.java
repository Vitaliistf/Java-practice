package com.epam.rd.autotasks;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.code.CtConditional;
import spoon.reflect.code.CtExecutableReferenceExpression;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLambda;
import spoon.reflect.code.CtLoop;
import spoon.reflect.code.CtSwitch;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.List;
import java.util.stream.BaseStream;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CodeComplianceTest {

    private static CtPackage spoonRootPackage;

    @BeforeAll
    static void init() {
        final SpoonAPI spoon = new Launcher();
        spoon.addInputResource("src/main/java/");
        spoon.buildModel();
        spoonRootPackage = spoon.getFactory().Package().getRootPackage();
    }

    @Test
    void testNoCycles() {
        final List<CtLoop> list =
                spoonRootPackage.getElements(new TypeFilter<>(CtLoop.class));
        assertEquals(0, list.size(),
                () -> "You must not use cycles in this exercises, " +
                        "but you have supplied " + list.size() + " of them: "
                        + list);
    }

    @Test
    void testNoIfs() {
        final List<CtIf> list =
                spoonRootPackage.getElements(new TypeFilter<>(CtIf.class));
        assertEquals(0, list.size(),
                () -> "You must not use conditions in this exercises, " +
                        "but you have supplied " + list.size() + " of them: "
                        + list);
    }


    @Test
    void testNoSwitches() {
        final List<CtSwitch> list =
                spoonRootPackage.getElements(new TypeFilter<>(CtSwitch.class));
        assertEquals(0, list.size(),
                () -> "You must not use conditions in this exercises, " +
                        "but you have supplied " + list.size() + " of them: "
                        + list);
    }


    @Test
    void testNoTernaryOperators() {
        final List<CtConditional> list =
                spoonRootPackage.getElements(new TypeFilter<>(CtConditional.class));
        assertEquals(0, list.size(),
                () -> "You must not use conditions in this exercises, " +
                        "but you have supplied " + list.size() + " of them: "
                        + list);
    }


}
