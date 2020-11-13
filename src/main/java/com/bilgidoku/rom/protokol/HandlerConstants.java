package com.bilgidoku.rom.protokol;



/**
 * Provide the keys under which the {@link ChannelHandler}'s are stored in the
 * {@link ChannelPipeline}
 * 
 * 
 */
public interface HandlerConstants {

    public static final String SSL_HANDLER = "sslHandler";

    public static final String GROUP_HANDLER = "groupHandler";

    public static final String CONNECTION_LIMIT_HANDLER = " connectionLimit";

    public static final String CONNECTION_PER_IP_LIMIT_HANDLER = "connectionPerIpLimit";

    public static final String FRAMER = "framer";

    public static final String EXECUTION_HANDLER = "executionHandler";

    public static final String TIMEOUT_HANDLER = "timeoutHandler";

    public static final String CORE_HANDLER = "handler";

    public static final String CHUNK_HANDLER = "chunkHandler";
    
    public static final String REQUEST_DECODER="requestDecoder";

}