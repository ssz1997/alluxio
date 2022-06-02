package alluxio.cli.fuse;


import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * This class stores the options passed in from CLI.
 */
public final class CorrectnessValidationOptions {

  public static final CommandLineParser PARSER = new DefaultParser();
  private final String mLocalDir;
  private final String mFuseDir;
  private final CorrectnessValidationOperation mOperation;
  private final int mNumThreads;

  /**
   * Constructs a {@CorrectnessValidationOptions} object through CLI.
   * @param args arguments from CLI
   * @return a {@CorrectnessValidationOptions} holding all the test options
   * @throws ParseException
   * @throws IllegalArgumentException
   */
  public static CorrectnessValidationOptions createOptions(String[] args)
      throws ParseException, IllegalArgumentException {
    CommandLine cli = PARSER.parse(CorrectnessOptionsParser.OPTIONS, args);
    String localDir = cli.getOptionValue(CorrectnessOptionsParser.LOCAL_DIR_OPTION_NAME);
    String fuseDir = cli.getOptionValue(CorrectnessOptionsParser.MOUNT_POINT_OPTION_NAME);
    CorrectnessValidationOperation operation = CorrectnessValidationOperation.fromString(
        cli.getOptionValue(CorrectnessOptionsParser.TEST_OPERATION_OPTION_NAME));
    int numThreads = Integer.parseInt(
        cli.getOptionValue(CorrectnessOptionsParser.THREAD_NUMBER_OPTION_NAME));
    return new CorrectnessValidationOptions(localDir, fuseDir, operation, numThreads);
  }

  private CorrectnessValidationOptions(String localDir, String fuseDir,
      CorrectnessValidationOperation operation, int numThreads) {
    mLocalDir = localDir;
    mFuseDir = fuseDir;
    mOperation = operation;
    mNumThreads = numThreads;
  }

  /**
   * @return operation being tested
   */
  public CorrectnessValidationOperation getOperation() {
    return mOperation;
  }

  /**
   * @return number of threads for the test
   */
  public int getNumThreads() {
    return mNumThreads;
  }

  /**
   * @return the local filesystem directory for testing
   */
  public String getLocalDir() {
    return mLocalDir;
  }

  /**
   * @return the fuse directory for testing
   */
  public String getFuseDir() {
    return mFuseDir;
  }

  private static class CorrectnessOptionsParser {
    private static final String LOCAL_DIR_OPTION_NAME = "l";
    private static final String MOUNT_POINT_OPTION_NAME = "f";
    private static final String THREAD_NUMBER_OPTION_NAME = "t";
    private static final String TEST_OPERATION_OPTION_NAME = "o";
    private static final String HELP_OPTION_NAME = "h";

    private static final Option LOCAL_DIR_OPTION =
        Option.builder(LOCAL_DIR_OPTION_NAME)
            .required(true)
            .hasArg()
            .longOpt("local-dir")
            .desc("The local filesystem directory to write source file"
                + " which is used for validating correctness.")
            .build();
    private static final Option MOUNT_POINT_OPTION =
        Option.builder(MOUNT_POINT_OPTION_NAME)
            .required(true)
            .hasArg()
            .longOpt("mount-point")
            .desc("The mount point of Alluxio Fuse.")
            .build();
    private static final Option TEST_OPERATION_OPTION =
        Option.builder(TEST_OPERATION_OPTION_NAME)
            .required(true)
            .longOpt("operation")
            .desc("Operation being tested. Valid options include `Read` and `Write`.")
            .build();
    private static final Option THREAD_NUMBER_OPTION =
        Option.builder(THREAD_NUMBER_OPTION_NAME)
            .required(true)
            .hasArg()
            .longOpt("num-threads")
            .desc("Number of threads used by the client for validating.")
            .build();
    private static final Option HELP_OPTION =
        Option.builder(HELP_OPTION_NAME)
            .required(false)
            .longOpt("help")
            .desc("Print this help message.")
            .build();

    public static final Options OPTIONS =
        new Options()
            .addOption(LOCAL_DIR_OPTION)
            .addOption(MOUNT_POINT_OPTION)
            .addOption(TEST_OPERATION_OPTION)
            .addOption(THREAD_NUMBER_OPTION)
            .addOption(HELP_OPTION);
  }
}
