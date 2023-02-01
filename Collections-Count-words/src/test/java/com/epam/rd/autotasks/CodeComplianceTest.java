package com.epam.rd.autotasks;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.code.CtExecutableReferenceExpression;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLambda;
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
    void testNoStreams() {

        CtTypeReference baseStreamRefType = new Launcher().getFactory().Code().createCtTypeReference(BaseStream.class);

        final List<CtInvocation> methodCallsReturningStreams = spoonRootPackage.getElements(
                new AbstractFilter<>() {
                    @Override
                    public boolean matches(final CtInvocation invocation) {
                        return invocation.getType().isSubtypeOf(baseStreamRefType);
                    }
                }
        );
        assertEquals(0, methodCallsReturningStreams.size(),
                () -> "You must not use streams in this exercises, " +
                        "but you have supplied " + methodCallsReturningStreams.size() + " of them: "
                        + methodCallsReturningStreams);
    }

    @Test
    void testNoLambdas() {
        final List<CtLambda> lambdas =
                spoonRootPackage.getElements(new TypeFilter<>(CtLambda.class));
        assertEquals(0, lambdas.size(),
                () -> "You must not use lambdas in this exercises, " +
                        "but you have supplied " + lambdas.size() + " of them: "
                        + lambdas);
    }

    @Test
    void testNoMethodRefs() {
        final List<CtExecutableReferenceExpression> methodRefs =
                spoonRootPackage.getElements(new TypeFilter<>(CtExecutableReferenceExpression.class));
        assertEquals(0, methodRefs.size(),
                () -> "You must not use method references in this exercises, " +
                        "but you have supplied " + methodRefs.size() + " of them: "
                        + methodRefs);
    }

}
