package com.planespotter.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchTests;
import com.tngtech.archunit.junit.ArchUnitRunner;
import org.junit.runner.RunWith;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.planespotter")
public class ArchitectureTest {

    @ArchTest
    public static final ArchTests domainRules = ArchTests.in(DomainArchitectureRules.class);

    @ArchTest
    public static final ArchTests applicationRules = ArchTests.in(ApplicationArchitectureRules.class);

    @ArchTest
    public static final ArchTests infrastructureRules = ArchTests.in(InfrastructureArchitectureRules.class);
}

// Separate classes for each layer's rules can be defined similarly
