package com.bilgidoku.rom.protokol;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.session.ConnectionSession;
import com.bilgidoku.rom.session.GuardianHandler;
import com.bilgidoku.rom.session.IpStat;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.HashedWheelTimer;


/**
 * Abstract base class for {@link ChannelPipelineFactory} implementations
 * 
 *
 */
public abstract class AbstractChannelPipelineFactory extends  ChannelInitializer<SocketChannel>{

    public final static int MAX_LINE_LENGTH = 8192;
    protected final ConnectionLimitUpstreamHandler connectionLimitHandler;
//    protected final GuardianHandler connectionPerIpLimitHandler;
    private final HashedWheelTimer timer = new HashedWheelTimer();
    private final int timeout;
	private int moduleId;

    
    public AbstractChannelPipelineFactory(int timeout, int maxConnections) {
    	this.moduleId=IpStat.getModuleIdByName(serviceName());
        this.connectionLimitHandler = new ConnectionLimitUpstreamHandler(maxConnections);
//        this.connectionPerIpLimitHandler = new GuardianHandler(moduleId);
        this.timeout = timeout;
    }
    
    
    
    LoggingHandler lh=new LoggingHandler();
    @Override
	public void initChannel(SocketChannel ch) throws Exception {
        // Create a default pipeline implementation.
        ChannelPipeline pipeline = ch.pipeline();
//        pipeline.addLast(HandlerConstants.GROUP_HANDLER, groupHandler);
        pipeline.addLast(new GuardianHandler(IpStat.MODULE_PROTOCOL));
        
        pipeline.addLast("logger",lh);
        pipeline.addLast(HandlerConstants.CONNECTION_LIMIT_HANDLER, connectionLimitHandler);

//        pipeline.addLast(HandlerConstants.CONNECTION_PER_IP_LIMIT_HANDLER, connectionPerIpLimitHandler);

        
        // Add the text line decoder which limit the max line length, don't strip the delimiter and use CRLF as delimiter
        pipeline.addLast(HandlerConstants.FRAMER, new DelimiterBasedFrameDecoder(MAX_LINE_LENGTH, false, Delimiters.lineDelimiter()));
       
        // Add the ChunkedWriteHandler to be able to write ChunkInput
        pipeline.addLast(HandlerConstants.CHUNK_HANDLER, new ChunkedWriteHandler());
        pipeline.addLast(HandlerConstants.TIMEOUT_HANDLER, new TimeoutHandler(timeout));
        
        ChannelHandler coder = createCoder();
        if(coder!=null)
        	pipeline.addLast(HandlerConstants.REQUEST_DECODER,coder);
        
        pipeline.addLast(HandlerConstants.CORE_HANDLER, createHandler());
    }



    protected abstract ChannelHandler createCoder();

    
    /**
     * Create the core {@link ChannelUpstreamHandler} to use
     * 
     * @return coreHandeler
     * @throws KnownError 
     */
    protected abstract ConnectionSession createHandler() throws KnownError;

    
    /*
     * (non-Javadoc)
     * @see org.jboss.netty.util.ExternalResourceReleasable#releaseExternalResources()
     */
    public void releaseExternalResources() {
        timer.stop();
    }
    
    public abstract String serviceName();
}