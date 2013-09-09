


package net.medfusion.integrations.webservices.framework;


public final class ConfigError
        extends Error {
    //~ Static fields/initializers -------------------------------------------------------------------------------------

    /** The serialVersionUID value for this class. */
    private static final long serialVersionUID = 20081015L;

    //~ Constructors ---------------------------------------------------------------------------------------------------

    /**
     * Creates a new ConfigError object.
     *
     * @param  message  DOCUMENT ME
     */
    public ConfigError(String message) {
        super(message);
    } // end ctor ConfigError

    /**
     * Creates a new ConfigError object.
     *
     * @param  message  DOCUMENT ME
     * @param  cause  DOCUMENT ME
     */
    public ConfigError(String message, Throwable cause) {
        super(message, cause);
    } // end ctor ConfigError
} // end class ConfigError
