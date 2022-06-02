package alluxio.cli.fuse;

/**
 * The valid operations that can be tested by the Fuse correctness validation tool.
 */
public enum CorrectnessValidationOperation {
  READ,
  WRITE;

  /**
   * Convert operation string to {@link CorrectnessValidationOperation}
   * @param operationStr the operation in string format
   * @return the operation
   */
  public static CorrectnessValidationOperation fromString(String operationStr) {
    for (CorrectnessValidationOperation type : CorrectnessValidationOperation.values()) {
      if (type.toString().equalsIgnoreCase(operationStr)) {
        return type;
      }
    }
    throw new IllegalArgumentException(String.format("Operation %s is not valid.", operationStr));
  }
}
