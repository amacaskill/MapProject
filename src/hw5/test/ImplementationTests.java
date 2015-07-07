package hw5.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import hw5.test.GraphTests;
import hw5.test.EdgeTests; 

/**
 * ImplementationTests is a test suite used to encapsulate all
 * tests specific to your implementation of this problem set.
 *
 * For instance, unit tests for your individual methods would
 * go here.
 */

@RunWith(Suite.class)
@SuiteClasses({ GraphTests.class, EdgeTests.class })

public final class ImplementationTests
{
  //this class is a placeholder for the suite, so it has no members.
}

