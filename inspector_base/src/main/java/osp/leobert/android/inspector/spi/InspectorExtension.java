package osp.leobert.android.inspector.spi;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterSpec;

import java.util.Collections;
import java.util.Set;

import javax.annotation.Nullable;

/**
 * Basic extension interface.
 */
public interface InspectorExtension {

  /**
   * @return any other applicable annotations you want to process.
   */
   Set<String> applicableAnnotations();

  /**
   * This is for checking if a given `Property` is applicable for this extension. For instance,
   * a nullability extension would check if the property was a non-primitive/non-Void type.
   *
   * @param property the property to check
   * @return true if applicable, false if not.
   */
    boolean applicable(Property property);

  /**
   * This is for generating your actual validation. This uses a JavaPoet CodeBlock, and gives you
   * information about three parameters:
   *
   * @param prop the property itself
   * @param propertyName the name of the property being validated.
   * If you are validating a `Person`'s `name` property, this is `name`
   * @param typeInstance the instance of the type being validated.
   * If you are validating a `Person` instance, this is the instance as passed
   * to the `validate` method.
   * @return a codeblock of validation logic to execute, or null if there is none.
   */
  @Nullable   CodeBlock generateValidation(Property prop,
                                                 String propertyName,
                                                 ParameterSpec typeInstance);

  /**
   * This for declaring priority of your extension. This is useful if you have higher priority
   * validation that should run as early as possible (such as nullability). Most validations should
   * not care what order they are run in though. Default is {@link Priority#NONE}.
   *
   * @return the priority
   */
    Priority priority();

  enum Priority {
    /**
     * Use this priority to indicate that this must be run as soon as possible, such as nullability
     * checks.
     */
    HIGH,

    /**
     * Use this priority to indicate that sooner is better, but not important.
     */
    NORMAL,

    /**
     * Use this priority to indicate that it does not matter when this runs, as long as it does.
     * This is the default mode.
     */
    NONE
  }
}
